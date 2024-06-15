package com.ssomar.score.features.custom.required.experience;

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
public class RequiredExperience extends FeatureWithHisOwnEditor<RequiredExperience, RequiredExperience, RequiredExperienceEditor, RequiredExperienceEditorManager> implements RequiredPlayerInterface {

    private IntegerFeature experience;
    private ColoredStringFeature errorMessage;
    private BooleanFeature cancelEventIfError;

    public RequiredExperience(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.requiredExperience);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            if (!isPremiumLoading) {
                error.add("&cERROR, Couldn't load the Required Experience value of " + this.getName() + " from config, &7&o" + getParent().getParentInfo() + " &6>> Because it's a premium feature !");
                return error;
            }
            experience.load(plugin, section, isPremiumLoading);
            errorMessage.load(plugin, section, isPremiumLoading);
            cancelEventIfError.load(plugin, section, isPremiumLoading);
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (experience.getValue().isPresent() && experience.getValue().get() > 0) {
            ConfigurationSection requiredLevelSection = config.createSection(this.getName());
            experience.save(requiredLevelSection);
            errorMessage.save(requiredLevelSection);
            cancelEventIfError.save(requiredLevelSection);
        }
    }

    @Override
    public boolean verify(Player player, Event event) {
        if (experience.getValue().isPresent() && experience.getValue().get() > 0) {
            int actualExperience = getExpAtLevel(player.getLevel()) + (int) (player.getExpToLevel()*player.getExp());
            //SsomarDev.testMsg("actualExperience: "+actualExperience+ " exptolevel>> "+player.getExpToLevel()+" exp>> "+player.getExp(), true);
            if (actualExperience < experience.getValue().get()) {
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

    // Calculate amount of EXP needed to level up
    public int getExpToLevelUp(int level){
        if(level <= 15){
            return 2*level+7;
        } else if(level <= 30){
            return 5*level-38;
        } else {
            return 9*level-158;
        }
    }
    // Calculate total experience up to a level
    public int getExpAtLevel(int level) {
        if (level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }
    }

    @Override
    public void take(Player player) {
        if (experience.getValue().isPresent() && experience.getValue().get() > 0) {
            int newAmount = getExpAtLevel(player.getLevel()) + (int)(player.getExpToLevel()*player.getExp());
            newAmount = newAmount - experience.getValue().get();

            int level = 0;
            for (int i = 0; i < 10000; i++) {
                if(getExpAtLevel(i) > newAmount) {
                    break;
                }
                level = i;
            }
            player.setLevel(level);
            float exp = (float) (newAmount - getExpAtLevel(level)) / (float) getExpToLevelUp(level);
            //SsomarDev.testMsg("exp: "+exp, true);
            player.setExp(exp);
        }
    }

    @Override
    public RequiredExperience getValue() {
        return this;
    }

    @Override
    public RequiredExperience initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oRequired experience: &e" + experience.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RequiredExperience clone(FeatureParentInterface parent) {
        RequiredExperience requiredLevel = new RequiredExperience(parent);
        requiredLevel.setExperience(experience.clone(requiredLevel));
        requiredLevel.setErrorMessage(errorMessage.clone(requiredLevel));
        requiredLevel.setCancelEventIfError(cancelEventIfError.clone(requiredLevel));
        return requiredLevel;
    }

    @Override
    public void reset() {
        this.experience = new IntegerFeature(getParent(), Optional.of(0), FeatureSettingsSCore.requiredExperience);
        this.errorMessage = new ColoredStringFeature(getParent(), Optional.of("&4&l>> &cError you don't have the required experience"), FeatureSettingsSCore.errorMessage, true);
        this.cancelEventIfError = new BooleanFeature(getParent(), false, FeatureSettingsSCore.cancelEventIfNotValid, true);
    }

    @Override
    public void openEditor(Player player) {
        if (!isPremium() && this.isRequirePremium()) return;
        RequiredExperienceEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return Arrays.asList(experience, errorMessage, cancelEventIfError);
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
            if (feature instanceof RequiredExperience) {
                RequiredExperience requiredLevel = (RequiredExperience) feature;
                requiredLevel.setExperience(experience);
                requiredLevel.setErrorMessage(errorMessage);
                requiredLevel.setCancelEventIfError(cancelEventIfError);
                break;
            }
        }
    }
}
