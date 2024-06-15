package com.ssomar.score.features.custom.ifhas.items.attribute;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.detailedslots.DetailedSlots;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.MaterialFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
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
public class HasItemFeature extends FeatureWithHisOwnEditor<HasItemFeature, HasItemFeature, HasItemFeatureEditor, HasItemFeatureEditorManager> {

    private MaterialFeature material;
    private IntegerFeature amount;
    private DetailedSlots detailedSlots;
    private String id;

    public HasItemFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.hasItem);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.material = new MaterialFeature(this, Optional.of(Material.STONE), FeatureSettingsSCore.material);
        this.amount = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.amount);
        this.detailedSlots = new DetailedSlots(this);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.material.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.amount.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.detailedSlots.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Item feature because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
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
        this.material.save(attributeConfig);
        this.amount.save(attributeConfig);
        this.detailedSlots.save(attributeConfig);
    }

    @Override
    public HasItemFeature getValue() {
        return this;
    }

    @Override
    public HasItemFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (material.getValue().isPresent())
            finalDescription[finalDescription.length - 4] = "&7Item: &e" + material.getValue().get();
        else
            finalDescription[finalDescription.length - 4] = "&7Item: &cINVALID EI";
        finalDescription[finalDescription.length - 3] = "&7Amount: &e" + amount.getValue().get();
        finalDescription[finalDescription.length - 2] = "&7Slots count: &e" + detailedSlots.getSlots().size();
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public HasItemFeature clone(FeatureParentInterface newParent) {
        HasItemFeature eF = new HasItemFeature(newParent, id);
        eF.setMaterial(material.clone(eF));
        eF.setAmount(amount.clone(eF));
        eF.setDetailedSlots(detailedSlots.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(material, amount, detailedSlots));
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
            if (feature instanceof HasItemFeature) {
                HasItemFeature aFOF = (HasItemFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setMaterial(material);
                    aFOF.setAmount(amount);
                    aFOF.setDetailedSlots(detailedSlots);
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
        HasItemFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
