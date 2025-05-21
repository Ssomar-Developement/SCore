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
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ListGameModeFeature extends ListFeatureAbstract<GameMode, ListGameModeFeature> {

    public ListGameModeFeature(FeatureParentInterface parent, List<GameMode> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, "List of GameModes",defaultValue, featureSettings);
        reset();
    }

    public List<GameMode> loadValues(List<String> entries, List<String> errors) {
        List<GameMode> val = new ArrayList<>();
        for(String s : entries) {
            val.add(GameMode.valueOf(s.toUpperCase()));
        }
        return val;
    }

    @Override
    public String transfromToString(GameMode value) {
        return value.name();
    }

    @Override
    public ListGameModeFeature clone(FeatureParentInterface newParent) {
        ListGameModeFeature clone = new ListGameModeFeature(newParent,  getDefaultValue(), getFeatureSettings());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }


    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message).trim();
        try {
            GameMode.valueOf(message.toUpperCase());
        } catch (Exception e) {
            return Optional.of("&cThis GameMode doesn't exist &7https://hub.spigotmc.org/javadocs/spigot/org/bukkit/GameMode.html");
        }
        return Optional.empty();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return new ArrayList<>();
    }

    @Override
    public List<Suggestion> getSuggestions() {
        return new ArrayList<>();
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

    public boolean isValid(GameMode gameMode) {
        if (getBlacklistedValues() != null && getBlacklistedValues().contains(gameMode)) {
            return false;
        }
        return getValues() == null || getValues().isEmpty() || getValues().contains(gameMode);
    }
}
