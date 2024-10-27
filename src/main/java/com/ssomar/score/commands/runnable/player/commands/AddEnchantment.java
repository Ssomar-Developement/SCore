package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddEnchantment extends PlayerCommand {

    public AddEnchantment() {

        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, 0);
        slot.setSlot(true);
        CommandSetting enchantment = new CommandSetting("enchantment", 1, Enchantment.class, Enchantment.EFFICIENCY);
        CommandSetting level = new CommandSetting("level", 2, Integer.class, 1);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(enchantment);
        settings.add(level);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec){
        ItemStack item;
        ItemMeta itemMeta;
        int slot = (int) sCommandToExec.getSettingValue("slot");
        Enchantment enchant = (Enchantment) sCommandToExec.getSettingValue("enchantment");
        int level = (int) sCommandToExec.getSettingValue("level");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
        if (level <= 0 || enchant == null || item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;
        itemMeta = item.getItemMeta();
        itemMeta.addEnchant(enchant, level, true);
        item.setItemMeta(itemMeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_ENCHANTMENT");
        names.add("ADDENCHANTMENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_ENCHANTMENT slot:-1 enchantment:EFFICIENCY level:1";
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
