package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegainHealth extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        if(!(receiver instanceof LivingEntity)) return;
        LivingEntity livingReceiver = (LivingEntity) receiver;

        double regain = Double.parseDouble(args.get(0));
        double maxHealth;
        if (SCore.is1v8()) {
            maxHealth = 20;
        } else maxHealth = livingReceiver.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (livingReceiver.getHealth() + regain < 0) livingReceiver.setHealth(0);
        else if (maxHealth >= livingReceiver.getHealth() + regain)
            livingReceiver.setHealth(livingReceiver.getHealth() + regain);
        else{
            livingReceiver.setHealth(maxHealth);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REGAIN HEALTH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REGAIN HEALTH {amount}";
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
