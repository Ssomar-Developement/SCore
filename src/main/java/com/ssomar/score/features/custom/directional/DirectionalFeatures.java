package com.ssomar.score.features.custom.directional;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BlockFaceFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DirectionalFeatures extends FeatureWithHisOwnEditor<DirectionalFeatures, DirectionalFeatures, DirectionalFeaturesEditor, DirectionalFeaturesEditorManager> {


    private BooleanFeature forceBlockFaceOnPlace;
    private BlockFaceFeature blockFaceOnPlace;

    public DirectionalFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.directionalFeatures);
        reset();
    }

    @Override
    public void reset() {
         this.forceBlockFaceOnPlace = new BooleanFeature(this,  false, FeatureSettingsSCore.forceBlockFaceOnPlace, false);
         this.blockFaceOnPlace = new BlockFaceFeature(this, Optional.empty(), null, FeatureSettingsSCore.blockFaceOnPlace);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection containerFeaturesSection = config.getConfigurationSection(this.getName());
            error.addAll(this.forceBlockFaceOnPlace.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.blockFaceOnPlace.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection furnaceFeaturesSection = config.createSection(this.getName());
        this.forceBlockFaceOnPlace.save(furnaceFeaturesSection);
        this.blockFaceOnPlace.save(furnaceFeaturesSection);
    }

    @Override
    public DirectionalFeatures getValue() {
        return this;
    }

    @Override
    public DirectionalFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = "&7Directional force BlockFace: &e" + this.forceBlockFaceOnPlace.getValue();
        finalDescription[finalDescription.length - 1] = "&7Directional BlockFace: &e" + this.blockFaceOnPlace.getValue().orElse(null);

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public DirectionalFeatures clone(FeatureParentInterface newParent) {
        DirectionalFeatures eF = new DirectionalFeatures(newParent);
        eF.forceBlockFaceOnPlace = this.forceBlockFaceOnPlace.clone(eF);
        eF.blockFaceOnPlace = this.blockFaceOnPlace.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.forceBlockFaceOnPlace);
        features.add(this.blockFaceOnPlace);
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
            if (feature instanceof DirectionalFeatures) {
                DirectionalFeatures eF = (DirectionalFeatures) feature;
                eF.setForceBlockFaceOnPlace(this.forceBlockFaceOnPlace);
                eF.setBlockFaceOnPlace(this.blockFaceOnPlace);
                break;
            }
        }
    }

    public boolean canBeApplied(BlockData blockData) {
        return blockData instanceof Directional;
    }

    public void applyDirectionalFeatures(Block block) {
        if (canBeApplied(block.getBlockData())) {
            Directional directional = (Directional) block.getBlockData();
            if (this.forceBlockFaceOnPlace.getValue() && this.blockFaceOnPlace.getValue().isPresent()) {
                directional.setFacing(this.blockFaceOnPlace.getValue().get());
                block.setBlockData(directional);
            }
        }
    }


    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        DirectionalFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
