package com.ssomar.score.features.custom.container;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ContainerFeatures extends FeatureWithHisOwnEditor<ContainerFeatures, ContainerFeatures, ContainerFeaturesEditor, ContainerFeaturesEditorManager> {


    private ListDetailedMaterialFeature whitelistMaterials;
    private ListDetailedMaterialFeature blacklistMaterials;
    private BooleanFeature isLocked;
    private UncoloredStringFeature lockedName;
    private ColoredStringFeature inventoryTitle;

    public ContainerFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.containerFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.whitelistMaterials = new ListDetailedMaterialFeature(this, new ArrayList<>(),FeatureSettingsSCore.whitelistMaterials, false, false);
        this.blacklistMaterials = new ListDetailedMaterialFeature(this, new ArrayList<>(), FeatureSettingsSCore.blacklistMaterials, false, false);
        this.isLocked = new BooleanFeature(this,  false, FeatureSettingsSCore.isLocked, false);
        this.lockedName = new UncoloredStringFeature(this, Optional.empty(), FeatureSettingsSCore.lockedName, false);
        this.inventoryTitle = new ColoredStringFeature(this, Optional.empty(), FeatureSettingsSCore.inventoryTitle, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection containerFeaturesSection = config.getConfigurationSection(this.getName());
            error.addAll(this.whitelistMaterials.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.blacklistMaterials.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.isLocked.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.lockedName.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.inventoryTitle.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection containerFeaturesSection = config.createSection(this.getName());
        this.whitelistMaterials.save(containerFeaturesSection);
        this.blacklistMaterials.save(containerFeaturesSection);
        this.isLocked.save(containerFeaturesSection);
        this.lockedName.save(containerFeaturesSection);
        this.inventoryTitle.save(containerFeaturesSection);
    }

    @Override
    public ContainerFeatures getValue() {
        return this;
    }

    @Override
    public ContainerFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 5] = "&7Whitelist size: &e" + this.whitelistMaterials.getValue().size();
        finalDescription[finalDescription.length - 4] = "&7Blacklist size: &e" + this.blacklistMaterials.getValue().size();
        finalDescription[finalDescription.length - 3] = "&7Is locked: &e" + this.isLocked.getValue();
        finalDescription[finalDescription.length - 2] = "&7Locked name: &e" + this.lockedName.getValue().orElse("none");
        finalDescription[finalDescription.length - 1] = "&7Inventory title: &e" + this.inventoryTitle.getValue().orElse("none");

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public ContainerFeatures clone(FeatureParentInterface newParent) {
        ContainerFeatures eF = new ContainerFeatures(newParent);
        eF.whitelistMaterials = this.whitelistMaterials.clone(eF);
        eF.blacklistMaterials = this.blacklistMaterials.clone(eF);
        eF.isLocked = this.isLocked.clone(eF);
        eF.lockedName = this.lockedName.clone(eF);
        eF.inventoryTitle = this.inventoryTitle.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.whitelistMaterials);
        features.add(this.blacklistMaterials);
        features.add(this.isLocked);
        features.add(this.lockedName);
        features.add(this.inventoryTitle);
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
            if (feature instanceof ContainerFeatures) {
                ContainerFeatures eF = (ContainerFeatures) feature;
                eF.setWhitelistMaterials(this.whitelistMaterials);
                eF.setBlacklistMaterials(this.blacklistMaterials);
                eF.setIsLocked(this.isLocked);
                eF.setLockedName(this.lockedName);
                eF.setInventoryTitle(this.inventoryTitle);
                break;
            }
        }
    }

    public boolean canBeApplied(BlockData blockData) {
        return blockData instanceof Chest /*|| blockData instanceof EnderChest -> it isnt a containe*/ || blockData instanceof Hopper || blockData instanceof Furnace || blockData instanceof Dispenser || blockData instanceof BrewingStand;
    }

    public void applyContainerFeatures(Block block) {
        //SsomarDev.testMsg("Passe container 1", true);
        if (canBeApplied(block.getBlockData())) {
            Container container = (Container) block.getState();
            //SsomarDev.testMsg("Passe container", true);
            if (getInventoryTitle().getValue().isPresent()) {
                String title = getInventoryTitle().getValue().get();
                // SsomarDev.testMsg("Title: " + title, true);
                container.setCustomName(StringConverter.coloredString(title));
            }
            if (getIsLocked().getValue() && getLockedName().getValue().isPresent()) {
                //SsomarDev.testMsg("Passe locked", true);
                container.setLock(getLockedName().getValue().get());
            }
            container.update();
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        ContainerFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
