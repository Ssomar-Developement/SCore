package com.ssomar.score.events;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;

public class RemoveProjectileHitBlockEvent implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHitBlock(ProjectileHitEvent e){
        if((SCore.is1v13Less()) && e.getHitBlock() == null) return;

        NamespacedKey key = new NamespacedKey(SCore.plugin, "remove_hit_block");

        if(e.getEntity().getPersistentDataContainer().get(key, PersistentDataType.INTEGER) != null){
            e.getEntity().remove();
        }
    }
}
