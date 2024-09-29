package com.ssomar.score.features.custom.rarity;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ItemRarityFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RarityFeatures extends FeatureWithHisOwnEditor<RarityFeatures, RarityFeatures, RarityFeaturesEditor, RarityFeaturesEditorManager> {

    private BooleanFeature enableRarity;
    private ItemRarityFeature itemRarity;

    public RarityFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.itemRarity);
        reset();
    }

    @Override
    public void reset() {
        this.enableRarity = new BooleanFeature(this, false, FeatureSettingsSCore.enableRarity, false);
        this.itemRarity = new ItemRarityFeature(this, Optional.of(ItemRarity.COMMON), FeatureSettingsSCore.rarity);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (isPremiumLoading && config.isConfigurationSection(getName())) {
            error.addAll(this.enableRarity.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
            error.addAll(this.itemRarity.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.enableRarity.save(section);
        this.itemRarity.save(section);
    }

    public RarityFeatures getValue() {
        return this;
    }

    @Override
    public RarityFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 3] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        if (enableRarity.getValue())
            finalDescription[finalDescription.length - 2] = "&7Enable rarity: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Enable rarity: &c&l✘";

        finalDescription[finalDescription.length - 1] = "&7Rarity: &e" + itemRarity.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public RarityFeatures clone(FeatureParentInterface newParent) {
        RarityFeatures dropFeatures = new RarityFeatures(newParent);
        dropFeatures.enableRarity = this.enableRarity.clone(dropFeatures);
        dropFeatures.itemRarity = this.itemRarity.clone(dropFeatures);
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(enableRarity);
        features.add(itemRarity);
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
            if (feature instanceof RarityFeatures) {
                RarityFeatures dropFeatures = (RarityFeatures) feature;
                dropFeatures.setEnableRarity(this.enableRarity);
                dropFeatures.setItemRarity(this.itemRarity);
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
        RarityFeaturesEditorManager.getInstance().startEditing(player, this);
    }

    public String getSimpleLocString(Location loc){
        return loc.getWorld().getName() + "-" + loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ();
    }

    public @Nullable ItemRarity getItemRarityValue() {
        if (enableRarity.getValue()) return itemRarity.getValue().get();
        return null;
    }
}
