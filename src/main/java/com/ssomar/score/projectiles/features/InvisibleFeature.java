package com.ssomar.score.projectiles.features;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.Dependency;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class InvisibleFeature extends BooleanFeature implements SProjectileFeatureInterface {


    public InvisibleFeature(FeatureParentInterface parent) {
        super(parent,  false, FeatureSettingsSCore.invisible, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue() && Dependency.PROTOCOL_LIB.isEnabled()) {
            PacketContainer entityPacketContainer = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            if (!SCore.is1v17Plus()) entityPacketContainer.getIntegerArrays().write(0, new int[]{e.getEntityId()});
            else entityPacketContainer.getIntLists().write(0, Collections.singletonList(e.getEntityId()));
            Bukkit.getOnlinePlayers().forEach(p -> {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(p, entityPacketContainer);
                } catch (InvocationTargetException err) {
                    err.printStackTrace();
                }
            });
        }
    }

    @Override
    public InvisibleFeature clone(FeatureParentInterface newParent) {
        InvisibleFeature clone = new InvisibleFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
