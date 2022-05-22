package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.testRecode.editor.NewGUIManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BooleanFeature extends FeatureAbstract<Boolean, BooleanFeature> implements FeatureRequireOnlyClicksInEditor {

    private boolean value;
    private boolean defaultValue;

    public BooleanFeature(FeatureParentInterface parent, String name, boolean defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        this.value = config.getBoolean(getName(), this.defaultValue);
        return new ArrayList<>();
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public BooleanFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateBoolean(getEditorName(), value);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        this.value = ((GUI)manager.getCache().get(player)).getBoolean(getEditorName());
    }

    @Override
    public BooleanFeature clone() {
        return new BooleanFeature(getParent(), getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        ((GUI)manager.getCache().get(editor)).changeBoolean(getEditorName());
    }
}
