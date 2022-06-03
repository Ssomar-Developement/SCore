package com.ssomar.testRecode.features.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.AttributeSlot;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOnlyClicksInEditor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter @Setter
public class SlotFeature extends FeatureAbstract<Optional<AttributeSlot>, SlotFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<AttributeSlot> value;
    private Optional<AttributeSlot> defaultValue;

    public SlotFeature(FeatureParentInterface parent, String name, Optional<AttributeSlot> defaultValue, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            value = Optional.ofNullable(AttributeSlot.valueOf(colorStr.toUpperCase()));
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the Slot value of " + this.getName() + " from config, value: " + colorStr+ " &7&o"+getParent().getParentInfo()+" &6>> HEAD, CHEST, FEET, LEGS, HAND, OFF_HAND, ALL_SLOTS");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<AttributeSlot> value = getValue();
        if(value.isPresent()) config.set(this.getName(), value.get().name());
    }

    @Override
    public Optional<AttributeSlot> getValue() {
        if(value.isPresent()) return value;
        else return defaultValue;
    }

    public Optional<EquipmentSlot> getEquipmentSlotValue() {
        if(getValue().isPresent()) {
            try {
                return Optional.of(EquipmentSlot.valueOf(getValue().get().name().toUpperCase()));
            }
            catch (Exception e) {
                return Optional.empty();
            }
        }
        else return Optional.empty();
    }

    @Override
    public SlotFeature initItemParentEditor(GUI gui, int slot) {
        String [] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR+getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<AttributeSlot> value = getValue();
        AttributeSlot finalValue = value.orElse(AttributeSlot.HAND);
        updateAttributeSlot(finalValue, gui);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        this.value = Optional.of(getAttributeSlot( (GUI) manager.getCache().get(player)));
    }

    @Override
    public SlotFeature clone() {
        SlotFeature clone = new SlotFeature(getParent(), this.getName(), getDefaultValue(), getEditorName(), getEditorDescription(), getEditorMaterial(), requirePremium());
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
        AttributeSlot slot = getAttributeSlot( (GUI) manager.getCache().get(editor));
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        slot = nextAttributeSlot(slot);
        updateAttributeSlot(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        AttributeSlot slot = getAttributeSlot( (GUI) manager.getCache().get(editor));
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        slot = prevAttributeSlot(slot);
        updateAttributeSlot(slot, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateAttributeSlot(nextAttributeSlot(getAttributeSlot( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateAttributeSlot(prevAttributeSlot(getAttributeSlot( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public AttributeSlot nextAttributeSlot(AttributeSlot slot) {
        boolean next = false;
        for (AttributeSlot check : getSortAttributeSlots()) {
            if (check.equals(slot)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortAttributeSlots().get(0);
    }

    public AttributeSlot prevAttributeSlot(AttributeSlot slot) {
        int i = -1;
        int cpt = 0;
        for (AttributeSlot check : getSortAttributeSlots()) {
            if (check.equals(slot)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortAttributeSlots().get(getSortAttributeSlots().size()- 1);
        else return getSortAttributeSlots().get(cpt - 1);
    }

    public void updateAttributeSlot(AttributeSlot slot, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortAttributeSlots().size();
        if(maxSize > 17)  maxSize = 17;
        boolean find = false;
        for (AttributeSlot check : getSortAttributeSlots()) {
            if (slot.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" +slot.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (AttributeSlot check : getSortAttributeSlots()) {
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

    public AttributeSlot getAttributeSlot(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return AttributeSlot.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<AttributeSlot> getSortAttributeSlots() {
        SortedMap<String, AttributeSlot> map = new TreeMap<String, AttributeSlot>();
        for (AttributeSlot l : AttributeSlot.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
