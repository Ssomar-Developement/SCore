package com.ssomar.score.features.custom.armortrim;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.TrimMaterialFeature;
import com.ssomar.score.features.types.TrimPatternFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ArmorTrim extends FeatureWithHisOwnEditor<ArmorTrim, ArmorTrim, ArmorTrimEditor, ArmorTrimEditorManager> {

    private BooleanFeature enableArmorTrim;
    private TrimMaterialFeature trimMaterial;
    private TrimPatternFeature pattern;

    public ArmorTrim(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.armorTrim);
        reset();
    }

    @Override
    public void reset() {
        this.enableArmorTrim = new BooleanFeature(getParent(), false, FeatureSettingsSCore.enableArmorTrim, false);
        if(SCore.is1v20Plus()){
            this.trimMaterial = new TrimMaterialFeature(getParent(), Optional.of(TrimMaterial.DIAMOND), FeatureSettingsSCore.trimMaterial);
            this.pattern = new TrimPatternFeature(getParent(), Optional.of(TrimPattern.EYE), FeatureSettingsSCore.pattern);
        }
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection section = config.getConfigurationSection(this.getName());
            enableArmorTrim.load(plugin, section, isPremiumLoading);
            trimMaterial.load(plugin, section, isPremiumLoading);
            pattern.load(plugin, section, isPremiumLoading);
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        enableArmorTrim.save(section);
        trimMaterial.save(section);
        pattern.save(section);
    }

    @Override
    public ArmorTrim getValue() {
        return this;
    }

    public org.bukkit.inventory.meta.trim.ArmorTrim getArmorTrim(){
        org.bukkit.inventory.meta.trim.ArmorTrim armorTrim = new org.bukkit.inventory.meta.trim.ArmorTrim(this.getTrimMaterial().getValue().get(), this.getPattern().getValue().get());
        return armorTrim;
    }

    @Override
    public ArmorTrim initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;

        if (enableArmorTrim.getValue())
            finalDescription[finalDescription.length - 3] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Enabled: &c&l✘";

        finalDescription[finalDescription.length - 2] = "&7TrimMaterial: &e" + trimMaterial.getStringValue(trimMaterial.getValue().get());

        finalDescription[finalDescription.length - 1] = "&7TrimPattern: &e"+pattern.getStringValue(pattern.getValue().get());

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ArmorTrim clone(FeatureParentInterface newParent) {
        ArmorTrim dropFeatures = new ArmorTrim(newParent);
        dropFeatures.enableArmorTrim = enableArmorTrim.clone(dropFeatures);
        if(SCore.is1v20Plus()){
            dropFeatures.trimMaterial = trimMaterial.clone(dropFeatures);
            dropFeatures.pattern = pattern.clone(dropFeatures);
        }
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(enableArmorTrim);
        features.add(trimMaterial);
        features.add(pattern);
        return features;
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
            if (feature instanceof ArmorTrim) {
                ArmorTrim hiders = (ArmorTrim) feature;
                hiders.setEnableArmorTrim(enableArmorTrim);
                hiders.setTrimMaterial(trimMaterial);
                hiders.setPattern(pattern);
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        ArmorTrimEditorManager.getInstance().startEditing(player, this);
    }

}
