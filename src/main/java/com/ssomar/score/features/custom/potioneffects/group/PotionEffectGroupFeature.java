package com.ssomar.score.features.custom.potioneffects.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.potioneffects.potioneffect.PotionEffectFeature;
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
public class PotionEffectGroupFeature extends FeatureWithHisOwnEditor<PotionEffectGroupFeature, PotionEffectGroupFeature, PotionEffectGroupFeatureEditor, PotionEffectGroupFeatureEditorManager> implements FeaturesGroup<PotionEffectFeature> {

    private Map<String, PotionEffectFeature> effects;
    private boolean notSaveIfNoValue;

    public PotionEffectGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, FeatureSettingsSCore.potionEffects);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.effects = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                PotionEffectFeature attribute = new PotionEffectFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                effects.put(attributeID, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && effects.size() == 0) return;
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : effects.keySet()) {
            effects.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public PotionEffectGroupFeature getValue() {
        return this;
    }

    @Override
    public PotionEffectGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oAttribute(s) added: &e" + effects.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public PotionEffectFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (PotionEffectFeature x : effects.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public PotionEffectGroupFeature clone(FeatureParentInterface newParent) {
        PotionEffectGroupFeature eF = new PotionEffectGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, PotionEffectFeature> newEffects = new HashMap<>();
        for (String key : effects.keySet()) {
            newEffects.put(key, effects.get(key).clone(eF));
        }
        eF.setEffects(newEffects);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(effects.values());
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
            if (feature instanceof PotionEffectGroupFeature) {
                PotionEffectGroupFeature eF = (PotionEffectGroupFeature) feature;
                eF.setEffects(this.getEffects());
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
        PotionEffectGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "pEffect";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!effects.containsKey(id)) {
                PotionEffectFeature eF = new PotionEffectFeature(this, id);
                effects.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, PotionEffectFeature feature) {
        effects.remove(feature.getId());
    }
}
