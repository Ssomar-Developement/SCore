package com.ssomar.scoretestrecode.features.custom.potioneffects.potioneffect;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.AttributeSlot;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.types.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class PotionEffectFeature extends FeatureWithHisOwnEditor<PotionEffectFeature, PotionEffectFeature, PotionEffectFeatureEditor, PotionEffectFeatureEditorManager> {

    private IntegerFeature amplifier;
    private IntegerFeature duration;
    private PotionEffectTypeFeature type;
    private BooleanFeature ambient;
    private BooleanFeature particles;
    private BooleanFeature icon;
    private String id;

    public PotionEffectFeature(FeatureParentInterface parent, String id) {
        super(parent, "potionEffect", "Potion Effect", new String[]{"&7&oA potion effect with its options"}, Material.BREWING_STAND, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.amplifier = new IntegerFeature(this, "amplifier", Optional.ofNullable(0), "Amplifier", new String[]{"&7&oThe amplifier of the potion effect"}, GUI.CLOCK, false);
        this.duration = new IntegerFeature(this, "duration", Optional.ofNullable(30), "Duration", new String[]{"&7&oThe duration of the potion effect"}, GUI.CLOCK, false);
        this.type = new PotionEffectTypeFeature(this, "potionEffectType", Optional.ofNullable(PotionEffectType.HEAL), "Type", new String[]{"&7&oThe type of the potion effect"}, Material.COMPASS, false);
        this.ambient = new BooleanFeature(this, "isAmbient", false, "Ambient", new String[]{"&7&oIf the potion effect is ambient"}, Material.LEVER, false, false);
        this.particles = new BooleanFeature(this, "hasParticles", false, "Particles", new String[]{"&7&oIf the potion effect has particles"}, Material.LEVER, false, false);
        this.icon = new BooleanFeature(this, "hasIcon", false, "Icon", new String[]{"&7&oIf the potion effect has an icon"}, Material.LEVER, false, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if(config.isConfigurationSection(id)){
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(amplifier.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(duration.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(type.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(ambient.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(particles.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(icon.load(plugin, enchantmentConfig, isPremiumLoading));
        }
        else{
            errors.add("&cERROR, Couldn't load the Attribute with its options because there is not section with the good ID: "+id+" &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("("+id+")");
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
        if(ambient.getValue())
            finalDescription[finalDescription.length - 4] = "&7Ambient: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Ambient: &c&l✘";
        if(particles.getValue())
            finalDescription[finalDescription.length - 3] = "&7Particles: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Particles: &c&l✘";
        if(icon.getValue())
            finalDescription[finalDescription.length - 2] = "&7Icon: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Icon: &c&l✘";
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName()+ " - "+"("+id+")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public PotionEffectFeature clone() {
        PotionEffectFeature eF = new PotionEffectFeature(getParent(), id);
        eF.setAmbient(ambient.clone());
        eF.setDuration(duration.clone());
        eF.setType(type.clone());
        eF.setAmplifier(amplifier.clone());
        eF.setParticles(particles.clone());
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(amplifier, duration, type, ambient, particles, icon));
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
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof PotionEffectFeature) {
                PotionEffectFeature aFOF = (PotionEffectFeature) feature;
                if(aFOF.getId().equals(id)) {
                    aFOF.setAmbient(ambient);
                    aFOF.setDuration(duration);
                    aFOF.setType(type);
                    aFOF.setAmplifier(amplifier);
                    aFOF.setParticles(particles);
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
