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

    private static final Boolean DEBUG = false;

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlaceEvent(BlockPlaceEvent e) {

        SsomarDev.testMsg("FixSpawnerPlaceEvent.onBlockPlaceEvent()", DEBUG);
        /* No Spawner type before the 1.14 */
        Block block = e.getBlock();
        ItemStack is = e.getItemInHand();

        Material spawer;
        if(SCore.is1v12Less()) {
            spawer = Material.valueOf("MOB_SPAWNER");
        }
        else spawer = Material.SPAWNER;

        SsomarDev.testMsg("item hand: "+is.getType()+ " equals ? "+((is.getType().equals(spawer))), DEBUG);
        if(is.getType().equals(spawer)){
            SsomarDev.testMsg(">> Its a spawner ! ", DEBUG);
            CreatureSpawner cs = (CreatureSpawner)block.getState();
            BlockStateMeta meta = (BlockStateMeta)is.getItemMeta();
            CreatureSpawner csm = (CreatureSpawner)meta.getBlockState();
            SsomarDev.testMsg(">> type of the spawner placed ! "+ csm.getSpawnedType(), DEBUG);
            cs.setSpawnedType(csm.getSpawnedType());
            cs.update();
        }
    }
}
