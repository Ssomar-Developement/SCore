package com.ssomar.score.nofalldamage;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.Couple;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


public class NoFallDamageManager {

    private static NoFallDamageManager instance;
    private final Map<Entity, List<Couple<UUID, ScheduledTask>>> noFallDamageMap = new HashMap<>();

    public static NoFallDamageManager getInstance() {
        if (instance == null) instance = new NoFallDamageManager();
        return instance;
    }

    public void addNoFallDamage(Entity e) {
        UUID uuid = UUID.randomUUID();

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                NoFallDamageManager.getInstance().removeNoFallDamage(e, uuid);
            }
        };
        ScheduledTask task = SCore.schedulerHook.runTask(runnable, 300); // 300 ticks = 15 seconds (1 tick = 0.05 seconds

        NoFallDamageManager.getInstance().addNoFallDamage(e, new Couple<>(uuid, task));
    }

    public void addNoFallDamage(Entity e, Couple<UUID, ScheduledTask> c) {
        if (noFallDamageMap.containsKey(e)) {
            noFallDamageMap.get(e).add(c);
        } else {
            List<Couple<UUID, ScheduledTask>> newList = new ArrayList<>();
            newList.add(c);
            noFallDamageMap.put(e, newList);
        }
    }

    public void removeNoFallDamage(Entity e, UUID uuid) {
        boolean emptyList = false;
        for (Entity entity : noFallDamageMap.keySet()) {

            if (e.equals(entity)) {
                List<Couple<UUID, ScheduledTask>> tasks = noFallDamageMap.get(e);

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
        if (emptyList) noFallDamageMap.remove(e);

    }

    public void removeAllNoFallDamage(Entity e) {
        noFallDamageMap.remove(e);
    }

    public boolean contains(Entity e) {
        for (Entity entity : noFallDamageMap.keySet()) {
            if (e.equals(entity)) {
                return true;
            }
        }
        return false;
    }

}
