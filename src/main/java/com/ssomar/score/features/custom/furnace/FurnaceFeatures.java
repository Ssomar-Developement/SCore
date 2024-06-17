package com.ssomar.score.features.custom.furnace;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.DoubleFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class FurnaceFeatures extends FeatureWithHisOwnEditor<FurnaceFeatures, FurnaceFeatures, FurnaceFeaturesEditor, FurnaceFeaturesEditorManager> {


    private DoubleFeature furnaceSpeed;
    private BooleanFeature infiniteFuel;
    private BooleanFeature infiniteVisualLit;

    private DoubleFeature fortuneChance;
    private DoubleFeature fortuneMultiplier;

    public FurnaceFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.furnaceFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.furnaceSpeed = new DoubleFeature(this, Optional.of(1.0), FeatureSettingsSCore.furnaceSpeed);
        this.infiniteFuel = new BooleanFeature(this,  false, FeatureSettingsSCore.infiniteFuel, false);
        this.infiniteVisualLit = new BooleanFeature(this,  false, FeatureSettingsSCore.infiniteVisualLit, false);

        this.fortuneChance = new DoubleFeature(this, Optional.of(0.0), FeatureSettingsSCore.fortuneChance);
        this.fortuneMultiplier = new DoubleFeature(this,  Optional.of(1.0), FeatureSettingsSCore.fortuneMultiplier);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection containerFeaturesSection = config.getConfigurationSection(this.getName());
            error.addAll(this.furnaceSpeed.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.infiniteFuel.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.infiniteVisualLit.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.fortuneChance.load(plugin, containerFeaturesSection, isPremiumLoading));
            error.addAll(this.fortuneMultiplier.load(plugin, containerFeaturesSection, isPremiumLoading));
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection furnaceFeaturesSection = config.createSection(this.getName());
        this.furnaceSpeed.save(furnaceFeaturesSection);
        this.infiniteFuel.save(furnaceFeaturesSection);
        this.infiniteVisualLit.save(furnaceFeaturesSection);
        this.fortuneChance.save(furnaceFeaturesSection);
        this.fortuneMultiplier.save(furnaceFeaturesSection);
    }

    @Override
    public FurnaceFeatures getValue() {
        return this;
    }

    @Override
    public FurnaceFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 5] = "&7Furnace speed: &e" + this.furnaceSpeed.getValue().get();
        finalDescription[finalDescription.length - 4] = "&7Furnace infinite Fuel: &e" + this.infiniteFuel.getValue();
        finalDescription[finalDescription.length - 3] = "&7Furnace infinite Visual Lit: &e" + this.infiniteVisualLit.getValue();
        finalDescription[finalDescription.length - 2] = "&7Fortune Chance: &e" + this.fortuneChance.getValue().get();
        finalDescription[finalDescription.length - 1] = "&7Fortune Multiplier: &e" + this.fortuneMultiplier.getValue().get();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public FurnaceFeatures clone(FeatureParentInterface newParent) {
        FurnaceFeatures eF = new FurnaceFeatures(newParent);
        eF.furnaceSpeed = this.furnaceSpeed.clone(eF);
        eF.infiniteFuel = this.infiniteFuel.clone(eF);
        eF.infiniteVisualLit = this.infiniteVisualLit.clone(eF);
        eF.fortuneChance = this.fortuneChance.clone(eF);
        eF.fortuneMultiplier = this.fortuneMultiplier.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.furnaceSpeed);
        features.add(this.infiniteFuel);
        features.add(this.infiniteVisualLit);
        features.add(this.fortuneChance);
        features.add(this.fortuneMultiplier);
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
            if (feature instanceof FurnaceFeatures) {
                FurnaceFeatures eF = (FurnaceFeatures) feature;
                eF.setFurnaceSpeed(this.furnaceSpeed);
                eF.setInfiniteFuel(this.infiniteFuel);
                eF.setInfiniteVisualLit(this.infiniteVisualLit);
                eF.setFortuneChance(this.fortuneChance);
                eF.setFortuneMultiplier(this.fortuneMultiplier);
                break;
            }
        }
    }

    public boolean canBeApplied(BlockData blockData) {
        return (SCore.isPaperOrFork() || SCore.is1v17Plus()) && blockData instanceof Furnace;
    }

    public void applyFurnaceFeatures(Block block) {

        // Only available in Paper
        if (canBeApplied(block.getBlockData())) {
            org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();
            if (getInfiniteFuel().getValue()) furnace.setBurnTime((short) 9999);
            // Only available in Paper
            if(SCore.isPaperOrFork()){
                try {
                    org.bukkit.block.Furnace.class.getMethod("setCookSpeedMultiplier", double.class).invoke(furnace, getFurnaceSpeed().getValue().get());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                //furnace.setCookSpeedMultiplier(getFurnaceSpeed().getValue().get());
            }
            furnace.update();

            if (getInfiniteVisualLit().getValue()) {
                Furnace furnaceData = (Furnace) block.getBlockData();
                furnaceData.setLit(true);
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.getWorld().getPlayers().forEach(p -> p.sendBlockChange(block.getLocation(), furnaceData));
                    }
                };
                runnable.runTaskLater(SCore.plugin, 3);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        FurnaceFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
