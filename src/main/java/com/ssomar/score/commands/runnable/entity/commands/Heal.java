package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* HEAL */
public class Heal extends EntityCommand {


    public Heal() {
        CommandSetting amount = new CommandSetting("amount", 0, Double.class, Double.MIN_VALUE);
        List<CommandSetting> settings = getSettings();
        settings.add(amount);
        setNewSettingsMode(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {

        double amount = (double) sCommandToExec.getSettingValue("amount");
        boolean fullHeal = amount == Double.MIN_VALUE;

        Attribute att = null;
        if(SCore.is1v21v2Plus()) att = Attribute.MAX_HEALTH;
        else att = AttributeUtils.getAttribute("GENERIC_MAX_HEALTH");
        if (!fullHeal) {
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
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HEAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HEAL amount:10";
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
