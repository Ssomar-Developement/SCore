package com.ssomar.score.features.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.numbers.NTools;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.spawner.SpawnerEntry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityFactory;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ItemSpawnerFeature extends FeatureWithHisOwnEditor<ItemSpawnerFeature, ItemSpawnerFeature, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItem, FeatureForBlock {

    private IntegerFeature spawnCount;
    private IntegerFeature spawnDelay;
    private IntegerFeature spawnRange;
    private IntegerFeature requiredPlayerRange;
    private IntegerFeature minSpawnDelay;
    private IntegerFeature maxSpawnDelay;
    private IntegerFeature maxNearbyEntities;
    private ListUncoloredStringFeature potentialSpawns;
    private BooleanFeature addSpawnerNbtToItem;

    public ItemSpawnerFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.spawnerFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.spawnDelay = new IntegerFeature(this, Optional.of(0), FeatureSettingsSCore.spawnDelay);
        this.spawnRange = new IntegerFeature(this, Optional.of(4), FeatureSettingsSCore.spawnRange);
        this.requiredPlayerRange = new IntegerFeature(this, Optional.of(16), FeatureSettingsSCore.requiredPlayerRange);
        this.minSpawnDelay = new IntegerFeature(this, Optional.of(200), FeatureSettingsSCore.minSpawnDelay);
        this.maxSpawnDelay = new IntegerFeature(this, Optional.of(800), FeatureSettingsSCore.maxSpawnDelay);
        this.spawnCount = new IntegerFeature(this, Optional.of(4), FeatureSettingsSCore.spawnCount);
        this.maxNearbyEntities = new IntegerFeature(this, Optional.of(6), FeatureSettingsSCore.maxNearbyEntities);
        this.potentialSpawns = new ListUncoloredStringFeature(this, new ArrayList<>(), FeatureSettingsSCore.potentialSpawns, Optional.empty());
        this.addSpawnerNbtToItem = new BooleanFeature(this, false, FeatureSettingsSCore.addSpawnerNbtToItem);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(this.spawnCount.load(plugin, section, isPremiumLoading));
            errors.addAll(this.minSpawnDelay.load(plugin, section, isPremiumLoading));
            errors.addAll(this.maxSpawnDelay.load(plugin, section, isPremiumLoading));
            errors.addAll(this.spawnDelay.load(plugin, section, isPremiumLoading));
            errors.addAll(this.spawnRange.load(plugin, section, isPremiumLoading));
            errors.addAll(this.requiredPlayerRange.load(plugin, section, isPremiumLoading));
            errors.addAll(this.maxNearbyEntities.load(plugin, section, isPremiumLoading));
            errors.addAll(this.potentialSpawns.load(plugin, section, isPremiumLoading));
            errors.addAll(this.addSpawnerNbtToItem.load(plugin, section, isPremiumLoading));
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.spawnCount.save(section);
        this.spawnDelay.save(section);
        this.spawnRange.save(section);
        this.requiredPlayerRange.save(section);
        this.minSpawnDelay.save(section);
        this.maxSpawnDelay.save(section);
        this.maxNearbyEntities.save(section);
        this.potentialSpawns.save(section);
        this.addSpawnerNbtToItem.save(section);
    }

    @Override
    public ItemSpawnerFeature getValue() {
        return this;
    }

    @Override
    public ItemSpawnerFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 8];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 8] = GUI.CLICK_HERE_TO_CHANGE;

        finalDescription[finalDescription.length - 7] = "&7Spawn Count: &e" + spawnCount.getValue().orElse(0);
        finalDescription[finalDescription.length - 6] = "&7Spawn Delay: &e" + spawnDelay.getValue().orElse(0);
        finalDescription[finalDescription.length - 5] = "&7Spawn Range: &e" + spawnRange.getValue().orElse(0);
        finalDescription[finalDescription.length - 4] = "&7Required Player Range: &e" + requiredPlayerRange.getValue().orElse(0);
        finalDescription[finalDescription.length - 3] = "&7Max Nearby Entities: &e" + maxNearbyEntities.getValue().orElse(0);
        finalDescription[finalDescription.length - 2] = "&7Potential Spawns: &e" + potentialSpawns.getValue().size();
        finalDescription[finalDescription.length - 1] = "&7Add Spawner NBT to Item: &e" + addSpawnerNbtToItem.getValue();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public ItemSpawnerFeature clone(FeatureParentInterface newParent) {
        ItemSpawnerFeature clone = new ItemSpawnerFeature(newParent);
        clone.setSpawnCount(this.spawnCount.clone(clone));
        clone.setMinSpawnDelay(this.minSpawnDelay.clone(clone));
        clone.setMaxSpawnDelay(this.maxSpawnDelay.clone(clone));
        clone.setSpawnDelay(this.spawnDelay.clone(clone));
        clone.setSpawnRange(this.spawnRange.clone(clone));
        clone.setRequiredPlayerRange(this.requiredPlayerRange.clone(clone));
        clone.setMaxNearbyEntities(this.maxNearbyEntities.clone(clone));
        clone.setPotentialSpawns(this.potentialSpawns.clone(clone));
        clone.setAddSpawnerNbtToItem(this.addSpawnerNbtToItem.clone(clone));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.spawnCount);
        features.add(this.spawnDelay);
        features.add(this.spawnRange);
        features.add(this.requiredPlayerRange);
        features.add(this.minSpawnDelay);
        features.add(this.maxSpawnDelay);
        features.add(this.maxNearbyEntities);
        features.add(this.potentialSpawns);
        features.add(this.addSpawnerNbtToItem);
        return features;
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v4Plus();
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof ItemSpawnerFeature) {
                ItemSpawnerFeature spawnerFeature = (ItemSpawnerFeature) feature;
                spawnerFeature.setSpawnDelay(this.spawnDelay);
                spawnerFeature.setMinSpawnDelay(this.minSpawnDelay);
                spawnerFeature.setMaxSpawnDelay(this.maxSpawnDelay);
                spawnerFeature.setSpawnCount(this.spawnCount);
                spawnerFeature.setSpawnRange(this.spawnRange);
                spawnerFeature.setRequiredPlayerRange(this.requiredPlayerRange);
                spawnerFeature.setMaxNearbyEntities(this.maxNearbyEntities);
                spawnerFeature.setPotentialSpawns(this.potentialSpawns);
                spawnerFeature.setAddSpawnerNbtToItem(this.addSpawnerNbtToItem);
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
        return args.getBlockState() instanceof CreatureSpawner;
    }

    @Override
    public void applyOnBlockData(@NotNull FeatureForBlockArgs args) {
        if (!isAvailable() || !isApplicable(args)) return;

        CreatureSpawner spawner = (CreatureSpawner) args.getBlockState();

        if(spawnCount.getValue().isPresent()) {
            spawner.setSpawnCount(spawnCount.getValue().get());
        }

        if (minSpawnDelay.getValue().isPresent()) {
            spawner.setMinSpawnDelay(minSpawnDelay.getValue().get());
        }

        if (maxSpawnDelay.getValue().isPresent()) {
            spawner.setMaxSpawnDelay(maxSpawnDelay.getValue().get());
        }

        if (spawnDelay.getValue().isPresent()) {
            spawner.setDelay(spawnDelay.getValue().get());
        }

        if (spawnRange.getValue().isPresent()) {
            spawner.setSpawnRange(spawnRange.getValue().get());
        }

        if (requiredPlayerRange.getValue().isPresent()) {
            spawner.setRequiredPlayerRange(requiredPlayerRange.getValue().get());
        }

        if (maxNearbyEntities.getValue().isPresent()) {
            spawner.setMaxNearbyEntities(maxNearbyEntities.getValue().get());
        }

        if (!potentialSpawns.getValue().isEmpty()) {
            List<SpawnerEntry> entries = new ArrayList<>();
            EntityFactory entityFactory = Bukkit.getEntityFactory();
            for (String potentialSpawn : potentialSpawns.getValue()) {
                String[] split = potentialSpawn.split(";");
                EntitySnapshot snapshot = entityFactory.createEntitySnapshot(split[0]);
                int weight = 1;
                if (split.length > 1) {
                    Optional<Integer> optionalWeight = NTools.getInteger(split[1]);
                    if (optionalWeight.isPresent()) {
                        weight = optionalWeight.get();
                    }
                }
                entries.add(new SpawnerEntry(snapshot, weight, null));
            }
            spawner.setPotentialSpawns(entries);
        }

        spawner.update();
    }

    @Override
    public void loadFromBlockData(@NotNull FeatureForBlockArgs args) {
        if (!isAvailable() || !isApplicable(args)) return;

        CreatureSpawner spawner = (CreatureSpawner) args.getBlockState();

        this.spawnDelay.setValue(Optional.of(spawner.getDelay()));
        this.spawnRange.setValue(Optional.of(spawner.getSpawnRange()));
        this.requiredPlayerRange.setValue(Optional.of(spawner.getRequiredPlayerRange()));
        this.maxNearbyEntities.setValue(Optional.of(spawner.getMaxNearbyEntities()));
        this.spawnCount.setValue(Optional.of(spawner.getSpawnCount()));
        this.minSpawnDelay.setValue(Optional.of(spawner.getMinSpawnDelay()));
        this.maxSpawnDelay.setValue(Optional.of(spawner.getMaxSpawnDelay()));

        List<SpawnerEntry> entries = spawner.getPotentialSpawns();
        List<String> types = new ArrayList<>();
        for (SpawnerEntry entry : entries) {
            String snapshot = entry.getSnapshot().getAsString();
            int weight = entry.getSpawnWeight();
            types.add(snapshot + ";" + weight);
        }
        potentialSpawns.setValues(types);
    }

    @Override
    public void loadFromItemMeta(@NotNull FeatureForItemArgs args) {
        ItemMeta meta = args.getMeta();
        if (!isAvailable() || !isApplicable(args)) {
            return;
        }


        BlockStateMeta blockStateMeta = (BlockStateMeta) meta;

        BlockState blockState = blockStateMeta.getBlockState();
        if (!(blockState instanceof CreatureSpawner)) return;

        CreatureSpawner spawner = (CreatureSpawner) blockState;


        this.spawnDelay.setValue(Optional.of(spawner.getDelay()));
        this.spawnRange.setValue(Optional.of(spawner.getSpawnRange()));
        this.requiredPlayerRange.setValue(Optional.of(spawner.getRequiredPlayerRange()));
        this.maxNearbyEntities.setValue(Optional.of(spawner.getMaxNearbyEntities()));
        this.spawnCount.setValue(Optional.of(spawner.getSpawnCount()));
        this.minSpawnDelay.setValue(Optional.of(spawner.getMinSpawnDelay()));
        this.maxSpawnDelay.setValue(Optional.of(spawner.getMaxSpawnDelay()));

        List<SpawnerEntry> entries = spawner.getPotentialSpawns();
        List<String> types = new ArrayList<>();
        for (SpawnerEntry entry : entries) {
            String snapshot = entry.getSnapshot().getAsString();
            int weight = entry.getSpawnWeight();
            types.add(snapshot + ";" + weight);
        }
        potentialSpawns.setValues(types);
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForItemArgs args) {
        return args.getMeta() instanceof BlockStateMeta &&
                ((BlockStateMeta) args.getMeta()).getBlockState() instanceof CreatureSpawner;
    }

    @Override
    public void applyOnItemMeta(@NotNull FeatureForItemArgs args) {
        if (!isAvailable() || !isApplicable(args)) return;

        if(!addSpawnerNbtToItem.getValue()) return;

        BlockStateMeta meta = (BlockStateMeta) args.getMeta();
        CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();

        if (spawnCount.getValue().isPresent()) {
            spawner.setSpawnCount(spawnCount.getValue().get());
        }

        if (minSpawnDelay.getValue().isPresent()) {
            spawner.setMinSpawnDelay(minSpawnDelay.getValue().get());
        }

        if (maxSpawnDelay.getValue().isPresent()) {
            spawner.setMaxSpawnDelay(maxSpawnDelay.getValue().get());
        }

        if (spawnDelay.getValue().isPresent()) {
            spawner.setDelay(spawnDelay.getValue().get());
        }

        if (spawnRange.getValue().isPresent()) {
            spawner.setSpawnRange(spawnRange.getValue().get());
        }

        if (requiredPlayerRange.getValue().isPresent()) {
            spawner.setRequiredPlayerRange(requiredPlayerRange.getValue().get());
        }

        if (maxNearbyEntities.getValue().isPresent()) {
            spawner.setMaxNearbyEntities(maxNearbyEntities.getValue().get());
        }

        if (!potentialSpawns.getValue().isEmpty()) {
            List<SpawnerEntry> entries = new ArrayList<>();
            EntityFactory entityFactory = Bukkit.getEntityFactory();
            for (String potentialSpawn : potentialSpawns.getValue()) {
                String[] split = potentialSpawn.split(";");
                EntitySnapshot snapshot = entityFactory.createEntitySnapshot(split[0]);
                int weight = 1;
                if (split.length > 1) {
                    Optional<Integer> optionalWeight = NTools.getInteger(split[1]);
                    if (optionalWeight.isPresent()) {
                        weight = optionalWeight.get();
                    }
                }
                entries.add(new SpawnerEntry(snapshot, weight, null));
            }
            spawner.setPotentialSpawns(entries);
        }

        meta.setBlockState(spawner);
    }


    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.SPAWNER;
    }
}