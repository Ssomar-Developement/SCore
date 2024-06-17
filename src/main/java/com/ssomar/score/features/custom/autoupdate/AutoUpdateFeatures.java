package com.ssomar.score.features.custom.autoupdate;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AutoUpdateFeatures extends FeatureWithHisOwnEditor<AutoUpdateFeatures, AutoUpdateFeatures, AutoUpdateFeaturesEditor, AutoUpdateFeaturesEditorManager> {

    private BooleanFeature autoUpdateItem;
    private BooleanFeature updateName;
    private BooleanFeature updateLore;
    private BooleanFeature updateDurability;
    private BooleanFeature updateAttributes;
    private BooleanFeature updateEnchants;
    private BooleanFeature updateCustomModelData;
    private BooleanFeature updateArmorSettings;

    public AutoUpdateFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.autoUpdateFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.autoUpdateItem = new BooleanFeature(this,  false, FeatureSettingsSCore.autoUpdateItem, false);
        this.updateName = new BooleanFeature(this, true, FeatureSettingsSCore.updateName, false);
        this.updateLore = new BooleanFeature(this,  true, FeatureSettingsSCore.updateLore, false);
        this.updateDurability = new BooleanFeature(this, false, FeatureSettingsSCore.updateDurability, false);
        this.updateAttributes = new BooleanFeature(this, false, FeatureSettingsSCore.updateAttributes, false);
        this.updateEnchants = new BooleanFeature(this, false, FeatureSettingsSCore.updateEnchants, false);
        this.updateCustomModelData = new BooleanFeature(this, false, FeatureSettingsSCore.updateCustomModelData, false);
        this.updateArmorSettings = new BooleanFeature(this, false, FeatureSettingsSCore.updateArmorSettings, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        errors.addAll(autoUpdateItem.load(plugin, config, isPremiumLoading));
        errors.addAll(updateName.load(plugin, config, isPremiumLoading));
        errors.addAll(updateLore.load(plugin, config, isPremiumLoading));
        errors.addAll(updateDurability.load(plugin, config, isPremiumLoading));
        errors.addAll(updateAttributes.load(plugin, config, isPremiumLoading));
        errors.addAll(updateEnchants.load(plugin, config, isPremiumLoading));
        errors.addAll(updateCustomModelData.load(plugin, config, isPremiumLoading));
        errors.addAll(updateArmorSettings.load(plugin, config, isPremiumLoading));

        return errors;
    }

    public List<ResetSetting> getResetSettings(){
        List<ResetSetting> resetSettings = new ArrayList<>();
        if(updateName.getValue()) resetSettings.add(ResetSetting.NAME);
        if(updateLore.getValue()) resetSettings.add(ResetSetting.LORE);
        if(updateDurability.getValue()) resetSettings.add(ResetSetting.DURABILITY);
        if(updateAttributes.getValue()) resetSettings.add(ResetSetting.ATTRIBUTES);
        if(updateEnchants.getValue()) resetSettings.add(ResetSetting.ENCHANTS);
        if(updateCustomModelData.getValue()) resetSettings.add(ResetSetting.CUSTOM_MODEL_DATA);
        if(updateArmorSettings.getValue()) resetSettings.add(ResetSetting.ARMOR_SETTINGS);
        return resetSettings;
    }

    @Override
    public void save(ConfigurationSection config) {
        autoUpdateItem.save(config);
        updateName.save(config);
        updateLore.save(config);
        updateDurability.save(config);
        updateAttributes.save(config);
        updateEnchants.save(config);
        updateCustomModelData.save(config);
        updateArmorSettings.save(config);
    }

    @Override
    public AutoUpdateFeatures getValue() {
        return this;
    }

    @Override
    public AutoUpdateFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 9];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 9] = GUI.CLICK_HERE_TO_CHANGE;
        if (autoUpdateItem.getValue())
            finalDescription[finalDescription.length - 8] = "&7AutoUpdate: &a&l✔";
        else
            finalDescription[finalDescription.length - 8] = "&7AutoUpdate: &c&l✘";

        if (updateName.getValue())
            finalDescription[finalDescription.length - 7] = "&7Update Name: &a&l✔";
        else
            finalDescription[finalDescription.length - 7] = "&7Update Name: &c&l✘";

        if (updateLore.getValue())
            finalDescription[finalDescription.length - 6] = "&7Update Lore: &a&l✔";
        else
            finalDescription[finalDescription.length - 6] = "&7Update Lore: &c&l✘";

        if (updateDurability.getValue())
            finalDescription[finalDescription.length - 5] = "&7Update Durability: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7Update Durability: &c&l✘";

        if (updateAttributes.getValue())
            finalDescription[finalDescription.length - 4] = "&7Update Attributes: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Update Attributes: &c&l✘";

        if (updateEnchants.getValue())
            finalDescription[finalDescription.length - 3] = "&7Update Enchants: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Update Enchants: &c&l✘";

        if (updateCustomModelData.getValue())
            finalDescription[finalDescription.length - 2] = "&7Update Custom Model Data: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Update Custom Model Data: &c&l✘";

        if (updateArmorSettings.getValue())
            finalDescription[finalDescription.length - 1] = "&7Update Armor Settings: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Update Armor Settings: &c&l✘";

        gui.createItem(GUI.GRINDSTONE, 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public AutoUpdateFeatures clone(FeatureParentInterface newParent) {
        AutoUpdateFeatures dropFeatures = new AutoUpdateFeatures(newParent);
        dropFeatures.autoUpdateItem = autoUpdateItem.clone(dropFeatures);
        dropFeatures.updateName = updateName.clone(dropFeatures);
        dropFeatures.updateLore = updateLore.clone(dropFeatures);
        dropFeatures.updateDurability = updateDurability.clone(dropFeatures);
        dropFeatures.updateAttributes = updateAttributes.clone(dropFeatures);
        dropFeatures.updateEnchants = updateEnchants.clone(dropFeatures);
        dropFeatures.updateCustomModelData = updateCustomModelData.clone(dropFeatures);
        dropFeatures.updateArmorSettings = updateArmorSettings.clone(dropFeatures);
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(autoUpdateItem);
        features.add(updateName);
        features.add(updateLore);
        features.add(updateDurability);
        features.add(updateAttributes);
        features.add(updateEnchants);
        features.add(updateCustomModelData);
        features.add(updateArmorSettings);
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
            if (feature instanceof AutoUpdateFeatures) {
                AutoUpdateFeatures hiders = (AutoUpdateFeatures) feature;
                hiders.setAutoUpdateItem(autoUpdateItem);
                hiders.setUpdateName(updateName);
                hiders.setUpdateLore(updateLore);
                hiders.setUpdateDurability(updateDurability);
                hiders.setUpdateAttributes(updateAttributes);
                hiders.setUpdateEnchants(updateEnchants);
                hiders.setUpdateCustomModelData(updateCustomModelData);
                hiders.setUpdateArmorSettings(updateArmorSettings);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        AutoUpdateFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
