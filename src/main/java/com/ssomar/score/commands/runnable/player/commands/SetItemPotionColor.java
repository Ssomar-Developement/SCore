package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemPotionColor extends PlayerCommand {

    public SetItemPotionColor() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting String = new CommandSetting("color", 1, Integer.class, 0);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(String);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        int namespace = (int) sCommandToExec.getSettingValue("color");
        // String namespacesplit = namespace.replace("-",":");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        PotionMeta itemmeta = (PotionMeta) item.getItemMeta();
        itemmeta.setColor(Color.fromRGB(namespace));
        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_POTIONCOLOR");
        names.add("SETITEMPOTIONCOLOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_POTIONCOLOR slot:-1 color:12354";
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
