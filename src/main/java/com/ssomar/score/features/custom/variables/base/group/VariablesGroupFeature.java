package com.ssomar.score.features.custom.variables.base.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.variables.base.variable.VariableFeature;
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
public class VariablesGroupFeature extends FeatureWithHisOwnEditor<VariablesGroupFeature, VariablesGroupFeature, VariablesGroupFeatureEditor, VariablesGroupFeatureEditorManager> implements FeaturesGroup<VariableFeature> {

    private Map<String, VariableFeature> variables;

    public VariablesGroupFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.variables);
        reset();
    }

    @Override
    public void reset() {
        this.variables = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                VariableFeature attribute = new VariableFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                variables.put(attributeID, attribute);
            }
        }
        return error;
    }

    public List<String> getVariablesName() {
        List<String> variablesName = new ArrayList<>();
        for (String variableName : variables.keySet()) {
            VariableFeature variable = variables.get(variableName);
            variablesName.add(variable.getVariableName().getValue().get());
        }
        return variablesName;
    }

    public VariableFeature getVariable(String variableName) {
        for (String var : variables.keySet()) {
            VariableFeature variable = variables.get(var);
            if (variable.getVariableName().getValue().get().equals(variableName)) {
                return variable;
            }
        }
        return null;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : variables.keySet()) {
            variables.get(enchantmentID).save(attributesSection);
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
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oVariable(s) added: &e" + variables.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public VariablesGroupFeature clone(FeatureParentInterface newParent) {
        VariablesGroupFeature eF = new VariablesGroupFeature(getParent());
        HashMap<String, VariableFeature> newVariables = new HashMap<>();
        for (String variableName : variables.keySet()) {
            VariableFeature variable = variables.get(variableName);
            newVariables.put(variableName, variable.clone(eF));
        }
        eF.setVariables(newVariables);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(variables.values());
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
            if (feature instanceof VariablesGroupFeature) {
                VariablesGroupFeature eF = (VariablesGroupFeature) feature;
                eF.setVariables(this.getVariables());
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
    public VariableFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (VariableFeature x : variables.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "var";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!variables.containsKey(id)) {
                VariableFeature eF = new VariableFeature(this, id);
                variables.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, VariableFeature feature) {
        variables.remove(feature.getId());
    }

}
