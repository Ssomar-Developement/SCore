package com.ssomar.score.features.custom.firework.explosion.group;

import com.ssomar.score.SCore;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.firework.explosion.FireworkExplosionFeatures;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class FireworkExplosionGroupFeature extends FeatureWithHisOwnEditor<FireworkExplosionGroupFeature, FireworkExplosionGroupFeature, FireworkExplosionGroupFeatureEditor, FireworkExplosionGroupFeatureEditorManager> implements FeaturesGroup<FireworkExplosionFeatures>, FeatureForItem {

    private Map<String, FireworkExplosionFeatures> explosions;
    private boolean notSaveIfNoValue;

    int premiumLimit = 99;

    public FireworkExplosionGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, FeatureSettingsSCore.fireworkExplosions);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.explosions = new LinkedHashMap<>();
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Firework) {
            Firework firework = (Firework) e;
            FireworkMeta meta = firework.getFireworkMeta();

            if (explosions.isEmpty()) return;

            List<FireworkEffect> effects = new ArrayList<>();
            for (FireworkExplosionFeatures explosion : explosions.values()) {
                if (explosion.getColors().getValue().isEmpty() || !explosion.getType().getValue().isPresent()) continue;
                FireworkEffect.Builder builder = FireworkEffect.builder();
                if (explosion.getHasTrail().getValue()) builder.withTrail();
                if (explosion.getHasTwinkle().getValue()) builder.withFlicker();

                if (!explosion.getFadeColors().getValue().isEmpty())
                    builder.withFade(explosion.getFadeColors().getValue());
                builder.with(explosion.getType().getValue().get());
                builder.withColor(explosion.getColors().getValue());
                effects.add(builder.build());
            }

            for (FireworkEffect effect : effects) {
                meta.addEffect(effect);
            }
            firework.setFireworkMeta(meta);
        }
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String explosionId : enchantmentsSection.getKeys(false)) {
                if (explosions.size() >= premiumLimit && !isPremium()) {
                    error.add("&cERROR, Couldn't load the Explosion of " + explosionId + " from config, &7&o" + getParent().getParentInfo() + " &6>> Because it requires the premium version to have more than 99 explosions !");
                    return error;
                }
                FireworkExplosionFeatures attribute = new FireworkExplosionFeatures(this, explosionId);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                explosions.put(explosionId, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && explosions.isEmpty()) return;
        ConfigurationSection explosionsSection = config.createSection(this.getName());
        for (String explosionId : explosions.keySet()) {
            explosions.get(explosionId).save(explosionsSection);
        }
    }

    @Override
    public FireworkExplosionGroupFeature getValue() {
        return this;
    }

    @Override
    public FireworkExplosionGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oExplosion(s) added: &e" + explosions.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public FireworkExplosionFeatures getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (FireworkExplosionFeatures x : explosions.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public FireworkExplosionGroupFeature clone(FeatureParentInterface newParent) {
        FireworkExplosionGroupFeature eF = new FireworkExplosionGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, FireworkExplosionFeatures> newAttributes = new LinkedHashMap<>();
        for (String x : explosions.keySet()) {
            newAttributes.put(x, explosions.get(x).clone(eF));
        }
        eF.setExplosions(newAttributes);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>(explosions.values());
        return features;
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof FireworkExplosionGroupFeature) {
                FireworkExplosionGroupFeature eF = (FireworkExplosionGroupFeature) feature;
                eF.setExplosions(explosions);
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
        FireworkExplosionGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        if (!isPremium() && explosions.size() >= premiumLimit) return;
        String baseId = "explosion_";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!explosions.containsKey(id)) {
                FireworkExplosionFeatures eF = new FireworkExplosionFeatures(this, id);
                explosions.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, FireworkExplosionFeatures feature) {
        explosions.remove(feature.getId());
    }

    @Override
    public boolean isAvailable() {
        return !SCore.is1v12Less();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof FireworkMeta;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {

        ItemMeta itemMeta = args.getMeta();

        if (!explosions.isEmpty() && itemMeta instanceof FireworkMeta) {
            FireworkMeta fireworkMeta = (FireworkMeta) itemMeta;
            List<FireworkEffect> effects = new ArrayList<>();
            for(FireworkExplosionFeatures explosion : explosions.values()) {
                FireworkEffect.Builder builder = FireworkEffect.builder();
                if (explosion.getHasTrail().getValue()) builder.withTrail();
                if (explosion.getHasTwinkle().getValue()) builder.withFlicker();

                if (!explosion.getFadeColors().getValues().isEmpty()) builder.withFade(explosion.getFadeColors().getValue());
                if (explosion.getType().getValue().isPresent()) builder.with(explosion.getType().getValue().get());

                if (!explosion.getColors().getValues().isEmpty()) builder.withColor(explosion.getColors().getValues());
                // Cannot make FireworkEffect without any color
                else builder.withColor(Color.AQUA);
                effects.add(builder.build());
            }
            if(!effects.isEmpty()) fireworkMeta.addEffects(effects);
        }
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {
        ItemMeta meta = args.getMeta();
        if (meta instanceof FireworkMeta) {
            FireworkMeta fireworkMeta = (FireworkMeta) meta;
            int i = 0;
            for (FireworkEffect effect : fireworkMeta.getEffects()) {
                FireworkExplosionFeatures explosion = new FireworkExplosionFeatures(this, "explosion_"+i);

                explosion.getType().setValue(Optional.of(effect.getType()));
                explosion.getColors().setValues(effect.getColors());
                explosion.getFadeColors().setValues(effect.getFadeColors());
                explosion.getHasTrail().setValue(effect.hasTrail());
                explosion.getHasTwinkle().setValue(effect.hasFlicker());
                explosions.put(explosion.getId(), explosion);
                i++;
            }
        }
    }
}
