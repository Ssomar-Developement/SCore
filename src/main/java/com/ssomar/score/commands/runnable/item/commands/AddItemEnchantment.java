package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.SCore;
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

public class AddItemEnchantment extends ItemMetaCommand {

    public AddItemEnchantment() {

        CommandSetting enchantment = new CommandSetting("enchantment", -1, Enchantment.class, SCore.is1v20v5Plus() ? Enchantment.EFFICIENCY : Enchantment.getByName("DIG_SPEED"));
        CommandSetting level = new CommandSetting("level", -1, Integer.class, 1);
        List<CommandSetting> settings = getSettings();
        settings.add(enchantment);
        settings.add(level);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec){
        Enchantment enchant = (Enchantment) sCommandToExec.getSettingValue("enchantment");
        int level = (int) sCommandToExec.getSettingValue("level");

        ItemMeta itemMeta = dynamicMeta.getMeta();
        itemMeta.addEnchant(enchant, level, true);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_ITEM_ENCHANTMENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_ITEM_ENCHANTMENT enchantment:EFFICIENCY level:1";
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
