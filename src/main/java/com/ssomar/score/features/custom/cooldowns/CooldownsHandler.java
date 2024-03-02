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

import static com.ssomar.score.SCore.isFolia;

public class CooldownsHandler implements Listener {

    public static void loadCooldowns() {
        if(isFolia()) return;
        Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, () -> {
            List<Cooldown> cooldowns = CooldownsQuery.getGlobalCooldowns(Database.getInstance().connect());
            Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
                @Override
                public void run() {
                    CooldownsManager.getInstance().addCooldowns(cooldowns);

                    Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, new Runnable() {
                        @Override
                        public void run() {
                            CooldownsQuery.deleteGlobalCooldowns(Database.getInstance().connect());
                        }
                    });
                }
            });
        });

        /* Async task to update and pause cooldowns if necessary */
        Bukkit.getScheduler().runTaskTimerAsynchronously(SCore.plugin, new Runnable() {
            @Override
            public void run() {
                List<Cooldown> cooldowns = CooldownsManager.getInstance().getAllCooldowns();
                for (Cooldown cd : cooldowns) {
                    if(cd != null) cd.updatePausePlaceholdersConditions();
                }
            }
        }, 0L, 20L);
    }

    public static void closeServerSaveAll() {
        List<Cooldown> cooldowns = CooldownsManager.getInstance().getAllCooldowns();
        for (Cooldown cd : cooldowns) {
            if(cd != null) cd.updatePlayerDisconnect();
        }

        CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);

        CooldownsManager.getInstance().clearCooldowns();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(!SCore.plugin.isEnabled()) return;

        if(isFolia()) return;
        Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, () -> {
            if(!SCore.plugin.isEnabled()) return;
            List<Cooldown> cooldowns = CooldownsQuery.getCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
            for (Cooldown cd : cooldowns) {
                cd.updatePlayerReconnect();
            }
            Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
                @Override
                public void run() {
                    //SsomarDev.testMsg("COOLDOWNS SIZE: "+cooldowns.size(), true);
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

        for (Cooldown cd : cooldowns) {
            cd.updatePlayerDisconnect();
        }

        if(!SCore.plugin.isEnabled()) return;

        if(isFolia()) return;
        Bukkit.getScheduler().runTaskAsynchronously(SCore.plugin, () -> {
            CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);
            if(!SCore.plugin.isEnabled()) return;
            // go back to the tick loop
            Bukkit.getScheduler().runTask(SCore.plugin, () -> {
                // call the callback with the result
                CooldownsManager.getInstance().removeCooldownsOf(p.getUniqueId());
            });
        });

    }

}
