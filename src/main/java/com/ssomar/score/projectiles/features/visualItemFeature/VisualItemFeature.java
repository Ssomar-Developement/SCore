package com.ssomar.score.projectiles.features.visualItemFeature;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.headfeatures.HeadFeatures;
import com.ssomar.score.features.types.MaterialFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class VisualItemFeature extends FeatureWithHisOwnEditor<VisualItemFeature, VisualItemFeature, VisualItemFeatureEditor, VisualItemFeatureEditorManager> implements SProjectileFeatureInterface {

    private MaterialFeature material;
    private HeadFeatures headFeatures;

    public VisualItemFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.visualItem);
        reset();
    }

    @Override
    public void reset() {
        this.headFeatures = new HeadFeatures(this);
        this.material = new MaterialFeature(this, Optional.of(Material.BARRIER), FeatureSettingsSCore.material);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (material.getValue().isPresent() && e instanceof ThrowableProjectile && !material.getValue().get().equals(Material.BARRIER)) {

            ItemStack item = new ItemStack(headFeatures.getHeadOr(material.getValue().get()));
            ((ThrowableProjectile) e).setItem(item);
        }
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(material.load(plugin, section, isPremiumLoading));
            errors.addAll(headFeatures.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        material.save(section);
        headFeatures.save(section);
    }

    @Override
    public VisualItemFeature getValue() {
        return this;
    }

    @Override
    public VisualItemFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        if (material.getValue().isPresent() && !material.getValue().get().equals(Material.BARRIER))
            finalDescription[finalDescription.length - 1] = "&7Material: &a&l"+material.getValue().get();
        else
            finalDescription[finalDescription.length - 1] = "&7Material: &c&l✘";

        gui.createItem(Material.ITEM_FRAME, 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public VisualItemFeature clone(FeatureParentInterface newParent) {
        VisualItemFeature clone = new VisualItemFeature(newParent);
        clone.setMaterial(material.clone(clone));
        clone.setHeadFeatures(headFeatures.clone(clone));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(material);
        features.add(headFeatures);
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
            if (feature instanceof VisualItemFeature && feature.getName().equals(getName())) {
                VisualItemFeature hiders = (VisualItemFeature) feature;
                hiders.setHeadFeatures(headFeatures);
                hiders.setMaterial(material);
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
        VisualItemFeatureEditorManager.getInstance().startEditing(player, this);
    }
}
