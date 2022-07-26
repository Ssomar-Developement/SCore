package com.ssomar.score.features.custom.ifhas.items.group;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.FeaturesGroup;
import com.ssomar.score.features.custom.ifhas.items.attribute.HasItemFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HasItemGroupFeature extends FeatureWithHisOwnEditor<HasItemGroupFeature, HasItemGroupFeature, HasItemGroupFeatureEditor, HasItemGroupFeatureEditorManager> implements FeaturesGroup<HasItemFeature> {

    private Map<String, HasItemFeature> hasItems;
    private boolean notSaveIfNoValue;

    public HasItemGroupFeature(FeatureParentInterface parent, String name, String editorName, String[] description, Material material, Boolean requirePremium, boolean notSaveIfNoValue) {
        super(parent, name, editorName, description, material, requirePremium);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.hasItems = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                HasItemFeature attribute = new HasItemFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                hasItems.put(attributeID, attribute);
            }
        }
        return error;
    }

    public boolean verifHas(ItemStack[] items, int heldSlot) {
        for (HasItemFeature feature : hasItems.values()) {
            boolean valid = false;

            int needed = feature.getAmount().getValue().get();
            int slot = 0;
            for (ItemStack item : items) {
                if (feature.getDetailedSlots().verifSlot(slot, slot == heldSlot)) {
                    if (item != null) {
                        if (item.getType().equals(feature.getMaterial().getValue().get())) {
                            needed = needed - item.getAmount();
                        }
                    }
                }
                if (needed <= 0) {
                    valid = true;
                    break;
                }
                slot++;
            }
            if (!valid) return false;
        }
        return true;
    }

    public boolean verifHasNot(ItemStack[] items, int heldSlot) {
        return !verifHas(items, heldSlot);
    }


    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && hasItems.size() == 0) return;
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : hasItems.keySet()) {
            hasItems.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public HasItemGroupFeature getValue() {
        return this;
    }

    @Override
    public HasItemGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oHasItems(s) added: &e" + hasItems.size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public HasItemFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (HasItemFeature x : hasItems.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public HasItemGroupFeature clone(FeatureParentInterface newParent) {
        HasItemGroupFeature eF = new HasItemGroupFeature(newParent, getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfNoValue());
        HashMap<String, HasItemFeature> newHasItems = new HashMap<>();
        for (String key : hasItems.keySet()) {
            newHasItems.put(key, hasItems.get(key).clone(eF));
        }
        eF.setHasItems(newHasItems);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(hasItems.values());
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
            if (feature instanceof HasItemGroupFeature) {
                HasItemGroupFeature eF = (HasItemGroupFeature) feature;
                eF.setHasItems(this.getHasItems());
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
        HasItemGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "hasItem";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!hasItems.containsKey(id)) {
                HasItemFeature eF = new HasItemFeature(this, id);
                hasItems.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, HasItemFeature feature) {
        hasItems.remove(feature.getId());
    }

}
