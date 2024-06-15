package com.ssomar.score.features.custom.required.parent;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.required.RequiredPlayerInterface;
import com.ssomar.score.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeature;
import com.ssomar.score.features.custom.required.experience.RequiredExperience;
import com.ssomar.score.features.custom.required.items.group.RequiredItemGroupFeature;
import com.ssomar.score.features.custom.required.level.RequiredLevel;
import com.ssomar.score.features.custom.required.magic.group.RequiredMagicGroupFeature;
import com.ssomar.score.features.custom.required.mana.RequiredMana;
import com.ssomar.score.features.custom.required.money.RequiredMoney;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class RequiredGroup extends FeatureWithHisOwnEditor<RequiredGroup, RequiredGroup, RequiredGroupEditor, RequiredGroupEditorManager> implements RequiredPlayerInterface {

    private static final Boolean DEBUG = false;
    private RequiredLevel requiredLevel;
    private RequiredExperience requiredExperience;
    private RequiredMoney requiredMoney;
    private RequiredItemGroupFeature requiredItems;
    private RequiredExecutableItemGroupFeature requiredExecutableItems;
    private RequiredMana requiredMana;
    private RequiredMagicGroupFeature requiredMagics;

    public RequiredGroup(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.requiredGroups);
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        error.addAll(requiredLevel.load(plugin, config, isPremiumLoading));
        error.addAll(requiredExperience.load(plugin, config, isPremiumLoading));
        error.addAll(requiredMoney.load(plugin, config, isPremiumLoading));
        error.addAll(requiredItems.load(plugin, config, isPremiumLoading));
        error.addAll(requiredExecutableItems.load(plugin, config, isPremiumLoading));
        error.addAll(requiredMana.load(plugin, config, isPremiumLoading));
        error.addAll(requiredMagics.load(plugin, config, isPremiumLoading));
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        requiredLevel.save(config);
        requiredExperience.save(config);
        requiredMoney.save(config);
        requiredItems.save(config);
        requiredExecutableItems.save(config);
        requiredMana.save(config);
        requiredMagics.save(config);
    }

    @Override
    public boolean verify(Player player, Event event) {
        if (!requiredLevel.verify(player, event)) {
            SsomarDev.testMsg("Invalid level", DEBUG);
            return false;
        }
        if (!requiredExperience.verify(player, event)) {
            SsomarDev.testMsg("Invalid experience", DEBUG);
            return false;
        }
        if (!requiredMoney.verify(player, event)) {
            SsomarDev.testMsg("Invalid money", DEBUG);
            return false;
        }
        if (!requiredItems.verify(player, event)) {
            SsomarDev.testMsg("Invalid items", DEBUG);
            return false;
        }
        if (!requiredExecutableItems.verify(player, event)) {
            SsomarDev.testMsg("Invalid executable items", DEBUG);
            return false;
        }
        if (!requiredMana.verify(player, event)) {
            SsomarDev.testMsg("Invalid mana", DEBUG);
            return false;
        }
        if (!requiredMagics.verify(player, event)) {
            SsomarDev.testMsg("Invalid magics", DEBUG);
            return false;
        }

        return true;
    }

    @Override
    public void take(Player player) {
        SsomarDev.testMsg("Taking required things", DEBUG);
        requiredLevel.take(player);
        requiredExperience.take(player);
        requiredMoney.take(player);
        requiredItems.take(player);
        requiredExecutableItems.take(player);
        requiredMana.take(player);
        requiredMagics.take(player);
    }

    @Override
    public RequiredGroup getValue() {
        return this;
    }

    @Override
    public RequiredGroup initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 8];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 8] = GUI.CLICK_HERE_TO_CHANGE;
        if (requiredLevel.getValue().getLevel().getValue().get() > 0)
            finalDescription[finalDescription.length - 7] = "&7Required level: &a&l✔";
        else
            finalDescription[finalDescription.length - 7] = "&7Required level: &c&l✘";

        if (requiredExperience.getValue().getExperience().getValue().get() > 0)
            finalDescription[finalDescription.length - 6] = "&7Required experience: &a&l✔";
        else
            finalDescription[finalDescription.length - 6] = "&7Required experience: &c&l✘";

        if (requiredMoney.getValue().getMoney().getValue().get() > 0)
            finalDescription[finalDescription.length - 5] = "&7Required money: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7Required money: &c&l✘";

        if (requiredItems.getValue().getRequiredItems().size() > 0)
            finalDescription[finalDescription.length - 4] = "&7Required items: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Required items: &c&l✘";

        if (requiredExecutableItems.getValue().getRequiredExecutableItems().size() > 0)
            finalDescription[finalDescription.length - 3] = "&7Required executable items: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Required executable items: &c&l✘";

        if (requiredMana.getValue().getMana().getValue().get() > 0)
            finalDescription[finalDescription.length - 2] = "&7Required mana: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Required mana: &c&l✘";

        if (requiredMagics.getValue().getRequiredMagics().size() > 0)
            finalDescription[finalDescription.length - 1] = "&7Required magics: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Required magics: &c&l✘";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }


    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RequiredGroup clone(FeatureParentInterface newParent) {
        RequiredGroup requiredGroup = new RequiredGroup(newParent);
        requiredGroup.setRequiredLevel(getRequiredLevel().clone(requiredGroup));
        requiredGroup.setRequiredExperience(getRequiredExperience().clone(requiredGroup));
        requiredGroup.setRequiredMoney(getRequiredMoney().clone(requiredGroup));
        requiredGroup.setRequiredItems(getRequiredItems().clone(requiredGroup));
        requiredGroup.setRequiredExecutableItems(getRequiredExecutableItems().clone(requiredGroup));
        requiredGroup.setRequiredMana(getRequiredMana().clone(requiredGroup));
        requiredGroup.setRequiredMagics(getRequiredMagics().clone(requiredGroup));
        return requiredGroup;
    }

    @Override
    public void reset() {
        this.requiredLevel = new RequiredLevel(this);
        this.requiredExperience = new RequiredExperience(this);
        this.requiredMoney = new RequiredMoney(this);
        this.requiredItems = new RequiredItemGroupFeature(this);
        this.requiredExecutableItems = new RequiredExecutableItemGroupFeature(this);
        this.requiredMana = new RequiredMana(this);
        this.requiredMagics = new RequiredMagicGroupFeature(this);
    }

    @Override
    public void openEditor(Player player) {
        RequiredGroupEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return Arrays.asList(requiredLevel, requiredExperience, requiredMoney, requiredItems, requiredExecutableItems, requiredMana, requiredMagics);
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
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof RequiredGroup) {
                RequiredGroup requiredgroup = (RequiredGroup) feature;
                requiredgroup.setRequiredLevel(requiredLevel);
                requiredgroup.setRequiredExperience(requiredExperience);
                requiredgroup.setRequiredMoney(requiredMoney);
                requiredgroup.setRequiredItems(requiredItems);
                requiredgroup.setRequiredExecutableItems(requiredExecutableItems);
                requiredgroup.setRequiredMana(requiredMana);
                requiredgroup.setRequiredMagics(requiredMagics);
                break;
            }
        }
    }
}
