package com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.group;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.FeaturesGroup;
import com.ssomar.scoretestrecode.features.custom.ifhas.executableitems.attribute.HasExecutableItemFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class HasExecutableItemGroupFeature extends FeatureWithHisOwnEditor<HasExecutableItemGroupFeature, HasExecutableItemGroupFeature, HasExecutableItemGroupFeatureEditor, HasExecutableItemGroupFeatureEditorManager> implements FeaturesGroup<HasExecutableItemFeature> {

    private Map<String, HasExecutableItemFeature> hasExecutableItems;
    private boolean notSaveIfNoValue;

    public HasExecutableItemGroupFeature(FeatureParentInterface parent, String name, String editorName, String[] description, Material material, Boolean requirePremium, boolean notSaveIfNoValue) {
        super(parent, name, editorName, description, material, requirePremium);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.hasExecutableItems = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                HasExecutableItemFeature attribute = new HasExecutableItemFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                hasExecutableItems.put(attributeID, attribute);
            }
        }
        return error;
    }

    public boolean verifHas(ItemStack[] items, int heldSlot) {

        for (HasExecutableItemFeature feature : hasExecutableItems.values()) {
            boolean valid = false;

            Optional<ExecutableItemInterface> ei = feature.getExecutableItem().getValue();
            if (!ei.isPresent()) continue;
            ExecutableItemInterface executableItem = ei.get();

            int needed = feature.getAmount().getValue().get();
            int slot = 0;
            for (ItemStack item : items) {
                if (feature.getDetailedSlots().verifSlot(slot, slot == heldSlot)) {
                    if (item != null) {
                        ExecutableItemObject eiObject = new ExecutableItemObject(item);
                        if (eiObject.isValid()) {
                            if (eiObject.getConfig().getId().equals(executableItem.getId())) {
                                if (feature.getUsageCondition().getValue().isPresent()) {
                                    eiObject.loadExecutableItemInfos();
                                    if (!StringCalculation.calculation(feature.getUsageCondition().getValue().get(), eiObject.getUsage())) {
                                        continue;
                                    }
                                }
                                needed = needed - item.getAmount();
                            }
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
        if (notSaveIfNoValue && hasExecutableItems.size() == 0) return;
        ConfigurationSection attributesSection = config.createSection(this.getName());
        for (String enchantmentID : hasExecutableItems.keySet()) {
            hasExecutableItems.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public HasExecutableItemGroupFeature getValue() {
        return this;
    }

    @Override
    public HasExecutableItemGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oHasExecutableItems(s) added: &e" + hasExecutableItems.size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public HasExecutableItemFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (HasExecutableItemFeature x : hasExecutableItems.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public HasExecutableItemGroupFeature clone(FeatureParentInterface newParent) {
        HasExecutableItemGroupFeature eF = new HasExecutableItemGroupFeature(newParent, getName(), getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium(), isNotSaveIfNoValue());
        HashMap<String, HasExecutableItemFeature> newHasExecutableItems = new HashMap<>();
        for (String key : hasExecutableItems.keySet()) {
            newHasExecutableItems.put(key, hasExecutableItems.get(key).clone(eF));
        }
        eF.setHasExecutableItems(newHasExecutableItems);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(hasExecutableItems.values());
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
            if (feature instanceof HasExecutableItemGroupFeature) {
                HasExecutableItemGroupFeature eF = (HasExecutableItemGroupFeature) feature;
                eF.setHasExecutableItems(this.getHasExecutableItems());
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
        HasExecutableItemGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "hasExecutableItem";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!hasExecutableItems.containsKey(id)) {
                HasExecutableItemFeature eF = new HasExecutableItemFeature(this, id);
                hasExecutableItems.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, HasExecutableItemFeature feature) {
        hasExecutableItems.remove(feature.getId());
    }

}
