package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.VaultAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GiveMoney extends PlayerCommand {

    public GiveMoney() {
        CommandSetting amount = new CommandSetting("amount", 0, Double.class, 50.0);
        List<CommandSetting> settings = getSettings();
        settings.add(amount);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        double regain = (double) sCommandToExec.getSettingValue("amount");
        VaultAPI api = new VaultAPI();
        api.addMoney(receiver, regain);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("GIVE_MONEY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "GIVE_MONEY amount:50.0";
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
