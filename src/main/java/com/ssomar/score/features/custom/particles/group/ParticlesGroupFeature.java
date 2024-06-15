package com.ssomar.score.features.custom.particles.group;

import com.ssomar.score.SCore;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.particles.particle.ParticleFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ParticlesGroupFeature extends FeatureWithHisOwnEditor<ParticlesGroupFeature, ParticlesGroupFeature, ParticlesGroupFeatureEditor, ParticlesGroupFeatureEditorManager> implements FeaturesGroup<ParticleFeature>, SProjectileFeatureInterface {

    private Map<String, ParticleFeature> particles;
    private boolean notSaveIfNoValue;

    public ParticlesGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, FeatureSettingsSCore.particles);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.particles = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String enchantmentID : enchantmentsSection.getKeys(false)) {
                ParticleFeature enchantment = new ParticleFeature(this, enchantmentID);
                List<String> subErrors = enchantment.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                particles.put(enchantmentID, enchantment);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && particles.size() == 0) return;
        ConfigurationSection enchantmentsSection = config.createSection(this.getName());
        for (String enchantmentID : particles.keySet()) {
            particles.get(enchantmentID).save(enchantmentsSection);
        }

    }

    @Override
    public ParticlesGroupFeature getValue() {
        return this;
    }

    @Override
    public ParticlesGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oParticle(s) added: &e" + particles.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ParticleFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (ParticleFeature x : particles.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public ParticlesGroupFeature clone(FeatureParentInterface newParent) {
        ParticlesGroupFeature eF = new ParticlesGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, ParticleFeature> newEnchantments = new HashMap<>();
        for (String x : particles.keySet()) {
            newEnchantments.put(x, particles.get(x).clone(eF));
        }
        eF.setParticles(newEnchantments);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(particles.values());
    }

    @Override
    public String getParentInfo() {
        if (getParent() == this) {
            return "";
        } else return getParent().getParentInfo() + ".(" + getName() + ")";
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        } else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof ParticlesGroupFeature) {
                ParticlesGroupFeature eF = (ParticlesGroupFeature) feature;
                eF.setParticles(this.getParticles());
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        ParticlesGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "particle";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!particles.containsKey(id)) {
                ParticleFeature eF = new ParticleFeature(this, id);
                particles.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, ParticleFeature feature) {
        particles.remove(feature.getId());
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
       if(SCore.is1v19v4Plus()){
           if(SCore.isSpigot()){
               ParticlesSpigot1194_120.transformTheProjectile(particles, e, launcher, materialLaunched);
           }
           else ParticlesPaper1194_120.transformTheProjectile(particles, e, launcher, materialLaunched);
       }
         else ParticlesXenonDev112_1193.transformTheProjectile(particles, e, launcher, materialLaunched);
    }
}
