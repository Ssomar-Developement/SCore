package com.ssomar.score.events;

import com.ssomar.score.SCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class RemoveCancelDamageEventMetadataTagEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            if (damager.hasMetadata("cancelDamageEvent")) {
                damager.removeMetadata("cancelDamageEvent", (Plugin) SCore.plugin);
            }
            if (damager.hasMetadata("damageFromCustomCommand")) {
                damager.removeMetadata("damageFromCustomCommand", (Plugin) SCore.plugin);
            }
        }
    }
}
