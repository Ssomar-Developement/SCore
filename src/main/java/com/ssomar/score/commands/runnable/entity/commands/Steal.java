package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
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

public class Steal extends EntityCommand {

    public Steal() {
        CommandSetting slot = new CommandSetting("slot", 0, EquipmentSlot.class, EquipmentSlot.HAND);
        CommandSetting remove = new CommandSetting("remove", 1, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(remove);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        EquipmentSlot slot = (EquipmentSlot) sCommandToExec.getSettingValue("slot");
        boolean remove = (boolean) sCommandToExec.getSettingValue("remove");

        if(entity.isDead() | p.isDead()) return;

        LivingEntity livingEntity;
        if (entity instanceof LivingEntity) {
            livingEntity = (LivingEntity) entity;
        }
        else return;

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

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STEAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STEAL slot:HAND remove:false";
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
