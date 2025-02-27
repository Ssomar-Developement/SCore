package com.ssomar.score.events;

import com.ssomar.score.SCore;
import lombok.Getter;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DamageWithoutKnockbackListener implements Listener {

    private static DamageWithoutKnockbackListener instance;
    @Getter
    private final List<UUID> damageWithoutKnockbackList = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageEvent(EntityDamageEvent event) {

        //SsomarDev.testMsg("DamageWithoutKnockbackListener 111111111", true);

        Entity e = event.getEntity();
        if (!(e instanceof LivingEntity)) return;

        LivingEntity entity = (LivingEntity) event.getEntity();
        //SsomarDev.testMsg(DamageWithoutKnockbackManager.getInstance().getDamageWithoutKnockbackList().size()+"<<<<<2 <<<<", true);
        if (DamageWithoutKnockbackListener.getInstance().contains(e)) {
            Runnable runnable3 = new Runnable() {
                @Override
                public void run() {
                    entity.setVelocity(new Vector(0, 0, 0));
                    if (SCore.is1v20Plus()) {
                        entity.playHurtAnimation(50);
                    } else entity.playEffect(EntityEffect.HURT);
                    //SsomarDev.testMsg("DamageWithoutKnockbackListener 2222", true);
                }
            };
            SCore.schedulerHook.runEntityTask(runnable3, null, entity, 1);

            DamageWithoutKnockbackListener.getInstance().removeDamageWithoutKnockback(e);
        }
        //SsomarDev.testMsg("DamageWithoutKnockbackListener 3333 no contains >> "+e.getType(), true);
    }


    public static DamageWithoutKnockbackListener getInstance() {
        if (instance == null) instance = new DamageWithoutKnockbackListener();
        return instance;
    }

    public void addDamageWithoutKnockback(Entity e) {
        UUID uuid = e.getUniqueId();
        damageWithoutKnockbackList.add(uuid);
    }

    public void removeDamageWithoutKnockback(Entity e) {
        UUID uuid = e.getUniqueId();
        damageWithoutKnockbackList.remove(uuid);
    }

    public boolean contains(Entity e) {
        UUID uuid = e.getUniqueId();
        return damageWithoutKnockbackList.contains(uuid);
    }
}
