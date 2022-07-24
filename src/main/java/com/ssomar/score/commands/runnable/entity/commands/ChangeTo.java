package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* CHANGETO {entityType} */
@SuppressWarnings("deprecation")
public class ChangeTo extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        /* EXCEPTION */
        Location loc = entity.getLocation();
        Vector velocity = entity.getVelocity();
        entity.remove();
        Entity newEntity = loc.getWorld().spawnEntity(loc, EntityType.fromName(args.get(0)));
        newEntity.setVelocity(velocity);
        aInfo.setEntityUUID(newEntity.getUniqueId());
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String changeto = "CHANGETO {entityType}";
        if (args.size() < 1) error = notEnoughArgs + changeto;
        else if (args.size() == 1) {
            if (EntityType.fromName(args.get(0)) == null)
                error = invalidEntityType + args.get(0) + " for command: " + changeto;
        } else error = tooManyArgs + changeto;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CHANGETO");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CHANGETO {entityType}";
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
