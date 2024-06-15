package com.ssomar.score.features.custom.ifhas.executableitems.attribute;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.detailedslots.DetailedSlots;
import com.ssomar.score.features.types.ExecutableItemFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class HasExecutableItemFeature extends FeatureWithHisOwnEditor<HasExecutableItemFeature, HasExecutableItemFeature, HasExecutableItemFeatureEditor, HasExecutableItemFeatureEditorManager> {

    private ExecutableItemFeature executableItem;
    private IntegerFeature amount;
    private DetailedSlots detailedSlots;
    private NumberConditionFeature usageCondition;
    private String id;

    public HasExecutableItemFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.hasExecutableItem);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.executableItem = new ExecutableItemFeature(this, FeatureSettingsSCore.executableItem);
        this.amount = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.amount);
        this.detailedSlots = new DetailedSlots(this);
        this.usageCondition = new NumberConditionFeature(this, FeatureSettingsSCore.usageConditions);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.executableItem.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.amount.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.detailedSlots.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.usageCondition.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the HasExecutableItem feature because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.executableItem.save(attributeConfig);
        this.amount.save(attributeConfig);
        this.detailedSlots.save(attributeConfig);
        this.usageCondition.save(attributeConfig);
    }

    @Override
    public HasExecutableItemFeature getValue() {
        return this;
    }

    @Override
    public HasExecutableItemFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (executableItem.getValue().isPresent())
            finalDescription[finalDescription.length - 5] = "&7ExecutableItem: &e" + executableItem.getValue().get().getId();
        else
            finalDescription[finalDescription.length - 5] = "&7ExecutableItem: &cINVALID EI";
        finalDescription[finalDescription.length - 4] = "&7Amount: &e" + amount.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Slots count: &c" + detailedSlots.getSlots().size();
        if (usageCondition.getValue().isPresent())
            finalDescription[finalDescription.length - 2] = "&7Usage Condition: &e" + usageCondition.getValue().get();
        else
            finalDescription[finalDescription.length - 2] = "&7Usage Condition: &cNO CONDITION";
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public HasExecutableItemFeature clone(FeatureParentInterface newParent) {
        HasExecutableItemFeature eF = new HasExecutableItemFeature(newParent, id);
        eF.setExecutableItem(executableItem.clone(eF));
        eF.setAmount(amount.clone(eF));
        eF.setDetailedSlots(detailedSlots.clone(eF));
        eF.setUsageCondition(usageCondition.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(executableItem, amount, detailedSlots, usageCondition));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection parentSection = getParent().getConfigurationSection();
        if (parentSection.isConfigurationSection(getId())) {
            return parentSection.getConfigurationSection(getId());
        } else return parentSection.createSection(getId());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof HasExecutableItemFeature) {
                HasExecutableItemFeature aFOF = (HasExecutableItemFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setExecutableItem(executableItem);
                    aFOF.setAmount(amount);
                    aFOF.setDetailedSlots(detailedSlots);
                    aFOF.setUsageCondition(usageCondition);
                    break;
                }
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        HasExecutableItemFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
