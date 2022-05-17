package com.ssomar.testRecode.features.required.level;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.required.RequiredPlayerInterface;
import com.ssomar.testRecode.features.types.BooleanFeature;
import com.ssomar.testRecode.features.types.ColoredStringFeature;
import com.ssomar.testRecode.menu.NewGUIManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.menu.GUI.WRITABLE_BOOK;

@Getter@Setter
public class RequiredLevel extends FeatureAbstract<RequiredLevel, RequiredLevel> implements RequiredPlayerInterface, FeatureWithHisOwnEditor<RequireLevelGUI, RequireLevelGUIManager>, FeatureParentInterface {

    private Optional<Integer> level;
    private ColoredStringFeature errorMessage;
    private BooleanFeature cancelEventIfError;

    public RequiredLevel(FeatureParentInterface parent) {
        super(parent, "requiredLevel", "Required Level", new String[]{"&7&oRequired level"}, Material.ANVIL);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.contains("requiredLevel")) {
            if (!isPremiumLoading) {
                error.add(plugin.getNameDesign()+" " + getParent().getParentInfo() + " REQUIRE PREMIUM: required Level is only in the premium version");
            } else {
                int levelConfig = config.getInt("requiredLevel", -1);
                if (levelConfig > 0) level = Optional.of(levelConfig);
            }
            errorMessage.load(plugin, config, isPremiumLoading);
            cancelEventIfError.load(plugin, config, isPremiumLoading);
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {

    }

    @Override
    public boolean verify(Player player, Event event) {
        if (level.isPresent()) {
            if (player.getLevel() < level.get()) {
                if (errorMessage.getValue().isPresent()) {
                    player.sendMessage(errorMessage.getValue().get());
                }
                if (cancelEventIfError.getValue() && event instanceof Cancellable) {
                    ((Cancellable) event).setCancelled(true);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void take(Player player) {
       if(level.isPresent()) player.setLevel(player.getLevel() - level.get());
    }

    @Override
    public RequiredLevel getValue() {
        return this;
    }

    @Override
    public RequiredLevel initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return false;
    }


    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }




    @Override
    public RequiredLevel clone() {
        RequiredLevel requiredLevel = new RequiredLevel(getParent());
        requiredLevel.setLevel(level);
        requiredLevel.setErrorMessage(errorMessage);
        requiredLevel.setCancelEventIfError(cancelEventIfError.clone());
        return requiredLevel;
    }

    @Override
    public void reset() {
        this.level = Optional.empty();
        this.errorMessage = new ColoredStringFeature(getParent(), "requiredLevelMsg", "&cRequired level error message", new String[]{"&7&oEdit the error message"}, WRITABLE_BOOK);
        this.cancelEventIfError = new BooleanFeature(getParent(), "cancelEventIfInvalidRequiredLevel", false, "cancelEventIfInvalidRequiredLevel", new String[]{"&7&oCancel the vanilla event"}, Material.LEVER);

    }

    @Override
    public void openEditor(Player player) {
        RequireLevelGUIManager.getInstance().startEditing(player, this);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return Arrays.asList(errorMessage, cancelEventIfError);
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return getParent().getConfigurationSection();
    }

    @Override
    public void reload() {
        getParent().reload();
    }
}
