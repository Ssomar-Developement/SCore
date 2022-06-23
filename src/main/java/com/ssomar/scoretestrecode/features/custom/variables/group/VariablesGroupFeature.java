package com.ssomar.scoretestrecode.features.custom.variables.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.FeaturesGroup;
import com.ssomar.scoretestrecode.features.custom.variables.variable.VariableFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class VariablesGroupFeature extends FeatureWithHisOwnEditor<VariablesGroupFeature, VariablesGroupFeature, VariablesGroupFeatureEditor, VariablesGroupFeatureEditorManager> implements FeaturesGroup<VariableFeature> {

    private Map<String, VariableFeature> attributes;

    public VariablesGroupFeature(FeatureParentInterface parent) {
        super(parent, "variables", "Variables", new String[]{"&7&oThe variables"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.attributes = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if(config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for(String attributeID : enchantmentsSection.getKeys(false)) {
                VariableFeature attribute = new VariableFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                attributes.put(attributeID, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for(String enchantmentID : attributes.keySet()) {
            attributes.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public VariablesGroupFeature getValue() {
        return this;
    }

    @Override
    public VariablesGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length -2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length -1] = "&7&oVariable(s) added: &e"+ attributes.size();

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
    public VariablesGroupFeature clone() {
        VariablesGroupFeature eF = new VariablesGroupFeature(getParent());
        eF.setAttributes(new HashMap<>(this.getAttributes()));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(attributes.values());
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
            if(feature instanceof VariablesGroupFeature) {
                VariablesGroupFeature eF = (VariablesGroupFeature) feature;
                eF.setAttributes(this.getAttributes());
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
        VariablesGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "var";
        for(int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if(!attributes.containsKey(id)) {
                VariableFeature eF = new VariableFeature(this, id);
                attributes.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, VariableFeature feature) {
        attributes.remove(feature.getId());
    }

}
