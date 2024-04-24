package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SPAWNENTITYONCURSOR {entity} {amount} {maxRange} */
public class SpawnEntityOnCursor extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int range = 200;
        int amount = 1;
        EntityType entityType = EntityType.valueOf(args.get(0).toUpperCase());

        if (args.size() >= 2) {
            amount = Double.valueOf(args.get(1)).intValue();
        }

        if (args.size() >= 3) {
            range = Double.valueOf(args.get(2)).intValue();
        }

        Block block = receiver.getTargetBlock(null, range);

        if (!block.getType().equals(Material.AIR)) {

            Location loc = block.getLocation();
            loc.add(0, 1, 0);

            EntityType lightning = SCore.is1v20v5Plus() ? EntityType.LIGHTNING_BOLT : EntityType.valueOf("LIGHTNING");
            if (entityType.equals(lightning)) {
                for (int i = 0; i < amount; i++) receiver.getWorld().strikeLightning(loc);
            } else {
                for (int i = 0; i < amount; i++) {
                    Entity e = receiver.getWorld().spawnEntity(loc, entityType);
                    if (entityType.equals(EntityType.FIREBALL)) e.setVelocity(new Vector(0, 0, 0));
                }
            }


        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkEntity(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if (args.size() >= 2) {
            ArgumentChecker ac2 = checkInteger(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        if (args.size() >= 3) {
            ArgumentChecker ac3 = checkInteger(args.get(2), isFinalVerification, getTemplate());
            if (!ac3.isValid()) return Optional.of(ac3.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SPAWNENTITYONCURSOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SPAWNENTITYONCURSOR {entity} [amount] [maxRange]";
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
