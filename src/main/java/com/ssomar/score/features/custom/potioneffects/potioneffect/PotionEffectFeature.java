package com.ssomar.score.features.custom.potioneffects.potioneffect;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.PotionEffectTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PotionEffectFeature extends FeatureWithHisOwnEditor<PotionEffectFeature, PotionEffectFeature, PotionEffectFeatureEditor, PotionEffectFeatureEditorManager> {

    private IntegerFeature amplifier;
    private IntegerFeature duration;
    private PotionEffectTypeFeature type;
    private BooleanFeature ambient;
    private BooleanFeature particles;
    private BooleanFeature icon;
    private String id;

    public PotionEffectFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.potionEffect);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        PotionEffectType type = SCore.is1v20v5Plus() ? PotionEffectType.HEALTH_BOOST : PotionEffectType.getByName("HEAL");
        this.amplifier = new IntegerFeature(this, Optional.ofNullable(0), FeatureSettingsSCore.amplifier);
        this.duration = new IntegerFeature(this, Optional.ofNullable(30), FeatureSettingsSCore.duration);
        this.type = new PotionEffectTypeFeature(this, Optional.ofNullable(type), FeatureSettingsSCore.potionEffectType);
        this.ambient = new BooleanFeature(this, false, FeatureSettingsSCore.isAmbient, false);
        this.particles = new BooleanFeature(this, false, FeatureSettingsSCore.hasParticles, false);
        this.icon = new BooleanFeature(this, false, FeatureSettingsSCore.hasIcon, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(amplifier.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(duration.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(type.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(ambient.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particles.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(icon.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Attribute with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    public PotionEffect getPotionEffect() {
        if (SCore.is1v12Less())
            return new PotionEffect(type.getValue().get(), duration.getValue().get(), amplifier.getValue().get(), ambient.getValue(), particles.getValue());
        else
            return new PotionEffect(type.getValue().get(), duration.getValue().get(), amplifier.getValue().get(), ambient.getValue(), particles.getValue(), icon.getValue());
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.ambient.save(attributeConfig);
        this.duration.save(attributeConfig);
        this.type.save(attributeConfig);
        this.amplifier.save(attributeConfig);
        this.particles.save(attributeConfig);
        this.icon.save(attributeConfig);
    }

    @Override
    public PotionEffectFeature getValue() {
        return this;
    }

    @Override
    public PotionEffectFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 7];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 7] = "&7Type: &e" + type.getValue().get().getName();
        finalDescription[finalDescription.length - 6] = "&7Amplifier: &e" + amplifier.getValue().get();
        finalDescription[finalDescription.length - 5] = "&7Duration: &e" + duration.getValue().get();
        if (ambient.getValue())
            finalDescription[finalDescription.length - 4] = "&7Ambient: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Ambient: &c&l✘";
        if (particles.getValue())
            finalDescription[finalDescription.length - 3] = "&7Particles: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Particles: &c&l✘";

        if (SCore.is1v11Less()) finalDescription[finalDescription.length - 2] = "&7Icon : &c&lNot for 1.12 or lower";
        else if (icon.getValue())
            finalDescription[finalDescription.length - 2] = "&7Icon: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Icon: &c&l✘";
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public PotionEffectFeature clone(FeatureParentInterface newParent) {
        PotionEffectFeature eF = new PotionEffectFeature(newParent, id);
        eF.setAmbient(ambient.clone(eF));
        eF.setDuration(duration.clone(eF));
        eF.setType(type.clone(eF));
        eF.setAmplifier(amplifier.clone(eF));
        eF.setParticles(particles.clone(eF));
        eF.setIcon(icon.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(amplifier);
        features.add(duration);
        features.add(type);
        features.add(ambient);
        features.add(particles);
        if (!SCore.is1v12Less()) features.add(icon);
        return features;
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return getParent().getConfigurationSection();
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof PotionEffectFeature) {
                PotionEffectFeature aFOF = (PotionEffectFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setAmbient(ambient);
                    aFOF.setDuration(duration);
                    aFOF.setType(type);
                    aFOF.setAmplifier(amplifier);
                    aFOF.setParticles(particles);
                    aFOF.setIcon(icon);
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
        PotionEffectFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
