package com.ssomar.scoretestrecode.features.types.list;

import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.editor.Suggestion;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureRequireSubTextEditorInEditor;
import com.ssomar.scoretestrecode.features.FeatureReturnCheckPremium;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListRegionStringFeature extends FeatureAbstract<List<String>, ListRegionStringFeature> implements FeatureRequireSubTextEditorInEditor {

    private List<String> value;
    private List<String> defaultValue;
    private boolean notSaveIfEqualsToDefaultValue;

    public ListRegionStringFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.notSaveIfEqualsToDefaultValue = notSaveIfEqualsToDefaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        value = config.getStringList(this.getName());
        for (int i = 0; i < value.size(); i++) {
            value.set(i, StringConverter.decoloredString(value.get(i)));
        }
        FeatureReturnCheckPremium<List<String>> checkPremium = checkPremium("List of Uncolored Strings", value, Optional.of(defaultValue), isPremiumLoading);
        if (checkPremium.isHasError()) value = checkPremium.getNewValue();
        return new ArrayList<>();
    }

    @Override
    public void save(ConfigurationSection config) {
        if (notSaveIfEqualsToDefaultValue) {
            if (defaultValue.containsAll(value)) {
                config.set(this.getName(), null);
                return;
            }
        }
        config.set(this.getName(), value);
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public ListRegionStringFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        gui.updateConditionList(getEditorName(), getValue(), "&cEMPTY");
    }


    @Override
    public ListRegionStringFeature clone(FeatureParentInterface newParent) {
        ListRegionStringFeature clone = new ListRegionStringFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void reset() {
        this.value = new ArrayList<>(defaultValue);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        return value;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        //TODO ADD REGIONS HERE
        return new ArrayList<>();
    }

    @Override
    public String getTips() {
        return "";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        value = (List<String>) manager.currentWriting.get(editor);
        for (int i = 0; i < value.size(); i++) {
            value.set(i, StringConverter.decoloredString(value.get(i)));
        }
        manager.requestWriting.remove(editor);
        manager.activeTextEditor.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }


    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
