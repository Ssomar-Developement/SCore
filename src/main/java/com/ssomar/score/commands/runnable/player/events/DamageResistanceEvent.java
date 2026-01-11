package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.commands.runnable.mixed_player_entity.commands.DamageResistance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageResistanceEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageEvent e) {
        // Try catch was added due to WeaponMechanics plugin conflict.
        // An exception happens if a player damages a mob using a gun from WeaponMechanics
        try {
            e.setDamage(DamageResistance.getInstance().getNewDamage(e.getEntity().getUniqueId(), e.getDamage()));
        } catch (Exception ex) {}
    }
}
