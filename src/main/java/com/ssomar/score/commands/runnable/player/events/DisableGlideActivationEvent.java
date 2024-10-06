package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.DisableGlideActivation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class DisableGlideActivationEvent implements Listener {

    private static final Boolean DEBUG = true;

    @EventHandler(priority = EventPriority.HIGH)
    public void entityToggleGlideEvent(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            SsomarDev.testMsg("DisableFlyActivationEvent", DEBUG);
            if (event.isGliding() && DisableGlideActivation.getInstance().getActiveDisabled().containsKey(player.getUniqueId())) {
                SsomarDev.testMsg("DisableGlideActivationEvent: " + player.getName() + " try to glide but he is canceled", DEBUG);
                event.setCancelled(true);
            }
        }
    }
}
