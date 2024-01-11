package com.ssomar.score.damagewithoutknockback;

import lombok.Getter;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DamageWithoutKnockbackManager {

    private static DamageWithoutKnockbackManager instance;
    @Getter
    private final List<UUID> damageWithoutKnockbackList = new ArrayList<>();

    public static DamageWithoutKnockbackManager getInstance() {
        if (instance == null) instance = new DamageWithoutKnockbackManager();
        return instance;
    }

    public void addDamageWithoutKnockback(Entity e) {
        UUID uuid = e.getUniqueId();
        damageWithoutKnockbackList.add(uuid);
    }

    public void removeDamageWithoutKnockback(Entity e) {
        UUID uuid = e.getUniqueId();
        damageWithoutKnockbackList.remove(uuid);
    }

    public boolean contains(Entity e) {
        UUID uuid = e.getUniqueId();
        return damageWithoutKnockbackList.contains(uuid);
    }

}
