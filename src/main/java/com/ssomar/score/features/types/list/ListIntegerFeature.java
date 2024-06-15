package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListIntegerFeature extends ListFeatureAbstract<Integer, ListIntegerFeature> {

    private Optional<List<Suggestion>> suggestions;

    public ListIntegerFeature(FeatureParentInterface parent, List<Integer> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, "List of Colored Strings", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        this.suggestions = suggestions;
        reset();
    }

    public List<Integer> loadValues(List<String> entries, List<String> errors) {
        List<Integer> ints = new ArrayList<>();
        for (String s : entries) {
            ints.add(Integer.valueOf(s));
        }
        return ints;
    }

    @Override
    public String transfromToString(Integer value) {
        return value.toString();
    }

    @Override
    public ListIntegerFeature clone(FeatureParentInterface newParent) {
        ListIntegerFeature clone = new ListIntegerFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue(), suggestions);
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }


    @Override
    public Optional<String> verifyMessage(String message) {
        try{
            Integer.valueOf(message);
        } catch (NumberFormatException e) {
            return Optional.of("This is not a number");
        }
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
