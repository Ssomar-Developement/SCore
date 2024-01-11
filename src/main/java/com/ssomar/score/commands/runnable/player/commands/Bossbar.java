package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* BOSSBAR {ticks} {color} {text} */
public class Bossbar extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        BarColor color = null;
        Integer duration = NTools.getInteger(args.get(0)).get();
        color = BarColor.valueOf(args.get(1).toUpperCase());


        StringBuilder build = new StringBuilder();
        for (int i = 2; i < args.size(); i++) {
            build.append(args.get(i) + " ");
        }
        String text = build.toString();

        BossBar bossBar = Bukkit.createBossBar(text, color, BarStyle.SOLID);
        bossBar.addPlayer(receiver);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                bossBar.removeAll();
            }
        };
        SCore.schedulerHook.runTask(runnable, duration);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkBarColor(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BOSSBAR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BOSSBAR {ticks} {color} {text}";
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
