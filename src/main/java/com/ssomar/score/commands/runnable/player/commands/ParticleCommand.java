package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticleCommand extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
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
       return staticVerif(args, isFinalVerification, getTemplate());
    }

    public static Optional<String> staticVerif(List<String> args, boolean isFinalVerification, String template) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + template);

        ArgumentChecker ac = checkInteger(args.get(1), isFinalVerification, template);
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkDouble(args.get(2), isFinalVerification, template);
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        ArgumentChecker ac3 = checkDouble(args.get(3), isFinalVerification, template);
        if (!ac3.isValid()) return Optional.of(ac3.getError());

        return Optional.empty();
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
