package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*  */
public class Steal extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {

        if(entity.isDead() | p.isDead()) return;

        boolean remove = true;
        if(args.size() == 2){
            if (args.get(1).equalsIgnoreCase("false")) remove = false;
        }

        EquipmentSlot slot = getSlot(args.get(0));
        if(slot == null) return;

        LivingEntity livingEntity;
        if (entity instanceof LivingEntity) {
            livingEntity = (LivingEntity) entity;
        }else{
            return;
        }

        try {

            ItemStack itemtosteal = livingEntity.getEquipment().getItem(slot);
            if(itemtosteal.getType() == Material.AIR) return;

            if(remove){
                livingEntity.getEquipment().setItem(slot,new ItemStack(Material.AIR));
            }

            if(p.getInventory().firstEmpty() == -1) Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(),itemtosteal);
            else p.getInventory().addItem(itemtosteal);
        }catch(NullPointerException | IllegalArgumentException e){
            return;
        }
    }

    public EquipmentSlot getSlot(String slotS){
        String slotS2 = slotS.toLowerCase();
        switch (slotS2){
            case "head":
                return EquipmentSlot.HEAD;
            case "chest":
                return EquipmentSlot.CHEST;
            case "legs":
                return EquipmentSlot.LEGS;
            case "feet":
                return EquipmentSlot.FEET;
            case "hand":
                return EquipmentSlot.HAND;
            case "offhand":
                return EquipmentSlot.OFF_HAND;
            default:
                return null;
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        if(args.size() >= 2){
            ArgumentChecker ac2 = checkBoolean(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STEAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STEAL [slot name] {remove item default true}";
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
