package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.utils.strings.StringConverter;
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
public class ListWeatherFeature extends ListFeatureAbstract<String, ListWeatherFeature> {

    public ListWeatherFeature(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, "List of Weather", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<String> loadValues(List<String> entries, List<String> errors) {
        List<String> value = new ArrayList<>();
        for (String w : entries) {
            if (isValidWeather(w.toUpperCase())) {
                value.add(w.toUpperCase());
            } else {
                errors.add("&cERROR, Couldn't load the Weather value of " + this.getName() + " from config, value: " + w + " &7&o" + getParent().getParentInfo() + " &6>> Weather available: RAIN, CLEAR, STORM");
                continue;
            }
        }
        return value;
    }

    @Override
    public String transfromToString(String value) {
        return value;
    }

    public boolean isValidWeather(String value) {
        List<String> weathers = new ArrayList<>();
        weathers.add("RAIN");
        weathers.add("STORM");
        weathers.add("CLEAR");

        return weathers.contains(value);
    }

    @Override
    public ListWeatherFeature clone(FeatureParentInterface newParent) {
        ListWeatherFeature clone = new ListWeatherFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = message.toUpperCase();
        message = StringConverter.decoloredString(message);
        if (isValidWeather(message)) {
            return Optional.empty();
        } else {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a weather. &7RAIN, CLEAR or STORM");
        }
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(new Suggestion("RAIN", "&bRain", "&7Add &bRain"));
        suggestions.add(new Suggestion("CLEAR", "&aClear", "&7Add &aClear"));
        suggestions.add(new Suggestion("STORM", "&8Storm", "&7Add &8Storm"));
        return suggestions;
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

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, false, false, true,
                true, false, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
