package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* PARTICLE {type} {quantity} {offset} {speed} */
public class ParticleCommand extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        try {
            entity.getWorld().spawnParticle(Particle.valueOf(args.get(0).toUpperCase()),
                    entity.getLocation(),
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
        return com.ssomar.score.commands.runnable.player.commands.ParticleCommand.staticVerif(args, isFinalVerification, getTemplate());
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
