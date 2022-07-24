package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* TELEPORT PLAYER TO ENTITY */
public class TeleportPlayerToEntity extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        Location eLoc = entity.getLocation();

        if (!entity.isDead() && p.isOnline() && !p.isDead())
            p.teleport(new Location(entity.getWorld(), eLoc.getX(), eLoc.getY(), eLoc.getZ()));
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("TELEPORT PLAYER TO ENTITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "TELEPORT PLAYER TO ENTITY";
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
