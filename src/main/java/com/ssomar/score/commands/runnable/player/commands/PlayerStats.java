package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats extends PlayerCommand {

    public PlayerStats() {
        CommandSetting showInChat = new CommandSetting("showInChat", 0, Boolean.class, true, true);
        List<CommandSetting> settings = getSettings();
        settings.add(showInChat);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Boolean showInChat = (Boolean) sCommandToExec.getSettingValue("showInChat");
        if (showInChat == null) showInChat = true;

        long playTimeTicks = receiver.getStatistic(Statistic.PLAY_ONE_MINUTE);
        long playTimeSeconds = playTimeTicks / 20;
        long hours = playTimeSeconds / 3600;
        long minutes = (playTimeSeconds % 3600) / 60;

        int deaths = receiver.getStatistic(Statistic.DEATHS);
        int playerKills = receiver.getStatistic(Statistic.PLAYER_KILLS);
        int mobKills = receiver.getStatistic(Statistic.MOB_KILLS);

        int damageDealt = receiver.getStatistic(Statistic.DAMAGE_DEALT) / 10;
        int damageTaken = receiver.getStatistic(Statistic.DAMAGE_TAKEN) / 10;

        int jumps = receiver.getStatistic(Statistic.JUMP);
        int blocksBroken = receiver.getStatistic(Statistic.MINE_BLOCK);

        if (showInChat) {
            sm.sendMessage(receiver, "&6&l--- Player Statistics ---", false);
            sm.sendMessage(receiver, "&ePlaytime: &f" + hours + "h " + minutes + "m", false);
            sm.sendMessage(receiver, "&eDeaths: &f" + deaths, false);
            sm.sendMessage(receiver, "&ePlayer Kills: &f" + playerKills, false);
            sm.sendMessage(receiver, "&eMob Kills: &f" + mobKills, false);
            sm.sendMessage(receiver, "&eDamage Dealt: &f" + damageDealt + " HP", false);
            sm.sendMessage(receiver, "&eDamage Taken: &f" + damageTaken + " HP", false);
            sm.sendMessage(receiver, "&eJumps: &f" + jumps, false);
            sm.sendMessage(receiver, "&eBlocks Mined: &f" + blocksBroken, false);
            sm.sendMessage(receiver, "&6&l--------------------------", false);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PLAYER_STATS");
        names.add("PLAYERSTATS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PLAYER_STATS [showInChat:true]";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GOLD;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.YELLOW;
    }
}
