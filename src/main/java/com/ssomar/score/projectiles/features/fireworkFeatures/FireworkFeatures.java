package com.ssomar.score.projectiles.features.fireworkFeatures;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.FireworkEffectTypeFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.list.ListBukkitColorFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class FireworkFeatures extends FeatureWithHisOwnEditor<FireworkFeatures, FireworkFeatures, FireworkFeaturesEditor, FireworkFeaturesEditorManager> implements SProjectileFeatureInterface {

    private IntegerFeature lifeTime;
    private ListBukkitColorFeature colors;
    private ListBukkitColorFeature fadeColors;
    private FireworkEffectTypeFeature type;

    public FireworkFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.fireworkFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.lifeTime = new IntegerFeature(this, Optional.empty(), FeatureSettingsSCore.lifeTime);
        this.colors = new ListBukkitColorFeature(this,  new ArrayList<>(), FeatureSettingsSCore.colors, false, Optional.empty());
        this.fadeColors = new ListBukkitColorFeature(this,  new ArrayList<>(), FeatureSettingsSCore.fadeColors, false, Optional.empty());
        this.type = new FireworkEffectTypeFeature(this, Optional.empty(), FeatureSettingsSCore.type);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Firework) {
           Firework firework = (Firework) e;
           if(lifeTime.getValue().isPresent()) firework.setMaxLife(lifeTime.getValue().get());
            FireworkMeta meta = firework.getFireworkMeta();

            if(!colors.getValue().isEmpty() && type.getValue().isPresent()) {
                FireworkEffect.Builder builder = FireworkEffect.builder();
                builder.withTrail().withFlicker();
                if (!fadeColors.getValue().isEmpty()) builder.withFade(fadeColors.getValue());
                builder.with(type.getValue().get());
                builder.withColor(colors.getValue());
                meta.addEffect(builder.build());
                meta.setPower(1);
                firework.setFireworkMeta(meta);
            }
        }
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(lifeTime.load(plugin, section, isPremiumLoading));
            errors.addAll(colors.load(plugin, section, isPremiumLoading));
            errors.addAll(fadeColors.load(plugin, section, isPremiumLoading));
            errors.addAll(type.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        lifeTime.save(section);
        colors.save(section);
        fadeColors.save(section);
        type.save(section);
    }

    @Override
    public FireworkFeatures getValue() {
        return this;
    }

    @Override
    public FireworkFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public FireworkFeatures clone(FeatureParentInterface newParent) {
        FireworkFeatures clone = new FireworkFeatures(newParent);
        clone.setLifeTime(lifeTime.clone(clone));
        clone.setColors(colors.clone(clone));
        clone.setFadeColors(fadeColors.clone(clone));
        clone.setType(type.clone(clone));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(lifeTime);
        features.add(colors);
        features.add(fadeColors);
        features.add(type);
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
            if (feature instanceof FireworkFeatures && feature.getName().equals(getName())) {
                FireworkFeatures hiders = (FireworkFeatures) feature;
                hiders.setLifeTime(lifeTime);
                hiders.setColors(colors);
                hiders.setFadeColors(fadeColors);
                hiders.setType(type);
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openBackEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        FireworkFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
