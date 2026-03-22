package com.ssomar.score.sparticles;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play;
import com.ssomar.score.SCore;
import com.ssomar.score.usedapi.Dependency;
import com.ssomar.score.utils.logging.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Manages per-player particle visibility toggling.
 * Players who have toggled particles off will not receive SCore particle packets.
 * Persists the toggle state across restarts.
 *
 * Packet-level filtering is applied via ProtocolLib or PacketEvents (whichever
 * is available) so ALL world particles sent by SCore shapes/commands are hidden
 * for opted-out players.
 */
public class ParticleToggleManager {

    private static ParticleToggleManager instance;

    /** UUIDs of players who currently have particles hidden. */
    private final Set<UUID> hiddenPlayers = new HashSet<>();

    private final File dataFile;

    private ParticleToggleManager() {
        dataFile = new File(SCore.dataFolder, "particle-toggles.yml");
        load();
        if (SCore.hasProtocolLib) {
            registerProtocolLibListener();
        } else if (Dependency.PACKET_EVENTS.isEnabled()) {
            registerPacketEventsListener();
        }
    }

    public static ParticleToggleManager getInstance() {
        if (instance == null) {
            instance = new ParticleToggleManager();
        }
        return instance;
    }

    /**
     * Toggle particle visibility for a player.
     *
     * @param uuid the player's UUID
     * @return {@code true} if particles are now hidden, {@code false} if now visible
     */
    public boolean toggle(UUID uuid) {
        if (hiddenPlayers.contains(uuid)) {
            hiddenPlayers.remove(uuid);
            save();
            return false;
        } else {
            hiddenPlayers.add(uuid);
            save();
            return true;
        }
    }

    /**
     * Returns whether the player currently has particles hidden.
     */
    public boolean isHidden(UUID uuid) {
        return hiddenPlayers.contains(uuid);
    }

    /**
     * Forcefully set the hidden state for a player.
     */
    public void setHidden(UUID uuid, boolean hidden) {
        if (hidden) {
            hiddenPlayers.add(uuid);
        } else {
            hiddenPlayers.remove(uuid);
        }
        save();
    }

    // -------------------------------------------------------------------------
    // Persistence
    // -------------------------------------------------------------------------

    public void load() {
        hiddenPlayers.clear();
        if (!dataFile.exists()) return;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
        List<String> list = config.getStringList("hidden-players");
        for (String entry : list) {
            try {
                hiddenPlayers.add(UUID.fromString(entry));
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public void save() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("hidden-players",
                hiddenPlayers.stream()
                        .map(UUID::toString)
                        .collect(java.util.stream.Collectors.toList()));
        try {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }
            config.save(dataFile);
        } catch (IOException e) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cFailed to save particle-toggles.yml: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // ProtocolLib integration
    // -------------------------------------------------------------------------

    /**
     * Registers a ProtocolLib packet listener that cancels WORLD_PARTICLES
     * packets for players who have opted out.
     */
    private void registerProtocolLibListener() {
        Plugin plugin = SCore.plugin;
        SCore.protocolManager.addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.WORLD_PARTICLES) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        Player recipient = event.getPlayer();
                        if (recipient != null && hiddenPlayers.contains(recipient.getUniqueId())) {
                            event.setCancelled(true);
                        }
                    }
                }
        );
    }

    // -------------------------------------------------------------------------
    // PacketEvents integration
    // -------------------------------------------------------------------------

    /**
     * Registers a PacketEvents listener that cancels PARTICLE packets
     * for players who have opted out.
     */
    private void registerPacketEventsListener() {
        PacketEvents.getAPI().getEventManager().registerListener(new PacketListenerAbstract() {
            @Override
            public void onPacketSend(PacketSendEvent event) {
                if (event.getPacketType() != Play.Server.PARTICLE) return;
                Player recipient = (Player) event.getPlayer();
                if (recipient != null && hiddenPlayers.contains(recipient.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
