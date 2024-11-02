package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.emums.AttributeRework;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* HEAL */
public class Heal extends EntityCommand {

    @SuppressWarnings("deprecation")
    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        Attribute att = null;
        if(SCore.is1v21v2Plus()) att = Attribute.MAX_HEALTH;
        else att = AttributeRework.getAttribute("GENERIC_MAX_HEALTH");
        if (args.size() >= 1) {
            int amount = Double.valueOf(args.get(0)).intValue();
            if (amount > 0 && !entity.isDead() && entity instanceof LivingEntity) {
                LivingEntity e = (LivingEntity) entity;
                if (!SCore.is1v12Less()) {
                    e.setHealth(Math.min(e.getHealth() + amount, Objects.requireNonNull(e.getAttribute(att)).getValue()));
                } else {
                    e.setHealth(Math.min(e.getHealth() + amount, e.getMaxHealth()));
                }
            }
        } else {
            if (!entity.isDead()) {
                LivingEntity e = (LivingEntity) entity;
                if (!SCore.is1v12Less()) {
                    e.setHealth(e.getAttribute(att).getValue());
                } else {
                    e.setHealth(e.getMaxHealth());
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() > 1) {
            ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return Optional.empty();
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
