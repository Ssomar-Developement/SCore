package com.ssomar.score.features.custom.entities.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.entities.entity.EntityTypeForGroupFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class EntityTypeGroupFeature extends FeatureWithHisOwnEditor<EntityTypeGroupFeature, EntityTypeGroupFeature, EntityTypeGroupFeatureEditor, EntityTypeGroupFeatureEditorManager> implements FeaturesGroup<EntityTypeForGroupFeature> {

    private Map<String, EntityTypeForGroupFeature> entityTypes;
    private boolean hasID;
    private boolean useTheParentSection;
    private boolean notSaveIfEmpty;

    public EntityTypeGroupFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings, boolean hasID, boolean useTheParentSection, boolean notSaveIfEmpty) {
        super(parent, featureSettings);
        this.useTheParentSection = useTheParentSection;
        this.hasID = hasID;
        this.notSaveIfEmpty = notSaveIfEmpty;
        reset();
    }

    @Override
    public void reset() {
        this.entityTypes = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            if (hasID) {
                ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
                for (String attributeID : enchantmentsSection.getKeys(false)) {
                    EntityTypeForGroupFeature attribute = new EntityTypeForGroupFeature(this, attributeID);
                    List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                    if (subErrors.size() > 0) {
                        error.addAll(subErrors);
                        continue;
                    }
                    entityTypes.put(attributeID, attribute);
                }
            } else {
                List<String> configs = config.getStringList(this.getName());
                for (String strConfig : configs) {
                    UUID uuid = UUID.randomUUID();
                    EntityTypeForGroupFeature entityType = new EntityTypeForGroupFeature(this, uuid.toString());
                    List<String> subErrors = entityType.load(plugin, strConfig, isPremiumLoading);
                    if (subErrors.size() > 0) {
                        error.addAll(subErrors);
                        continue;
                    }
                    entityTypes.put(uuid.toString(), entityType);
                }
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        if (notSaveIfEmpty && entityTypes.size() == 0) return;
        if (hasID) {
            ConfigurationSection attributesSection = config.createSection(this.getName());
            for (String enchantmentID : entityTypes.keySet()) {
                entityTypes.get(enchantmentID).save(attributesSection);
            }
        } else {
            List<String> configs = new ArrayList<>();
            for (EntityType entityType : getEntityTypeList()) {
                configs.add(entityType.name());
            }
            config.set(this.getName(), configs);
        }
    }

    @Override
    public EntityTypeGroupFeature getValue() {
        return this;
    }

    public List<EntityType> getEntityTypeList() {
        List<EntityType> entityTypes = new ArrayList<>();
        for (EntityTypeForGroupFeature entityType : this.entityTypes.values()) {
            entityTypes.add(entityType.getValue().getEntityType().getValue().get());
        }
        return entityTypes;
    }

    @Override
    public EntityTypeGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oEntityType(s) added: &e" + entityTypes.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public EntityTypeForGroupFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (EntityTypeForGroupFeature x : entityTypes.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public EntityTypeGroupFeature clone(FeatureParentInterface newParent) {
        EntityTypeGroupFeature eF = new EntityTypeGroupFeature(newParent, getFeatureSettings(), hasID, useTheParentSection, notSaveIfEmpty);
        HashMap<String, EntityTypeForGroupFeature> newEntityTypes = new HashMap<>();
        for (String key : entityTypes.keySet()) {
            newEntityTypes.put(key, entityTypes.get(key).clone(eF));
        }
        eF.setEntityTypes(newEntityTypes);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(entityTypes.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (!useTheParentSection) {
            if (section.isConfigurationSection(this.getName())) {
                return section.getConfigurationSection(this.getName());
            } else return section.createSection(this.getName());
        }
        return section;
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof EntityTypeGroupFeature) {
                EntityTypeGroupFeature eF = (EntityTypeGroupFeature) feature;
                eF.setEntityTypes(entityTypes);
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
        EntityTypeGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "eT";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!entityTypes.containsKey(id)) {
                EntityTypeForGroupFeature eF = new EntityTypeForGroupFeature(this, id);
                entityTypes.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, EntityTypeForGroupFeature feature) {
        entityTypes.remove(feature.getId());
    }

}
