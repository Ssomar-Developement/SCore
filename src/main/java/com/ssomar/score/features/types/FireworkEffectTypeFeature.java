package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class FireworkEffectTypeFeature extends FeatureAbstract<Optional<FireworkEffect.Type>, FireworkEffectTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<FireworkEffect.Type> value;
    private Optional<FireworkEffect.Type> defaultValue;

    public FireworkEffectTypeFeature(FeatureParentInterface parent, Optional<FireworkEffect.Type> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            value = defaultValue;
        } else {
            try {
                FireworkEffect.Type attributeSlot = FireworkEffect.Type.valueOf(colorStr.toUpperCase());
                value = Optional.ofNullable(attributeSlot);
                FeatureReturnCheckPremium<FireworkEffect.Type> checkPremium = checkPremium("FireworkEffectType", attributeSlot, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the FireworkEffectType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> List: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/FireworkEffect.Type.html");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<FireworkEffect.Type> value = getValue();
        value.ifPresent(potionType -> config.set(this.getName(), potionType.name()));
    }

    @Override
    public Optional<FireworkEffect.Type> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public FireworkEffectTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<FireworkEffect.Type> value = getValue();
        FireworkEffect.Type finalValue = value.orElse(FireworkEffect.Type.BALL);
        updateFireworkEffectType(finalValue, gui);
    }

    @Override
    public FireworkEffectTypeFeature clone(FeatureParentInterface newParent) {
        FireworkEffectTypeFeature clone = new FireworkEffectTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        return;
    }

    @Override
    public boolean noShiftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(Player editor, NewGUIManager manager) {
        FireworkEffect.Type slot = getFireworkEffectType((GUI) manager.getCache().get(editor));
        slot = nextFireworkEffectType(slot);
        updateFireworkEffectType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        FireworkEffect.Type slot = getFireworkEffectType((GUI) manager.getCache().get(editor));
        slot = prevFireworkEffectType(slot);
        updateFireworkEffectType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateFireworkEffectType(nextFireworkEffectType(getFireworkEffectType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateFireworkEffectType(prevFireworkEffectType(getFireworkEffectType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public FireworkEffect.Type nextFireworkEffectType(FireworkEffect.Type slot) {
        boolean next = false;
        for (FireworkEffect.Type check : getSortFireworkEffectTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortFireworkEffectTypes().get(0);
    }

    public FireworkEffect.Type prevFireworkEffectType(FireworkEffect.Type slot) {
        int i = -1;
        int cpt = 0;
        for (FireworkEffect.Type check : getSortFireworkEffectTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortFireworkEffectTypes().get(getSortFireworkEffectTypes().size() - 1);
        else return getSortFireworkEffectTypes().get(cpt - 1);
    }

    public void updateFireworkEffectType(FireworkEffect.Type slot, GUI gui) {
        value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortFireworkEffectTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (FireworkEffect.Type check : getSortFireworkEffectTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (FireworkEffect.Type check : getSortFireworkEffectTypes()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public FireworkEffect.Type getFireworkEffectType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return FireworkEffect.Type.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<FireworkEffect.Type> getSortFireworkEffectTypes() {
        SortedMap<String, FireworkEffect.Type> map = new TreeMap<String, FireworkEffect.Type>();
        for (FireworkEffect.Type l : FireworkEffect.Type.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
