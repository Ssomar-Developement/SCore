package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.Couple;
import com.ssomar.score.utils.CustomColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ParticlesFeature extends DecorateurCustomProjectiles {

    List<CustomParticle> particles;

    public ParticlesFeature(CustomProjectile cProj){
        super.cProj = cProj;
        particles = new ArrayList<>();
    }


    @Override
    public boolean loadConfiguration(FileConfiguration projConfig, boolean showError) {
        List<CustomParticle> particles = new ArrayList<>();
        if (projConfig.contains("particles")) {
            ConfigurationSection particlesSection = projConfig.getConfigurationSection("particles");
            for(String key : particlesSection.getKeys(false)){
                ConfigurationSection particleSection = particlesSection.getConfigurationSection(key);
                Couple<CustomParticle, Boolean> couple = this.loadParticle(particleSection, showError);
                if(couple.getElem2()){
                    particles.add(couple.getElem1());
                }
                else return cProj.loadConfiguration(projConfig, showError) && false;
            }
        }
        else{
           Couple<CustomParticle, Boolean> couple = this.loadParticle(projConfig, showError);
           if(couple.getElem2()){
                particles.add(couple.getElem1());
           }
           else return cProj.loadConfiguration(projConfig, showError) && false;
        }
        this.particles = particles;
        return cProj.loadConfiguration(projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        // #TODO save particles
        cProj.saveConfiguration(config);
    }

    public Couple<CustomParticle, Boolean> loadParticle(ConfigurationSection conf, boolean showError){

        Particle particlesType = null;
        int particlesAmount = 1;
        double particlesOffSet = 1;
        double particlesSpeed = 1;
        int particlesDelay = 1;

        Color redstoneColor = null;

        if (conf.contains("particlesType")) {
            try {
                particlesType = Particle.valueOf(conf.getString("particlesType"));
            } catch (Exception e) {
                if(showError) SCore.plugin.getLogger()
                        .severe("[SCore] Error invalid particlesType "+conf.getString("particlesType")+" for the projectile: " + "ADD ID HERE"
                                + " (https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Particle.html)");
                // #TODO add id here
                return new Couple(null, false);
            }
        }

        particlesAmount = conf.getInt("particlesAmount", 1);

        particlesOffSet = conf.getDouble("particlesOffSet", 1);

        particlesSpeed = conf.getDouble("particlesSpeed", 1);

        particlesDelay = conf.getInt("particlesDelay", 1);

        String redstoneColorStr = conf.getString("redstoneColor", "null");
        try {
            redstoneColor = CustomColor.valueOf(redstoneColorStr);
        } catch (Exception e) {
            if(showError) SCore.plugin.getLogger()
                    .severe("[SCore] Error invalid redstoneColor "+redstoneColorStr+" for the projectile: " + "ADD ID HERE"
                            + " (https://helpch.at/docs/1.12.2/org/bukkit/Color.html)");
            // #TODO add id here
        }

        CustomParticle particle = new CustomParticle(particlesType, particlesAmount, particlesOffSet, particlesSpeed, particlesDelay);
        if(redstoneColor != null){
            particle.setRedstoneColor(redstoneColor);
        }
        return new Couple(particle, true);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        for(CustomParticle particle : particles){
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (e.isDead()) cancel();

                    if(Particle.REDSTONE.equals(particle.getParticlesType())) {
                        Particle.DustOptions dO = new Particle.DustOptions(Color.RED, 1);
                        if(particle.getRedstoneColor() != null) dO = new Particle.DustOptions(particle.getRedstoneColor(), 1);
                        e.getWorld().spawnParticle(particle.getParticlesType(), e.getLocation(), particle.getParticlesAmount(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesSpeed(), dO);
                    }
                    else {
                        e.getWorld().spawnParticle(particle.getParticlesType(), e.getLocation(), particle.getParticlesAmount(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesSpeed());
                    }
                }
            };
            runnable.runTaskTimerAsynchronously(SCore.plugin, 0L, particle.getParticlesDelay());
        }
        cProj.transformTheProjectile(e, launcher);
    }

    class CustomParticle{
        private Particle particlesType;
        private int particlesAmount = 1;
        private double particlesOffSet = 1;
        private double particlesSpeed = 1;
        private int particlesDelay = 1;

        /* sepecific for the Particle.REDSTONE */
        private Color redstoneColor;

        public CustomParticle(Particle particlesType, int particlesAmount, double particlesOffSet, double particlesSpeed, int particlesDelay) {
            this.particlesType = particlesType;
            this.particlesAmount = particlesAmount;
            this.particlesOffSet = particlesOffSet;
            this.particlesSpeed = particlesSpeed;
            this.particlesDelay = particlesDelay;
        }

        public Particle getParticlesType() {
            return particlesType;
        }

        public void setParticlesType(Particle particlesType) {
            this.particlesType = particlesType;
        }

        public int getParticlesAmount() {
            return particlesAmount;
        }

        public void setParticlesAmount(int particlesAmount) {
            this.particlesAmount = particlesAmount;
        }

        public double getParticlesOffSet() {
            return particlesOffSet;
        }

        public void setParticlesOffSet(double particlesOffSet) {
            this.particlesOffSet = particlesOffSet;
        }

        public double getParticlesSpeed() {
            return particlesSpeed;
        }

        public void setParticlesSpeed(double particlesSpeed) {
            this.particlesSpeed = particlesSpeed;
        }

        public int getParticlesDelay() {
            return particlesDelay;
        }

        public void setParticlesDelay(int particlesDelay) {
            this.particlesDelay = particlesDelay;
        }

        public Color getRedstoneColor() {
            return redstoneColor;
        }

        public void setRedstoneColor(Color redstoneColor) {
            this.redstoneColor = redstoneColor;
        }
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        return gui;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
    }
}
