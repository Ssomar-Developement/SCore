package com.ssomar.score.sparticles;

import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.CustomColor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

@Getter
@Setter
public class SParticle {

    private String id;
    private Particle particlesType;
    private int particlesAmount = 1;
    private double particlesOffSet = 1;
    private double particlesSpeed = 1;
    private int particlesDelay = 1;

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
    }

    public SParticle(String id, Particle particlesType, int particlesAmount, double particlesOffSet, double particlesSpeed, int particlesDelay) {
        this.id = id;
        this.particlesType = particlesType;
        this.particlesAmount = particlesAmount;
        this.particlesOffSet = particlesOffSet;
        this.particlesSpeed = particlesSpeed;
        this.particlesDelay = particlesDelay;
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

        String redstoneColorStr = conf.getString("redstoneColor", "null");
        try {
            redstoneColor = CustomColor.valueOf(redstoneColorStr);
        } catch (Exception e) {
            if (showError) SCore.plugin.getLogger()
                    .severe(sPlugin.getNameDesign() + " Error invalid redstoneColor " + redstoneColorStr + " for the projectile: " + path + " > " + id
                            + " (https://helpch.at/docs/1.12.2/org/bukkit/Color.html)");
        }

        String blockTypeStr = conf.getString("blockType", "null").toUpperCase();
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
}
