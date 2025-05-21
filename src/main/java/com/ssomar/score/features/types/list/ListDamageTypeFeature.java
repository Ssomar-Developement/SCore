package com.ssomar.score.features.types.list;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.utils.strings.StringConverter;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class ListDamageTypeFeature extends ListFeatureAbstract<DamageType, ListDamageTypeFeature> {

    public ListDamageTypeFeature(FeatureParentInterface parent, List<DamageType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, "List of Damage Types", defaultValue, featureSettings);
        reset();
    }

    @Override
    public List<DamageType> loadValues(List<String> entries, List<String> errors) {
        List<DamageType> value = new ArrayList<>();
        for (String s : entries) {
            s = StringConverter.decoloredString(s.toUpperCase());
            try {
                TypedKey<DamageType> key = TypedKey.create(RegistryKey.DAMAGE_TYPE, s);
                DamageType damageType = RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(key);
                if (damageType != null) {
                    value.add(damageType);
                } else {
                    errors.add("&cERROR, Couldn't load the DamageType value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> DamageTypes available: https://jd.papermc.io/paper/1.21.5/org/bukkit/damage/DamageType.html");
                }
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the DamageType value of " + this.getName() + " from config, value: " + s + " &7&o" + getParent().getParentInfo() + " &6>> DamageTypes available: https://jd.papermc.io/paper/1.21.5/org/bukkit/damage/DamageType.html");
            }
        }
        return value;
    }

    @Override
    public String transfromToString(DamageType value) {
        return value.toString();
    }

    public boolean verifCause(DamageType cause) {
        if (cause != null) {
            if (getValues().isEmpty()) return true;
            return getValues().contains(cause);
        }
        return false;
    }

    @Override
    public ListDamageTypeFeature clone(FeatureParentInterface newParent) {
        ListDamageTypeFeature clone = new ListDamageTypeFeature(newParent,  getDefaultValue(), getFeatureSettings());
        clone.setValues(getValues());
        clone.setBlacklistedValues(getBlacklistedValues());
        return clone;
    }

    @Override
    public Optional<String> verifyMessage(String message) {
        message = StringConverter.decoloredString(message);
        try {
            TypedKey<DamageType> key = TypedKey.create(RegistryKey.DAMAGE_TYPE, message);
            DamageType damageType = RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(key);
            if (damageType == null) {
                return Optional.of("&4&l[ERROR] &cThe message you entered is not a DamageType &6>> DamageTypes available: https://jd.papermc.io/paper/1.21.5/org/bukkit/damage/DamageType.html");
            }
            getValues().add(damageType);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("&4&l[ERROR] &cThe message you entered is not a DamageType &6>> DamageTypes available: https://jd.papermc.io/paper/1.21.5/org/bukkit/damage/DamageType.html");
        }
    }

    @Override
    public List<TextComponent> getMoreInfo() {
        return null;
    }

    @Override
    public List<Suggestion> getSuggestions() {
        SortedMap<String, Suggestion> map = new TreeMap<String, Suggestion>();
        for (DamageType mat : RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE)) {
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

    public RegistryKeySet<DamageType> asRegistryKeySet() {
        return RegistrySet.keySetFromValues(RegistryKey.DAMAGE_TYPE, getValues());
    }

    public void fromRegistryKeySet(RegistryKeySet<DamageType> set) {
        for (DamageType type : set.resolve(RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE))) {
            getValues().add(type);
        }
    }
}
