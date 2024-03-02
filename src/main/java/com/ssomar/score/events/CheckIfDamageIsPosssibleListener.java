package com.ssomar.score.events;

import com.ssomar.score.config.GeneralConfig;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CheckIfDamageIsPosssibleListener implements Listener {


    private static CheckIfDamageIsPosssibleListener instance;
    private Map<UUID, Boolean> canDamage;

    private List<UUID> damageList;

    public CheckIfDamageIsPosssibleListener() {
        this.canDamage = new HashMap<>();
        this.damageList = new ArrayList<>();
    }

    public static CheckIfDamageIsPosssibleListener getInstance() {
        if (instance == null) instance = new CheckIfDamageIsPosssibleListener();
        return instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void checkIfDamageEventCancelled(EntityDamageByEntityEvent e) {
       Entity damager = e.getDamager();
       if(e.getDamage() == 0){
           //SsomarDev.testMsg("CheckIfDamageIsPosssibleListener: checkIfDamageEventCancelled: damage = 0 >> "+e.isCancelled(), true);
           canDamage.put(damager.getUniqueId(), !e.isCancelled());
           if(damageList.contains(damager.getUniqueId())){
               e.setCancelled(true);
               damageList.remove(damager.getUniqueId());
           }
       }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void checkIfDamageEventCancelled2(EntityDamageByEntityEvent e) {
        if(!GeneralConfig.getInstance().isDebugCheckDamages()) return;
        if(e.getDamage() == 0){
            RegisteredListener[] registeredListener = e.getHandlers().getRegisteredListeners();
            for(RegisteredListener rl : registeredListener){
                if(rl.getListener() == this) continue;
                try {
                    rl.callEvent(e);
                } catch (EventException ex) {
                    throw new RuntimeException(ex);
                }
                if(e.isCancelled()){
                    System.out.println("[[[DEBUG PROJECTILE]]] PLUGIN >> "+rl.getPlugin().getName()+" >> "+rl.getListener().getClass()+" >>> "+rl.getPriority()+" >>"+e.isCancelled());
                    break;
                }
            }
        }
    }

    public boolean canDamage(@NotNull Entity damager, @NotNull Damageable damaged) {
        damageList.add(damager.getUniqueId());
        damaged.damage((double) 0, damager);
        //if(!canDamage.containsKey(damager.getUniqueId())) SsomarDev.testMsg("CheckIfDamageIsPosssibleListener: canDamage: canDamage not containsKey", true);
        return canDamage.getOrDefault(damager.getUniqueId(), true);
    }
}
