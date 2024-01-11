package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.features.custom.drop.glowdrop.GlowDropManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* GLOWING */
public class Glowing extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        if (!receiver.isDead()) {
            int time = Double.valueOf(args.get(0)).intValue();
            ChatColor color = ChatColor.WHITE;
            if (args.size() >= 2) {
                color = ChatColor.valueOf(args.get(1).toUpperCase());
            }
            GlowDropManager.getInstance().addGlow(receiver, color);

            final ChatColor finalColor = color;
            BukkitRunnable runnable3 = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!receiver.isDead()) {
                        GlowDropManager.getInstance().removeGlow(receiver, finalColor);
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
