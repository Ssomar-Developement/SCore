package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class MusicIntrusmentFeature extends FeatureAbstract<Optional<MusicInstrument>, MusicIntrusmentFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<MusicInstrument> value;
    private Optional<MusicInstrument> defaultValue;

    public MusicIntrusmentFeature(FeatureParentInterface parent, Optional<MusicInstrument> defaultValue, FeatureSettingsInterface settings) {
        super(parent, settings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            if (defaultValue.isPresent()) {
                value = defaultValue;
            } else {
                errors.add("&cERROR, Couldn't load the MusicInstrument value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/MusicInstrument.html");
                value = Optional.empty();
            }
            return errors;
        }
        try {
            MusicInstrument attributeSlot = Registry.INSTRUMENT.get(NamespacedKey.minecraft(colorStr));
            value = Optional.ofNullable(attributeSlot);
            FeatureReturnCheckPremium<MusicInstrument> checkPremium = checkPremium("MusicInstrument", attributeSlot, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the MusicInstrument value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/MusicInstrument.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<MusicInstrument> value = getValue();
        value.ifPresent(musicInstrument -> config.set(this.getName(), musicInstrument.getKey().getKey()));
    }

    @Override
    public Optional<MusicInstrument> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public MusicIntrusmentFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<MusicInstrument> value = getValue();
        MusicInstrument finalValue = value.orElse(MusicInstrument.PONDER_GOAT_HORN);
        updateDropType(finalValue, gui);
    }

    @Override
    public MusicIntrusmentFeature clone(FeatureParentInterface newParent) {
        MusicIntrusmentFeature clone = new MusicIntrusmentFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        return false;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateDropType(nextCreationType(getDropType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateDropType(prevCreationType(getDropType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    public MusicInstrument nextCreationType(MusicInstrument slot) {
        boolean next = false;
        for (MusicInstrument check : getSortDropTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortDropTypes().get(0);
    }

    public MusicInstrument prevCreationType(MusicInstrument slot) {
        int i = -1;
        int cpt = 0;
        for (MusicInstrument check : getSortDropTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortDropTypes().get(getSortDropTypes().size() - 1);
        else return getSortDropTypes().get(cpt - 1);
    }

    public void updateDropType(MusicInstrument slot, GUI gui) {

        this.value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortDropTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (MusicInstrument check : getSortDropTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.getKey().getKey()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.getKey().getKey()));
            }
        }
        for (MusicInstrument check : getSortDropTypes()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.getKey().getKey()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Update the gui only for the right click , for the left it updated automaticaly idk why */
        for (HumanEntity e : gui.getInv().getViewers()) {
            if (e instanceof Player) {
                Player p = (Player) e;
                p.updateInventory();
            }
        }
    }

    public MusicInstrument getDropType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Registry.INSTRUMENT.get(NamespacedKey.minecraft(str.split("➤ ")[1]));
            }
        }
        return null;
    }

    public List<MusicInstrument> getSortDropTypes() {
        SortedMap<String, MusicInstrument> map = new TreeMap<String, MusicInstrument>();
        for (MusicInstrument l : MusicInstrument.values()) {
            map.put(l.getKey().toString(), l);
        }
        return new ArrayList<>(map.values());
    }

}
