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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListEntityTypeFeature extends ListFeatureAbstract<EntityType, ListEntityTypeFeature> {

    public ListEntityTypeFeature(FeatureParentInterface parent, List<EntityType> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent,"List of EntityTypes", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<EntityType> loadValues(List<String> entries, List<String> errors) {
        List<EntityType> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s);
            try {
                EntityType mat = EntityType.valueOf(s);
                value.add(mat);
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the EntityType value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
            }
        }
        return value;
    }

    @Override
    public String transfromToString(EntityType value) {
        return value.name();
    }

    @Override
    public ListEntityTypeFeature clone(FeatureParentInterface newParent) {
        ListEntityTypeFeature clone = new ListEntityTypeFeature(newParent, getDefaultValue(),getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message).toUpperCase();
        try {
            EntityType mat = EntityType.valueOf(message);
            getValues().add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a EntityType &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
        }
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (EntityType mat : EntityType.values()) {
            map.put(mat.toString(), new Suggestion(mat + "", "&6[" + "&e" + mat + "&6]", "&7Add &e" + mat));
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public String getTips() {
        return "&8Example &7&oBLAZE";
    }


    @Override
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
