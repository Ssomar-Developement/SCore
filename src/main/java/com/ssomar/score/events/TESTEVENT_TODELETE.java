package com.ssomar.score.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TESTEVENT_TODELETE implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntityEvent(PlayerInteractEvent e) {

        e.setCancelled(true);
        e.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
        e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);

    }
}
