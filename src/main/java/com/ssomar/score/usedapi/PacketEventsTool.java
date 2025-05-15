package com.ssomar.score.usedapi;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerRotation;
import org.bukkit.entity.Player;

public class PacketEventsTool {

    public static  int sendTest(Player player) {
        int entityId = (int) (Math.random() * Integer.MAX_VALUE);


        WrapperPlayClientPlayerRotation clientPlayerRotation = new WrapperPlayClientPlayerRotation(100,100, true);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, clientPlayerRotation);

        return entityId;
    }
}
