package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* HEAL */
public class Heal extends EntityCommand {

    @SuppressWarnings("deprecation")
    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        try {
            if (args.size() == 1) {
                int amount = Integer.parseInt(args.get(0));
                if (amount > 0 && !entity.isDead() && entity instanceof LivingEntity) {
                    LivingEntity e = (LivingEntity) entity;
                    if (!SCore.is1v12Less()) {
                        if (e.getHealth() + amount >= e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
                            e.setHealth(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                        } else e.setHealth(e.getHealth() + amount);
                    } else {
                        if (e.getHealth() + amount >= e.getMaxHealth()) {
                            e.setHealth(e.getMaxHealth());
                        } else e.setHealth(e.getHealth() + amount);
                    }
                }
            } else {
                if (!entity.isDead()) {
                    LivingEntity e = (LivingEntity) entity;
                    if (!SCore.is1v12Less()) {
                        e.setHealth(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    } else {
                        e.setHealth(e.getMaxHealth());
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String heal = "HEAL {amount}";
        if (args.size() > 1) error = tooManyArgs + heal;
        else if (args.size() == 1) {
            try {
                Double.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidQuantity + args.get(0) + " for command: " + heal;
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HEAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HEAL {amount}";
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
