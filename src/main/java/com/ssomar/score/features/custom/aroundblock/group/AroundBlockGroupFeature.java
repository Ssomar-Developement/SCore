package com.ssomar.score.features.custom.aroundblock.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.aroundblock.aroundblock.AroundBlockFeature;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AroundBlockGroupFeature extends FeatureWithHisOwnEditor<AroundBlockGroupFeature, AroundBlockGroupFeature, AroundBlockGroupFeatureEditor, AroundBlockGroupFeatureEditorManager> implements FeaturesGroup<AroundBlockFeature> {

    private Map<String, AroundBlockFeature> aroundBlockGroup;
    private boolean notSaveIfNoValue;

    public AroundBlockGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, FeatureSettingsSCore.aroundBlockCdts);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.aroundBlockGroup = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                AroundBlockFeature attribute = new AroundBlockFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                aroundBlockGroup.put(attributeID, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && aroundBlockGroup.size() == 0) return;
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : aroundBlockGroup.keySet()) {
            aroundBlockGroup.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public AroundBlockGroupFeature getValue() {
        return this;
    }

    @Override
    public AroundBlockGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = TM.g(Text.FEATURES_AROUNDBLOCKS_PARENTDESCRIPTION_BLOCKAROUNDADDED) + aroundBlockGroup.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public AroundBlockFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (AroundBlockFeature x : aroundBlockGroup.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public AroundBlockGroupFeature clone(FeatureParentInterface newParent) {
        AroundBlockGroupFeature eF = new AroundBlockGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, AroundBlockFeature> newAroundBlockGroup = new HashMap<>();
        for (String x : aroundBlockGroup.keySet()) {
            newAroundBlockGroup.put(x, aroundBlockGroup.get(x).clone(eF));
        }
        eF.setAroundBlockGroup(newAroundBlockGroup);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(aroundBlockGroup.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
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
            if (feature instanceof AroundBlockGroupFeature) {
                AroundBlockGroupFeature eF = (AroundBlockGroupFeature) feature;
                eF.setAroundBlockGroup(this.getAroundBlockGroup());
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
        AroundBlockGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "blockAround";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!aroundBlockGroup.containsKey(id)) {
                AroundBlockFeature eF = new AroundBlockFeature(this, id);
                aroundBlockGroup.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, AroundBlockFeature feature) {
        aroundBlockGroup.remove(feature.getId());
    }

}
