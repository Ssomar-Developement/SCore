package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TESTEVENT_TODELETE implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntityEvent(ProjectileHitEvent e) {

        SsomarDev.testMsg("TESTEVENT_TODELETE INTERACT");

    }
}
