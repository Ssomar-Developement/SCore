package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegainFood extends PlayerCommand {

    public RegainFood() {
        CommandSetting amount = new CommandSetting("amount", 0, Integer.class, 5);
        List<CommandSetting> settings = getSettings();
        settings.add(amount);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int regain = (int) sCommandToExec.getSettingValue("amount");
        receiver.setFoodLevel(receiver.getFoodLevel() + regain);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REGAIN_FOOD");
        names.add("REGAIN FOOD");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REGAIN_FOOD amount:5";
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
