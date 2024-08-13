package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
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
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        ActionInfo aInfo = sCommandToExec.getActionInfo();
        List<String> args = sCommandToExec.getOtherArgs();
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
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkEntity(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
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
