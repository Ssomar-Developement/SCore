package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.data.CooldownsQuery;
import com.ssomar.score.data.Database;
import com.ssomar.score.utils.logging.Utils;
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

    public static void loadCooldowns() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                List<Cooldown> cooldowns = CooldownsQuery.getGlobalCooldowns(Database.getInstance().connect());
                Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
                    @Override
                    public void run() {
                        CooldownsManager.getInstance().addCooldowns(cooldowns);

                        Runnable r2 = new Runnable() {
                            @Override
                            public void run() {
                                CooldownsQuery.deleteGlobalCooldowns(Database.getInstance().connect());
                            }
                        };
                        SCore.schedulerHook.runAsyncTask(r2, 0);
                    }
                });
            }
        };
        SCore.schedulerHook.runAsyncTask(r, 0);

        /* Async task to update and pause cooldowns if necessary */
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                List<Cooldown> cooldowns = CooldownsManager.getInstance().getAllCooldowns();
                for (Cooldown cd : cooldowns) {
                    if(cd != null) cd.updatePausePlaceholdersConditions();
                }
            }
        };
        SCore.schedulerHook.runAsyncRepeatingTask(r2, 0, 20);
    }

    public static void closeServerSaveAll() {
        List<Cooldown> cooldowns = CooldownsManager.getInstance().getAllCooldowns();
        for (Cooldown cd : cooldowns) {
            if(cd != null) cd.updatePlayerDisconnect();
        }

        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7"+cooldowns.size()+" cooldowns saved");

        CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);

        CooldownsManager.getInstance().clearCooldowns();
    }

    public static void connectAllOnlinePlayers(){
        for(Player p : Bukkit.getOnlinePlayers()){
            connect(p);
        }
    }

    public static void connect(Player p){
        if(!SCore.plugin.isEnabled()) return;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(!SCore.plugin.isEnabled()) return;
                List<Cooldown> cooldowns = CooldownsQuery.getCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
                for (Cooldown cd : cooldowns) {
                    cd.updatePlayerReconnect();
                }
                Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
                    @Override
                    public void run() {
                        SsomarDev.testMsg("COOLDOWNS SIZE: "+cooldowns.size(), true);
                        CooldownsManager.getInstance().addCooldowns(cooldowns);

                        Runnable r2 = new Runnable() {
                            @Override
                            public void run() {
                                CooldownsQuery.deleteCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
                            }
                        };
                        SCore.schedulerHook.runAsyncTask(r2, 0);
                    }
                });
            }
        };
        SCore.schedulerHook.runAsyncTask(r, 0);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        connect(p);
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

        Runnable r = new Runnable() {
            @Override
            public void run() {
                CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);
                if(!SCore.plugin.isEnabled()) return;
                // go back to the tick loop
                Runnable r2 = new Runnable() {
                    @Override
                    public void run() {
                        // call the callback with the result
                        CooldownsManager.getInstance().removeCooldownsOf(p.getUniqueId());
                    }
                };
                SCore.schedulerHook.runAsyncTask(r2, 0);
            }
        };
        SCore.schedulerHook.runAsyncTask(r, 0);
    }

}
