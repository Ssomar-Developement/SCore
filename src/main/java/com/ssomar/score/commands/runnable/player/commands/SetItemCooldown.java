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
import org.bukkit.inventory.meta.components.UseCooldownComponent;

import java.util.ArrayList;
import java.util.List;

public class SetItemCooldown extends PlayerCommand {


    public SetItemCooldown() {
        CommandSetting material = new CommandSetting("material", 0, Material.class, Material.STONE);
        CommandSetting cooldown = new CommandSetting("cooldown", 1, Integer.class, 10);
        CommandSetting group = new CommandSetting("group", -1, String.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(material);
        settings.add(cooldown);
        settings.add(group);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Material mat = (Material) sCommandToExec.getSettingValue("material");
        int cooldown = (int) sCommandToExec.getSettingValue("cooldown");
        String group = (String) sCommandToExec.getSettingValue("group");
        if(group != null && !group.isEmpty() && SCore.is1v21v2Plus()) {
            ItemStack item = new ItemStack(Material.STONE);
            ItemMeta meta = item.getItemMeta();
            UseCooldownComponent component = (UseCooldownComponent) meta;
            component.setCooldownGroup(NamespacedKey.fromString(group));
            meta.setUseCooldown(component);
            item.setItemMeta(meta);
            receiver.setCooldown(item, 20 * cooldown);
        }
        else receiver.setCooldown(mat, 20 * cooldown);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_COOLDOWN");
        names.add("SET_MATERIAL_COOLDOWN");
        names.add("SETMATERIALCOOLDOWN");
        return names;
    }

    @Override
    public String getTemplate() {
        if(SCore.is1v21v2Plus()) return "SET_ITEM_COOLDOWN material:STONE or group:my_cooldown_group cooldown:10";
        return "SET_MATERIAL_COOLDOWN material:STONE cooldown:10";
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
