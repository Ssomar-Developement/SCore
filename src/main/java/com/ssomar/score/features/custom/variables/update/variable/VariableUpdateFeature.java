package com.ssomar.score.features.custom.variables.update.variable;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.variables.base.group.VariablesGroupFeature;
import com.ssomar.score.features.custom.variables.base.variable.VariableFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.features.types.VariableUpdateTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.VariableType;
import com.ssomar.score.utils.emums.VariableUpdateType;
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
public class VariableUpdateFeature extends FeatureWithHisOwnEditor<VariableUpdateFeature, VariableUpdateFeature, VariableUpdateFeatureEditor, VariableUpdateFeatureEditorManager> {

    private final static boolean DEBUG = false;
    private UncoloredStringFeature variableName;
    private VariableUpdateTypeFeature type;
    private ColoredStringFeature stringUpdate;
    private DoubleFeature doubleUpdate;
    private String id;

    private VariablesGroupFeature features;

    public VariableUpdateFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.variableUpdate);
        this.id = id;
        reset();
    }

    /* For manual variable update */
    public VariableUpdateFeature(String id, VariablesGroupFeature features) {
        super(null, FeatureSettingsSCore.variableUpdate);
        this.id = id;
        this.features = features;
    }

    @Override
    public void reset() {
        this.variableName = new UncoloredStringFeature(this, Optional.of("var"), FeatureSettingsSCore.variableName, false);
        this.type = new VariableUpdateTypeFeature(this, Optional.of(VariableUpdateType.SET), FeatureSettingsSCore.type);
        this.stringUpdate = new ColoredStringFeature(this, Optional.of(""), FeatureSettingsSCore.modification_string, false);
        this.doubleUpdate = new DoubleFeature(this, Optional.of(0.0), FeatureSettingsSCore.modification_double);
    }

    public VariablesGroupFeature getVariables() {
        FeatureParentInterface parent = this.getParent();
        while (parent instanceof FeatureAbstract) {
            if (parent instanceof FeatureParentInterface) {
                for (FeatureInterface feature : parent.getFeatures()) {
                    if (feature instanceof VariablesGroupFeature) {
                        return (VariablesGroupFeature) feature;
                    }
                }
            }
            if (((FeatureAbstract) parent).getParent() == parent) break;
            else parent = ((FeatureAbstract) parent).getParent();
        }

        return null;
    }

    public Optional<VariableFeature> getAssociatedVariable() {
        VariablesGroupFeature variables = null;
        if(this.features != null) variables = this.features;
        else variables = getVariables();

        if (variables == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(variables.getVariable(this.variableName.getValue().get()));
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.variableName.load(plugin, enchantmentConfig, isPremiumLoading));

            if (DEBUG) {
                SsomarDev.testMsg("should load id " + id + " >> " + (getVariables().getVariablesName().contains(this.variableName.getValue().get())), DEBUG);
                for (String s : getVariables().getVariablesName()) {
                    SsomarDev.testMsg("EXIST <" + s + "> >> and we need <" + this.variableName.getValue().get() + ">", DEBUG);
                }
            }

            if (variableName.getValue().isPresent()) {
                if (getVariables().getVariablesName().contains(this.variableName.getValue().get())) {
                    errors.addAll(this.type.load(plugin, enchantmentConfig, isPremiumLoading));
                    VariableType variableType = getVariables().getVariable(this.variableName.getValue().get()).getType().getValue().get();
                    if (variableType.equals(VariableType.STRING) || variableType.equals(VariableType.LIST)) {
                        errors.addAll(this.stringUpdate.load(plugin, enchantmentConfig, isPremiumLoading));
                    } else {
                        errors.addAll(this.doubleUpdate.load(plugin, enchantmentConfig, isPremiumLoading));
                    }
                } else {
                    errors.add("&cERROR, Couldn't load the VariableUpdate with its options (ID: " + id + ") because the variable associated (" + this.variableName.getValue().get() + ") doesnt exist &7&o" + getParent().getParentInfo());
                }
            } else {
                errors.add("&cERROR, Couldn't load the VariableUpdate with its options (ID: " + id + ") because their is not variableName defined ! &7&o" + getParent().getParentInfo());
            }
        } else {
            errors.add("&cERROR, Couldn't load the VariableUpdate with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
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

        if (DEBUG) {
            SsomarDev.testMsg("should save id " + id + " >> " + (getVariables().getVariablesName().contains(this.variableName.getValue().get())), DEBUG);
            for (String s : getVariables().getVariablesName()) {
                SsomarDev.testMsg("EXIST <" + s + "> >> and we need <" + this.variableName.getValue().get() + ">", DEBUG);
            }
        }

        if (getVariables().getVariablesName().contains(this.variableName.getValue().get())) {
            VariableType variableType = getVariables().getVariable(this.variableName.getValue().get()).getType().getValue().get();
            this.variableName.save(attributeConfig);
            this.type.save(attributeConfig);
            if (variableType.equals(VariableType.STRING) || variableType.equals(VariableType.LIST)) {
                this.stringUpdate.save(attributeConfig);
            } else {
                this.doubleUpdate.save(attributeConfig);
            }
        }
    }

    @Override
    public VariableUpdateFeature getValue() {
        return this;
    }

    @Override
    public VariableUpdateFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = "&7For the var: &e" + variableName.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Type: &e" + type.getValue().get();
        if (getVariables().getVariablesName().contains(this.variableName.getValue().get())) {
            VariableType variableType = getVariables().getVariable(this.variableName.getValue().get()).getType().getValue().get();
            if (variableType.equals(VariableType.STRING) || variableType.equals(VariableType.LIST)) {
                finalDescription[finalDescription.length - 2] = "&7Update: &e" + stringUpdate.getValue().get();
            } else {
                finalDescription[finalDescription.length - 2] = "&7Update: &e" + doubleUpdate.getValue().get();
            }
        } else finalDescription[finalDescription.length - 2] = "&7Update: &c?";
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public VariableUpdateFeature clone(FeatureParentInterface newParent) {
        VariableUpdateFeature eF = new VariableUpdateFeature(newParent, id);
        eF.setVariableName(variableName.clone(eF));
        eF.setType(type.clone(eF));
        eF.setStringUpdate(stringUpdate.clone(eF));
        eF.setDoubleUpdate(doubleUpdate.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(variableName, type, stringUpdate, doubleUpdate));
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
            if (feature instanceof VariableUpdateFeature) {
                VariableUpdateFeature aFOF = (VariableUpdateFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setVariableName(variableName);
                    aFOF.setType(type);
                    aFOF.setStringUpdate(stringUpdate);
                    aFOF.setDoubleUpdate(doubleUpdate);
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
        VariableUpdateFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
