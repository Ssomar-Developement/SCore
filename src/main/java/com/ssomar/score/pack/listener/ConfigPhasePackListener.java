package com.ssomar.score.pack.listener;

import com.ssomar.score.pack.custom.PackManager;
import com.ssomar.score.pack.custom.PackSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

import java.util.Map;
import java.util.UUID;

/**
 * Sends resource packs during the configuration phase (before world join) using PlayerLinksSendEvent.
 * Players see the loading screen before entering the world — no visual glitch period.
 *
 * Only sends if the pack hosted URL is already resolved (non-blocking).
 * First player joining resolves the URL via JoinQuitListener, subsequent players
 * get the pack here during config phase for instant loading.
 *
 * The JoinQuitListener is NOT skipped — Minecraft deduplicates same-UUID packs client-side.
 */
public class ConfigPhasePackListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLinksSend(PlayerLinksSendEvent event) {
        Player player = event.getPlayer();
        Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();

        for (PackSettings pack : packs.values()) {
            // Only send if the hosted path is already cached (non-blocking)
            // getHostedPath() does HTTP checks on first call — we don't want that during config phase
            String cachedPath = pack.getCachedHostedPath();
            if (cachedPath == null) continue;

            try {
                player.addResourcePack(pack.getUuid(), cachedPath, pack.getHash(), pack.getCustomPromptMessage(), pack.isForce());
            } catch (Exception | Error e) {
                // Silently fail — JoinQuitListener will send on join
            }
        }
    }
}
