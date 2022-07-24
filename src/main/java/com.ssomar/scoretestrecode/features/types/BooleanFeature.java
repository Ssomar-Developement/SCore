package com.ssomar.scoretestrecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireOnlyClicksInEditor;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BooleanFeature extends FeatureAbstract<Boolean, BooleanFeature> implements FeatureRequireOnlyClicksInEditor, Serializable {

    private boolean value;
    private boolean defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;

    public BooleanFeature(FeatureParentInterface parent, String name, boolean defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        this.value = config.getBoolean(this.getName(), this.defaultValue);
        setPremium(isPremiumLoading);
        FeatureReturnCheckPremium<Boolean> checkPremium = checkPremium("Boolean", value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        return errors;
    }

    @Override
    public BooleanFeature clone(FeatureParentInterface newParent) {
        BooleanFeature clone = new BooleanFeature(newParent, this.getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), notSaveIfEqualsToDefaultValue);
        clone.setValue(value);
        return clone;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (notSaveIfEqualsToDefaultValue) {
            if (value == defaultValue) {
                config.set(this.getName(), null);
                return;
            }
        }
        config.set(this.getName(), value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public BooleanFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if(!isPremium() && requirePremium()){
            finalDescription[finalDescription.length - 2] = gui.PREMIUM;
        }
        else finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateBoolean(getEditorName(), value);
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        if(requirePremium() && !isPremium()) return;
        ((GUI) manager.getCache().get(editor)).changeBoolean(getEditorName());
        value = !value;
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
