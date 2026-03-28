package com.ssomar.score.pack.listener;

import com.ssomar.score.pack.custom.PackManager;
import com.ssomar.score.pack.custom.PackSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

import java.util.*;

/**
 * Sends resource packs during the configuration phase (before world join) using PlayerLinksSendEvent.
 * This provides a better UX — players see the loading screen before entering the world instead of
 * getting a visual glitch period after join.
 *
 * Available on Paper 1.20.5+.
 */
public class ConfigPhasePackListener implements Listener {

    private static final Set<UUID> playersServedInConfigPhase = Collections.synchronizedSet(new HashSet<>());

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLinksSend(PlayerLinksSendEvent event) {
        Player player = event.getPlayer();
        Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();

        if (packs.isEmpty()) return;

        boolean sent = false;
        for (PackSettings pack : packs.values()) {
            try {
                player.addResourcePack(pack.getUuid(), pack.getHostedPath(), pack.getHash(), pack.getCustomPromptMessage(), pack.isForce());
                sent = true;
            } catch (Exception | Error e) {
                // Silently fail — JoinQuitListener will handle as fallback
            }
        }
        if (sent) {
            playersServedInConfigPhase.add(player.getUniqueId());
        }
    }

    /**
     * Check if this player already received packs during config phase.
     * Clears the flag after checking (one-time use per join).
     */
    public static boolean wasServedInConfigPhase(UUID playerUuid) {
        return playersServedInConfigPhase.remove(playerUuid);
    }
}
