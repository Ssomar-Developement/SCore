/*
 * Copyright (c) 2022 Alexander Majka (mfnalex) / JEFF Media GbR
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * If you need help or have any suggestions, feel free to join my Discord and head to #programming-help:
 *
 * Discord: https://discord.jeff-media.com/
 *
 * If you find this library helpful or if you're using it one of your paid plugins, please consider leaving a donation
 * to support the further development of this project :)
 *
 * Donations: https://paypal.me/mfnalex
 */

package com.ssomar.customblockdata;

import com.ssomar.customblockdata.events.CustomBlockDataMoveEvent;
import com.ssomar.customblockdata.events.CustomBlockDataPlaceEvent;
import com.ssomar.customblockdata.events.CustomBlockDataRemoveEvent;
import com.ssomar.customblockdata.events.fallingblock.FallingBlockLandEvent;
import com.ssomar.customblockdata.events.fallingblock.FallingBlockStartFallingEvent;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

final class BlockDataListener implements Listener {

    private final Plugin plugin;
    private final Predicate<Block> customDataPredicate;

    public BlockDataListener(Plugin plugin) {
        this.plugin = plugin;
        this.customDataPredicate = block -> CustomBlockData.hasCustomBlockData(block, plugin);
    }

    private CustomBlockData getCbd(BlockEvent event) {
        return getCbd(event.getBlock());
    }

    private CustomBlockData getCbd(Block block) {
        return new CustomBlockData(block, plugin);
    }

    private void callAndRemove(BlockEvent blockEvent) {
        if (callRemoveEvent(blockEvent)) {
            getCbd(blockEvent).clear();
        }
    }

    private boolean callRemoveEvent(BlockEvent blockEvent) {
        return callRemoveEvent(blockEvent.getBlock(), blockEvent);
    }

    private boolean callRemoveEvent(Block block, Event bukkitEvent) {
        if (!CustomBlockData.hasCustomBlockData(block, plugin) || CustomBlockData.isProtected(block, plugin)) {
            return false;
        }

        CustomBlockDataRemoveEvent cbdEvent = new CustomBlockDataRemoveEvent(plugin, block, bukkitEvent);
        Bukkit.getPluginManager().callEvent(cbdEvent);

        if (bukkitEvent instanceof Cancellable) {
            ((Cancellable) bukkitEvent).setCancelled(cbdEvent.isCancelled());
        }

        return !cbdEvent.isCancelled();
    }

    private boolean callPlaceEvent(Block block, Event bukkitEvent) {
        if (!CustomBlockData.hasCustomBlockData(block, plugin) || CustomBlockData.isProtected(block, plugin)) {
            return false;
        }

        CustomBlockDataPlaceEvent cbdEvent = new CustomBlockDataPlaceEvent(plugin, block, bukkitEvent);
        Bukkit.getPluginManager().callEvent(cbdEvent);

        if (bukkitEvent instanceof Cancellable) {
            ((Cancellable) bukkitEvent).setCancelled(cbdEvent.isCancelled());
        }

        return !cbdEvent.isCancelled();
    }

    private void callAndRemoveBlockStateList(List<BlockState> blockStates, Event bukkitEvent) {
        blockStates.stream()
                .map(BlockState::getBlock)
                .filter(customDataPredicate)
                .forEach(block -> callAndRemove(block, bukkitEvent));
    }

    private void callAndRemoveBlockList(List<Block> blocks, Event bukkitEvent) {
        blocks.stream()
                .filter(customDataPredicate)
                .forEach(block -> callAndRemove(block, bukkitEvent));
    }

    private void callAndRemove(Block block, Event bukkitEvent) {
        if (callRemoveEvent(block, bukkitEvent)) {
            getCbd(block).clear();
        }
    }

