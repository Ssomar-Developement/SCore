package com.ssomar.score.pack.listener;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.configuration.server.WrapperConfigServerResourcePackSend;
import com.ssomar.score.pack.custom.PackManager;
import com.ssomar.score.pack.custom.PackSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

/**
 * Sends resource packs during the configuration phase (before world join) using PacketEvents.
 * Players see the loading screen before entering the world — like Nexo.
 *
 * Uses WrapperConfigServerResourcePackSend to send the pack during config phase,
 * which works on both Spigot and Paper regardless of API version.
 *
 * Only sends if the pack URL is already cached (non-blocking).
 * JoinQuitListener is NEVER skipped — MC deduplicates same-UUID packs.
 */
public class ConfigPhasePackListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLinksSend(PlayerLinksSendEvent event) {
        Map<UUID, PackSettings> packs = PackManager.getInstance().getPacks();
        if (packs.isEmpty()) return;

        // Get the player or connection object to find the PacketEvents User
        Object channelOwner = getChannelOwner(event);
        if (channelOwner == null) return;

        User user;
        try {
            user = PacketEvents.getAPI().getPlayerManager().getUser(channelOwner);
        } catch (Exception | Error e) {
            return; // PacketEvents not ready or can't find user
        }
        if (user == null) return;

        for (PackSettings pack : packs.values()) {
            String cachedPath = pack.getCachedHostedPath();
            if (cachedPath == null) continue;

            try {
                String hashHex = pack.getHash() != null ? PackSettings.hashToHex(pack.getHash()) : "";
                WrapperConfigServerResourcePackSend packet = new WrapperConfigServerResourcePackSend(
                        pack.getUuid(),
                        cachedPath,
                        hashHex,
                        pack.isForce(),
                        pack.getCustomPromptMessage() != null && !pack.getCustomPromptMessage().isEmpty()
                                ? Component.text(pack.getCustomPromptMessage())
                                : null
                );
                user.sendPacket(packet);
            } catch (Exception | Error e) {
                // JoinQuitListener will send on join as fallback
                if (com.ssomar.score.config.GeneralConfig.getInstance().isSelfHostPackDebug()) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the object that PacketEvents can use to look up the User.
     * - Spigot / Paper <= 1.21.4: getPlayer() returns a Player
     * - Paper >= 1.21.10: getConnection() returns the config connection
     */
    private Object getChannelOwner(PlayerLinksSendEvent event) {
        // Try getPlayer() first (Spigot and older Paper)
        try {
            Method getPlayer = event.getClass().getMethod("getPlayer");
            Object player = getPlayer.invoke(event);
            if (player != null) return player;
        } catch (Exception ignored) {}

        // Try getConnection() (Paper 1.21.10+)
        try {
            Method getConnection = event.getClass().getMethod("getConnection");
            return getConnection.invoke(event);
        } catch (Exception ignored) {}

        return null;
    }
}
