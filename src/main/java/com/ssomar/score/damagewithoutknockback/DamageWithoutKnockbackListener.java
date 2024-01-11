package com.ssomar.score.damagewithoutknockback;

import com.ssomar.score.SCore;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DamageWithoutKnockbackListener implements Listener {


    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageEvent(EntityDamageEvent event) {

        //SsomarDev.testMsg("DamageWithoutKnockbackListener 111111111", true);

        Entity e = event.getEntity();
        if (!(e instanceof LivingEntity)) return;

        LivingEntity entity = (LivingEntity) event.getEntity();
        //SsomarDev.testMsg(DamageWithoutKnockbackManager.getInstance().getDamageWithoutKnockbackList().size()+"<<<<<2 <<<<", true);
        if (DamageWithoutKnockbackManager.getInstance().contains(e)) {
            BukkitRunnable runnable3 = new BukkitRunnable() {
                @Override
                public void run() {
                    entity.setVelocity(new Vector(0, 0, 0));
                    if (SCore.is1v20Plus()) {
                        entity.playHurtAnimation(50);
                    } else entity.playEffect(EntityEffect.HURT);
                    //SsomarDev.testMsg("DamageWithoutKnockbackListener 2222", true);
                }
            };
            runnable3.runTaskLater(SCore.plugin, 1);

            DamageWithoutKnockbackManager.getInstance().removeDamageWithoutKnockback(e);
        }
        //SsomarDev.testMsg("DamageWithoutKnockbackListener 3333 no contains >> "+e.getType(), true);
    }
}
