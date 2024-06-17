package com.ssomar.score.features.custom.hopper;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class HopperFeatures extends FeatureWithHisOwnEditor<HopperFeatures, HopperFeatures, HopperFeaturesEditor, HopperFeaturesEditorManager> {


    private IntegerFeature amountItemsTransferred;

    public HopperFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.hopperFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.amountItemsTransferred = new IntegerFeature(this, Optional.empty(), FeatureSettingsSCore.amountItemsTransferred);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection containerFeaturesSection = config.getConfigurationSection(this.getName());
            error.addAll(this.amountItemsTransferred.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection containerFeaturesSection = config.createSection(this.getName());
        this.amountItemsTransferred.save(containerFeaturesSection);
    }

    @Override
    public HopperFeatures getValue() {
        return this;
    }

    @Override
    public HopperFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Amount transferred: &e" + this.amountItemsTransferred.getValue().orElse(1);

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public HopperFeatures clone(FeatureParentInterface newParent) {
        HopperFeatures eF = new HopperFeatures(newParent);
        eF.amountItemsTransferred = this.amountItemsTransferred.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.amountItemsTransferred);
        return features;
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
            if (feature instanceof HopperFeatures) {
                HopperFeatures eF = (HopperFeatures) feature;
                eF.setAmountItemsTransferred(this.amountItemsTransferred);
                break;
            }
        }
    }

    public boolean canBeApplied(BlockData blockData) {
        return blockData instanceof Hopper;
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        HopperFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
