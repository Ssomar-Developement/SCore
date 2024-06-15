package com.ssomar.score.features.custom.entities.entity;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.entities.group.EntityTypeGroupFeature;
import com.ssomar.score.features.types.EntityTypeFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class EntityTypeForGroupFeature extends FeatureWithHisOwnEditor<EntityTypeForGroupFeature, EntityTypeForGroupFeature, EntityTypeForGroupFeatureEditor, EntityTypeForGroupFeatureEditorManager> {

    private EntityTypeFeature entityType;
    private String id;

    public EntityTypeForGroupFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.entityType);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.entityType = new EntityTypeFeature(this, Optional.of(EntityType.ZOMBIE), FeatureSettingsSCore.entityType);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.entityType.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the EntityType with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    public List<String> load(SPlugin plugin, String config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>(this.entityType.load(plugin, config, isPremiumLoading));

        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        reload();
        ((EntityTypeGroupFeature) getParent()).save(config);
    }

    @Override
    public EntityTypeForGroupFeature getValue() {
        return this;
    }

    @Override
    public EntityTypeForGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = "&7EntityType: &e" + entityType.getValue().get();
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public EntityTypeForGroupFeature clone(FeatureParentInterface newParent) {
        EntityTypeForGroupFeature eF = new EntityTypeForGroupFeature(newParent, id);
        eF.setEntityType(entityType.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Collections.singletonList(entityType));
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
            if (feature instanceof EntityTypeForGroupFeature) {
                EntityTypeForGroupFeature aFOF = (EntityTypeForGroupFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setEntityType(entityType);
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
        EntityTypeForGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
