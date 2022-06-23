package com.ssomar.scoretestrecode.features.custom.variables.base.variable;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.VariableType;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class VariableFeature extends FeatureWithHisOwnEditor<VariableFeature, VariableFeature, VariableFeatureEditor, VariableFeatureEditorManager> {

    private UncoloredStringFeature variableName;
    private VariableTypeFeature type;
    private ColoredStringFeature stringValue;
    private DoubleFeature doubleValue;
    private String id;

    public VariableFeature(FeatureParentInterface parent, String id) {
        super(parent, "variable", "Variable", new String[]{"&7&oA variable with its options"}, GUI.WRITABLE_BOOK, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.variableName = new UncoloredStringFeature(this, "variableName", Optional.of("var"), "Variable Name", new String[]{"&7&oThe variable name"}, GUI.WRITABLE_BOOK, false, false);
        this.type = new VariableTypeFeature(this, "type", Optional.of(VariableType.STRING), "Type", new String[]{"&7&oThe variable type"}, Material.COMPASS, false);
        this.stringValue = new ColoredStringFeature(this, "default", Optional.of(""), "String Value", new String[]{"&7&oThe variable default value"}, GUI.WRITABLE_BOOK, false, false);
        this.doubleValue = new DoubleFeature(this, "default", Optional.of(0.0), "Number Value", new String[]{"&7&oThe variable default value"}, GUI.WRITABLE_BOOK, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if(config.isConfigurationSection(id)){
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.variableName.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.type.load(plugin, enchantmentConfig, isPremiumLoading));
            if(type.getValue().get().equals(VariableType.STRING)) {
                errors.addAll(this.stringValue.load(plugin, enchantmentConfig, isPremiumLoading));
            } else {
                errors.addAll(this.doubleValue.load(plugin, enchantmentConfig, isPremiumLoading));
            }
        }
        else{
            errors.add("&cERROR, Couldn't load the Variable with its options because there is not section with the good ID: "+id+" &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("("+id+")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.variableName.save(attributeConfig);
        this.type.save(attributeConfig);
        if(type.getValue().get().equals(VariableType.STRING)) {
            this.stringValue.save(attributeConfig);
        } else {
            this.doubleValue.save(attributeConfig);
        }
    }

    @Override
    public VariableFeature getValue() {
        return this;
    }

    @Override
    public VariableFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = "&7Name: &e" + variableName.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Type: &e" + type.getValue().get();
        if(type.getValue().get().equals(VariableType.STRING)) finalDescription[finalDescription.length - 2] = "&7Default: &e" + stringValue.getValue().get();
        else finalDescription[finalDescription.length - 2] = "&7Default: &e" + doubleValue.getValue().get();
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName()+ " - "+"("+id+")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public VariableFeature clone() {
        VariableFeature eF = new VariableFeature(getParent(), id);
        eF.setVariableName(variableName.clone());
        eF.setType(type.clone());
        eF.setStringValue(stringValue.clone());
        eF.setDoubleValue(doubleValue.clone());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(variableName, type, stringValue, doubleValue));
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
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof VariableFeature) {
                VariableFeature aFOF = (VariableFeature) feature;
                if(aFOF.getId().equals(id)) {
                    aFOF.setVariableName(variableName);
                    aFOF.setType(type);
                    aFOF.setStringValue(stringValue);
                    aFOF.setDoubleValue(doubleValue);
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
        VariableFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
