package com.ssomar.score.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TESTEVENT_TODELETE implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

      /* if(e.getDamager() instanceof Guardian && e.getEntity() instanceof Player) {
           SsomarDev.testMsg("Guardian >> "+e.getCause().name() + " >> " + e.getDamage()+ " >> " + e.getEntity().getUniqueId());
       }*/

    }
}
