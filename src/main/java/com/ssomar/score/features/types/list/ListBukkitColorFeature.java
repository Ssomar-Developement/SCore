package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.utils.emums.CustomColor;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListBukkitColorFeature extends ListFeatureAbstract<Color, ListBukkitColorFeature> {

    private Optional<List<Suggestion>> suggestions;

    public ListBukkitColorFeature(FeatureParentInterface parent, List<Color> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, "List of Bukkit colors",defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        this.suggestions = suggestions;
        reset();
    }

    public List<Color> loadValues(List<String> entries, List<String> errors) {
        List<Color> val = new ArrayList<>();
        for(String s : entries) {
            val.add(CustomColor.valueOf(s));
        }
        return val;
    }

    @Override
    public String transfromToString(Color value) {
        return CustomColor.getName(value);
    }

    @Override
    public ListBukkitColorFeature clone(FeatureParentInterface newParent) {
        ListBukkitColorFeature clone = new ListBukkitColorFeature(newParent,  getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue(), suggestions);
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }


    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message).trim();
        if(CustomColor.valueOf(message) == null) return Optional.of("&cThis color doesn't exist &7https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Color.html");
        return Optional.empty();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return new ArrayList<>();
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
