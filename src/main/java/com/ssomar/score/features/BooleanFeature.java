package com.ssomar.score.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BooleanFeature extends FeatureAbstract<Boolean> {

    private boolean value;
    private boolean defaultValue;

    public BooleanFeature(String name, boolean defaultValue, String editorName, String [] editorDescription, Material editorMaterial) {
        super(name, editorName, editorDescription, editorMaterial);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config) {
        this.value = config.getBoolean(getName(), this.defaultValue);
        return new ArrayList<>();
    }

    @Override
    public void save(SPlugin plugin, ConfigurationSection config) {
        config.set(getName(), value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void initEditorItem(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        gui.updateBoolean(getEditorName(), value);
    }

    @Override
    public void clickEditor(GUIManager manager, Player player) {
        ((GUI)manager.getCache().get(player)).changeBoolean(getEditorName());
    }

    @Override
    public void extractInfoFromEditor(GUIManager manager, Player player) {
        this.value = ((GUI)manager.getCache().get(player)).getBoolean(getEditorName());
    }
}
