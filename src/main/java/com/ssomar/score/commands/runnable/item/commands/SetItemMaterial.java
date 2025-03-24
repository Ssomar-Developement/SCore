package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SetItemMaterial extends ItemCommand {

    public SetItemMaterial() {
        CommandSetting material = new CommandSetting("material", -1, Material.class, Material.STONE);
        List<CommandSetting> settings = getSettings();
        settings.add(material);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, ItemStack itemStack, SCommandToExec sCommandToExec) {
        Material material = (Material) sCommandToExec.getSettingValue("material");

        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        itemStack.setType(material);

    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_MATERIAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_MATERIAL material:STONE";
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
