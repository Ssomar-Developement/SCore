package com.ssomar.score.utils.placeholders;

import com.ssomar.score.events.PlaceholderLastDamageDealtEvent;
import com.ssomar.score.utils.NTools;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class PlayerPlaceholdersAbstract extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String particle;
    private boolean acceptWithoutParticle;

    /* placeholders of the player */
    @Getter
    private UUID playerUUID;
    private String player = "";

    private double x;
    private double y;
    private double z;
    private String world = "";
    private float pitch;
    private float pitchPositive;
    private float yaw;
    private float yawPositive;
    private String direction;

    private int fixSlot;
    private String slot = "";
    private String slotLive = "";

    private double lastDamageTaken;
    private double lastDamageDealt;

    public PlayerPlaceholdersAbstract(String particle, boolean acceptWithoutParticle) {
        this.particle = particle;
        this.acceptWithoutParticle = acceptWithoutParticle;
    }

    public void setPlayerPlcHldr(UUID uuid) {
        this.playerUUID = uuid;
        this.reloadPlayerPlcHldr();
    }

    public void setPlayerPlcHldr(UUID uuid, int fixSlot) {
        this.playerUUID = uuid;
        this.fixSlot = fixSlot;
        this.reloadPlayerPlcHldr();
    }

    public void reloadPlayerPlcHldr() {
        Player player;
        if(this.playerUUID != null && (player = Bukkit.getPlayer(playerUUID)) != null) {
            this.player = player.getName();
            this.playerUUID = player.getUniqueId();
            Location pLoc = player.getLocation();
            this.x = pLoc.getX();
            this.y = pLoc.getY();
            this.z = pLoc.getZ();
            this.world = pLoc.getWorld().getName();
            if(fixSlot != -1) this.slot = fixSlot+"";
            else this.slot = player.getInventory().getHeldItemSlot()+"";
            this.slotLive = player.getInventory().getHeldItemSlot()+"";
            this.lastDamageTaken = player.getLastDamage();
            this.pitch = pLoc.getPitch();
            if(pitch < 0) pitchPositive = pitch * -1;
            else pitchPositive = pitch;
            this.yaw = pLoc.getYaw();
            if(yaw < 0) yawPositive = yaw * -1;
            else yawPositive = yaw;
            float yaw = pLoc.getYaw();
            if(yaw >= -30 && yaw <= 30){
                direction = "S";
            }
            else if(yaw > 30 && yaw < 60){
                direction = "SW";
            }
            else if(yaw >= 60 && yaw <= 120){
                direction = "W";
            }
            else if(yaw > 120 && yaw < 150){
                direction = "NW";
            }
            else if(yaw >= 150 || yaw <= -150){
                direction = "N";
            }
            else if(yaw > -150 && yaw <-120 ){
                direction = "NE";
            }
            else if(yaw >= -120 && yaw <= -60){
                direction = "E";
            }
            else if(yaw > -60 && yaw <-30 ){
                direction = "SE";
            }
            if(PlaceholderLastDamageDealtEvent.getInstance().lastDamageDealt.containsKey(playerUUID)) {
                this.lastDamageDealt = PlaceholderLastDamageDealtEvent.getInstance().lastDamageDealt.get(playerUUID);
            }
            else this.lastDamageDealt = 0;
        }
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if(playerUUID != null) {
            toReplace = toReplace.replaceAll("%"+particle+"%", player);
            toReplace = toReplace.replaceAll("%"+particle+"_name%", player);
            toReplace = toReplace.replaceAll("%"+particle+"_uuid%", playerUUID.toString());

            /* I need to let that because old versions doesnt have particle */
            if(acceptWithoutParticle) {
                toReplace = replaceCalculPlaceholder(toReplace, "%x%", NTools.reduceDouble(x, 2)+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%y%", NTools.reduceDouble(y, 2)+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%z%", NTools.reduceDouble(z, 2)+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%x_int%", ((int) x)+"", true);
                toReplace = replaceCalculPlaceholder(toReplace, "%y_int%", ((int) y)+"", true);
                toReplace = replaceCalculPlaceholder(toReplace, "%z_int%", ((int) z)+"", true);

                toReplace = toReplace.replaceAll("%world%", world);
                toReplace = toReplace.replaceAll("%world%_lower%", world);
                toReplace = toReplace.replaceAll("%slot%", slot);
                toReplace = toReplace.replaceAll("%slot_live%", slotLive);

                toReplace = replaceCalculPlaceholder(toReplace, "%last_damage_taken%", lastDamageTaken+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%last_damage_taken_int%", ((int) lastDamageTaken)+"", true);
                toReplace = replaceCalculPlaceholder(toReplace, "%last_damage_dealt%", lastDamageDealt+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%last_damage_dealt_int%", ((int) lastDamageDealt)+"", true);
                toReplace = replaceCalculPlaceholder(toReplace, "%pitch%", pitch+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%pitch_int%", ((int) pitch)+"", true);
                toReplace = replaceCalculPlaceholder(toReplace, "%pitch_positive%", pitchPositive+"", false);
                toReplace = replaceCalculPlaceholder(toReplace, "%pitch_positive_int%", ((int) pitchPositive)+"", false);
                toReplace = toReplace.replaceAll("%direction%", direction);
            }

            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_x%", NTools.reduceDouble(x, 2)+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_y%", NTools.reduceDouble(y, 2)+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_z%", NTools.reduceDouble(z, 2)+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_x_int%", ((int) x)+"", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_y_int%", ((int) y)+"", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_z_int%", ((int) z)+"", true);
            toReplace = toReplace.replaceAll("%"+particle+"_world%", world);
            toReplace = toReplace.replaceAll("%"+particle+"_world_lower%", world.toLowerCase());
            toReplace = toReplace.replaceAll("%"+particle+"_slot%", slot);
            toReplace = toReplace.replaceAll("%"+particle+"_slot_live%", slotLive);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_last_damage_taken%", lastDamageTaken+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_last_damage_taken_int%", ((int) lastDamageTaken)+"", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_last_damage_dealt%", lastDamageTaken+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_last_damage_dealt_int%", ((int) lastDamageTaken)+"", true);

            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_pitch%", pitch+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"er_pitch_int%", ((int) pitch)+"", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_pitch_positive%", pitchPositive+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_pitch_positive_int%", ((int) pitchPositive)+"", false);

            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_yaw%", yaw+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_yaw_int%", ((int) yaw)+"", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_yaw_positive%", yawPositive+"", false);
            toReplace = replaceCalculPlaceholder(toReplace, "%"+particle+"_yaw_positive_int%", ((int) yawPositive)+"", false);
            toReplace = toReplace.replaceAll("%"+particle+"_direction%", direction);
        }

        return toReplace;
    }
}
