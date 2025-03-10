package com.ssomar.score.features.custom.container;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.*;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.utils.emums.ResetSetting;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContainerContent extends ListUncoloredStringFeature implements FeatureForItem, FeatureForBlock {

    private final static boolean DEBUG = true;


    public ContainerContent(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, defaultValue, featureSettings, Optional.empty());
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v5Plus();
    }

    @Override
    public boolean isApplicable(@NotNull FeatureForBlockArgs args) {
        return args.getBlockState() instanceof Container;
    }

    @Override
    public void applyOnBlockData(@NotNull FeatureForBlockArgs args) {

        // Need to delay because getInventory only works when the block is placed
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (getValues().isEmpty() || !isAvailable() || !isApplicable(args)) {
                    SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the value is not present or the meta is not a BlockStateMeta", DEBUG);
                    return;
                }
                // Need to get block and then blockstate again to be sure to have the right blockstate
                BlockState blockState = args.getBlockState().getBlock().getState();
                if (!(blockState instanceof Container)) {
                    SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the meta is not a BlockStateMeta", DEBUG);
                    return;
                }
                Container container = (Container) blockState;

                try {
                    Inventory inv = container.getInventory();
                    for (String value : getValues()) {
                        String[] split = value.split(";");
                        int slot = Integer.parseInt(split[0].split(":")[1]);
                        String itemString = value.substring(value.indexOf(";") + 1);
                        int amount = 1;
                        if(itemString.contains(";amount:")) {
                            amount = Integer.parseInt(itemString.split(";amount:")[1]);
                            itemString = itemString.split(";amount:")[0];
                        }
                        ItemStack item = Bukkit.getServer().getItemFactory().createItemStack(itemString);
                        item.setAmount(amount);
                        SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the item " + item, DEBUG);
                        if(slot >= inv.getSize()) {
                            SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the slot is too big", DEBUG);
                            continue;
                        }
                        inv.setItem(slot, item);
                    }
                    SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the blockState is updated", DEBUG);
                    //container.update();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        };
        SCore.schedulerHook.runLocationTask(runnable, args.getBlockState().getLocation(), 1);
    }

    @Override
    public void loadFromBlockData(@NotNull FeatureForBlockArgs args) {
        BlockData blockData = args.getData();
        SsomarDev.testMsg("ItemContainerFeature loadFromBlockData: " + blockData, DEBUG);

        List<String> values = new ArrayList<>();

        if (isAvailable() && isApplicable(args)) {
            BlockState blockState = args.getBlockState();
            Container container = (Container) blockState;
            int slot = -1;
            for (ItemStack item : container.getInventory().getContents()) {
                slot++;
                if (item == null) continue;
                String value = "";
                value = "slot:" + slot + ";minecraft:" + item.getType().toString().toLowerCase() + item.getItemMeta().getAsComponentString()+";amount:"+item.getAmount();
                SsomarDev.testMsg("ItemContainerFeature loadFromBlockData: the item meta " + value, DEBUG);
                values.add(value);
            }
            SsomarDev.testMsg("ItemContainerFeature loadFromBlockData: the blockState loading finish ", DEBUG);
            setValues(values);
        } else SsomarDev.testMsg("ItemContainerFeature loadFromBlockData: the meta is not a BlockStateMeta", DEBUG);

    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return args.getMeta() instanceof BlockStateMeta;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: " + meta, DEBUG);

        if (this.getValues().isEmpty() || !isAvailable() || !isApplicable(args)) {
            SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the value is not present or the meta is not a BlockStateMeta", DEBUG);
            return;
        }

        BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
        // To not create a new blockState for nothing
        if (getValues().isEmpty() &&  !blockStateMeta.hasBlockState()) {
            SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the meta has no blockData", DEBUG);
            return;
        }

        if (!(blockStateMeta.getBlockState() instanceof Container)) {
            SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the meta has no blockData", DEBUG);
            return;
        }
        Container container = (Container) blockStateMeta.getBlockState();

        try {
            Inventory inv = container.getInventory();
            for (String value : this.getValues()) {
                String[] split = value.split(";");
                int slot = Integer.parseInt(split[0].split(":")[1]);
                String itemString = value.substring(value.indexOf(";") + 1);
                int amount = 1;
                if(itemString.contains(";amount:")) {
                    amount = Integer.parseInt(itemString.split(";amount:")[1]);
                    itemString = itemString.split(";amount:")[0];
                }
                SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the itemString " + itemString, DEBUG);
                ItemStack item = Bukkit.getServer().getItemFactory().createItemStack(itemString);
                item.setAmount(amount);
                SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the item " + item, DEBUG);
                if (slot >= inv.getSize()) {
                    SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the slot is too big", DEBUG);
                    continue;
                }
                inv.setItem(slot, item);
            }
            SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the blockState is updated", DEBUG);
            container.update();
            blockStateMeta.setBlockState(container);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        SsomarDev.testMsg("ItemContainerFeature loadFromItemMeta: " + meta, DEBUG);

        List<String> values = new ArrayList<>();

        if (isAvailable() && isApplicable(args)) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            if (!blockStateMeta.hasBlockState()) {
                SsomarDev.testMsg("ItemContainerFeature loadFromItemMeta: the meta has no blockData", DEBUG);
                return;
            }
            BlockState blockState = blockStateMeta.getBlockState();
            Container container = (Container) blockState;
            int slot = -1;
            for (ItemStack item : container.getInventory().getContents()) {
                slot++;
                if (item == null) continue;
                String value = "";
                value = "slot:" + slot + ";minecraft:" + item.getType().toString().toLowerCase() + item.getItemMeta().getAsComponentString()+";amount:"+item.getAmount();
                SsomarDev.testMsg("ItemContainerFeature loadFromItemMeta: the item meta " + value, DEBUG);
                values.add(value);
            }
            SsomarDev.testMsg("ItemContainerFeature loadFromItemMeta: the blockState loading finish ", DEBUG);
            setValues(values);
        } else SsomarDev.testMsg("ItemContainerFeature loadFromItemMeta: the meta is not a BlockStateMeta", DEBUG);

    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.CONTAINER;
    }

    @Override
    public ContainerContent clone(FeatureParentInterface newParent) {
        ContainerContent clone = new ContainerContent(newParent, getDefaultValue(), getFeatureSettings());
        clone.setValues(getValues());
        return clone;
    }
}
