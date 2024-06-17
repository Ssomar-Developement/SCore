package com.ssomar.score.features.custom.durabilityFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DurabilityFeatures extends FeatureWithHisOwnEditor<DurabilityFeatures, DurabilityFeatures, DurabilityFeaturesEditor, DurabilityFeaturesEditorManager> {


    private IntegerFeature maxDurability;
    private IntegerFeature durability;
    private BooleanFeature isDurabilityBasedOnUsage;


    public DurabilityFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.durability_features);
        reset();
    }

    @Override
    public void reset() {
        this.maxDurability = new IntegerFeature(this, Optional.empty(), FeatureSettingsSCore.maxDurability);
        this.durability = new IntegerFeature(this, Optional.empty(), FeatureSettingsSCore.durability);
        this.isDurabilityBasedOnUsage = new BooleanFeature(this, false, FeatureSettingsSCore.isDurabilityBasedOnUsage, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (SCore.is1v20v5Plus()) {
            errors.addAll(isDurabilityBasedOnUsage.load(plugin, config, isPremiumLoading));
            errors.addAll(maxDurability.load(plugin, config, isPremiumLoading));
        }
        errors.addAll(durability.load(plugin, config, isPremiumLoading));

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        if (SCore.is1v20v5Plus()) {
            maxDurability.save(config);
            isDurabilityBasedOnUsage.save(config);
        }
        durability.save(config);
    }

    @Override
    public DurabilityFeatures getValue() {
        return this;
    }

    @Override
    public DurabilityFeatures initItemParentEditor(GUI gui, int slot) {
        int amount = 2;
        if (SCore.is1v20v5Plus()) amount = 4;
        String[] finalDescription = new String[getEditorDescription().length + amount];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - amount] = GUI.CLICK_HERE_TO_CHANGE;
        amount--;
        if (SCore.is1v20v5Plus()) {
            if (isDurabilityBasedOnUsage.getValue())
                finalDescription[finalDescription.length - amount] = "&7isDurabilityBasedOnUsage: &a&l✔";
            else finalDescription[finalDescription.length - amount] = "&7isDurabilityBasedOnUsage: &c&l✘";
            amount--;

            if (maxDurability.getValue().isPresent())
                finalDescription[finalDescription.length - amount] = "&7Max durability: &e" + maxDurability.getValue().get();
            else finalDescription[finalDescription.length - amount] = "&7Max durability: &cNot set";
            amount--;
        }
        if (durability.getValue().isPresent())
            finalDescription[finalDescription.length - amount] = "&7Durability: &e" + durability.getValue().get();
        else finalDescription[finalDescription.length - amount] = "&7Durability: &cNot set";


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DurabilityFeatures clone(FeatureParentInterface newParent) {
        DurabilityFeatures dropFeatures = new DurabilityFeatures(newParent);
        dropFeatures.maxDurability = this.maxDurability.clone(dropFeatures);
        dropFeatures.durability = this.durability.clone(dropFeatures);
        dropFeatures.isDurabilityBasedOnUsage = this.isDurabilityBasedOnUsage.clone(dropFeatures);

        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(maxDurability);
        features.add(durability);
        features.add(isDurabilityBasedOnUsage);
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
            if (feature instanceof DurabilityFeatures) {
                DurabilityFeatures hiders = (DurabilityFeatures) feature;
                hiders.setMaxDurability(maxDurability);
                hiders.setDurability(durability);
                hiders.setIsDurabilityBasedOnUsage(isDurabilityBasedOnUsage);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        DurabilityFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
