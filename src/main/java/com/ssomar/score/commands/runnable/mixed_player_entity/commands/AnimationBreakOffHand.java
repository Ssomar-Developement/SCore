package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* TOTEM_ANIMATION */
public class AnimationBreakOffHand extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        receiver.playEffect(EntityEffect.TOTEM_RESURRECT);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("~ANIMATION_BREAK_OFF_HAND");
        names.add("BREAK_OFF_HAND_ANIMATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BREAK_OFF_HAND_ANIMATION";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.GOLD;
    }
}
