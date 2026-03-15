package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
<<<<<<< HEAD
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
=======
import com.ssomar.score.commands.runnable.item.ItemCommand;
import com.ssomar.sevents.events.player.equip.armor.ArmorType;
>>>>>>> 10560f2fa62c333fe0ac1dc99ad1d771fa2a77cc
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
<<<<<<< HEAD
=======
import org.bukkit.inventory.EquipmentSlot;
>>>>>>> 10560f2fa62c333fe0ac1dc99ad1d771fa2a77cc
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

import java.util.ArrayList;
import java.util.List;

public class SetEquippableModel extends ItemCommand {

    public SetEquippableModel() {
        CommandSetting model = new CommandSetting("model", -1, String.class, "minecraft:diamond");
        List<CommandSetting> settings = getSettings();
        settings.add(model);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, ItemStack item, SCommandToExec sCommandToExec) {
        String model = (String) sCommandToExec.getSettingValue("model");

<<<<<<< HEAD
        boolean hasEquippable = itemMeta.hasEquippable();
        EquippableComponent equippable = itemMeta.getEquippable();
        if (!hasEquippable) {
            ItemStack defaultItem = new ItemStack(dMeta.getMaterial());
=======
        if (item == null || item.getType() == Material.AIR) return;
        if (!item.hasItemMeta()) {
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        ItemMeta itemMeta = item.getItemMeta();

        boolean hasEquippable = itemMeta.hasEquippable();
        EquippableComponent equippable = itemMeta.getEquippable();
        if (!hasEquippable) {
            ItemStack defaultItem = new ItemStack(item.getType());
>>>>>>> 10560f2fa62c333fe0ac1dc99ad1d771fa2a77cc
            if (defaultItem.hasData(DataComponentTypes.EQUIPPABLE)) {
                io.papermc.paper.datacomponent.item.Equippable defaultEquippable = defaultItem.getData(DataComponentTypes.EQUIPPABLE);
                equippable.setDamageOnHurt(defaultEquippable.damageOnHurt());
                equippable.setSlot(defaultEquippable.slot());
<<<<<<< HEAD
            }
        }
=======
            } else {
                ArmorType armorType = ArmorType.matchType(item, false);
                if (armorType != null) {
                    EquipmentSlot equipSlot;
                    switch (armorType) {
                        case CHESTPLATE:
                            equipSlot = EquipmentSlot.CHEST;
                            break;
                        case LEGGINGS:
                            equipSlot = EquipmentSlot.LEGS;
                            break;
                        case BOOTS:
                            equipSlot = EquipmentSlot.FEET;
                            break;
                        default:
                            equipSlot = EquipmentSlot.HEAD;
                            break;
                    }
                    equippable.setSlot(equipSlot);
                }
            }
        }

>>>>>>> 10560f2fa62c333fe0ac1dc99ad1d771fa2a77cc
        equippable.setModel(NamespacedKey.fromString(model));
        itemMeta.setEquippable(equippable);
        item.setItemMeta(itemMeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_EQUIPPABLE_MODEL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_EQUIPPABLE_MODEL model:minecraft:diamond";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }
}
