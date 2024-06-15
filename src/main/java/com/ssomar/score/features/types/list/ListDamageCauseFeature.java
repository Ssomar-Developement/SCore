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
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

@Getter
@Setter
public class ListDamageCauseFeature extends ListFeatureAbstract<EntityDamageEvent.DamageCause, ListDamageCauseFeature> {

    public ListDamageCauseFeature(FeatureParentInterface parent, List<EntityDamageEvent.DamageCause> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, "List of DamageCauses", defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
        reset();
    }

    @Override
    public List<EntityDamageEvent.DamageCause> loadValues(List<String> entries, List<String> errors) {
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

    @Override
    public String transfromToString(EntityDamageEvent.DamageCause value) {
        return value.name();
    }

    public boolean verifCause(EntityDamageEvent.DamageCause cause) {
        if (cause != null) {
            if (getValues().isEmpty()) return true;
            return getValues().contains(cause);
        }
        return false;
    }

    @Override
    public ListDamageCauseFeature clone(FeatureParentInterface newParent) {
        ListDamageCauseFeature clone = new ListDamageCauseFeature(newParent,  getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message);
        try {
            EntityDamageEvent.DamageCause mat = EntityDamageEvent.DamageCause.valueOf(message);
            getValues().add(mat);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a DamageCause &6>> DamageCause available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html");
        }
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
    public void sendBeforeTextEditor(Player playerEditor, NewGUIManager manager) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ Your custom " + getEditorName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, (List<String>) manager.currentWriting.get(playerEditor), getEditorName() + ":", true, true, true, true,
                true, true, true, "", suggestions);
        editor.generateTheMenuAndSendIt(playerEditor);
    }
}
