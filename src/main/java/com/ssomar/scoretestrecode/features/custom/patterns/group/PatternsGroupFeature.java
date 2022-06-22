package com.ssomar.scoretestrecode.features.custom.patterns.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.FeaturesGroup;
import com.ssomar.scoretestrecode.features.custom.patterns.subgroup.PatternFeature;
import com.ssomar.scoretestrecode.features.custom.patterns.subpattern.SubPatternFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class PatternsGroupFeature extends FeatureWithHisOwnEditor<PatternsGroupFeature, PatternsGroupFeature, PatternsGroupFeatureEditor, PatternsGroupFeatureEditorManager> implements FeaturesGroup<SubPatternFeature> {

    private Map<String, PatternFeature> patterns;

    public PatternsGroupFeature(FeatureParentInterface parent) {
        super(parent, "patterns", "Patterns", new String[]{"&7&oThe patterns"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.patterns = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if(config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for(String attributeID : enchantmentsSection.getKeys(false)) {
                PatternFeature attribute = new PatternFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                patterns.put(attributeID, attribute);
            }
        }
        return error;
    }

    public List<Pattern> getMCPatterns(){
        List<Pattern> patterns = new ArrayList<>();
        for(PatternFeature pattern : this.patterns.values()){
            patterns.add(pattern.getPattern());
        }
        return patterns;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for(String enchantmentID : patterns.keySet()) {
            patterns.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public PatternsGroupFeature getValue() {
        return this;
    }

    @Override
    public PatternsGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length -2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length -1] = "&7&oPattern(s) added: &e"+ patterns.size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public PatternsGroupFeature clone() {
        PatternsGroupFeature eF = new PatternsGroupFeature(getParent());
        eF.setPatterns(new HashMap<>(patterns));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(patterns.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if(section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        }
        else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof PatternsGroupFeature) {
                PatternsGroupFeature eF = (PatternsGroupFeature) feature;
                eF.setPatterns(patterns);
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
        PatternsGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "pattern";
        for(int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if(!patterns.containsKey(id)) {
                PatternFeature eF = new PatternFeature(this, id);
                patterns.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, SubPatternFeature feature) {
        patterns.remove(feature.getId());
    }

}
