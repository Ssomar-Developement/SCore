package com.ssomar.score.events;

import com.ssomar.score.SCore;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class EntitiesFromSpawnerListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onEntitySpawnEvent(CreatureSpawnEvent e) {
        Entity entity = e.getEntity();

        try {
            CreatureSpawnEvent.SpawnReason spawnReason = e.getSpawnReason();

            if (spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                entity.setMetadata("fromSpawner", (MetadataValue) new FixedMetadataValue((Plugin) SCore.plugin, Integer.valueOf(1)));
            }
        }
        // Unregister automatically if the method is not found
        catch (NoSuchMethodError ex){
            e.getHandlers().unregister(this);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntitySpawnEvent(EntityDeathEvent e) {
        Entity entity = e.getEntity();

        /* * Because it's not removed automatically when the entity is removed  https://hub.spigotmc.org/jira/browse/SPIGOT-262 */
        entity.removeMetadata("fromSpawner", SCore.plugin);
    }
}
