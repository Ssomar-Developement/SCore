package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/* ADDITEMATTRIBUTE {slot} {Attribute} {value} {equipment slot}*/
public class AddItemAttribute extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        ItemStack item;
        ItemMeta itemmeta;
        Attribute attribute;
        int slot = Integer.parseInt(args.get(0));

        try {
            if(slot == -1) item = receiver.getInventory().getItemInMainHand();
            else item = receiver.getInventory().getItem(slot);

            itemmeta = item.getItemMeta();
        } catch (NullPointerException e) {
            return;
        }

        try {
             attribute = Attribute.valueOf(args.get(1).toUpperCase());
        }catch(IllegalArgumentException er){
            return;
        }

        double attributeValue = Double.parseDouble(args.get(2));

        EquipmentSlot equipmentSlot;
        try {
            equipmentSlot = EquipmentSlot.valueOf(args.get(3).toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return;
        }

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
        }catch(NullPointerException err){
        }

        if(existingModifier == null) {
            AttributeModifier newModifier = new AttributeModifier(
                    UUID.randomUUID(),
                    "ScoreAttribute",
                    attributeValue,
                    AttributeModifier.Operation.ADD_NUMBER,
                    equipmentSlot
            );
            itemmeta.addAttributeModifier(attribute, newModifier);
        }else {
            AttributeModifier copyExistingModifier = existingModifier;
            existingModifier = new AttributeModifier(
                    existingModifier.getUniqueId(),
                    "ScoreAttribute",
                    existingModifier.getAmount() + attributeValue,
                    existingModifier.getOperation(),
                    existingModifier.getSlot()
            );
            itemmeta.removeAttributeModifier(attribute, existingModifier);
            if(copyExistingModifier.getAmount() + attributeValue != 0) {
                itemmeta.addAttributeModifier(attribute, existingModifier);
            }
        }
        item.setItemMeta(itemmeta);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADDITEMATTRIBUTE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADDITEMATTRIBUTE {slot} {Attribute} {value} {equipment slot}";
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
