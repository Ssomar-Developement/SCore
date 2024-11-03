package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import com.ssomar.score.utils.backward_compatibility.SoundUtils;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class SoundFeature extends FeatureAbstract<Optional<Sound>, SoundFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<Sound> value;
    private Optional<Sound> defaultValue;

    public SoundFeature(FeatureParentInterface parent, Optional<Sound> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            Sound operation = SoundUtils.getSound(colorStr);
            value = Optional.ofNullable(operation);
            FeatureReturnCheckPremium<Sound> checkPremium = checkPremium("Sound", operation, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the Sound value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Sound available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Sound> value = getValue();
        value.ifPresent(operation -> config.set(this.getName(), SoundUtils.getSounds().get(operation)));
    }

    @Override
    public Optional<Sound> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public SoundFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Sound> value = getValue();
        Sound finalValue = value.orElse(Sound.ITEM_ARMOR_EQUIP_DIAMOND);
        updateOperation(finalValue, gui);
    }

    @Override
    public SoundFeature clone(FeatureParentInterface newParent) {
        SoundFeature clone = new SoundFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        Sound operation = getOperation((GUI) manager.getCache().get(editor));
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        updateOperation(operation, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        Sound operation = getOperation((GUI) manager.getCache().get(editor));
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        updateOperation(operation, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateOperation(nextOperation(getOperation((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateOperation(prevOperation(getOperation((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Sound nextOperation(Sound sound) {
        boolean next = false;
        Map<Object, String> map = AttributeUtils.getAttributes();
        for (Object check : map.keySet()) {
            if (check.equals(sound)) {
                next = true;
                continue;
            }
            if (next) return (Sound) check;
        }
        return (Sound) map.keySet().iterator().next();
    }

    public Sound prevOperation(Sound sound) {
        int i = -1;
        int cpt = 0;
        Map<Object, String> map = SoundUtils.getSounds();
        for (Object check : map.keySet()) {
            if (check.equals(sound)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return (Sound) map.keySet().toArray()[map.size() - 1];
        else return (Sound) map.keySet().toArray()[cpt - 1];
    }

    public void updateOperation(Sound operation, GUI gui) {
        this.value = Optional.of(operation);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        Map<Object, String> map = SoundUtils.getSounds();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += map.size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (Object check : map.keySet()) {
            if (operation.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + map.get(operation)));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + map.get(check)));
            }
        }
        for (Object check : map.keySet()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + map.get(check)));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Sound getOperation(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return SoundUtils.getSound(str.split("➤ ")[1]);
            }
        }
        return null;
    }

}
