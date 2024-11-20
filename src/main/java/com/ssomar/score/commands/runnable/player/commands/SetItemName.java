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

public class SetItemName extends PlayerCommand {

    public SetItemName() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting name = new CommandSetting("name", 1, String.class, "&eNew_name");
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(name);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        String name = (String) sCommandToExec.getSettingValue("name");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        itemmeta = item.getItemMeta();

        StringBuilder build = new StringBuilder(name);

        for (String s : sCommandToExec.getOtherArgs()) {
            build.append(s + " ");
        }

        itemmeta.setDisplayName(StringConverter.coloredString(build.toString()));

        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_NAME");
        names.add("SETITEMNAME");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_NAME slot:-1 name:&eNew_name";
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
