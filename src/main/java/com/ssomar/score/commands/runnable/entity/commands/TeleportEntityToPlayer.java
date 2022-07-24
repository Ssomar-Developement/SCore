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

/* TELEPORT ENTITY TO PLAYER */
public class TeleportEntityToPlayer extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        Location pLoc = p.getLocation();

        if (!entity.isDead() && p.isOnline() && !p.isDead())
            entity.teleport(new Location(p.getWorld(), pLoc.getX(), pLoc.getY(), pLoc.getZ()));
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("TELEPORT ENTITY TO PLAYER");
        return names;
    }

    @Override
    public String getTemplate() {
        return "TELEPORT ENTITY TO PLAYER";
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
