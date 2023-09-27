package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/* COPYEFFECTS */
public class CopyEffects extends MixedCommand {

    @Override
    public void run(Player p, LivingEntity receiver, List<String> args, ActionInfo aInfo) {
        if(receiver == null || p == null || p.isDead() ||  receiver.isDead()) return;

        Set<PotionEffect> potionEffects = new HashSet<>(receiver.getActivePotionEffects());
        p.addPotionEffects(potionEffects);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        //if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("COPYEFFECTS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "COPYEFFECTS";
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
