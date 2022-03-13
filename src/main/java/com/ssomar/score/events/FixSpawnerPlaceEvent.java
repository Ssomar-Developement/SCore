package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class FixSpawnerPlaceEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        /* No Spawner type before the 1.14 */
        if(SCore.is1v12() || SCore.is1v13()) return;
        Block block = e.getBlock();
        ItemStack is = e.getItemInHand();
        if(is.getType().equals(Material.SPAWNER)){
            CreatureSpawner cs = (CreatureSpawner)block.getState();
            BlockStateMeta meta = (BlockStateMeta)is.getItemMeta();
            CreatureSpawner csm = (CreatureSpawner)meta.getBlockState();
            cs.setSpawnedType(csm.getSpawnedType());
        }
    }
}
