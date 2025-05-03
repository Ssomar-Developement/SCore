package com.ssomar.score.utils.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum PLAYER_PLACEHOLDERS_TEST_NEW {

    X(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION),
    Y(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION),
    Z(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION),
    PITCH(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION),
    PITCH_POSITIVE(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION),
    YAW(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION),
    YAW_POSITIVE(PLAYER_DATA.PLAYER, PLAYER_DATA.LOCATION);

    public PLAYER_DATA [] requirePlayerData;

    PLAYER_PLACEHOLDERS_TEST_NEW(PLAYER_DATA... requirePlayerData) {
        this.requirePlayerData = requirePlayerData;
    }

    public enum PLAYER_DATA {
        PLAYER,
        LOCATION
    }

    public static Map<String, PLAYER_PLACEHOLDERS_TEST_NEW> getPlaceholders(String particle) {
        Map<String, PLAYER_PLACEHOLDERS_TEST_NEW> placeholders = new HashMap<>();
        for (PLAYER_PLACEHOLDERS_TEST_NEW placeholder : PLAYER_PLACEHOLDERS_TEST_NEW.values()) {
            if(particle != null && !particle.isEmpty())  placeholders.put("%"+particle+"_"+placeholder.name()+"%", placeholder);
            else placeholders.put("%"+placeholder.name()+"%", placeholder);
        }
        return placeholders;
    }

    public String getValue(UUID uuid, Map<PLAYER_DATA, Object> cachePlayerData){

        Player player = null;
        Location location = null;

        for (PLAYER_DATA data : requirePlayerData) {
            switch (data) {
                case PLAYER:
                    if(cachePlayerData.containsKey(PLAYER_DATA.PLAYER)) {
                        player = (Player) cachePlayerData.get(PLAYER_DATA.PLAYER);
                        if(player == null) return null;
                    } else {
                        player = Bukkit.getPlayer(uuid);
                        if(player == null) return null;
                        cachePlayerData.put(PLAYER_DATA.PLAYER, player);
                    }
                    break;
                case LOCATION:
                    if(cachePlayerData.containsKey(PLAYER_DATA.LOCATION)) {
                        location = (Location) cachePlayerData.get(PLAYER_DATA.LOCATION);
                        if(location == null) return null;
                    } else {
                        location = player.getLocation();
                        cachePlayerData.put(PLAYER_DATA.LOCATION, location);
                    }
            }
        }


        switch (this) {
            case X:
                return location.getX() + "";
            case Y:
                return location.getY() + "";
            case Z:
                return location.getZ() + "";

            case PITCH:
                return location.getPitch() + "";

            case PITCH_POSITIVE:
                return Math.abs(location.getPitch()) + "";

            case YAW:
                return location.getYaw() + "";

            case YAW_POSITIVE:
                return Math.abs(location.getYaw()) + "";

        }
        return null;
    }
}
