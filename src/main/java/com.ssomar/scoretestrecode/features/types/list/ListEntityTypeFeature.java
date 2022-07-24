package com.ssomar.scoretestrecode.features.types.list;

import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.editor.Suggestion;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListEntityTypeFeature extends ListFeatureAbstract<EntityType, ListEntityTypeFeature> {

    public ListEntityTypeFeature(FeatureParentInterface parent, String name, List<EntityType> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, "List of EntityTypes", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);;
        reset();
    }

    @Override
    public List<EntityType> loadValue(List<String> entries, List<String> errors) {
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
    public ListEntityTypeFeature clone(FeatureParentInterface newParent) {
        ListEntityTypeFeature clone = new ListEntityTypeFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        message = StringConverter.decoloredString(message).toUpperCase();
        try {
            EntityType mat = EntityType.valueOf(message);
            getValue().add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a EntityType &6>> EntityTypes available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html");
        }
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> currentValues = new ArrayList<>();
        for (EntityType mat : getValue()) {
            currentValues.add(mat.name());
        }
        return currentValues;
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
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        setValue(new ArrayList<>());
        for (String s : (List<String>) manager.currentWriting.get(editor)) {
            s = StringConverter.decoloredString(s).toUpperCase();
            try {
                EntityType mat = EntityType.valueOf(s);
                getValue().add(mat);
            } catch (Exception e) {
            }
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
                true, true, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
