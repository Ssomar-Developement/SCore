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
public class BooleanFeature implements FeatureInterface<Boolean> {

    private String name;
    private boolean value;
    private boolean defaultValue;
    private String editorName;
    private String [] editorDescription;
    private Material editorMaterial;

    public BooleanFeature(String name, boolean defaultValue, String editorName, String [] editorDescription, Material editorMaterial) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.editorMaterial = editorMaterial;
    }

    @Override
    public List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config) {
        this.value = config.getBoolean(this.name, this.defaultValue);
        return new ArrayList<>();
    }

    @Override
    public void save(SPlugin plugin, ConfigurationSection config) {

    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void initEditorItem(GUI gui, int slot) {
        String [] finalDescription = new String[editorDescription.length + 2];
        System.arraycopy(editorDescription, 0, finalDescription, 0, editorDescription.length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(editorMaterial, 1, slot, gui.TITLE_COLOR+editorName, false, false, finalDescription);
        gui.updateBoolean(editorName, value);
    }

    @Override
    public void clickEditor(GUIManager manager, Player player) {
        ((GUI)manager.getCache().get(player)).changeBoolean(editorName);
    }
}
