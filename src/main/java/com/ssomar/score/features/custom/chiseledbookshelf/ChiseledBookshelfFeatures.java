package com.ssomar.score.features.custom.chiseledbookshelf;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.list.ListIntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.FixedMaterial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ChiseledBookshelfFeatures extends FeatureWithHisOwnEditor<ChiseledBookshelfFeatures, ChiseledBookshelfFeatures, ChiseledBookshelfFeaturesEditor, ChiseledBookshelfFeaturesEditorManager> {


    private ListIntegerFeature occupiedSlots;

    public ChiseledBookshelfFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.chiseledBookshelfFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.occupiedSlots = new ListIntegerFeature(this, new ArrayList<>(), FeatureSettingsSCore.occupiedSlots, false, Optional.empty());
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection containerFeaturesSection = config.getConfigurationSection(this.getName());
            error.addAll(this.occupiedSlots.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection furnaceFeaturesSection = config.createSection(this.getName());
        this.occupiedSlots.save(furnaceFeaturesSection);
    }

    @Override
    public ChiseledBookshelfFeatures getValue() {
        return this;
    }

    @Override
    public ChiseledBookshelfFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Occupied slots: &e" + this.occupiedSlots.getCurrentValues().size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public ChiseledBookshelfFeatures clone(FeatureParentInterface newParent) {
        ChiseledBookshelfFeatures eF = new ChiseledBookshelfFeatures(newParent);
        eF.occupiedSlots = this.occupiedSlots.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.occupiedSlots);
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
            if (feature instanceof ChiseledBookshelfFeatures) {
                ChiseledBookshelfFeatures eF = (ChiseledBookshelfFeatures) feature;
                eF.setOccupiedSlots(this.occupiedSlots);
                break;
            }
        }
    }

    public boolean canBeApplied(BlockData blockData) {
        return SCore.is1v20Plus() && blockData instanceof ChiseledBookshelf;
    }

    public void applyChiseledBookshelfFeatures(Block block) {

        // Only available in Paper
        if (canBeApplied(block.getBlockData())) {
            ChiseledBookshelf chiseledBookshelf = (ChiseledBookshelf) block.getBlockData();
            for (Integer i : occupiedSlots.getValue()){
                chiseledBookshelf.setSlotOccupied(i, true);
                SsomarDev.testMsg("Slot "+i+" is occupied", true);
            }
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    block.getWorld().getPlayers().forEach(p -> p.sendBlockChange(block.getLocation(), chiseledBookshelf));
                }
            };
            runnable.runTaskLater(SCore.plugin, 3);
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        ChiseledBookshelfFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
