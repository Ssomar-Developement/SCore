package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StunEvent implements Listener {

    public static Map<UUID, Boolean> stunPlayers = new HashMap<>();

    @EventHandler
    public void PlayerGlideEvent(EntityToggleGlideEvent e) {
        if(stunPlayers.containsKey(e.getEntity().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        if(stunPlayers.containsKey(e.getPlayer().getUniqueId()) && stunPlayers.get(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
