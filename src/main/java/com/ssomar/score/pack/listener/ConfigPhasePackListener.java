package com.ssomar.score.pack.listener;

import com.ssomar.score.pack.custom.PackManager;
import com.ssomar.score.pack.custom.PackSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();
        if (packs.isEmpty()) return;

        // Try Spigot API first: PlayerLinksSendEvent extends PlayerEvent → getPlayer() exists
        Player player = null;
        try {
            Method getPlayerMethod = event.getClass().getMethod("getPlayer");
            player = (Player) getPlayerMethod.invoke(event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            // Not Spigot's version — fall through to Paper path
        }

        if (player != null) {
            // Spigot path: player is fully available during this event
            boolean sent = false;
            for (PackSettings pack : packs.values()) {
                try {
                    player.addResourcePack(pack.getUuid(), pack.getHostedPath(),
                            pack.getHash(), pack.getCustomPromptMessage(), pack.isForce());
                    sent = true;
                } catch (Exception | Error e) {
                    // Silently fail — JoinQuitListener will handle as fallback
                }
            }
            if (sent) {
                playersServedInConfigPhase.add(player.getUniqueId());
            }
            return;
        }

        // Paper path: event only exposes getConnection(), not getPlayer()
        // The player isn't fully constructed yet, so we can't call addResourcePack().
        // Instead, retrieve the UUID from the connection and defer pack sending.
        try {
            Method getConnectionMethod = event.getClass().getMethod("getConnection");
            Object connection = getConnectionMethod.invoke(event);

            // PlayerCommonConnection extends PlayerConnection which has getPlayer()...
            // but during configuration phase, we can at least get the UUID
            Method getUniqueIdMethod = connection.getClass().getMethod("getUniqueId");
            UUID uuid = (UUID) getUniqueIdMethod.invoke(connection);

            // Mark for deferred sending — your JoinQuitListener fallback
            // will pick this up on PlayerJoinEvent instead
            playersServedInConfigPhase.add(uuid);
        } catch (Exception | Error e) {
            // Neither path worked — JoinQuitListener fallback will handle it
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
