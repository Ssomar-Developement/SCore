package com.ssomar.score.features.custom.othereicooldowns.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.othereicooldowns.cooldown.OtherEICooldown;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OtherEICooldownGroupFeature extends FeatureWithHisOwnEditor<OtherEICooldownGroupFeature, OtherEICooldownGroupFeature, OtherEICooldownGroupFeatureEditor, OtherEICooldownGroupFeatureEditorManager> implements FeaturesGroup<OtherEICooldown> {

    private Map<String, OtherEICooldown> attributes;

    public OtherEICooldownGroupFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.otherEICooldowns);
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
                OtherEICooldown attribute = new OtherEICooldown(this, attributeID);
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

    public void addOtherCooldowns(@NotNull Player player, @Nullable StringPlaceholder sp) {
        for (OtherEICooldown attribute : attributes.values()) {
            attribute.addOtherCooldowns(player, sp);
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : attributes.keySet()) {
            attributes.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public OtherEICooldownGroupFeature getValue() {
        return this;
    }

    @Override
    public OtherEICooldownGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oOther CD(s) added: &e" + attributes.size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public OtherEICooldown getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (OtherEICooldown x : attributes.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public OtherEICooldownGroupFeature clone(FeatureParentInterface newParent) {
        OtherEICooldownGroupFeature eF = new OtherEICooldownGroupFeature(newParent);
        eF.setAttributes(new HashMap<>(this.getAttributes()));
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
            if (feature instanceof OtherEICooldownGroupFeature) {
                OtherEICooldownGroupFeature eF = (OtherEICooldownGroupFeature) feature;
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
        OtherEICooldownGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "cd";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!attributes.containsKey(id)) {
                OtherEICooldown eF = new OtherEICooldown(this, id);
                attributes.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, OtherEICooldown feature) {
        attributes.remove(feature.getId());
    }

}
