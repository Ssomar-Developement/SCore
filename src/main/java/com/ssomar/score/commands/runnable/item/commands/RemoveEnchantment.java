package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveEnchantment extends ItemMetaCommand {

    public RemoveEnchantment() {
        CommandSetting enchantment = new CommandSetting("enchantment", -1, Enchantment.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(enchantment);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec) {
        Enchantment enchantment = (Enchantment) sCommandToExec.getSettingValue("enchantment");

        ItemMeta itemMeta = dynamicMeta.getMeta();

        if (enchantment == null) {
            Map<Enchantment, Integer> enchantmentsOfItem = itemMeta.getEnchants();
            for (Enchantment enchants : enchantmentsOfItem.keySet()) {
                itemMeta.removeEnchant(enchants);
            }
        } else {
            itemMeta.removeEnchant(enchantment);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REMOVE_ENCHANTMENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REMOVE_ENCHANTMENT enchantment:EFFICIENCY";
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
