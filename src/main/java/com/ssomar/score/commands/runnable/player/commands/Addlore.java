package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Addlore extends PlayerCommand {

    public Addlore() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, 0);
        slot.setSlot(true);
        CommandSetting text = new CommandSetting("text", 1, String.class, "New lore");
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(text);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        ArrayList<String> list;

        int slot = (int) sCommandToExec.getSettingValue("slot");
        String text = (String) sCommandToExec.getSettingValue("text");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;

        itemmeta = item.getItemMeta();

        list = (ArrayList<String>) itemmeta.getLore();
        if(list == null) list = new ArrayList<>();
        list.add(StringConverter.coloredString(text));
        itemmeta.setLore(list);
        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_LORE");
        names.add("ADDLORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_LORE slot:-1 text:My_new_lore_line";
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
