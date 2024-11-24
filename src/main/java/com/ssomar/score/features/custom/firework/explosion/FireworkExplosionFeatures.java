package com.ssomar.score.features.custom.firework.explosion;

import com.ssomar.score.SCore;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.FireworkEffectTypeFeature;
import com.ssomar.score.features.types.list.ListBukkitColorFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class FireworkExplosionFeatures extends FeatureWithHisOwnEditor<FireworkExplosionFeatures, FireworkExplosionFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem {

    private FireworkEffectTypeFeature type;
    private ListBukkitColorFeature colors;
    private ListBukkitColorFeature fadeColors;
    private BooleanFeature hasTrail;
    private BooleanFeature hasTwinkle;

    private String id;

    public FireworkExplosionFeatures(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.fireworkExplosion);
        this.id = id;
        reset();
    }

    public FireworkExplosionFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.fireworkExplosion);
        reset();
    }

    @Override
    public void reset() {
        this.type = new FireworkEffectTypeFeature(this, Optional.empty(), FeatureSettingsSCore.type);
        this.colors = new ListBukkitColorFeature(this, new ArrayList<>(), FeatureSettingsSCore.colors, false, Optional.empty());
        this.fadeColors = new ListBukkitColorFeature(this, new ArrayList<>(), FeatureSettingsSCore.fadeColors, false, Optional.empty());
        this.hasTrail = new BooleanFeature(this, true, FeatureSettingsSCore.hasTrail, false);
        this.hasTwinkle = new BooleanFeature(this, true, FeatureSettingsSCore.hasTwinkle, false);
    }


    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String nameOrId = getName();
        if(id != null && !id.isEmpty()) nameOrId = id;
        if (config.isConfigurationSection(nameOrId)) {
            ConfigurationSection section = config.getConfigurationSection(nameOrId);
            errors.addAll(colors.load(plugin, section, isPremiumLoading));
            errors.addAll(fadeColors.load(plugin, section, isPremiumLoading));
            errors.addAll(type.load(plugin, section, isPremiumLoading));
            errors.addAll(hasTrail.load(plugin, section, isPremiumLoading));
            errors.addAll(hasTwinkle.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        String nameOrId = getName();
        if(id != null && !id.isEmpty()) nameOrId = id;
        config.set(nameOrId, null);
        ConfigurationSection section = config.createSection(nameOrId);
        colors.save(section);
        fadeColors.save(section);
        type.save(section);
        hasTrail.save(section);
        hasTwinkle.save(section);
    }

    @Override
    public FireworkExplosionFeatures getValue() {
        return this;
    }

    @Override
    public FireworkExplosionFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 5] = "&7Type: &e" + this.type.getValue().orElse(FireworkEffect.Type.BALL);
        finalDescription[finalDescription.length - 4] = "&7Colors: &e" + Arrays.toString(this.colors.getValues().toArray());
        finalDescription[finalDescription.length - 3] = "&7Fade Colors: &e" + Arrays.toString(this.fadeColors.getValues().toArray());
        finalDescription[finalDescription.length - 2] = "&7Trail: " + (this.hasTrail.getValue() ? "&aTrue" : "&cFalse");
        finalDescription[finalDescription.length - 1] = "&7Twinkle: " + (this.hasTwinkle.getValue() ? "&aTrue" : "&cFalse");


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public FireworkExplosionFeatures clone(FeatureParentInterface newParent) {
        FireworkExplosionFeatures clone = new FireworkExplosionFeatures(newParent);
        clone.setColors(colors.clone(clone));
        clone.setFadeColors(fadeColors.clone(clone));
        clone.setType(type.clone(clone));
        clone.setHasTrail(hasTrail.clone(clone));
        clone.setHasTwinkle(hasTwinkle.clone(clone));
        clone.setId(id);
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(type);
        features.add(colors);
        features.add(fadeColors);
        features.add(hasTrail);
        features.add(hasTwinkle);
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof FireworkExplosionFeatures && feature.getName().equals(getName())) {
                if(id != null && !id.isEmpty() && !Objects.equals(((FireworkExplosionFeatures) feature).getId(), getId())) continue;
                FireworkExplosionFeatures hiders = (FireworkExplosionFeatures) feature;
                hiders.setColors(colors);
                hiders.setFadeColors(fadeColors);
                hiders.setType(type);
                hiders.setHasTrail(hasTrail);
                hiders.setHasTwinkle(hasTwinkle);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean isAvailable() {
        return !SCore.is1v12Less();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof FireworkEffectMeta;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {

        ItemMeta itemMeta = args.getMeta();

        if (itemMeta instanceof FireworkEffectMeta && !getColors().getValue().isEmpty()) {
            FireworkEffectMeta fireworkEffectMeta = (FireworkEffectMeta) itemMeta;
            FireworkEffect.Builder builder = FireworkEffect.builder();
            if (getHasTrail().getValue()) builder.withTrail();
            if (getHasTwinkle().getValue()) builder.withFlicker();

            if (!getFadeColors().getValue().isEmpty()) builder.withFade(getFadeColors().getValue());
            if (getType().getValue().isPresent()) builder.with(getType().getValue().get());
            builder.withColor(getColors().getValue());
            fireworkEffectMeta.setEffect(builder.build());
        }
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {

        ItemMeta itemMeta = args.getMeta();
        if (itemMeta instanceof FireworkEffectMeta) {
            FireworkEffectMeta fireworkEffectMeta = (FireworkEffectMeta) itemMeta;
            if (!fireworkEffectMeta.hasEffect()) return;
            FireworkEffect effect = fireworkEffectMeta.getEffect();

            getType().setValue(Optional.of(effect.getType()));
            getColors().setValues(effect.getColors());
            getFadeColors().setValues(effect.getFadeColors());
            getHasTrail().setValue(effect.hasTrail());
            getHasTwinkle().setValue(effect.hasFlicker());
        }
    }
}
