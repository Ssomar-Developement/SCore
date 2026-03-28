package com.ssomar.score.pack.listener;

import com.ssomar.score.pack.custom.PackManager;
import com.ssomar.score.pack.custom.PackSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

/**
 * Sends resource packs during the configuration phase (before world join).
 *
 * Handles both API versions:
 * - Spigot / Paper <= 1.21.4: PlayerLinksSendEvent extends PlayerEvent → getPlayer() available
 * - Paper >= 1.21.10: PlayerLinksSendEvent extends Event → only getConnection() available,
 *   no Player object during config phase — we use reflection to call addResourcePack if possible
 *
 * Only sends if the pack URL is already cached (non-blocking).
 * JoinQuitListener is NEVER skipped — MC deduplicates same-UUID packs.
 */
public class ConfigPhasePackListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLinksSend(PlayerLinksSendEvent event) {
        Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();
        if (packs.isEmpty()) return;

        // Try to get the Player object — works on Spigot and Paper <= 1.21.4
        Player player = getPlayerFromEvent(event);
        if (player == null) return; // Paper 1.21.10+ — no Player during config phase, skip

        for (PackSettings pack : packs.values()) {
            String cachedPath = pack.getCachedHostedPath();
            if (cachedPath == null) continue;

            try {
                player.addResourcePack(pack.getUuid(), cachedPath, pack.getHash(), pack.getCustomPromptMessage(), pack.isForce());
            } catch (Exception | Error e) {
                // Silently fail — JoinQuitListener will send on join
            }
        }
    }

    /**
     * Try to get a Player from the event using reflection.
     * On Spigot/Paper <= 1.21.4: event extends PlayerEvent → getPlayer() works.
     * On Paper >= 1.21.10: event extends Event → getPlayer() doesn't exist, returns null.
     */
    private Player getPlayerFromEvent(PlayerLinksSendEvent event) {
        try {
            Method getPlayer = event.getClass().getMethod("getPlayer");
            return (Player) getPlayer.invoke(event);
        } catch (Exception | Error e) {
            return null;
        }
    }
}
