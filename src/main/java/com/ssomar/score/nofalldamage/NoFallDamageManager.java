package com.ssomar.score.nofalldamage;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.Couple;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;


public class NoFallDamageManager {

    private static NoFallDamageManager instance;
    private final Map<Entity, List<Couple<UUID, BukkitTask>>> noFallDamageMap = new HashMap<>();

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
        BukkitTask task = runnable.runTaskLater(SCore.plugin, 300);

        NoFallDamageManager.getInstance().addNoFallDamage(e, new Couple<>(uuid, task));
    }

    public void addNoFallDamage(Entity e, Couple<UUID, BukkitTask> c) {
        if (noFallDamageMap.containsKey(e)) {
            noFallDamageMap.get(e).add(c);
        } else {
            List<Couple<UUID, BukkitTask>> newList = new ArrayList<>();
            newList.add(c);
            noFallDamageMap.put(e, newList);
        }
    }

    public void removeNoFallDamage(Entity e, UUID uuid) {
        boolean emptyList = false;
        for (Entity entity : noFallDamageMap.keySet()) {

            if (e.equals(entity)) {
                List<Couple<UUID, BukkitTask>> tasks = noFallDamageMap.get(e);

                Couple<UUID, BukkitTask> toRemove = null;
                for (Couple<UUID, BukkitTask> c : tasks) {
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
