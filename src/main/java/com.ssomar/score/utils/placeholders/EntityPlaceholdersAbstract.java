package com.ssomar.score.utils.placeholders;

import com.ssomar.score.SCore;
import com.ssomar.score.events.PlaceholderLastDamageDealtEvent;
import com.ssomar.score.utils.NTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.io.Serializable;
import java.util.UUID;

public class EntityPlaceholdersAbstract extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String particle;

    /* placeholders of the target entity */
    private UUID entityUUID;
    private String entityType = "";
    private String entityName = "";

    private double x;
    private double y;
    private double z;
    private String world = "";
    private float pitch;
    private float pitchPositive;
    private float yaw;
    private float yawPositive;
    private String direction;

    private String entityDirection;
    private double entityHealth;
    private double entityMaxHealth;

    private String team;


    public EntityPlaceholdersAbstract(String particle) {
        this.particle = particle;
    }

    public void setEntityPlcHldr(UUID uuid) {
        this.entityUUID = uuid;
        this.reloadEntityPlcHldr();
    }

    public void setPlayerPlcHldr(UUID uuid) {
        this.entityUUID = uuid;
        this.reloadEntityPlcHldr();
    }

    public void reloadEntityPlcHldr() {
        Entity entity;
        if (entityUUID == null) return;

        if (SCore.is1v11Less()) {
            entity = getEntityByUniqueId(entityUUID);
        } else {
            entity = Bukkit.getEntity(entityUUID);
        }
        if (entity != null) {
            this.entityType = entity.getType().toString();
            this.entityName = entity.getName();
            Location eLoc = entity.getLocation();
            this.x = eLoc.getX();
            this.y = eLoc.getY();
            this.z = eLoc.getZ();
            this.world = eLoc.getWorld().getName();
            this.pitch = eLoc.getPitch();
            if (pitch < 0) pitchPositive = pitch * -1;
            else pitchPositive = pitch;
            this.yaw = eLoc.getYaw();
            if (yaw < 0) yawPositive = yaw * -1;
            else yawPositive = yaw;
            float yaw = eLoc.getYaw();
            if (yaw >= -30 && yaw <= 30) {
                entityDirection = "S";
            } else if (yaw > 30 && yaw < 60) {
                entityDirection = "SW";
            } else if (yaw >= 60 && yaw <= 120) {
                entityDirection = "W";
            } else if (yaw > 120 && yaw < 150) {
                entityDirection = "NW";
            } else if (yaw >= 150 || yaw <= -150) {
                entityDirection = "N";
            } else if (yaw > -150 && yaw < -120) {
                entityDirection = "NE";
            } else if (yaw >= -120 && yaw <= -60) {
                entityDirection = "E";
            } else if (yaw > -60 && yaw < -30) {
                entityDirection = "SE";
            }
            if (entity instanceof LivingEntity) {
                LivingEntity lE = (LivingEntity) entity;
                this.entityMaxHealth = lE.getMaxHealth();
                this.entityHealth = lE.getHealth();
            } else {
                this.entityMaxHealth = -1;
                this.entityHealth = -1;
            }

            team = "NO_TEAM";
            for (Team t : Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeams()) {
                if (t.hasEntry(entityUUID.toString())) {
                    team = t.getName();
                    break;
                }
            }
        }
    }

    public Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uniqueId))
                    return entity;
            }
        }

        return null;
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if (entityUUID != null) {
            toReplace = toReplace.replaceAll("%" + particle + "%", entityType);
            toReplace = toReplace.replaceAll("%" + particle + "_lower_case%", entityType.toLowerCase());
            toReplace = toReplace.replaceAll("%" + particle + "_name%", entityName);
            toReplace = toReplace.replaceAll("%" + particle + "_name_lower_case%", entityName.toLowerCase());
            toReplace = toReplace.replaceAll("%" + particle + "_uuid%", entityUUID.toString());

            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_x%", NTools.reduceDouble(x, 2) + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_y%", NTools.reduceDouble(y, 2) + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_z%", NTools.reduceDouble(z, 2) + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_x_int%", ((int) x) + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_y_int%", ((int) y) + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_z_int%", ((int) z) + "", true);
            toReplace = toReplace.replaceAll("%" + particle + "_world%", world);
            toReplace = toReplace.replaceAll("%" + particle + "_world_lower%", world.toLowerCase());

            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_pitch%", pitch + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_pitch_int%", ((int) pitch) + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_pitch_positive%", pitchPositive + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_pitch_positive_int%", ((int) pitchPositive) + "", false);

            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_yaw%", yaw + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_yaw_int%", ((int) yaw) + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_yaw_positive%", yawPositive + "", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%" + particle + "_yaw_positive_int%", ((int) yawPositive) + "", false);
            toReplace = toReplace.replaceAll("%" + particle + "_direction%", direction);

            if (entityMaxHealth != -1)
                toReplace = replaceCalculPlaceholder(toReplace, "%entity_max_health%", entityMaxHealth + "", false);
            if (entityHealth != -1)
                toReplace = replaceCalculPlaceholder(toReplace, "%entity_health%", entityHealth + "", false);

            toReplace = toReplace.replaceAll("%" + particle + "_team%", team);

        }

        return toReplace;
    }
}
