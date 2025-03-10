package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.Couple;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.*;

public class NoFallDamageListener implements Listener {

    private static NoFallDamageListener instance;

    private final Map<UUID, List<Couple<UUID, ScheduledTask>>> noFallDamageMap = new HashMap<>();

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {

        if (!e.getCause().equals(DamageCause.FALL) || !(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();

        NoFallDamageListener nFD = NoFallDamageListener.getInstance();

        if (nFD.contains(p)) {
            e.setCancelled(true);
            nFD.removeAllNoFallDamage(p);
        }
    }

    public static NoFallDamageListener getInstance() {
        if (instance == null) instance = new NoFallDamageListener();
        return instance;
    }

    public void addNoFallDamage(Entity e) {
        UUID uuid = UUID.randomUUID();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                NoFallDamageListener.getInstance().removeNoFallDamage(e, uuid);
            }
        };
        ScheduledTask task = SCore.schedulerHook.runTask(runnable, 300); // 300 ticks = 15 seconds (1 tick = 0.05 seconds

        NoFallDamageListener.getInstance().addNoFallDamage(e, new Couple<>(uuid, task));
    }

    public void addNoFallDamage(Entity e, Couple<UUID, ScheduledTask> c) {
        if (noFallDamageMap.containsKey(e.getUniqueId())) {
            noFallDamageMap.get(e.getUniqueId()).add(c);
        } else {
            List<Couple<UUID, ScheduledTask>> newList = new ArrayList<>();
            newList.add(c);
            noFallDamageMap.put(e.getUniqueId(), newList);
        }
    }

    public void removeNoFallDamage(Entity e, UUID uuid) {
        boolean emptyList = false;
        for (UUID eUUID : noFallDamageMap.keySet()) {

            if (e.getUniqueId() == eUUID) {
                List<Couple<UUID, ScheduledTask>> tasks = noFallDamageMap.get(e.getUniqueId());

                Couple<UUID, ScheduledTask> toRemove = null;
                for (Couple<UUID, ScheduledTask> c : tasks) {
                    if (c.getElem1().equals(uuid)) {
                        c.getElem2().cancel();
                        toRemove = c;
                    }
                }
                if (toRemove != null) tasks.remove(toRemove);

                emptyList = tasks.size() == 0;

                break;
            }
        }
        if (emptyList) noFallDamageMap.remove(e.getUniqueId());

    }

    public void removeAllNoFallDamage(Entity e) {
        noFallDamageMap.remove(e);
    }

    public boolean contains(Entity e) {
        for (UUID eUUID : noFallDamageMap.keySet()) {
            if (e.getUniqueId() == eUUID) {
                return true;
            }
        }
        return false;
    }
}
