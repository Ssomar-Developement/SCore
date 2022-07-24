package com.ssomar.score.usedapi;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public class MythicMobsAPI {

    public static boolean isMythicMob(Entity entity, String mobName) {
        if (SCore.hasMythicMobs) {
            BukkitAPIHelper api = MythicBukkit.inst().getAPIHelper();
            if (api.isMythicMob(entity)) {
                ActiveMob activeMob = api.getMythicMobInstance(entity);
                SsomarDev.testMsg("Name/ID: " + activeMob.getType().getInternalName());
                return mobName.equals(activeMob.getType().getInternalName());
            }
        }
        return false;
    }

    public static boolean isMythicMob(Entity entity, List<String> mobNames) {
        if (SCore.hasMythicMobs) {
            BukkitAPIHelper api = MythicBukkit.inst().getAPIHelper();
            if (api.isMythicMob(entity)) {
                ActiveMob activeMob = api.getMythicMobInstance(entity);
                SsomarDev.testMsg("Name/ID: " + activeMob.getType().getInternalName());
                return mobNames.contains(activeMob.getType().getInternalName());
            }
        }
        return false;
    }

    public static Entity changeToMythicMob(Entity entity, String id) {
        SsomarDev.testMsg("Changing to MythicMob: " + id);
        if (SCore.hasMythicMobs) {
            BukkitAPIHelper api = MythicBukkit.inst().getAPIHelper();
            /* EXCEPTION */
            Location loc = entity.getLocation();
            Vector velocity = entity.getVelocity();
            entity.remove();
            try {
                SsomarDev.testMsg("Changing to MythicMob SPAWN: " + id);
                Entity newEntity = api.spawnMythicMob(id, loc);
                SsomarDev.testMsg("Changing to MythicMob SPAWN VALID: " + id);
                newEntity.setVelocity(velocity);
                return newEntity;
            } catch (InvalidMobTypeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
