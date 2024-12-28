package com.ssomar.score.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedAttribute;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.UUID;

public class ShulkerPacketUtil {
    private final ProtocolManager protocolManager;

    public ShulkerPacketUtil() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public int sendClientTextDisplay(Player player, Location location) {
        // Create entity ID
        int entityId = (int) (Math.random() * Integer.MAX_VALUE);
        UUID uuid = UUID.randomUUID();

        // Create spawn packet
        PacketContainer spawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers()
                .write(0, entityId); // Entity ID

        spawnPacket.getUUIDs()
                .write(0, uuid); // Entity UUID

        spawnPacket.getEntityTypeModifier()
                .write(0, EntityType.TEXT_DISPLAY); // Entity Type


        // Set position
        spawnPacket.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        // Set rotation
        spawnPacket.getBytes()
                .write(0, (byte) (location.getYaw() * 256.0F / 360.0F))
                .write(1, (byte) (location.getPitch() * 256.0F / 360.0F));


        try {
            // Send spawn packet
            protocolManager.sendServerPacket(player, spawnPacket);

            return entityId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int sendClientShulker(Player player, Location location) {
        // Create entity ID
        int entityId = (int) (Math.random() * Integer.MAX_VALUE);
        UUID uuid = UUID.randomUUID();

        // Create spawn packet
        PacketContainer spawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers()
                .write(0, entityId); // Entity ID

        spawnPacket.getUUIDs()
                .write(0, uuid); // Entity UUID

        spawnPacket.getEntityTypeModifier()
                .write(0, EntityType.SHULKER); // Entity Type


        // Set position
        spawnPacket.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        // Set rotation
        spawnPacket.getBytes()
                .write(0, (byte) (location.getYaw() * 256.0F / 360.0F))
                .write(1, (byte) (location.getPitch() * 256.0F / 360.0F));

        // Create metadata packet for living entity properties
        PacketContainer attributes = protocolManager.createPacket(PacketType.Play.Server.UPDATE_ATTRIBUTES);

        WrappedAttribute scaleAttribute = WrappedAttribute.newBuilder()
                .attributeKey("scale")
                .baseValue(0.2)
                .packet(attributes)
                .modifiers(Arrays.asList())
                .build();

        // Add the attribute to the packet
        attributes.getAttributeCollectionModifier()
                .write(0, Arrays.asList(scaleAttribute));

        attributes.getIntegers().write(0, entityId);

        // Create effect packet
        PacketContainer effectPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EFFECT);
        effectPacket.getEffectTypes().write(0, PotionEffectType.INVISIBILITY);
        byte flags = 0x08;
        flags |= 0x02; // show particles
        effectPacket.getBytes().write(0, (byte) flags); // hide stuff
        effectPacket.getIntegers().write(0, entityId) // entity ID
                .write(1, 1) // amplifier
                .write(2, -1); // duration


        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, entityId);
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(null);
        watcher.setObject(0, serializer, (byte) 0x40 );
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());



        try {
            // Send spawn packet
            protocolManager.sendServerPacket(player, spawnPacket);
            // Send metadata packet
            protocolManager.sendServerPacket(player, effectPacket);
            // Send attributes packet
            protocolManager.sendServerPacket(player, attributes);

            protocolManager.sendServerPacket(player, packet);



            return entityId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void mountShulker(Player player, int shulkerId, int vehicleEntityId) {
        try {
            // Create mount packet
            PacketContainer mountPacket = protocolManager.createPacket(PacketType.Play.Server.MOUNT);

            // Set the vehicle's entity ID
            mountPacket.getIntegers()
                    .write(0, vehicleEntityId);  // Vehicle entity ID

            // Set passenger IDs (just our shulker in this case)
            mountPacket.getIntegerArrays()
                    .write(0, new int[]{shulkerId});

            // Send the packet
            protocolManager.sendServerPacket(player, mountPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Optional: Method to remove the client-side entity
    public void removeClientShulker(Player player, int entityId) {
        PacketContainer destroyPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        destroyPacket.getIntLists().write(0, Arrays.asList(entityId));

        try {
            protocolManager.sendServerPacket(player, destroyPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}