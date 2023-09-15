package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SHEAR} */
public class Shear extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {

        if (entity.getType() == EntityType.MUSHROOM_COW) {

            Location loc = entity.getLocation();
            Vector velocity = entity.getVelocity();

            entity.remove();
            Entity newEntity = loc.getWorld().spawnEntity(loc, EntityType.COW);
            newEntity.setVelocity(velocity);
            aInfo.setEntityUUID(newEntity.getUniqueId());


        } else if (entity.getType() == EntityType.SHEEP) {
            Sheep sheep = (Sheep) entity;
            if (sheep.isSheared()) return;

            sheep.setSheared(true);
        } else if (entity.getType() == EntityType.SNOWMAN) {
            Snowman snowman = (Snowman) entity;
            if (snowman.isDerp()) return;

            snowman.setDerp(true);
        }

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) { return Optional.empty(); }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SHEAR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SHEAR";
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
