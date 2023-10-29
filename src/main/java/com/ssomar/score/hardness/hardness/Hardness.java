package com.ssomar.score.hardness.hardness;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.activators.group.ActivatorsFeature;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.features.custom.detaileditems.DetailedItems;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.hardness.HardnessModifier;
import com.ssomar.score.hardness.hardness.loader.HardnessLoader;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.NewSObject;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Hardness extends NewSObject<Hardness, HardnessEditor, HardnessEditorManager> implements HardnessModifier {

    private String id;
    private String path;

    private DetailedBlocks detailedBlocks;

    private DetailedItems detailedItems;

    private IntegerFeature period;

    private BooleanFeature periodInTicks;

    public Hardness(FeatureParentInterface parent, String id, String path) {
        super(parent, "SPROJ", "SPROJ", new String[]{}, Material.ARROW);
        this.id = id;
        this.path = path;
        reset();
    }

    public Hardness(String id, String path) {
        super("HARDNESS", "HARDNESS", new String[]{}, Material.ARROW);
        this.id = id;
        this.path = path;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();

        errors.addAll(detailedBlocks.load(plugin, config, isPremiumLoading));
        errors.addAll(detailedItems.load(plugin, config, isPremiumLoading));
        errors.addAll(period.load(plugin, config, isPremiumLoading));
        errors.addAll(periodInTicks.load(plugin, config, isPremiumLoading));

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        for(String s : config.getKeys(false)){
            config.set(s, null);
        }
        for (FeatureInterface feature : getFeatures()) {
            feature.save(config);
        }
    }

    @Override
    public Hardness getValue() {
        return this;
    }

    @Override
    public Hardness initItemParentEditor(GUI gui, int slot) {
        return null;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public void reset() {

        detailedBlocks = new DetailedBlocks(this);
        detailedItems = new DetailedItems(this);
        period = new IntegerFeature(this, "period", Optional.of(3), "Period", new String[]{}, GUI.CLOCK, false);
        periodInTicks = new BooleanFeature(this, "periodInTicks", false, "Period in ticks", new String[]{}, GUI.CLOCK, false, false);

    }

    @Override
    public Hardness clone(FeatureParentInterface newParent) {
        Hardness clone = new Hardness(this, id, path);
        clone.setDetailedBlocks(detailedBlocks.clone(clone));
        clone.setDetailedItems(detailedItems.clone(clone));
        clone.setPeriod(period.clone(clone));
        clone.setPeriodInTicks(periodInTicks.clone(clone));
        return clone;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<FeatureInterface>();
        features.add(detailedBlocks);
        features.add(detailedItems);
        features.add(period);
        features.add(periodInTicks);
        return features;
    }

    @Override
    public String getParentInfo() {
        return "Hardness: " + id;
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        File file = getFile();

        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return config;
    }

    @Override
    public File getFile() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path).createNewFile();
                file = HardnessLoader.getInstance().searchFileOfObject(id);
            } catch (IOException ignored) {
                return null;
            }
        }
        return file;
    }

    @Override
    public void reload() {
        if (getParent() instanceof Hardness) {
            Hardness sProjectile = (Hardness) getParent();
            sProjectile.setDetailedBlocks(detailedBlocks);
            sProjectile.setDetailedItems(detailedItems);
            sProjectile.setPeriod(period);
            sProjectile.setPeriodInTicks(periodInTicks);
            //SsomarDev.testMsg("RELOAD INTO "+sProjectile.hashCode());
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        NewSObjectsManagerEditor.getInstance().startEditing(player, new HardnessesEditor());
    }

    @Override
    public void openEditor(@NotNull Player player) {
        HardnessEditorManager.getInstance().startEditing(player, this);
    }


    @Override
    public ActivatorsFeature getActivators() {
        return null;
    }

    @Override
    public Item dropItem(Location location, int amount) {
        return null;
    }

    @Override
    public ItemStack buildItem(int quantity, Optional<Player> creatorOpt) {
        return new ItemStack(Material.BOOK);
    }

    @Nullable
    @Override
    public NewSActivator getActivator(String actID) {
        return null;
    }

    @Override
    public List<String> getDescription() {
        List<String> description = new ArrayList<>();
        description.add("§7ID: §f" + id);
        description.add("§7Path: §f" + path);
        return description;
    }

    @Override
    public boolean isTriggered(Player player, Block block, ItemStack tool) {
        return detailedBlocks.isValid(block, Optional.ofNullable(player), null, new StringPlaceholder()) && detailedItems.isValid(tool, Optional.ofNullable(player), null, new StringPlaceholder());
    }

    @Override
    public void breakBlock(Player player, Block block, ItemStack tool) {
        //SsomarDev.testMsg("BREAK BLOCK", true);

        BlockData typeData = block.getType().createBlockData();
        block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5,0.5,0.5), 25, 0.5, 0.5, 0.5, 1, typeData);
        block.getWorld().playSound(block.getLocation(), block.getBlockData().getSoundGroup().getBreakSound(), 1, 1);

        block.breakNaturally();
        // play sound and particles

    }

    @Override
    public long getPeriod(Player player, Block block, ItemStack tool) {
        return period.getValue().get();
    }

    @Override
    public boolean isPeriodInTicks() {
        return periodInTicks.getValue();
    }
}
