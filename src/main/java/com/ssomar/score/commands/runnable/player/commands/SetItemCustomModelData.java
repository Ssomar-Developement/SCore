package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemCustomModelData extends PlayerCommand {

    public SetItemCustomModelData() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting customModelData = new CommandSetting("customModelData", 1, Integer.class, 2);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(customModelData);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        int customModelData = (int) sCommandToExec.getSettingValue("customModelData");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;

        itemmeta = item.getItemMeta();

        itemmeta.setCustomModelData(customModelData);

        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_CUSTOM_MODEL_DATA");
        names.add("SETITEMCUSTOMMODELDATA");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_CUSTOM_MODEL_DATA slot:-1 customModelData:2";
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
