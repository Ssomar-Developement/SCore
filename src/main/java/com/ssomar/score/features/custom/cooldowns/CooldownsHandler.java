package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.score.SCore;
import com.ssomar.score.data.CooldownsQuery;
import com.ssomar.score.data.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class CooldownsHandler implements Listener {

    public static void closeServerSaveAll() {
        List<Cooldown> cooldowns = CooldownsManager.getInstance().getAllCooldowns();

        CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);

        CooldownsManager.getInstance().clearCooldowns();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, () -> {
            List<Cooldown> cooldowns = CooldownsQuery.getCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
            Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
                @Override
                public void run() {
                    CooldownsManager.getInstance().addCooldowns(cooldowns);

                    Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, new Runnable() {
                        @Override
                        public void run() {
                            CooldownsQuery.deleteCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
                        }
                    });
                }
            });
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        List<Cooldown> cooldowns = new ArrayList<>(CooldownsManager.getInstance().getCooldownsOf(p.getUniqueId()));
        if (cooldowns.isEmpty()) return;

        Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, () -> {
            CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);
            // go back to the tick loop
            Bukkit.getScheduler().runTask(SCore.plugin, () -> {
                // call the callback with the result
                CooldownsManager.getInstance().removeCooldownsOf(p.getUniqueId());
            });
        });

    }

}
