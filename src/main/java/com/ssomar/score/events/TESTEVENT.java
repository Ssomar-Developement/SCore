package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class TESTEVENT implements Listener {


    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e) {

        LivingEntity entity = e.getEntity();

        if (entity instanceof Player) return;

        SsomarDev.testMsg("TESTEVENT "+e.getEntityType()+" killer ?"+e.getEntity().getKiller(), true);
    }
}
