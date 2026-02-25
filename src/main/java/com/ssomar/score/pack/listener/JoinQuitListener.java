package com.ssomar.score.pack.listener;

import com.ssomar.score.SCore;
import com.ssomar.score.pack.custom.PackManager;
import com.ssomar.score.pack.custom.PackSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class JoinQuitListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();
        // For some reason when ItemsAdder is installed on the server, we need to add a delay between each resource pack addition
        // https://discord.com/channels/701066025516531753/1443583007788105751
        int delay = 0;
        for (PackSettings pack : packs.values()) {
            Runnable runnable = () -> {
                try {
                    p.addResourcePack(pack.getUuid(), pack.getHostedPath(), null, pack.getCustomPromptMessage(), pack.isForce());
                } catch (Exception | Error ex) {
                    // Version not supported or error in adding resource packs
                }
            };
            SCore.schedulerHook.runAsyncTask(runnable, delay);
            delay += 40; // at least 2 seconds between each pack addition
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        /* Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();
        for(PackSettings pack : packs.values()) {
            p.removeResourcePack(pack.getUuid());
        }*/
    }
}
