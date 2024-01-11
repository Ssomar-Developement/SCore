package com.ssomar.score.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class TESTEVENT implements Listener {

    List<EntityType> entity0Damage;

    public TESTEVENT() {
        entity0Damage = new ArrayList<>();
        try{
            entity0Damage.add(EntityType.ITEM_FRAME);
        } catch (Exception | Error e) {}
        try{
            entity0Damage.add(EntityType.GLOW_ITEM_FRAME);
        } catch (Exception | Error e) {}
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {


    }
}
