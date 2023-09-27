package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.commands.runnable.player.events.StunEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StunEnable extends MixedCommand {

    @Override
    public void run(Player p, LivingEntity receiver, List<String> args, ActionInfo aInfo) {
        Location correctAnimation = receiver.getLocation();
        correctAnimation.setPitch(-10);
        receiver.teleport(correctAnimation);
        receiver.setGliding(true);
        StunEvent.stunPlayers.put(receiver.getUniqueId(), false);
        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                if (StunEvent.stunPlayers.containsKey(receiver.getUniqueId()))
                    StunEvent.stunPlayers.put(receiver.getUniqueId(), true);
            }
        };
        runnable3.runTaskLater(SCore.plugin, 20);
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STUN_ENABLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STUN_ENABLE";
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