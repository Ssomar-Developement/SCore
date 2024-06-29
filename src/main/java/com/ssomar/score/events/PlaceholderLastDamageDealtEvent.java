package com.ssomar.score.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceholderLastDamageDealtEvent implements Listener {

    private static PlaceholderLastDamageDealtEvent instance;
    public final Map<UUID, Double> lastDamageDealt;

    public PlaceholderLastDamageDealtEvent() {
        lastDamageDealt = new HashMap<>();
    }

    public static PlaceholderLastDamageDealtEvent getInstance() {
        if (instance == null) {
            instance = new PlaceholderLastDamageDealtEvent();
        }
        return instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {Entity damager = e.getDamager();
        if(damager instanceof Projectile){
            Projectile projectile = (Projectile) damager;
            damager = (Entity) projectile.getShooter();
        }
        if (damager instanceof Player) {
            //SsomarDev.testMsg("onEntityDamageByEntityEvent >> " +damager + " >> " + e.getFinalDamage(), true);
            UUID uuid = e.getDamager().getUniqueId();
            lastDamageDealt.put(uuid, e.getFinalDamage());
        }
    }
}
