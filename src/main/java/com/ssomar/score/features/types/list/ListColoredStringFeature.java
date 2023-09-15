package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListColoredStringFeature extends ListFeatureAbstract<String, ListColoredStringFeature> {

    private Optional<List<Suggestion>> suggestions;

    public ListColoredStringFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, name, "List of Colored Strings", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        this.suggestions = suggestions;
        reset();
    }

    public List<String> loadValues(List<String> entries, List<String> errors) {
        return entries;
    }

    @Override
    public String transfromToString(String value) {
        return value;
    }

    public List<String> getColoredValue() {
        List<String> colored = new ArrayList<>();
        for (String s : getValues()) {
            colored.add(StringConverter.coloredString(s));
        }
        return colored;
    }

    @Override
    public ListColoredStringFeature clone(FeatureParentInterface newParent) {
        ListColoredStringFeature clone = new ListColoredStringFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue(), suggestions);
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }


    @Override
    public Optional<String> verifyMessage(String message) {
        return Optional.empty();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
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
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
