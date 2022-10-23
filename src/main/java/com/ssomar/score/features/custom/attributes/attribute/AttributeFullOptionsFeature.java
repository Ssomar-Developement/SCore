package com.ssomar.score.features.custom.attributes.attribute;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.AttributeSlot;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class AttributeFullOptionsFeature extends FeatureWithHisOwnEditor<AttributeFullOptionsFeature, AttributeFullOptionsFeature, AttributeFullOptionsFeatureEditor, AttributeFullOptionsFeatureEditorManager> {

    private AttributeFeature attribute;
    private OperationFeature operation;
    private DoubleFeature amount;
    private SlotFeature slot;
    private ColoredStringFeature attributeName;
    private UUIDFeature uuid;
    private String id;

    public AttributeFullOptionsFeature(FeatureParentInterface parent, String id) {
        super(parent, "Attribute", "Attribute", new String[]{"&7&oAn attribute with its options"}, Material.BREWING_STAND, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.attribute = new AttributeFeature(this, "attribute", Optional.of(Attribute.GENERIC_ARMOR), "Attribute", new String[]{"&7&oThe attribute"}, Material.BREWING_STAND, false);
        this.operation = new OperationFeature(this, "operation", Optional.of(AttributeModifier.Operation.ADD_NUMBER), "Operation", new String[]{"&7&oThe operation"}, Material.DISPENSER, false);
        this.amount = new DoubleFeature(this, "amount", Optional.of(1.0), "Amount", new String[]{"&7&oThe amount"}, GUI.CLOCK, false);
        this.slot = new SlotFeature(this, "slot", Optional.of(AttributeSlot.HAND), "Slot", new String[]{"&7&oThe slot"}, Material.ARMOR_STAND, false);
        this.attributeName = new ColoredStringFeature(this, "name", Optional.of("&eDefault name"), "Name", new String[]{"&7&oThe name"}, Material.NAME_TAG, false, true);
        this.uuid = new UUIDFeature(this, "uuid", "UUID", new String[]{"&7&oThe UUID"}, Material.NAME_TAG, false);
    }

    public AttributeModifier getAttributeModifier() {
        if (slot.getValue().get().equals(AttributeSlot.ALL_SLOTS)) {
            return new AttributeModifier(uuid.getValue(), attributeName.getValue().get(), amount.getValue().get(), operation.getValue().get(), null);
        } else
            return new AttributeModifier(uuid.getValue(), attributeName.getValue().get(), amount.getValue().get(), operation.getValue().get(), slot.getEquipmentSlotValue().get());
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.attribute.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.operation.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.amount.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.slot.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.attributeName.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.uuid.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Attribute with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.attribute.save(attributeConfig);
        this.operation.save(attributeConfig);
        this.amount.save(attributeConfig);
        this.slot.save(attributeConfig);
        this.attributeName.save(attributeConfig);
        this.uuid.save(attributeConfig);
    }

    @Override
    public AttributeFullOptionsFeature getValue() {
        return this;
    }

    @Override
    public AttributeFullOptionsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = "&7Attribute: &e" + attribute.getValue().get().name();
        finalDescription[finalDescription.length - 5] = "&7Operation: &e" + operation.getValue().get();
        finalDescription[finalDescription.length - 4] = "&7Amount: &e" + amount.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Slot: &e" + this.slot.getValue().get().name();
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = GUI.SHIFT_CLICK_TO_REMOVE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public AttributeFullOptionsFeature clone(FeatureParentInterface newParent) {
        AttributeFullOptionsFeature eF = new AttributeFullOptionsFeature(newParent, id);
        eF.setAttribute(attribute.clone(eF));
        eF.setOperation(operation.clone(eF));
        eF.setAmount(amount.clone(eF));
        eF.setSlot(slot.clone(eF));
        eF.setAttributeName(attributeName.clone(eF));
        eF.setUuid(uuid.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(attribute, operation, amount, slot, attributeName, uuid));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection parentSection = getParent().getConfigurationSection();
        if (parentSection.isConfigurationSection(getId())) {
            return parentSection.getConfigurationSection(getId());
        } else return parentSection.createSection(getId());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof AttributeFullOptionsFeature) {
                AttributeFullOptionsFeature aFOF = (AttributeFullOptionsFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setAttribute(attribute);
                    aFOF.setOperation(operation);
                    aFOF.setAmount(amount);
                    aFOF.setSlot(slot);
                    aFOF.setAttributeName(attributeName);
                    aFOF.setUuid(uuid);
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
        AttributeFullOptionsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
