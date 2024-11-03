package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


public class AddAttribute extends PlayerCommand {

    public AddAttribute() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, 0);
        slot.setSlot(true);
        Attribute att = null;
        if(SCore.is1v21v2Plus()) att = Attribute.MAX_HEALTH;
        else att = AttributeUtils.getAttribute("GENERIC_MAX_HEALTH");
        CommandSetting attribute = new CommandSetting("attribute", 1, Attribute.class, att);
        CommandSetting value = new CommandSetting("value", 2, Double.class, 1.0);
        CommandSetting equipmentSlot = new CommandSetting("equipmentSlot", 3, EquipmentSlot.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(attribute);
        settings.add(value);
        settings.add(equipmentSlot);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        Attribute attribute = (Attribute) sCommandToExec.getSettingValue("attribute");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        double value = (double) sCommandToExec.getSettingValue("value");
        EquipmentSlot equipmentSlot = (EquipmentSlot) sCommandToExec.getSettingValue("equipmentSlot");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (attribute == null || item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;
        itemmeta = item.getItemMeta();

        AttributeModifier existingModifier = null;

        try {
            Collection<AttributeModifier> existingModifiers = itemmeta.getAttributeModifiers(attribute);

            if (existingModifiers != null && !existingModifiers.isEmpty()) {
                for (AttributeModifier attributeModifier : existingModifiers) {
                    if (attributeModifier.getSlot().equals(equipmentSlot)) {
                        existingModifier = attributeModifier;
                    }
                }
            }
        } catch (NullPointerException err) {
        }

        if (existingModifier == null) {
            AttributeModifier newModifier = new AttributeModifier(
                    UUID.randomUUID(),
                    "ScoreAttribute",
                    value,
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlot
            );
            itemmeta.addAttributeModifier(attribute, newModifier);
        } else {
            AttributeModifier copyExistingModifier = existingModifier;
            existingModifier = new AttributeModifier(
                    existingModifier.getUniqueId(),
                    "ScoreAttribute",
                    existingModifier.getAmount() + value,
                    existingModifier.getOperation(),
                    existingModifier.getSlot()
            );
            itemmeta.removeAttributeModifier(attribute, existingModifier);
            if (copyExistingModifier.getAmount() + value != 0) {
                itemmeta.addAttributeModifier(attribute, existingModifier);
            }
        }
        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_ATTRIBUTE");
        names.add("ADDITEMATTRIBUTE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_ATTRIBUTE slot:-1 attribute:GENERIC_MAX_HEALTH value:1.0 equipmentSlot:HAND";
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
