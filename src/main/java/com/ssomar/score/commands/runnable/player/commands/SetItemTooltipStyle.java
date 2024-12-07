package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SetItemTooltipStyle extends PlayerCommand {

    public SetItemTooltipStyle() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting material = new CommandSetting("tooltipModel", 1, String.class, "namespace:id");
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(material);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        Material material = (Material) sCommandToExec.getSettingValue("tooltipModel");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;

        item.setType(material);

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
