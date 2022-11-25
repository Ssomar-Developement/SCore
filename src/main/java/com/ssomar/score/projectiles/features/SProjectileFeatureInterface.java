package com.ssomar.score.projectiles.features;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface SProjectileFeatureInterface {

    void transformTheProjectile(Entity e, Player launcher, Material materialLaunched);
}
