package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.commands.runnable.mixed_player_entity.commands.DamageBoost;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageBoostEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageByEntityEvent e) {
        // Try catch was added due to WeaponMechanics plugin conflict
        // An exception happens if a player damages a mob using a gun from WeaponMechanics
        try {
           e.setDamage(NTools.reduceDouble(DamageBoost.getInstance().getNewDamage(e.getDamager().getUniqueId(), e.getDamage()), 2));
        } catch (Exception ex) {}
    }
}
