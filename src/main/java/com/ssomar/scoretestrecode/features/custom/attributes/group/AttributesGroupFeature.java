package com.ssomar.scoretestrecode.features.custom.attributes.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.FeaturesGroup;
import com.ssomar.scoretestrecode.features.custom.attributes.attribute.AttributeFullOptionsFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AttributesGroupFeature extends FeatureWithHisOwnEditor<AttributesGroupFeature, AttributesGroupFeature, AttributesGroupFeatureEditor, AttributesGroupFeatureEditorManager> implements FeaturesGroup<AttributeFullOptionsFeature> {

    private Map<String, AttributeFullOptionsFeature> attributes;
    private boolean notSaveIfNoValue;

    private int premiumLimit = 2;

    public AttributesGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, "attributes", "Attributes", new String[]{"&7&oThe attributes"}, Material.BREWING_STAND, false);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.attributes = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                if (attributes.size() >= premiumLimit && !isPremium()) {
                    error.add("&cERROR, Couldn't load the Attribute of " + attributeID + " from config, &7&o" + getParent().getParentInfo() + " &6>> Because it requires the premium version to have more than 2 attributes !");
                    return error;
                }
                AttributeFullOptionsFeature attribute = new AttributeFullOptionsFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                attributes.put(attributeID, attribute);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && attributes.size() == 0) return;
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : attributes.keySet()) {
            attributes.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public AttributesGroupFeature getValue() {
        return this;
    }

    @Override
    public AttributesGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oAttribute(s) added: &e" + attributes.size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public AttributeFullOptionsFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (AttributeFullOptionsFeature x : attributes.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public AttributesGroupFeature clone(FeatureParentInterface newParent) {
        AttributesGroupFeature eF = new AttributesGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, AttributeFullOptionsFeature> newAttributes = new HashMap<>();
        for (String x : attributes.keySet()) {
            newAttributes.put(x, attributes.get(x).clone(eF));
        }
        eF.setAttributes(newAttributes);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(attributes.values());
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
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof AttributesGroupFeature) {
                AttributesGroupFeature eF = (AttributesGroupFeature) feature;
                eF.setAttributes(this.getAttributes());
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
        AttributesGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        if (!isPremium() && attributes.size() >= premiumLimit) return;
        String baseId = "attribute";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!attributes.containsKey(id)) {
                AttributeFullOptionsFeature eF = new AttributeFullOptionsFeature(this, id);
                attributes.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, AttributeFullOptionsFeature feature) {
        attributes.remove(feature.getId());
    }

}
