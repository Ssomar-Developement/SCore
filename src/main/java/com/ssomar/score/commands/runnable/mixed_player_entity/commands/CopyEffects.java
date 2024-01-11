package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/* COPYEFFECTS */
public class CopyEffects extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        if(!(receiver instanceof LivingEntity)) return;
        LivingEntity livingReceiver = (LivingEntity) receiver;

        if(receiver == null || p == null || p.isDead() ||  receiver.isDead()) return;

        int limitDuration = Integer.MAX_VALUE;
        if(args.size() > 0) limitDuration = Integer.parseInt(args.get(0));

        Set<PotionEffect> potionEffects = new HashSet<>(livingReceiver.getActivePotionEffects());
        for(PotionEffect pe : potionEffects){
            if(pe.getDuration() > limitDuration) pe = new PotionEffect(pe.getType(), limitDuration, pe.getAmplifier(), pe.isAmbient(), pe.hasParticles(), pe.hasIcon());
        }
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
        return "COPYEFFECTS [limitDuration]";
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
