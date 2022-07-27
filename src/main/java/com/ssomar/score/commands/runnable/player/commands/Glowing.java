package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.features.custom.drop.glowdrop.GlowDropManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* GLOWING */
public class Glowing extends PlayerCommand {

    @Override
    public void run(Player p, Player entity, List<String> args, ActionInfo aInfo) {
        if (!entity.isDead()) {
            int time = Double.valueOf(args.get(0)).intValue();
            ChatColor color = ChatColor.WHITE;
            if (args.size() >= 2) {
                color = ChatColor.valueOf(args.get(1).toUpperCase());
            }
            GlowDropManager.getInstance().addGlow(entity, color);

            final ChatColor finalColor = color;
            BukkitRunnable runnable3 = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!entity.isDead()) {
                        GlowDropManager.getInstance().removeGlow(entity, finalColor);
                    }
                }
            };
            runnable3.runTaskLater(SCore.plugin, time);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if (args.size() >= 2) {
            ArgumentChecker ac2 = checkChatColor(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("GLOWING");
        return names;
    }

    @Override
    public String getTemplate() {
        return "GLOWING {time in ticks} [color]";
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
