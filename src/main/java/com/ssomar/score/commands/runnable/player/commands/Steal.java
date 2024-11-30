package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Steal extends PlayerCommand {

    public Steal() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting removeItem = new CommandSetting("removeItem", 1, Boolean.class, true);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(removeItem);
        setNewSettingsMode(true);
    }


    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        if (receiver.isDead() | p.isDead()) return;

        boolean remove = (boolean) sCommandToExec.getSettingValue("removeItem");
        int slot = (int) sCommandToExec.getSettingValue("slot");

        ItemStack itemtosteal;

        if (slot == -1) itemtosteal = receiver.getInventory().getItemInMainHand();
        else itemtosteal = receiver.getInventory().getItem(slot);

        if (itemtosteal.getType() == Material.AIR) return;

        if (remove) {
            if (slot == -1) receiver.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            else receiver.getInventory().setItem(slot, new ItemStack(Material.AIR));
        }

        if (p.getInventory().firstEmpty() == -1)
            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), itemtosteal);
        else p.getInventory().addItem(itemtosteal);

    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STEAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STEAL slot:-1 removeItem:true";
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
