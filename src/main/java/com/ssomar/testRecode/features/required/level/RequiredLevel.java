package com.ssomar.testRecode.features.required.level;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.types.BooleanFeature;
import com.ssomar.testRecode.menu.NewGUIManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter@Setter
public class RequiredLevel extends FeatureAbstract<RequiredLevel, RequiredLevel> {

    private Optional<Integer> level;
    private Optional<String> errorMessage;
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
            if (config.contains("requiredLevelMsg")) {
                String errorMessageConfig = config.getString("requiredLevelMsg", "");
                if (!errorMessageConfig.isEmpty()) errorMessage = Optional.of(errorMessageConfig);
            }
            if (config.contains("cancelEventIfInvalidRequiredLevel")) {
                cancelEventIfError.load(plugin, config, isPremiumLoading);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {

    }

    public boolean verify(Player player, Event event) {
        if (level.isPresent()) {
            if (player.getLevel() < level.get()) {
                if (errorMessage.isPresent()) {
                    player.sendMessage(errorMessage.get());
                }
                if (cancelEventIfError.getValue() && event instanceof Cancellable) {
                    ((Cancellable) event).setCancelled(true);
                }
                return false;
            }
        }
        return true;
    }

    public void takeLevel(Player player) {
       if(level.isPresent()) player.setLevel(player.getLevel() - level.get());
    }

    @Override
    public RequiredLevel getValue() {
        return this;
    }

    @Override
    public void initEditorItem(GUI gui, int slot) {

    }

    @Override
    public void clickEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public void extractInfoFromEditor(NewGUIManager manager, Player player) {

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
        this.errorMessage = Optional.empty();
        this.cancelEventIfError = new BooleanFeature(getParent(), "cancelEventIfInvalidRequiredLevel", false, "cancelEventIfInvalidRequiredLevel", new String[]{"&7&oCancel the vanilla event"}, Material.LEVER);

    }
}
