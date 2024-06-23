package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.CreationType;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class CreationTypeFeature extends FeatureAbstract<Optional<CreationType>, CreationTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<CreationType> value;
    private Optional<CreationType> defaultValue;

    public CreationTypeFeature(FeatureParentInterface parent, Optional<CreationType> defaultValue, FeatureSettingsInterface settings) {
        super(parent, settings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if(colorStr.equals("NULL")){
            if(defaultValue.isPresent()) {
                value = defaultValue;
            } else {
                errors.add("&cERROR, Couldn't load the CreationType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> BASIC_CREATION, IMPORT_FROM_EXECUTABLE_ITEMS, IMPORT_FROM_ORAXEN, IMPORT_FROM_ITEMS_ADDER");
                value = Optional.empty();
            }
            return errors;
        }
        try {
            CreationType attributeSlot = CreationType.valueOf(colorStr.toUpperCase());
            value = Optional.ofNullable(attributeSlot);
            FeatureReturnCheckPremium<CreationType> checkPremium = checkPremium("CreationType", attributeSlot, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the CreationType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> BASIC_CREATION, IMPORT_FROM_EXECUTABLE_ITEMS, IMPORT_FROM_ORAXEN, IMPORT_FROM_ITEMS_ADDER");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<CreationType> value = getValue();
        if (value.isPresent()) config.set(this.getName(), value.get().name());
    }

    @Override
    public Optional<CreationType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public CreationTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<CreationType> value = getValue();
        CreationType finalValue = value.orElse(CreationType.BASIC_CREATION);
        updateCreationType(finalValue, gui);
    }

    @Override
    public CreationTypeFeature clone(FeatureParentInterface newParent) {
        CreationTypeFeature clone = new CreationTypeFeature(newParent,  getDefaultValue(), getFeatureSettings());
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
        CreationType slot = getCreationType((GUI) manager.getCache().get(editor));
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        slot = nextCreationType(slot);
        updateCreationType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        CreationType slot = getCreationType((GUI) manager.getCache().get(editor));
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        slot = prevCreationType(slot);
        updateCreationType(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateCreationType(nextCreationType(getCreationType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateCreationType(prevCreationType(getCreationType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public CreationType nextCreationType(CreationType slot) {
        boolean next = false;
        for (CreationType check : getSortCreationTypes()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortCreationTypes().get(0);
    }

    public CreationType prevCreationType(CreationType slot) {
        int i = -1;
        int cpt = 0;
        for (CreationType check : getSortCreationTypes()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortCreationTypes().get(getSortCreationTypes().size() - 1);
        else return getSortCreationTypes().get(cpt - 1);
    }

    public void updateCreationType(CreationType slot, GUI gui) {

        if((slot.equals(CreationType.IMPORT_FROM_EXECUTABLE_ITEMS) && !SCore.hasExecutableItems)
        || (slot.equals(CreationType.IMPORT_FROM_ITEMS_ADDER) && !SCore.hasItemsAdder)
        || (slot.equals(CreationType.IMPORT_FROM_ORAXEN) && !SCore.hasOraxen)){
            updateCreationType(nextCreationType(slot), gui);
            return;
        }

        this.value = Optional.of(slot);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortCreationTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (CreationType check : getSortCreationTypes()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (CreationType check : getSortCreationTypes()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
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

    public CreationType getCreationType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return CreationType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<CreationType> getSortCreationTypes() {
        SortedMap<String, CreationType> map = new TreeMap<String, CreationType>();
        for (CreationType l : CreationType.values()) {
            if(!SCore.is1v19v4Plus() && l == CreationType.DISPLAY_CREATION) continue;   // 1.19.4+ only
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