    private void callAndPlace(Block block, Event bukkitEvent, PersistentDataContainer data) {
        try {
            byte[] ser = data.serializeToBytes();
            getCbd(block).readFromBytes(ser, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!callPlaceEvent(block, bukkitEvent)) {
            getCbd(block).clear();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        callAndRemove(event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if (!CustomBlockData.isDirty(event.getBlock())) {
            callAndRemove(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntity(EntityChangeBlockEvent event) {
        Entity ent = event.getEntity();
        Block block = event.getBlock();

        if (!(ent instanceof FallingBlock)) {
            if (event.getTo() != event.getBlock().getType()) {
                callAndRemove(event.getBlock(), event);
            }
            return;
        }

        if (block.getType() == Material.AIR) {
            PersistentDataContainer entPdc = ent.getPersistentDataContainer();
            // Copy entPdc in the block persistent data container
            PersistentDataContainer pdc = new CustomBlockData(block, plugin);
            //SsomarDev.testMsg("ENTITY_CHANGE 1.5 " + entPdc.getKeys(), true);

            FallingBlockLandEvent fallingBlockLandEvent = new FallingBlockLandEvent(event);
            callAndPlace(event.getBlock(), fallingBlockLandEvent, entPdc);

        } else {
            PersistentDataContainer pdc = new CustomBlockData(block, plugin);
            // Copy pdc in the entity persistent data container
            PersistentDataContainer entPdc = ent.getPersistentDataContainer();
            try {
                byte[] ser = pdc.serializeToBytes();
                entPdc.readFromBytes(ser);

                // send to sssomar all data container of thez entity
               //SsomarDev.testMsg("ENTITY_CHANGE 11111111111 " + ent.getPersistentDataContainer().getKeys(), true);
                FallingBlockStartFallingEvent fallingBlockStartFallingEvent = new FallingBlockStartFallingEvent(event);
                callAndRemove(event.getBlock(), fallingBlockStartFallingEvent);
                entPdc.set(new NamespacedKey(SCore.plugin, "wasCustomBlockDataBlock"), PersistentDataType.BYTE, (byte) 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntijty(EntityChangeBlockEvent event) {
        SsomarDev.testMsg("ENTITY_DEATH 1 >> "+event.getEntity()+"   >>"+event.getEntity().getPersistentDataContainer().getKeys(), true);

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onExplode(BlockExplodeEvent event) {
        callAndRemoveBlockList(event.blockList(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent event) {
        callAndRemoveBlockList(event.blockList(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBurn(BlockBurnEvent event) {
        callAndRemove(event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPiston(BlockPistonExtendEvent event) {
        onPiston(event.getBlocks(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPiston(BlockPistonRetractEvent event) {
        onPiston(event.getBlocks(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFade(BlockFadeEvent event) {
        if (event.getBlock().getType() == Material.FIRE) return;
        if (event.getNewState().getType() != event.getBlock().getType()) {
            callAndRemove(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStructure(StructureGrowEvent event) {
        callAndRemoveBlockStateList(event.getBlocks(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFertilize(BlockFertilizeEvent event) {
        callAndRemoveBlockStateList(event.getBlocks(), event);
    }

    private void onPiston(List<Block> blocks, BlockPistonEvent bukkitEvent) {
        BlockFace direction = bukkitEvent.getDirection();

        AtomicBoolean moveStopped = new AtomicBoolean(false);

        blocks.stream().takeWhile(block -> !moveStopped.get()).forEach(block -> {

            CustomBlockData cbd = new CustomBlockData(block, plugin);
            if (block.getPistonMoveReaction().equals(PistonMoveReaction.BREAK)) {
                if (CustomBlockData.hasCustomBlockData(block, plugin) && !(cbd.isEmpty() || cbd.isProtected()))
                    callAndRemove(block, bukkitEvent);
                moveStopped.set(true);
            } else {
                if (CustomBlockData.hasCustomBlockData(block, plugin) && !(cbd.isEmpty() || cbd.isProtected())) {
                    Block destinationBlock = block.getRelative(direction);
                    CustomBlockDataMoveEvent moveEvent = new CustomBlockDataMoveEvent(plugin, block, destinationBlock, bukkitEvent);
                    Bukkit.getPluginManager().callEvent(moveEvent);
                    if (moveEvent.isCancelled()) {
                        bukkitEvent.setCancelled(true);
                        return;
                    }
                }

                /* Check if the block above is not impacted */
                Block above = block.getRelative(BlockFace.UP);
                CustomBlockData cbd2 = new CustomBlockData(above, plugin);
                if (CustomBlockData.hasCustomBlockData(above, plugin) && !(cbd2.isEmpty() || cbd2.isProtected())) {
                    if (above.getPistonMoveReaction().equals(PistonMoveReaction.BREAK)) {
                        callAndRemove(above, bukkitEvent);
                    }
                }
            }
        });
    }

    private static final class Utils {

        private static <K, V> Map<K, V> reverse(Map<K, V> map) {
            LinkedHashMap<K, V> reversed = new LinkedHashMap<>();
            List<K> keys = new ArrayList<>(map.keySet());
            Collections.reverse(keys);
            keys.forEach((key) -> reversed.put(key, map.get(key)));
            return reversed;
        }

    }

}
