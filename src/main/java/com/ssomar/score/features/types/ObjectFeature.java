package com.ssomar.score.features.types;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureNotEditable;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ObjectFeature extends FeatureAbstract<Object, ObjectFeature> implements FeatureNotEditable {

    private Object value;

    public ObjectFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.value = null;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        this.value = config.getObject(this.getName(), Object.class);
        return errors;
    }

    @Override
    public ObjectFeature clone(FeatureParentInterface newParent) {
        ObjectFeature clone = new ObjectFeature(newParent, getFeatureSettings());
        clone.setValue(value);
        return clone;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ObjectFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = "&cFEATURE NOT EDITABLE";


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }


    @Override
    public void reset() {
        this.value = null;
    }
}
