package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/* COPYEFFECTS */
public class CopyEffects extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        if(p.isDead() | receiver.isDead()) return;

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
