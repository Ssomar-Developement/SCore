package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UUIDFeature extends FeatureAbstract<UUID, UUIDFeature> implements FeatureRequireOnlyClicksInEditor {

    private UUID value;

    public UUIDFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.value = UUID.randomUUID();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        try {
            this.value = UUID.fromString(config.getString(this.getName()));
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the UUID value of " + this.getName() + " from config, value: " + config.getString(this.getName()) + " &7&o" + getParent().getParentInfo() + " &6>> https://www.uuidgenerator.net/version1");
            this.value = UUID.randomUUID();
        }
        return errors;
    }

    @Override
    public UUIDFeature clone(FeatureParentInterface newParent) {
        UUIDFeature clone = new UUIDFeature(newParent, this.getFeatureSettings());
        clone.setValue(value);
        return clone;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), value.toString());
    }

    @Override
    public UUID getValue() {
        return value;
    }

    @Override
    public UUIDFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateCurrently(getEditorName(), value.toString());
    }

    @Override
    public void reset() {
        this.value = UUID.randomUUID();
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        value = UUID.randomUUID();
        ((GUI) manager.getCache().get(editor)).updateCurrently(getEditorName(), value.toString());
    }

    @Override
    public boolean noShiftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        return false;
    }
}
