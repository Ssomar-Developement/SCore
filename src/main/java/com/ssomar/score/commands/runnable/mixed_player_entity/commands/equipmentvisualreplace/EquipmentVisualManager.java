package com.ssomar.score.commands.runnable.mixed_player_entity.commands.equipmentvisualreplace;

import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EquipmentVisualManager {

    private static EquipmentVisualManager instance;

    private Map<String, List<ScheduledTask>> tasks;

    public EquipmentVisualManager() {
        tasks = new HashMap<>();
    }

    public void addTask(UUID player, EquipmentSlot slot, List<ScheduledTask> tasks) {
        if (this.tasks.containsKey(player + String.valueOf(slot))) {
            /* add */
            this.tasks.get(player + String.valueOf(slot)).addAll(tasks);
        }
        else this.tasks.put(player + String.valueOf(slot), tasks);
    }

    public void removeTask(UUID player, EquipmentSlot slot) {
        this.tasks.remove(player + String.valueOf(slot));
    }

    public void cancelTasks(UUID player, EquipmentSlot slot) {
        if (this.tasks.containsKey(player + String.valueOf(slot))) {
            for (ScheduledTask task : this.tasks.get(player + String.valueOf(slot))) {
                task.cancel();
            }
            this.tasks.remove(player + String.valueOf(slot));
        }
    }

    public static EquipmentVisualManager getInstance() {
        if (instance == null) instance = new EquipmentVisualManager();
        return instance;
    }
}
