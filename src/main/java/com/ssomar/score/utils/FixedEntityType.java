package com.ssomar.score.utils;

import org.bukkit.entity.EntityType;

import java.util.List;

public class FixedEntityType {

    public static EntityType getEntity(List<String> entities) {
        for (String mat : entities) {
            try {
                return EntityType.valueOf(mat.toUpperCase());
            } catch (Exception e) {
                continue;
            }
        }
        return EntityType.UNKNOWN;
    }
}
