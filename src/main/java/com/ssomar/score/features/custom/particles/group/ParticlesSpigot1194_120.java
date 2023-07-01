package com.ssomar.score.features.custom.particles.group;

import com.ssomar.score.SCore;
import com.ssomar.score.features.custom.particles.particle.ParticleFeature;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ParticlesSpigot1194_120 {

    public static void transformTheProjectile(Map<String, ParticleFeature> particles, Entity e, Player launcher, Material materialLaunched) {
        if (particles != null) {
            for (ParticleFeature particle : particles.values()) {
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (e.isDead()) cancel();

                        if (particle.canHaveRedstoneColor()) {
                            Particle.DustOptions dO = new Particle.DustOptions(Color.RED, 1);
                            if (particle.getRedstoneColor().getValue().isPresent())
                                dO = new Particle.DustOptions(particle.getRedstoneColor().getValue().get(), 1);
                            e.getWorld().spawnParticle(particle.getParticlesType().getValue().get(), e.getLocation(), particle.getParticlesAmount().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesSpeed().getValue().get(), dO);
                        } else if (particle.canHaveBlocktype()) {
                            BlockData typeData = Material.STONE.createBlockData();
                            if (particle.getBlockType() != null)
                                typeData = particle.getBlockType().getValue().get().createBlockData();
                            e.getWorld().spawnParticle(particle.getParticlesType().getValue().get(), e.getLocation(), particle.getParticlesAmount().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesSpeed().getValue().get(), typeData);
                        } else {
                            e.getWorld().spawnParticle(particle.getParticlesType().getValue().get(), e.getLocation(), particle.getParticlesAmount().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesOffSet().getValue().get(), particle.getParticlesSpeed().getValue().get(), null);
                        }
                    }
                };
                runnable.runTaskTimerAsynchronously(SCore.plugin, 0L, particle.getParticlesDelay().getValue().get());
            }
        }
    }
}
