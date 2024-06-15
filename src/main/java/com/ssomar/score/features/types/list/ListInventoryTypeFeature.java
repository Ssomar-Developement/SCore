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
import org.bukkit.event.inventory.InventoryType;

import java.util.*;

@Getter
@Setter
public class ListInventoryTypeFeature extends ListFeatureAbstract<InventoryType, ListInventoryTypeFeature> {

    public ListInventoryTypeFeature(FeatureParentInterface parent, List<InventoryType> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent,"List of InventoryType", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<InventoryType> loadValues(List<String> entries, List<String> errors) {
        List<InventoryType> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s.toUpperCase());
            try {
                InventoryType mat = InventoryType.valueOf(s);
                value.add(mat);
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the InventoryType value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> InventoryType available: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/inventory/InventoryType.html");
            }
        }
        return value;
    }

    @Override
    public String transfromToString(InventoryType value) {
        return value.name();
    }

    public boolean verifInventoryType(InventoryType cause) {
        if (cause != null) {
            if (getValues().isEmpty()) return true;
            return getValues().contains(cause);
        }
        return false;
    }

    @Override
    public ListInventoryTypeFeature clone(FeatureParentInterface newParent) {
        ListInventoryTypeFeature clone = new ListInventoryTypeFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message);
        try {
            InventoryType mat = InventoryType.valueOf(message);
            getValues().add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not an InventoryType &6>> InventoryType available: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/inventory/InventoryType.html");
        }
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (InventoryType mat : InventoryType.values()) {
            map.put(mat.toString(), new Suggestion(mat + "", "&6[" + "&e" + mat + "&6]", "&7Add &e" + mat));
        }
        return new ArrayList<>(map.values());
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
                true, true, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
