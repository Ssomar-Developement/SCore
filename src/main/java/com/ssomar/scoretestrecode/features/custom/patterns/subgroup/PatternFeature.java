package com.ssomar.scoretestrecode.features.custom.patterns.subgroup;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.FeaturesGroup;
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
public class PatternFeature extends FeatureWithHisOwnEditor<PatternFeature, PatternFeature, PatternFeatureEditor, PatternFeatureEditorManager> implements FeaturesGroup<SubPatternFeature> {

    private Map<String, SubPatternFeature> subPattern;
    private String id;

    public PatternFeature(FeatureParentInterface parent, String id) {
        super(parent, "subPatterns", "Sub Patterns", new String[]{"&7&oThe sub patterns"}, Material.ANVIL, false);
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
        if(config.isConfigurationSection(this.getName()+"."+id)) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for(String attributeID : enchantmentsSection.getKeys(false)) {
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

    public Pattern getPattern(){
        HashMap<String,Object> map = new HashMap<>();
        for(SubPatternFeature sP : subPattern.values()){
            map.put(sP.getString().getValue().get(), sP.getObject().getValue());
        }
        Pattern pattern = new Pattern(map);
        return pattern;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName()+"."+id, null);
        ConfigurationSection attributesSection = config.createSection(this.getName()+"."+id);
        for(String enchantmentID : subPattern.keySet()) {
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
        finalDescription[finalDescription.length -2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length -1] = "&7&oSub Pattern(s) added: &e"+ subPattern.size();

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
    public PatternFeature clone() {
        PatternFeature eF = new PatternFeature(getParent(), id);
        eF.setSubPattern(new HashMap<>(this.getSubPattern()));
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
            if(feature instanceof PatternFeature) {
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
        for(int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if(!subPattern.containsKey(id)) {
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
