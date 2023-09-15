package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* OXYGEN {timeinticks} */
public class Oxygen extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        double oxygen = Double.parseDouble(args.get(0));
        double currentoxygen = receiver.getRemainingAir();
        double finaloxygen = 0;

        if(currentoxygen + oxygen < 0) finaloxygen = 0;
        else finaloxygen = currentoxygen + oxygen;

        SsomarDev.testMsg("OXYGEN: "+finaloxygen, true);
        receiver.setRemainingAir((int) finaloxygen);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("OXYGEN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "OXYGEN {timeinticks}";
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
