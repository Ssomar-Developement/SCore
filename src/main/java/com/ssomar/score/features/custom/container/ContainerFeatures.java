package com.ssomar.score.features.custom.container;

import com.ssomar.score.SCore;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Container;
import org.bukkit.block.Smoker;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ContainerFeatures extends FeatureWithHisOwnEditor<ContainerFeatures, ContainerFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem, FeatureForBlock {


    private ListDetailedMaterialFeature whitelistMaterials;
    private ListDetailedMaterialFeature blacklistMaterials;
    private BooleanFeature isLocked;
    private UncoloredStringFeature lockedName;
    private ColoredStringFeature inventoryTitle;
    private ContainerContent containerContent;

    private ContainerOptions onlyItemOptions = ContainerOptions.BOTH;

    public ContainerFeatures(FeatureParentInterface parent, ContainerOptions onItemOptions) {
        super(parent, FeatureSettingsSCore.containerFeatures);
        this.onlyItemOptions = onItemOptions;
        reset();
    }

    public ContainerFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.containerFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.whitelistMaterials = new ListDetailedMaterialFeature(this, new ArrayList<>(), FeatureSettingsSCore.whitelistMaterials, false);
        this.blacklistMaterials = new ListDetailedMaterialFeature(this, new ArrayList<>(), FeatureSettingsSCore.blacklistMaterials, false);
        this.isLocked = new BooleanFeature(this, false, FeatureSettingsSCore.isLocked);
        this.lockedName = new UncoloredStringFeature(this, Optional.empty(), FeatureSettingsSCore.lockedName, false);
        this.inventoryTitle = new ColoredStringFeature(this, Optional.empty(), FeatureSettingsSCore.inventoryTitle);
        this.containerContent = new ContainerContent(this, new ArrayList<>(), FeatureSettingsSCore.containerContent);
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
            error.addAll(this.containerContent.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection containerFeaturesSection = config.createSection(this.getName());
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_BLOCK) {
            this.whitelistMaterials.save(containerFeaturesSection);
            this.blacklistMaterials.save(containerFeaturesSection);
            this.inventoryTitle.save(containerFeaturesSection);
        }
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_ITEM) {
            this.isLocked.save(containerFeaturesSection);
            this.lockedName.save(containerFeaturesSection);
            this.containerContent.save(containerFeaturesSection);
        }
    }

    @Override
    public ContainerFeatures getValue() {
        return this;
    }

    @Override
    public ContainerFeatures initItemParentEditor(GUI gui, int slot) {
        int count = 7;
        if (onlyItemOptions == ContainerOptions.ONLY_ITEM || onlyItemOptions == ContainerOptions.ONLY_BLOCK) count = 4;
        String[] finalDescription = new String[getEditorDescription().length + count];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - count] = gui.CLICK_HERE_TO_CHANGE;
        count--;
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_BLOCK) {
            finalDescription[finalDescription.length - count] = "&7Whitelist size: &e" + this.whitelistMaterials.getValue().size();
            count--;
            finalDescription[finalDescription.length - count] = "&7Blacklist size: &e" + this.blacklistMaterials.getValue().size();
            count--;
        }
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_ITEM) {
            finalDescription[finalDescription.length - count] = "&7Is locked: &e" + this.isLocked.getValue();
            count--;
            finalDescription[finalDescription.length - count] = "&7Locked name: &e" + this.lockedName.getValue().orElse("none");
            count--;
        }
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_BLOCK) {
            finalDescription[finalDescription.length - count] = "&7Inventory title: &e" + this.inventoryTitle.getValue().orElse("none");
            count--;
        }
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_ITEM) {
            finalDescription[finalDescription.length - count] = "&7Container content: &e" + this.containerContent.getValue().size();
        }

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
        eF.containerContent = this.containerContent.clone(eF);
        eF.onlyItemOptions = this.onlyItemOptions;
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_BLOCK) {
            features.add(this.whitelistMaterials);
            features.add(this.blacklistMaterials);
            features.add(this.inventoryTitle);
        }
        if (onlyItemOptions == ContainerOptions.BOTH || onlyItemOptions == ContainerOptions.ONLY_ITEM) {
            features.add(this.isLocked);
            features.add(this.lockedName);
            features.add(this.containerContent);
        }
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof ContainerFeatures) {
                ContainerFeatures eF = (ContainerFeatures) feature;
                eF.setWhitelistMaterials(this.whitelistMaterials);
                eF.setBlacklistMaterials(this.blacklistMaterials);
                eF.setIsLocked(this.isLocked);
                eF.setLockedName(this.lockedName);
                eF.setInventoryTitle(this.inventoryTitle);
                eF.setContainerContent(this.containerContent);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForBlockArgs args) {
        BlockData blockData = args.getData();
        // Because before 1.21 it was not possible to create a blockstate from non existing block
        if (!SCore.is1v21Plus())
            return blockData instanceof Chest /*|| blockData instanceof EnderChest -> it isnt a containe*/ || blockData instanceof Hopper || blockData instanceof Furnace || blockData instanceof Dispenser || blockData instanceof BrewingStand || blockData.getMaterial().toString().contains("SHULKER_BOX") || (/* .type only available in 1.19 */ SCore.is1v19Plus() && blockData instanceof Barrel) || blockData instanceof Smoker || blockData instanceof BlastFurnace || (SCore.is1v20v4Plus() && blockData instanceof org.bukkit.block.data.type.Crafter);
        else return args.getBlockState() instanceof Container;
    }

    @Override
    public void applyOnBlockData(@NotNull FeatureForBlockArgs args) {

        if (!isApplicable(args)) return;

        Container container = (Container) args.getBlockState();
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

        containerContent.applyOnBlockData(args);
    }


    @Override
    public void loadFromBlockData(@NotNull FeatureForBlockArgs args) {

        if (!isAvailable() || !isApplicable(args)) return;

        Container container = (Container) args.getBlockState();
        if (container.getCustomName() != null) {
            getInventoryTitle().setValue(StringConverter.decoloredString(container.getCustomName()));
        }
        if (container.isLocked()) {
            getIsLocked().setValue(true);
            getLockedName().setValue(Optional.of(container.getLock()));
        }

        containerContent.loadFromBlockData(args);

    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        if(!SCore.is1v20v4Plus() || !(meta instanceof BlockStateMeta)) return false;
        BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
        return blockStateMeta.getBlockState() instanceof Container;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {
        containerContent.applyOnItemMeta(args);

        if(isLocked.getValue() && lockedName.getValue().isPresent()) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) args.getMeta();
            Container container = (Container) blockStateMeta.getBlockState();
            if(SCore.is1v21v2Plus()){
                try {
                    ItemStack item = Bukkit.getServer().getItemFactory().createItemStack(lockedName.getValue().get());
                    container.setLockItem(item);
                }catch (Exception e) {
                    //e.printStackTrace();
                    container.setLock(lockedName.getValue().get());
                   // SsomarDev.testMsg("container lock name : "+container.getLock(), true);
                }
            }
            else container.setLock(lockedName.getValue().get());

            container.update();
            blockStateMeta.setBlockState(container);
        }

    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {
        containerContent.loadFromItemMeta(args);

        BlockStateMeta blockStateMeta = (BlockStateMeta) args.getMeta();
        Container container = (Container) blockStateMeta.getBlockState();
        if(container.isLocked()) {
            isLocked.setValue(true);
            lockedName.setValue(Optional.of(container.getLock()));
        }
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.CONTAINER;
    }
}
