package com.ssomar.score.events;

import com.ssomar.executableitems.executableitems.ExecutableItem;
import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureForBlockArgs;
import com.ssomar.score.features.custom.ItemSpawnerFeature;
import com.ssomar.score.usedapi.Dependency;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class FixSpawnerPlaceEvent implements Listener {

    private static final Boolean DEBUG = false;

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlaceEvent(BlockPlaceEvent e) {

        SsomarDev.testMsg("FixSpawnerPlaceEvent.onBlockPlaceEvent()", DEBUG);
        /* No Spawner type before the 1.14 */
        Block block = e.getBlock();
        ItemStack is = e.getItemInHand();

        if (!is.hasItemMeta()) return;

        /* No localized name in 1.8.9 */
        if(SCore.is1v11Less()) return;


        if(Dependency.EXECUTABLE_ITEMS.isEnabled()){
            ExecutableItemObject eio = new ExecutableItemObject(is);
            if (eio.isValid()){
                ExecutableItem ei = eio.getConfig();
                ItemSpawnerFeature isf = ei.getSpawner();
                if(!isf.getPotentialSpawns().getCurrentValues().isEmpty()){
                    isf.applyOnBlockData(FeatureForBlockArgs.create(block.getBlockData(), block.getState(), block.getType()));
                    SsomarDev.testMsg(">> Its a spawner ! add spawner info", true);
                }
            }
        }





        // Old
        ItemMeta im = is.getItemMeta();
        if (!im.hasLocalizedName() || !im.getLocalizedName().equals("FROM_EXECUTABLEITEM")) return;

        Material spawer;
        if (SCore.is1v12Less()) {
            spawer = Material.valueOf("MOB_SPAWNER");
        } else spawer = Material.SPAWNER;

        SsomarDev.testMsg("item hand: " + is.getType() + " equals ? " + ((is.getType().equals(spawer))), DEBUG);
        if (is.getType().equals(spawer)) {
            SsomarDev.testMsg(">> Its a spawner ! ", DEBUG);
            CreatureSpawner cs = (CreatureSpawner) block.getState();
            BlockStateMeta meta = (BlockStateMeta) is.getItemMeta();
            CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();
            SsomarDev.testMsg(">> type of the spawner placed ! " + csm.getSpawnedType(), DEBUG);
            cs.setSpawnedType(csm.getSpawnedType());
            cs.update();
        }
    }
}
