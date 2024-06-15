package com.ssomar.score.features.custom.particles.particle;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ParticleFeature extends FeatureWithHisOwnEditor<ParticleFeature, ParticleFeature, ParticleFeatureEditor, ParticleFeatureEditorManager> {

    private String id;
    private ParticleTypeFeature particlesType;
    private IntegerFeature particlesAmount;
    private DoubleFeature particlesOffSet;
    private DoubleFeature particlesSpeed;
    private IntegerFeature particlesDelay;
    private IntegerFeature particlesDensity;

    /* specific for the Particle.REDSTONE */
    private BukkitColorFeature redstoneColor;

    /* specific for the Particle.BLOCK_CRACK, BLOCK_DUST, BLOCK_MARKER*/
    private MaterialFeature blockType;

    public ParticleFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.particle);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.particlesType = new ParticleTypeFeature(this, Optional.of(Particle.FLAME), FeatureSettingsSCore.particlesType);
        this.particlesAmount = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.particlesAmount);
        this.particlesOffSet = new DoubleFeature(this, Optional.of(1.0), FeatureSettingsSCore.particlesOffSet);
        this.particlesSpeed = new DoubleFeature(this, Optional.of(1.0), FeatureSettingsSCore.particlesSpeed);
        this.particlesDelay = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.particlesDelay);
        this.particlesDensity = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.particlesDensity);
        this.blockType = new MaterialFeature(this, Optional.of(Material.STONE), FeatureSettingsSCore.blockType);
        this.redstoneColor = new BukkitColorFeature(this, Optional.of(Color.RED), FeatureSettingsSCore.redstoneColor);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(particlesType.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particlesAmount.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particlesOffSet.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particlesSpeed.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particlesDelay.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particlesDensity.load(plugin, enchantmentConfig, isPremiumLoading));

            if(canHaveRedstoneColor()) errors.addAll(redstoneColor.load(plugin, enchantmentConfig, isPremiumLoading));
            else if(canHaveBlocktype()) errors.addAll(blockType.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Particle because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection enchantmentConfig = config.createSection(id);
        particlesType.save(enchantmentConfig);
        particlesAmount.save(enchantmentConfig);
        particlesOffSet.save(enchantmentConfig);
        particlesSpeed.save(enchantmentConfig);
        particlesDelay.save(enchantmentConfig);
        particlesDensity.save(enchantmentConfig);
        if(canHaveRedstoneColor()) redstoneColor.save(enchantmentConfig);
        else if(canHaveBlocktype()) blockType.save(enchantmentConfig);
    }

    @Override
    public ParticleFeature getValue() {
        return this;
    }

    @Override
    public ParticleFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 8];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 8] = "&7Type: &e" + particlesType.getValue().get().name();
        finalDescription[finalDescription.length - 7] = "&7Amount: &e" + particlesAmount.getValue().get();
        finalDescription[finalDescription.length - 6] = "&7Offset: &e" + particlesOffSet.getValue().get();
        finalDescription[finalDescription.length - 5] = "&7Speed: &e" + particlesSpeed.getValue().get();
        finalDescription[finalDescription.length - 4] = "&7Delay: &e" + particlesDelay.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Density: &e" + particlesDensity.getValue().get();
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ParticleFeature clone(FeatureParentInterface newParent) {
        ParticleFeature eF = new ParticleFeature(newParent, id);
        eF.setParticlesType(particlesType.clone(eF));
        eF.setParticlesAmount(particlesAmount.clone(eF));
        eF.setParticlesOffSet(particlesOffSet.clone(eF));
        eF.setParticlesSpeed(particlesSpeed.clone(eF));
        eF.setParticlesDelay(particlesDelay.clone(eF));
        eF.setParticlesDensity(particlesDensity.clone(eF));
        if(canHaveRedstoneColor()) eF.setRedstoneColor(redstoneColor.clone(eF));
        else if(canHaveBlocktype()) eF.setBlockType(blockType.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(particlesType, particlesAmount, particlesOffSet, particlesSpeed, particlesDelay, particlesDensity, redstoneColor, blockType));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection parentSection = getParent().getConfigurationSection();
        if (parentSection.isConfigurationSection(getId())) {
            return parentSection.getConfigurationSection(getId());
        } else return parentSection.createSection(getId());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof ParticleFeature) {
                ParticleFeature eF = (ParticleFeature) feature;
                if (eF.getId().equals(id)) {
                    eF.setParticlesType(particlesType.clone(eF));
                    eF.setParticlesAmount(particlesAmount.clone(eF));
                    eF.setParticlesOffSet(particlesOffSet.clone(eF));
                    eF.setParticlesSpeed(particlesSpeed.clone(eF));
                    eF.setParticlesDelay(particlesDelay.clone(eF));
                    eF.setParticlesDensity(particlesDensity.clone(eF));
                    if(canHaveRedstoneColor()) eF.setRedstoneColor(redstoneColor.clone(eF));
                    else if(canHaveBlocktype()) eF.setBlockType(blockType.clone(eF));
                    break;
                }
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        ParticleFeatureEditorManager.getInstance().startEditing(player, this);
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
        return getHaveRedstoneColorParticles().contains(particlesType.getValue().get());
    }

    public boolean canHaveBlocktype() {
        return getHaveBlocktypeParticles().contains(particlesType.getValue().get());
    }
}
