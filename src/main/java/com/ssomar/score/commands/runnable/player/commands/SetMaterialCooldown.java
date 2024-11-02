package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetMaterialCooldown extends PlayerCommand {


    public SetMaterialCooldown() {
        CommandSetting material = new CommandSetting("material", 0, Material.class, Material.STONE);
        CommandSetting cooldown = new CommandSetting("cooldown", 1, Integer.class, 10);
        List<CommandSetting> settings = getSettings();
        settings.add(material);
        settings.add(cooldown);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Material mat = (Material) sCommandToExec.getSettingValue("material");
        int cooldown = (int) sCommandToExec.getSettingValue("cooldown");
        receiver.setCooldown(mat, 20 * cooldown);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_MATERIAL_COOLDOWN");
        names.add("SETMATERIALCOOLDOWN");
        return names;
    }

    @Override
    public String getTemplate() {
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
