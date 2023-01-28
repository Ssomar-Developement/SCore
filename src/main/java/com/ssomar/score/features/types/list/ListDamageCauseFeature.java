package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

@Getter
@Setter
public class ListDamageCauseFeature extends ListFeatureAbstract<EntityDamageEvent.DamageCause, ListDamageCauseFeature> {

    public ListDamageCauseFeature(FeatureParentInterface parent, String name, List<EntityDamageEvent.DamageCause> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, name, "List of DamageCauses", editorName, editorDescription, editorMaterial, defaultValue, requirePremium, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<EntityDamageEvent.DamageCause> loadValue(List<String> entries, List<String> errors) {
        List<EntityDamageEvent.DamageCause> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s.toUpperCase());
            try {
                EntityDamageEvent.DamageCause mat = EntityDamageEvent.DamageCause.valueOf(s);
                value.add(mat);
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the DamageCause value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> DamageCauses available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html");
            }
        }
        return value;
    }

    public boolean verifCause(EntityDamageEvent.DamageCause cause) {
        if (cause != null) {
            if (getValue().isEmpty()) return true;
            return getValue().contains(cause);
        }
        return false;
    }

    @Override
    public ListDamageCauseFeature clone(FeatureParentInterface newParent) {
        ListDamageCauseFeature clone = new ListDamageCauseFeature(newParent, this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        message = StringConverter.decoloredString(message);
        try {
            EntityDamageEvent.DamageCause mat = EntityDamageEvent.DamageCause.valueOf(message);
            getValue().add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a DamageCause &6>> Materials available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html");
        }
    }

    @Override
    public List<String> getCurrentValues() {
        List<String> currentValues = new ArrayList<>();
        for (EntityDamageEvent.DamageCause mat : getValue()) {
            currentValues.add(mat.name());
        }
        return currentValues;
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (EntityDamageEvent.DamageCause mat : EntityDamageEvent.DamageCause.values()) {
            map.put(mat.toString(), new Suggestion(mat + "", "&6[" + "&e" + mat + "&6]", "&7Add &e" + mat));
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public String getTips() {
        return "";
    }

    @Override
    public void finishEditInSubEditor(Player editor, NewGUIManager manager) {
        setValue(new ArrayList<>());
        for (String s : (List<String>) manager.currentWriting.get(editor)) {
            s = StringConverter.decoloredString(s);
            try {
                EntityDamageEvent.DamageCause mat = EntityDamageEvent.DamageCause.valueOf(s);
                getValue().add(mat);
            } catch (Exception ignored) {
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
