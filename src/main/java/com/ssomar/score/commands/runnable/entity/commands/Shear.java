package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/* SHEAR} */
public class Shear extends EntityCommand {

    public Shear() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        ActionInfo aInfo = sCommandToExec.getActionInfo();

        EntityType mushroomCowType = SCore.is1v20v5Plus() ? EntityType.MOOSHROOM : EntityType.fromName("MUSHROOM_COW");
        EntityType snowmanType = SCore.is1v20v5Plus() ? EntityType.SNOW_GOLEM : EntityType.fromName("SNOWMAN");
        if (entity.getType() == mushroomCowType) {

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
        } else if (entity.getType() == snowmanType) {
            Snowman snowman = (Snowman) entity;
            if (snowman.isDerp()) return;

            snowman.setDerp(true);
        }

    }

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
