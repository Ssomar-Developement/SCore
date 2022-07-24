package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.sevents.events.player.click.onplayer.left.PlayerLeftClickOnPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

public class TESTEVENT_TODELETE implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

      /* if(e.getDamager() instanceof Guardian && e.getEntity() instanceof Player) {
           SsomarDev.testMsg("Guardian >> "+e.getCause().name() + " >> " + e.getDamage()+ " >> " + e.getEntity().getUniqueId());
       }*/

    }
}
