package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.DisableFlyActivation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DisableFlyActivationEvent implements Listener {

    private static final Boolean DEBUG = false;

    @EventHandler(priority = EventPriority.HIGH)
    public void playerToggleFlightEvent(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        SsomarDev.testMsg("DisableFlyActivationEvent", DEBUG);
        if (e.isFlying() && DisableFlyActivation.getInstance().getActiveDisabled().containsKey(player.getUniqueId())) {
            SsomarDev.testMsg("DisableFlyActivationEvent: " + player.getName() + " try to fly but he is canceled", DEBUG);
            e.setCancelled(true);
        }
    }
}
