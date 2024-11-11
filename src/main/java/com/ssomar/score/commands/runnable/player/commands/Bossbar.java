package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Bossbar extends PlayerCommand {

    public Bossbar() {
        CommandSetting time = new CommandSetting("time", 0, Integer.class, 200);
        CommandSetting color = new CommandSetting("color", 1, BarColor.class, BarColor.BLUE);
        CommandSetting text = new CommandSetting("text", 2, String.class, "Hello_world");
        text.setAcceptUnderScoreForLongText(true);
        List<CommandSetting> settings = getSettings();
        settings.add(time);
        settings.add(color);
        settings.add(text);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {

        BarColor color = (BarColor) sCommandToExec.getSettingValue("color");
        String text = (String) sCommandToExec.getSettingValue("text");
        Integer time = (Integer) sCommandToExec.getSettingValue("time");
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(text);
        message.append(" ");
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        if(!message.toString().isEmpty()) {
            BossBar bossBar = Bukkit.createBossBar(text, color, BarStyle.SOLID);
            bossBar.addPlayer(receiver);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    bossBar.removeAll();
                }
            };
            SCore.schedulerHook.runTask(runnable, time);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BOSSBAR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BOSSBAR time:200 color:BLUE text:Hello_world";
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
