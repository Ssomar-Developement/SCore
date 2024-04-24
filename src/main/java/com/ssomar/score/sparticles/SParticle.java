package com.ssomar.score.sparticles;

import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.CustomColor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SParticle {

    private String id;
    private Particle particlesType;
    private int particlesAmount;
    private double particlesOffSet;
    private double particlesSpeed;
    private int particlesDelay;

    /* specific for the Particle.REDSTONE */
    private Color redstoneColor;

    /* specific for the Particle.BLOCK_CRACK, BLOCK_DUST, BLOCK_MARKER*/
    private Material blockType;

    public SParticle(String id) {
        this.id = id;
        this.particlesType = Particle.FLAME;
        this.particlesAmount = 1;
        this.particlesOffSet = 1;
        this.particlesSpeed = 1;
        this.particlesDelay = 1;
        this.blockType = Material.STONE;
        this.redstoneColor = Color.RED;
    }

    public SParticle(String id, Particle particlesType, int particlesAmount, double particlesOffSet, double particlesSpeed, int particlesDelay) {
        this.id = id;
        this.particlesType = particlesType;
        this.particlesAmount = particlesAmount;
        this.particlesOffSet = particlesOffSet;
        this.particlesSpeed = particlesSpeed;
        this.particlesDelay = particlesDelay;
        this.blockType = Material.STONE;
        this.redstoneColor = Color.RED;
    }

    public static Optional<SParticle> loadSParticle(SPlugin sPlugin, String path, ConfigurationSection conf, String id, boolean showError) {

        Particle particlesType = null;
        int particlesAmount = 1;
        double particlesOffSet = 1;
        double particlesSpeed = 1;
        int particlesDelay = 1;

        Color redstoneColor = null;
        Material blockType = null;

        if (conf.contains("particlesType")) {
            try {
                particlesType = Particle.valueOf(conf.getString("particlesType"));
            } catch (Exception e) {
                if (showError) SCore.plugin.getLogger()
                        .severe(sPlugin.getNameDesign() + " Error invalid particlesType " + conf.getString("particlesType") + " for the projectile: " + path + " > " + id
                                + " (https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Particle.html)");
                return Optional.ofNullable(null);
            }
        }
        if (particlesType == null) return Optional.ofNullable(null);

        particlesAmount = conf.getInt("particlesAmount", 1);

        particlesOffSet = conf.getDouble("particlesOffSet", 1);

        particlesSpeed = conf.getDouble("particlesSpeed", 1);

        particlesDelay = conf.getInt("particlesDelay", 1);

        String redstoneColorStr = conf.getString("redstoneColor", "RED");
        try {
            redstoneColor = CustomColor.valueOf(redstoneColorStr);
        } catch (Exception e) {
            if (showError) SCore.plugin.getLogger()
                    .severe(sPlugin.getNameDesign() + " Error invalid redstoneColor " + redstoneColorStr + " for the projectile: " + path + " > " + id
                            + " (https://helpch.at/docs/1.12.2/org/bukkit/Color.html)");
        }

        String blockTypeStr = conf.getString("blockType", "STONE").toUpperCase();
        if (!blockTypeStr.equals("NULL")) {
            try {

                blockType = Material.valueOf(blockTypeStr);
            } catch (Exception e) {
                if (showError) SCore.plugin.getLogger()
                        .severe(sPlugin.getNameDesign() + " Error invalid blockType " + blockTypeStr + " for the projectile: " + path + " > " + id
                                + " (https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)");
            }
        }
        SParticle particle = new SParticle(id, particlesType, particlesAmount, particlesOffSet, particlesSpeed, particlesDelay);
        if (redstoneColor != null) {
            particle.setRedstoneColor(redstoneColor);
        }
        if (blockType != null) {
            particle.setBlockType(blockType);
        }
        return Optional.ofNullable(particle);
    }

    public static List<Particle> getHaveBlocktypeParticles() {
        List<Particle> particles = new ArrayList<>();
        if(SCore.is1v20v5Plus()){
            particles.add(Particle.BLOCK);
            particles.add(Particle.DUST);
        }
        else {
            particles.add(Particle.valueOf("BLOCK_CRACK"));
            particles.add(Particle.valueOf("BLOCK_DUST"));
        }
        if (SCore.is1v18Plus()) {
            particles.add(Particle.BLOCK_MARKER);
        }
        return particles;
    }

    public static List<Particle> getHaveRedstoneColorParticles() {
        List<Particle> particles = new ArrayList<>();
        if(SCore.is1v20v5Plus()){
            particles.add(Particle.DUST);
        }
        else {
            particles.add(Particle.valueOf("REDSTONE"));
        }
        return particles;
    }

    public boolean canHaveRedstoneColor() {
        return getHaveRedstoneColorParticles().contains(getParticlesType());
    }

    public boolean canHaveBlocktype() {
        return getHaveBlocktypeParticles().contains(particlesType);
    }
}
