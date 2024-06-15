package com.ssomar.score.features.custom.patterns.subgroup;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.patterns.subpattern.SubPatternFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class PatternFeature extends FeatureWithHisOwnEditor<PatternFeature, PatternFeature, PatternFeatureEditor, PatternFeatureEditorManager> implements FeaturesGroup<SubPatternFeature> {

    private Map<String, SubPatternFeature> subPattern;
    private String id;

    public PatternFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.subPatterns);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.subPattern = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(id);
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                SubPatternFeature attribute = new SubPatternFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                subPattern.put(attributeID, attribute);
            }
        }
        return error;
    }

    public void load(SPlugin plugin, Pattern pattern, boolean isPremiumLoading) {
        Map<String, Object> map = pattern.serialize();
        int i = 0;
        for (String key : map.keySet()) {
            SubPatternFeature subPattern = new SubPatternFeature(this, "subPattern" + i);
            subPattern.getString().setValue(Optional.of(key));
            subPattern.getObject().setValue(map.get(key));
            this.subPattern.put("subPattern" + i, subPattern);
            i++;
        }

    }

    public Pattern getPattern() {
        HashMap<String, Object> map = new HashMap<>();
        for (SubPatternFeature sP : subPattern.values()) {
            map.put(sP.getString().getValue().get(), sP.getObject().getValue());
        }
        return new Pattern(map);
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributesSection = config.createSection(id);
        for (String enchantmentID : subPattern.keySet()) {
            subPattern.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public PatternFeature getValue() {
        return this;
    }

    @Override
    public PatternFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oSub Pattern(s) added: &e" + subPattern.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public SubPatternFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (SubPatternFeature x : subPattern.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public PatternFeature clone(FeatureParentInterface newParent) {
        PatternFeature eF = new PatternFeature(newParent, id);
        HashMap<String, SubPatternFeature> newSubPattern = new HashMap<>();
        for (String key : subPattern.keySet()) {
            newSubPattern.put(key, subPattern.get(key).clone(eF));
        }
        eF.setSubPattern(newSubPattern);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(subPattern.values());
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
            if (feature instanceof PatternFeature) {
                PatternFeature eF = (PatternFeature) feature;
                eF.setSubPattern(this.getSubPattern());
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
        PatternFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "subPattern";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!subPattern.containsKey(id)) {
                SubPatternFeature eF = new SubPatternFeature(this, id);
                subPattern.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, SubPatternFeature feature) {
        subPattern.remove(feature.getId());
    }

}
