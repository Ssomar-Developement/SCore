package com.ssomar.score.newprojectiles.features;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.FixedMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;

public class InvisibleFeature extends BooleanFeature implements SProjectileFeatureInterface {


    public InvisibleFeature(FeatureParentInterface parent) {
        super(parent, "invisible", false, "Invisible", new String[]{}, FixedMaterial.getMaterial(Arrays.asList("GLASS_PANE", "GLASS")), false, false);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue() && SCore.hasProtocolLib) {
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
