package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetItemTooltipStyle extends PlayerCommand {

    public SetItemTooltipStyle() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting String = new CommandSetting("tooltipModel", 1, String.class, "namespace:id");
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(String);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        String namespace = (String) sCommandToExec.getSettingValue("tooltipModel");
        // String namespacesplit = namespace.replace("-",":");
        NamespacedKey material = NamespacedKey.fromString(namespace);
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setTooltipStyle(material);
        item.setItemMeta(itemmeta);

    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_TOOLTIPSTYLE");
        names.add("SETITEMTOOLTIPSTYLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_TOOLTIPSTYLE slot:-1 tooltipModel:namespace:id";
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
