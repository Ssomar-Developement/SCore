package com.ssomar.score.features.custom.detailedslots;

import com.ssomar.score.SCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.item.MakeItemGlow;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DetailedSlotsEditor extends FeatureEditorInterface<DetailedSlots> {

    public final DetailedSlots detailedSlots;

    public DetailedSlotsEditor(DetailedSlots dropFeatures) {
        super("&lDetailed slots Editor", 6 * 9);
        this.detailedSlots = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        List<Integer> slots = detailedSlots.getSlots();
        int i = 0;
        for (int j = 9; j < 36; j++) {
            this.updateSlot(i, j, slots.contains(j));
            i++;
        }

        i = i + 3;
        createItem(Material.LEVER, 1, i, "&7&oDisable all slots", false, false, "", "&c✎ Click here to disable");
        i++;

        i++;

        createItem(Material.LEVER, 1, i, "&7&oEnable all slots", false, false, "", "&a✎ Click here to enable");
        i++;
        i = i + 3;

        for (int j = 0; j < 9; j++) {
            this.updateSlot(i, j, slots.contains(j));
            i++;
        }

        updateSlotHelmet(i, slots.contains(39));
        i++;

        updateSlotChestplate(i, slots.contains(38));
        i++;

        updateSlotLeggings(i, slots.contains(37));
        i++;

        updateSlotBoots(i, slots.contains(36));
        i++;

        updateSlotOffHand(i, slots.contains(40));
        i++;

        updateSlotMainHand(i, slots.contains(-1));
        i++;
        i++;


        // Back
        createItem(RED, 1, i, GUI.BACK, false, false);

        i++;
        // Save menu
        createItem(GREEN, 1, i, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public DetailedSlots getParent() {
        return detailedSlots;
    }

    public void disableAllSlots() {
        detailedSlots.setSlots(new ArrayList<>());
        clearAndSetBackground();
        load();
    }

    public void enableAllSlots() {
        List<Integer> slots = new ArrayList<>();
        for (int i = -1; i <= 40; i++) {
            slots.add(i);
        }
        detailedSlots.setSlots(slots);
        clearAndSetBackground();
        load();
    }

    public void updateSlotMainHand(int i, boolean enable) {

        if (enable) {
            createItem(Material.STICK, 1, i, "&eSlot: mainHand", true, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        } else {
            createItem(Material.STICK, 1, i, "&eSlot: mainHand", false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
        }
    }

    public void changeSlotMainHand() {
        ItemStack item = this.getByName("Slot: mainHand");
        if (addOrRemoveEnchant(item)) {
            detailedSlots.getSlots().remove(Integer.valueOf(-1));
        } else detailedSlots.getSlots().add(-1);
    }

    public void updateSlotOffHand(int i, boolean enable) {
        Material offHandMat;
        if (SCore.is1v11Less()) offHandMat = Material.BARRIER;
        else offHandMat = Material.SHIELD;
        if (enable)
            createItem(offHandMat, 1, i, "&eSlot: offHand", true, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        else
            createItem(offHandMat, 1, i, "&eSlot: offHand", false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
    }

    public void changeSlotOffHand() {
        ItemStack item = this.getByName("Slot: offHand");
        if (addOrRemoveEnchant(item)) {
            detailedSlots.getSlots().remove(Integer.valueOf(40));
        } else detailedSlots.getSlots().add(40);
    }

    /**
     * return true if removed
     **/
    public boolean addOrRemoveEnchant(ItemStack item) {
        if (this.getCurrently(item).contains("Enable")) {
            this.updateCurrently(item, "&cDisable");
            for (Enchantment e : item.getItemMeta().getEnchants().keySet()) {
                item.removeEnchantment(e);
            }
            return true;
        } else {
            this.updateCurrently(item, "&aEnable");
            MakeItemGlow.makeGlow(item);
            return false;
        }
    }

    public void updateSlotBoots(int i, boolean enable) {
        if (enable)
            createItem(Material.DIAMOND_BOOTS, 1, i, "&eSlot: boots", true, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        else
            createItem(Material.DIAMOND_BOOTS, 1, i, "&eSlot: boots", false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
    }

    public void changeSlotBoots() {
        ItemStack item = this.getByName("Slot: boots");
        //SsomarDev.testMsg("Item: " + item, true);
        if (addOrRemoveEnchant(item)) {
            detailedSlots.getSlots().remove(Integer.valueOf(36));
        } else detailedSlots.getSlots().add(36);
    }


    public void updateSlotLeggings(int i, boolean enable) {
        if (enable)
            createItem(Material.DIAMOND_LEGGINGS, 1, i, "&eSlot: leggings", true, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        else
            createItem(Material.DIAMOND_LEGGINGS, 1, i, "&eSlot: leggings", false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
    }

    public void changeSlotLeggings() {
        ItemStack item = this.getByName("Slot: leggings");
        if (addOrRemoveEnchant(item)) {
            detailedSlots.getSlots().remove(Integer.valueOf(37));
        } else detailedSlots.getSlots().add(37);
    }


    public void updateSlotChestplate(int i, boolean enable) {
        if (enable)
            createItem(Material.DIAMOND_CHESTPLATE, 1, i, "&eSlot: chestplate", true, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        else
            createItem(Material.DIAMOND_CHESTPLATE, 1, i, "&eSlot: chestplate", false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
    }

    public void changeSlotChestplate() {
        ItemStack item = this.getByName("Slot: chestplate");
        if (addOrRemoveEnchant(item)) {
            detailedSlots.getSlots().remove(Integer.valueOf(38));
        } else detailedSlots.getSlots().add(38);
    }


    public void updateSlotHelmet(int i, boolean enable) {
        if (enable)
            createItem(Material.DIAMOND_HELMET, 1, i, "&eSlot: helmet", true, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        else
            createItem(Material.DIAMOND_HELMET, 1, i, "&eSlot: helmet", false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
    }

    public void changeSlotHelmet() {
        ItemStack item = this.getByName("Slot: helmet");
        if (addOrRemoveEnchant(item)) {
            detailedSlots.getSlots().remove(Integer.valueOf(39));
        } else detailedSlots.getSlots().add(39);
    }


    public void updateSlot(int i, int slot, boolean enable) {
        if (enable) {
            if (!SCore.is1v12Less())
                createItem(Material.GREEN_WOOL, 1, i, "&eSlot: " + slot, false, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
            else
                createItem(Material.EMERALD, 1, i, "&eSlot: " + slot, false, false, "", "&a✎ Click here to change", "&7Currently: &aEnable");
        } else {
            if (!SCore.is1v12Less())
                createItem(Material.RED_WOOL, 1, i, "&eSlot: " + slot, false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
            else
                createItem(Material.REDSTONE, 1, i, "&eSlot: " + slot, false, false, "", "&a✎ Click here to change", "&7Currently: &cDisable");
        }
    }

    public void changeSlot(int i) {
        ItemStack item = this.getByName("Slot: " + i);
        if (this.getCurrently(item).contains("Enable")) {
            this.updateCurrently(item, "&cDisable");
            if (!SCore.is1v12Less()) item.setType(Material.RED_WOOL);
            else item.setType(Material.REDSTONE);
            detailedSlots.getSlots().remove(i);
        } else {
            this.updateCurrently(item, "&aEnable");
            if (!SCore.is1v12Less()) item.setType(Material.GREEN_WOOL);
            else item.setType(Material.EMERALD);
            detailedSlots.getSlots().add(i);
        }
    }

}
