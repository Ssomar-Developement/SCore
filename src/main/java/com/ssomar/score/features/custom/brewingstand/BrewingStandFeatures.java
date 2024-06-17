package com.ssomar.score.features.custom.brewingstand;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BrewingStandFeatures extends FeatureWithHisOwnEditor<BrewingStandFeatures, BrewingStandFeatures, BrewingStandFeaturesEditor, BrewingStandFeaturesEditorManager> {


    private DoubleFeature brewingStandSpeed;

    public BrewingStandFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.brewingStandFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.brewingStandSpeed = new DoubleFeature(this, Optional.of(1.0), FeatureSettingsSCore.brewingStandSpeed);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection containerFeaturesSection = config.getConfigurationSection(this.getName());
            error.addAll(this.brewingStandSpeed.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection furnaceFeaturesSection = config.createSection(this.getName());
        this.brewingStandSpeed.save(furnaceFeaturesSection);
    }

    @Override
    public BrewingStandFeatures getValue() {
        return this;
    }

    @Override
    public BrewingStandFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Brewing Stand speed: &e" + this.brewingStandSpeed.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public BrewingStandFeatures clone(FeatureParentInterface newParent) {
        BrewingStandFeatures eF = new BrewingStandFeatures(newParent);
        eF.brewingStandSpeed = this.brewingStandSpeed.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.brewingStandSpeed);
        return features;
    }

    public boolean canBeApplied(BlockData blockData) {
        return SCore.is1v17Plus() && blockData instanceof BrewingStand;
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
            if (feature instanceof BrewingStandFeatures) {
                BrewingStandFeatures eF = (BrewingStandFeatures) feature;
                eF.setBrewingStandSpeed(this.brewingStandSpeed);
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
        BrewingStandFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
