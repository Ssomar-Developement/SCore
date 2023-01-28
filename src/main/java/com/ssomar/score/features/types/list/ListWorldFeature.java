package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.usedapi.MultiverseAPI;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListWorldFeature extends ListFeatureAbstract<String, ListWorldFeature> {


    public ListWorldFeature(FeatureParentInterface parent, String name, List<String> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, "List of Worlds", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<String> loadValue(List<String> entries, List<String> errors) {
        List<String> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s);
            Optional<World> worldOptional = AllWorldManager.getWorld(s);
            if (worldOptional.isPresent()) {
                value.add(s);
            } else
                errors.add("&cERROR, Couldn't load the World value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo());
        }
        return value;
    }


    @Override
    public ListWorldFeature clone(FeatureParentInterface newParent) {
        ListWorldFeature clone = new ListWorldFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        message = StringConverter.decoloredString(message);
        World w = MultiverseAPI.getWorld(message);
        if (w == null) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a world, please try again.");
        }
        return Optional.empty();
    }

    @Override
    public List<String> getCurrentValues() {
        return getValue();
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (String s : MultiverseAPI.getWorlds()) {
            map.put(s, new Suggestion(s, "&6[" + "&e" + s + "&6]", "&7Add &e" + s));
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public String getTips() {
        return "&8Example &7&oworld";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        List<String> value = new ArrayList<>();
        for (String s : (List<String>) manager.currentWriting.get(editor)) {
            value.add(StringConverter.decoloredString(s));
        }
        setValue(value);
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
