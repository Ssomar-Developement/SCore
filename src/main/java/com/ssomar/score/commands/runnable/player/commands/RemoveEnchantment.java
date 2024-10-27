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
import java.util.Map;

public class RemoveEnchantment extends PlayerCommand {

    public RemoveEnchantment() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting enchantment = new CommandSetting("enchantment", 1, Enchantment.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(enchantment);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemMeta;
        int slot = (int) sCommandToExec.getSettingValue("slot");
        Enchantment enchantment = (Enchantment) sCommandToExec.getSettingValue("enchantment");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;
        itemMeta = item.getItemMeta();

        if (enchantment == null) {
            Map<Enchantment, Integer> enchantmentsOfItem = itemMeta.getEnchants();
            for (Enchantment enchants : enchantmentsOfItem.keySet()) {
                itemMeta.removeEnchant(enchants);
            }
        } else {
            itemMeta.removeEnchant(enchantment);
        }
        item.setItemMeta(itemMeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REMOVE_ENCHANTMENT");
        names.add("REMOVEENCHANTMENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REMOVE_ENCHANTMENT slot:-1 enchantment:EFFICIENCY";
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
