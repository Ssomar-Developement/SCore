package com.ssomar.score.features.types.list;

import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListUncoloredStringFeature extends ListFeatureAbstract<String, ListUncoloredStringFeature> {

    private Optional<List<Suggestion>> suggestions;

    public ListUncoloredStringFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, name, "List of Colored Strings", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        this.suggestions = suggestions;
        reset();
    }

    public List<String> loadValue(List<String> entries, List<String> errors) {
        List<String> uncolored = new ArrayList<>();
        for (String s : entries) {
            uncolored.add(StringConverter.decoloredString(s));
        }
        return uncolored;
    }

    @Override
    public ListUncoloredStringFeature clone(FeatureParentInterface newParent) {
        ListUncoloredStringFeature clone = new ListUncoloredStringFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue(), suggestions);
        clone.setValue(getValue());
        return clone;
    }


    @Override
    public Optional<String> verifyMessageReceived(String message) {
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        return getValue();
    }

    @Override
    public List<Suggestion> getSuggestions() {
        if (suggestions.isPresent()) return suggestions.get();
        return new ArrayList<>();
    }

    @Override
    public String getTips() {
        return "";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        List<String> uncolored = new ArrayList<>();
        for (String s : (List<String>) manager.currentWriting.get(editor)) {
            uncolored.add(StringConverter.decoloredString(s));
        }
        setValue(uncolored);
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
