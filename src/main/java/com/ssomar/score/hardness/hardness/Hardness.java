package com.ssomar.score.hardness.hardness;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.features.custom.detaileditems.DetailedItems;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.hardness.HardnessModifier;
import com.ssomar.score.hardness.hardness.loader.HardnessLoader;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.SObjectWithFile;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Hardness extends SObjectWithFile<Hardness, HardnessEditor, HardnessEditorManager> implements HardnessModifier {

    private DetailedBlocks detailedBlocks;

    private DetailedItems detailedItems;

    private IntegerFeature period;

    private BooleanFeature periodInTicks;

    public Hardness(FeatureParentInterface parent, String id, String path) {
        super(id, parent, "SPROJ", "SPROJ", new String[]{}, Material.ARROW, path, HardnessLoader.getInstance());
        reset();
    }

    public Hardness(String id, String path) {
        super(id, "HARDNESS", "HARDNESS", new String[]{}, Material.ARROW, path, HardnessLoader.getInstance());
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
        Hardness clone = new Hardness(this, getId(), getPath());
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
        return "Hardness: " + getId();
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
    public ItemStack buildItem(int quantity, Optional<Player> creatorOpt) {
        return new ItemStack(Material.BOOK);
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
