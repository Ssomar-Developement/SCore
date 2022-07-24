package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticleCommand extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        try {
            receiver.getWorld().spawnParticle(Particle.valueOf(args.get(0).toUpperCase()),
                    receiver.getLocation(),
                    Double.valueOf(args.get(1)).intValue(),
                    Double.parseDouble(args.get(2)),
                    Double.parseDouble(args.get(2)),
                    Double.parseDouble(args.get(2)),
                    Double.parseDouble(args.get(3)), null);
        } catch (Exception ignored) {
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkDouble(args.get(2), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        ArgumentChecker ac3 = checkDouble(args.get(3), isFinalVerification, getTemplate());
        if (!ac3.isValid()) return Optional.of(ac3.getError());


        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PARTICLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PARTICLE {type} {quantity} {offset} {speed}";
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
