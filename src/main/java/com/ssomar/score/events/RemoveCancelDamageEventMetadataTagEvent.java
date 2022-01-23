package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/* Remove the tag added by the custom DAMAGE command, tag used to avoid a loop of damage event */
public class RemoveCancelDamageEventMetadataTagEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();

            if(damager.hasMetadata("cancelDamageEvent")){
                damager.removeMetadata("cancelDamageEvent", SCore.plugin);
                return;
            }
        }
    }
}
