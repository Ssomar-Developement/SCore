package com.ssomar.score.features.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureForItem;
import com.ssomar.score.features.FeatureForItemArgs;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.types.UncoloredStringFeature;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class ItemBlockStateFeature extends UncoloredStringFeature implements FeatureForItem {

    private final static boolean DEBUG = false;


    public ItemBlockStateFeature(FeatureParentInterface parent, Optional<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v5Plus();
    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return args.getMeta() instanceof BlockDataMeta;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("ItemBlockStateFeature applyOnItemMeta: "+meta, DEBUG);

        if(!this.getValue().isPresent() || !isAvailable() || !isApplicable(args)) {
            SsomarDev.testMsg("ItemBlockStateFeature applyOnItemMeta: the value is not present or the meta is not a BlockStateMeta", DEBUG);
            return;
        }

        BlockDataMeta blockDataMeta = (BlockDataMeta) meta;

        try {
            BlockData blockData = Bukkit.createBlockData(this.getValue().get());
            blockDataMeta.setBlockData(blockData);
            SsomarDev.testMsg("ItemBlockStateFeature applyOnItemMeta: the blockData is set", DEBUG);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("ItemBlockStateFeature loadFromItemMeta: "+meta, true);

        if(isAvailable() && isApplicable(args)) {
            BlockDataMeta blockDataMeta = (BlockDataMeta) meta;
            if(!blockDataMeta.hasBlockData()) {
                SsomarDev.testMsg("ItemBlockStateFeature loadFromItemMeta: the meta has no blockData", DEBUG);
                return;
            }
            String blockData = blockDataMeta.getBlockData(args.getMaterial()).getAsString(true);
            if (blockData.isEmpty()){
                SsomarDev.testMsg("ItemBlockStateFeature loadFromItemMeta: the blockData is empty", DEBUG);
                return;
            }
            setValue(Optional.of(blockData));
        }
        else SsomarDev.testMsg("ItemBlockStateFeature loadFromItemMeta: the meta is not a BlockDataMeta", DEBUG);

    }

    @Override
    public ItemBlockStateFeature clone(FeatureParentInterface newParent) {
        ItemBlockStateFeature clone = new ItemBlockStateFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }
}
