package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegainHealth extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        double regain = Double.parseDouble(args.get(0));
        double maxHealth;
        if (SCore.is1v8()) {
            maxHealth = 20;
        } else maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (maxHealth >= receiver.getHealth() + regain)
            receiver.setHealth(receiver.getHealth() + regain);
        else if (receiver.getHealth() + regain < 0) receiver.setHealth(0);
        else receiver.setHealth(maxHealth);
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
