package com.ssomar.score.features.custom.particles.particle;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
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
        super(parent, "Particle", "Particle", new String[]{"&7&oA custom particle"}, Material.BLAZE_POWDER, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.particlesType = new ParticleTypeFeature(this, "particlesType", Optional.of(Particle.FLAME), "Particles type", new String[]{"&7&oThe type of the particle"}, Material.BLAZE_POWDER, false);
        this.particlesAmount = new IntegerFeature(this, "particlesAmount", Optional.of(1), "Particles amount", new String[]{"&7&oThe amount of the particle"}, GUI.COMPARATOR, false);
        this.particlesOffSet = new DoubleFeature(this, "particlesOffSet", Optional.of(1.0), "Particles offset", new String[]{"&7&oThe offset of the particle"}, GUI.COMPARATOR, false);
        this.particlesSpeed = new DoubleFeature(this, "particlesSpeed", Optional.of(1.0), "Particles speed", new String[]{"&7&oThe speed of the particle"}, GUI.COMPARATOR, false);
        this.particlesDelay = new IntegerFeature(this, "particlesDelay", Optional.of(1), "Particles delay", new String[]{"&7&oThe delay of the particle"}, GUI.COMPARATOR, false);
        this.particlesDensity = new IntegerFeature(this, "particlesDensity", Optional.of(1), "Particles density", new String[]{"&7&oThe density of the particle"}, GUI.COMPARATOR, false);
        this.blockType = new MaterialFeature(this, "blockType", Optional.of(Material.STONE), "Block type", new String[]{"&7&oThe type of the block"}, Material.STONE, false);
        this.redstoneColor = new BukkitColorFeature(this, "redstoneColor", Optional.of(Color.RED), "Redstone color", new String[]{"&7&oThe color of the redstone"}, Material.REDSTONE, false);
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
        particles.add(Particle.BLOCK_CRACK);
        particles.add(Particle.BLOCK_DUST);
        if (SCore.is1v18Plus()) {
            particles.add(Particle.BLOCK_MARKER);
        }
        return particles;
    }

    public static List<Particle> getHaveRedstoneColorParticles() {
        List<Particle> particles = new ArrayList<>();
        particles.add(Particle.REDSTONE);
        return particles;
    }

    public boolean canHaveRedstoneColor() {
        return getHaveRedstoneColorParticles().contains(particlesType.getValue().get());
    }

    public boolean canHaveBlocktype() {
        return getHaveBlocktypeParticles().contains(particlesType.getValue().get());
    }
}
