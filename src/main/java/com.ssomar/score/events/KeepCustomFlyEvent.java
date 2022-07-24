package com.ssomar.score.events;

import com.ssomar.score.fly.FlyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class KeepCustomFlyEvent implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void playerChangedWorldEvent(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        if (FlyManager.getInstance().isPlayerWithFly(p)) {
            p.setAllowFlight(true);
        }
    }

}
