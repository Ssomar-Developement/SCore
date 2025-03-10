package com.ssomar.score.features.custom.toolrules.toolrule;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.features.types.list.ListMaterialFeature;
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
public class ToolRuleFeature extends FeatureWithHisOwnEditor<ToolRuleFeature, ToolRuleFeature, GenericFeatureParentEditor, GenericFeatureParentEditorManager> {

    private ListMaterialFeature materials;
    private DoubleFeature miningSpeed;
    private BooleanFeature correctForDrops;
    private String id;

    public ToolRuleFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.toolRule);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.miningSpeed = new DoubleFeature(this, Optional.of(1.0), FeatureSettingsSCore.miningSpeed);
        this.correctForDrops = new BooleanFeature(this, false, FeatureSettingsSCore.correctForDrops);
        this.materials = new ListMaterialFeature(this, new ArrayList<>(), FeatureSettingsSCore.materials);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.miningSpeed.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.correctForDrops.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.materials.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Tool rule with its options because there is not a section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
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
        this.miningSpeed.save(attributeConfig);
        this.correctForDrops.save(attributeConfig);
        this.materials.save(attributeConfig);
    }

    @Override
    public ToolRuleFeature getValue() {
        return this;
    }

    @Override
    public ToolRuleFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 5];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 5] = "&7&oSpeed: &e" + miningSpeed.getValue().get();
        finalDescription[finalDescription.length - 4] = "&7&oCorrectForDrops: &e" + correctForDrops.getValue();
        finalDescription[finalDescription.length - 3] = "&7&oMaterials: &e" + materials.getCurrentValues().toString();
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = GUI.SHIFT_CLICK_TO_REMOVE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ToolRuleFeature clone(FeatureParentInterface newParent) {
        ToolRuleFeature eF = new ToolRuleFeature(newParent, id);
        eF.setMiningSpeed(miningSpeed.clone(eF));
        eF.setCorrectForDrops(correctForDrops.clone(eF));
        eF.setMaterials(materials.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(materials, miningSpeed, correctForDrops));
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof ToolRuleFeature) {
                ToolRuleFeature aFOF = (ToolRuleFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setMiningSpeed(miningSpeed);
                    aFOF.setCorrectForDrops(correctForDrops);
                    aFOF.setMaterials(materials);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

}
