package com.ssomar.score.commands.runnable.player.commands.sudoop;

import com.ssomar.score.SCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class SecurityOPCommands implements Listener {

    @EventHandler
    public void onPerformCommandAsOP(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();
        if (SUDOOPManager.getInstance().getCommandsAsOP().containsKey(p)) {
            List<String> commands = SUDOOPManager.getInstance().getCommandsAsOP().get(p);
            if (!commands.contains(e.getMessage())) {
                Bukkit.getLogger().severe(SCore.NAME_COLOR_WITH_BRACKETS + " WARNING THE COMMAND " + e.getMessage() + " HAS BEEN BLOCKED WHEN SUDOOP " + p.getName() + " PROBABLY USE HACKED CLIENT");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPerformCommandAsOP(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (SUDOOPManager.getInstance().getCommandsAsOP().containsKey(p)) {
            e.setCancelled(true);
        }
    }
}
