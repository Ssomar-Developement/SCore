package com.ssomar.score.features.custom.required.level;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.required.RequiredPlayerInterface;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RequiredLevel extends FeatureWithHisOwnEditor<RequiredLevel, RequiredLevel, RequiredLevelEditor, RequiredLevelEditorManager> implements RequiredPlayerInterface {

    private IntegerFeature level;
    private ColoredStringFeature errorMessage;
    private BooleanFeature cancelEventIfError;

    public RequiredLevel(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.requiredLevel);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            if (!isPremiumLoading) {
                error.add("&cERROR, Couldn't load the Required Level value of " + this.getName() + " from config, &7&o" + getParent().getParentInfo() + " &6>> Because it's a premium feature !");
                return error;
            }
            level.load(plugin, section, isPremiumLoading);
            errorMessage.load(plugin, section, isPremiumLoading);
            cancelEventIfError.load(plugin, section, isPremiumLoading);
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (level.getValue().isPresent() && level.getValue().get() > 0) {
            ConfigurationSection requiredLevelSection = config.createSection(this.getName());
            level.save(requiredLevelSection);
            errorMessage.save(requiredLevelSection);
            cancelEventIfError.save(requiredLevelSection);
        }
    }

    @Override
    public boolean verify(Player player, Event event) {
        if (level.getValue().isPresent() && level.getValue().get() > 0) {
            if (player.getLevel() < level.getValue().get()) {
                if (errorMessage.getValue().isPresent()) {
                    SendMessage.sendMessageNoPlch(player, errorMessage.getValue().get());
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
        if (level.getValue().isPresent() && level.getValue().get() > 0)
            player.setLevel(player.getLevel() - level.getValue().get());
    }

    @Override
    public RequiredLevel getValue() {
        return this;
    }

    @Override
    public RequiredLevel initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oRequired level: &e" + level.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RequiredLevel clone(FeatureParentInterface parent) {
        RequiredLevel requiredLevel = new RequiredLevel(parent);
        requiredLevel.setLevel(level.clone(requiredLevel));
        requiredLevel.setErrorMessage(errorMessage.clone(requiredLevel));
        requiredLevel.setCancelEventIfError(cancelEventIfError.clone(requiredLevel));
        return requiredLevel;
    }

    @Override
    public void reset() {
        this.level = new IntegerFeature(getParent(), Optional.of(0), FeatureSettingsSCore.requiredLevel);
        this.errorMessage = new ColoredStringFeature(getParent(), Optional.of("&4&l>> &cError you don't have the required levels"), FeatureSettingsSCore.errorMessage, true);
        this.cancelEventIfError = new BooleanFeature(getParent(), false, FeatureSettingsSCore.cancelEventIfError, true);
    }

    @Override
    public void openEditor(Player player) {
        if (!isPremium() && this.isRequirePremium()) return;
        RequiredLevelEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return Arrays.asList(level, errorMessage, cancelEventIfError);
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo()+ ".("+getName()+")";
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        } else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof RequiredLevel) {
                RequiredLevel requiredLevel = (RequiredLevel) feature;
                requiredLevel.setLevel(level);
                requiredLevel.setErrorMessage(errorMessage);
                requiredLevel.setCancelEventIfError(cancelEventIfError);
                break;
            }
        }
    }
}
