package com.ssomar.score.events;

import com.ssomar.score.fly.FlyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceholderLastDamageDealtEvent implements Listener {

    private static PlaceholderLastDamageDealtEvent instance;
    public Map<UUID, Double> lastDamageDealt;

    public PlaceholderLastDamageDealtEvent() {
        lastDamageDealt = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            UUID uuid = e.getDamager().getUniqueId();
            lastDamageDealt.put(uuid, e.getFinalDamage());
        }
    }


    public static PlaceholderLastDamageDealtEvent getInstance() {
        if(instance == null) {
            instance = new PlaceholderLastDamageDealtEvent();
        }
        return instance;
    }
}
