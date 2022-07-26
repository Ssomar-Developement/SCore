package com.ssomar.score.newprojectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.IntegerFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class DespawnFeature extends IntegerFeature implements SProjectileFeatureInterface {

    public DespawnFeature(FeatureParentInterface parent) {
        super(parent, "despawnDelay", Optional.of(-1), "Despawn delay", new String[]{"&7&o-1 for vanilla despawn"}, Material.DEAD_BUSH, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && getValue().get() != -1) {
            BukkitRunnable runnable = new BukkitRunnable() {
                public void run() {
                    if (e != null)
                        e.remove();
                }
            };
            runnable.runTaskLater(SCore.plugin, getValue().get() * 20);
        }
    }
}
