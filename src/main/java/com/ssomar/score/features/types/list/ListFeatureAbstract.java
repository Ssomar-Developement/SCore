package com.ssomar.score.features.types.list;

import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public abstract class ListFeatureAbstract<T, Y extends FeatureInterface<List<T>, Y>> extends FeatureAbstract<List<T>, Y> implements FeatureRequireSubTextEditorInEditor {

    private List<T> value;
    private List<T> defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;
    private String featureName;


    public ListFeatureAbstract(FeatureParentInterface parent, String name, String featureName, String editorName, String[] editorDescription, Material editorMaterial, List<T> defaultValue, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        this.featureName = featureName;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        value = loadValue(config.getStringList(this.getName()), errors);
        FeatureReturnCheckPremium<List<T>> checkPremium = checkPremium(featureName, value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) {
            value = checkPremium.getNewValue();
            errors.add(checkPremium.getError());
        }
        return errors;
    }

    public abstract List<T> loadValue(List<String> entries, List<String> errors);

    @Override
    public void save(ConfigurationSection config) {
        //SsomarDev.testMsg("save deVal s: " + defaultValue.size() + " val s: " + value.size() + " >> " + (defaultValue.containsAll(value)));
        if (notSaveIfEqualsToDefaultValue && defaultValue.containsAll(value)) {
                config.set(this.getName(), null);
                return;
        }
        config.set(this.getName(), getCurrentValues());
    }

    @Override
    public List<T> getValue() {
        return value;
    }

    @Override
    public Y initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return (Y) this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getCurrentValues(), "&cEMPTY");
    }

    @Override
    public void reset() {
        this.value = new ArrayList<>(defaultValue);
    }

}
