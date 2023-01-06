package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.CustomColor;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListBukkitColorFeature extends ListFeatureAbstract<Color, ListBukkitColorFeature> {

    private Optional<List<Suggestion>> suggestions;

    public ListBukkitColorFeature(FeatureParentInterface parent, String name, List<Color> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, name, "List of Bukkit colors", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        this.suggestions = suggestions;
        reset();
    }

    public List<Color> loadValue(List<String> entries, List<String> errors) {
        List<Color> val = new ArrayList<>();
        for(String s : entries) {
            val.add(CustomColor.valueOf(s));
        }
        return val;
    }

    @Override
    public ListBukkitColorFeature clone(FeatureParentInterface newParent) {
        ListBukkitColorFeature clone = new ListBukkitColorFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue(), suggestions);
        clone.setValue(getValue());
        return clone;
    }


    @Override
    public Optional<String> verifyMessageReceived(String message) {
        message = StringConverter.decoloredString(message).trim();
        if(CustomColor.valueOf(message) == null) return Optional.of("&cThis color doesn't exist &7https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Color.html");
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> colors = new ArrayList<>();
        for(Color c : getValue()) {
            colors.add(CustomColor.getName(c));
        }
        return colors;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        return suggestions.orElseGet(ArrayList::new);
    }

    @Override
    public String getTips() {
        return "";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        List<String> strVal = (List<String>) manager.currentWriting.get(editor);
        List<Color> val = new ArrayList<>();
        for(String s : strVal) {
            val.add(CustomColor.valueOf(s));
        }
        setValue(val);
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
