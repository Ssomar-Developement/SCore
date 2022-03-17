package com.ssomar.score.sparticles;

import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class SParticles {

    private String filePath;
    private String configurationSectionPath;
    private List<SParticle> particles;

    public SParticles(SPlugin sPlugin, String filePath, String configurationSectionPath, ConfigurationSection section, boolean showError) {
        this.filePath = filePath;
        this.configurationSectionPath = configurationSectionPath;
        this.particles = new ArrayList<>();

        ConfigurationSection mainSection = section;

        if (!configurationSectionPath.isEmpty() && section.isConfigurationSection(configurationSectionPath))
            mainSection = section.getConfigurationSection(configurationSectionPath);

        if (mainSection.isConfigurationSection("particles")) {
            ConfigurationSection particlesSection = mainSection.getConfigurationSection("particles");
            for (String id : particlesSection.getKeys(false)) {
                Optional<SParticle> sParticleOpt = SParticle.loadSParticle(sPlugin, filePath + configurationSectionPath, particlesSection.getConfigurationSection(id), id, showError);
                if (sParticleOpt.isPresent()) {
                    particles.add(sParticleOpt.get());
                }
            }
        }
    }

    public void addParticle(SParticle sParticle) {
        particles.add(sParticle);
    }

    public void removeParticle(String id) {
        SParticle toRemove = null;
        for (SParticle sParticle : particles) {
            if (sParticle.getId().equals(id)) {
                toRemove = sParticle;
                break;
            }
        }
        if (toRemove != null) particles.remove(toRemove);
    }
}
