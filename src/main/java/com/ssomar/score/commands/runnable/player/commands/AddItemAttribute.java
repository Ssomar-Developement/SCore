package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;


public class AddItemAttribute extends PlayerCommand {

    public AddItemAttribute() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, 0);
        slot.setSlot(true);
        Attribute att = null;
        if(SCore.is1v21v2Plus()) att = Attribute.MAX_HEALTH;
        else att = AttributeUtils.getAttribute("GENERIC_MAX_HEALTH");
        CommandSetting attribute = new CommandSetting("attribute", 1, Attribute.class, att);
        CommandSetting value = new CommandSetting("value", 2, Double.class, 1.0);
        CommandSetting equipmentSlot = new CommandSetting("equipmentSlot", 3, EquipmentSlot.class, null);
        CommandSetting stack = new CommandSetting("stack", 4, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(attribute);
        settings.add(value);
        settings.add(equipmentSlot);
        settings.add(stack);
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
        boolean stack = (boolean) sCommandToExec.getSettingValue("stack");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (attribute == null || item == null || item.getType() == Material.AIR){
            SsomarDev.testMsg("Error in the command ADD_ATTRIBUTE please check the attribute or the item > att:"+attribute+" item:"+item, true);
            return;
        }
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }
        itemmeta = item.getItemMeta();

        AttributeModifier newModifier = new AttributeModifier(
                    UUID.randomUUID(),
                    "ScoreAttribute",
                    value,
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlot
            );

        LinkedHashMap<Attribute, AttributeModifier> map = new LinkedHashMap<>();
        map.put(attribute, newModifier);
        AttributeUtils.addAttributeOnItemMeta(itemmeta, item.getType(), map, true, true, true, stack);
        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_ITEM_ATTRIBUTE");
        names.add("ADD_ATTRIBUTE");
        names.add("ADDITEMATTRIBUTE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_ITEM_ATTRIBUTE slot:-1 attribute:GENERIC_MAX_HEALTH value:1.0 equipmentSlot:HAND stack:false";
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
