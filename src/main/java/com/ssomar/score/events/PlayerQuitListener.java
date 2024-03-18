package com.ssomar.score.events;

import com.ssomar.score.commands.runnable.player.commands.absorption.AbsorptionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void playerReconnexion(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        AbsorptionManager.getInstance().onDisconnect(p);
    }

}

