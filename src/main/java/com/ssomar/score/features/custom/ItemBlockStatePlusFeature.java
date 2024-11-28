package com.ssomar.score.features.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureForItem;
import com.ssomar.score.features.FeatureForItemArgs;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.utils.emums.ResetSetting;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class ItemBlockStatePlusFeature extends UncoloredStringFeature implements FeatureForItem {

    private final static boolean DEBUG = false;


    public ItemBlockStatePlusFeature(FeatureParentInterface parent, Optional<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue) {
        super(parent, defaultValue, featureSettings, notSaveIfEqualsToDefaultValue);
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v5Plus();
    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return args.getMeta() instanceof BlockStateMeta;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: "+meta, DEBUG);

        if(!this.getValue().isPresent() || !isAvailable() || !isApplicable(args)) {
            SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: the value is not present or the meta is not a BlockStateMeta", DEBUG);
            return;
        }

        BlockDataMeta blockStateMeta = (BlockDataMeta) meta;

        try {
            SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: the blockData is going to be created "+this.getValue().get(), DEBUG);
            BlockData toAdd = Bukkit.createBlockData(this.getValue().get());
            SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: the blockData is created "+toAdd, DEBUG);
            SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: the blockData is create getasstring "+toAdd.getAsString(true), DEBUG);
            /*
            // normally no need

            BlockData blockData = blockStateMeta.getBlockData(args.getMaterial());
            SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: the blockData is got "+blockData, DEBUG);
            blockData.merge(toAdd); */
            blockStateMeta.setBlockData(toAdd);
            SsomarDev.testMsg("ItemBlockStatePlusFeature applyOnItemMeta: the blockData is set", DEBUG);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: "+meta, DEBUG);

        if(isAvailable() && isApplicable(args)) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            if(!blockStateMeta.hasBlockState()) {
                SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: the meta has no blockData", DEBUG);
                return;
            }
            BlockState blockState = blockStateMeta.getBlockState();
            Container container = (Container) blockState;
            for(ItemStack item : container.getInventory().getContents()){
                if (item == null) continue;
                if(item.hasItemMeta()){
                    SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: the item has meta "+item.getItemMeta().getAsString(), DEBUG);
                }
                else SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: the blockState  no meta is got "+item.getType(), DEBUG);
            }
            //SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: the blockState is got "+container.getInventory().getContents().toString(), DEBUG);
            String blockData = blockState.getBlockData().getAsString(true);
            if (blockData.isEmpty()){
                SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: the blockData is empty", DEBUG);
                return;
            }
            setValue(Optional.of(blockData));
        }
        else SsomarDev.testMsg("ItemBlockStatePlusFeature loadFromItemMeta: the meta is not a BlockStateMeta", DEBUG);

    }

    @Override
    public ResetSetting getResetSetting() {
        return null;
    }

    @Override
    public ItemBlockStatePlusFeature clone(FeatureParentInterface newParent) {
        ItemBlockStatePlusFeature clone = new ItemBlockStatePlusFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue());
        clone.setValue(getValue());
        return clone;
    }
}
