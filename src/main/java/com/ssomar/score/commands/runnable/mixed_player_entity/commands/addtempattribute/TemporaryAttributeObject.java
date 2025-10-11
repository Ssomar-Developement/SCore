package com.ssomar.score.commands.runnable.mixed_player_entity.commands.addtempattribute;

import com.ssomar.score.utils.scheduler.ScheduledTask;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TemporaryAttributeObject {
    /**
     * Owner of the temporary attribute
     */
    private UUID entityUUID;
    private double absorption;
    private long time;
    private ScheduledTask task;
    private boolean toRemove;
    public TemporaryAttributeObject(UUID playerUUID, double absorption, long time) {
        this.entityUUID = playerUUID;
        this.absorption = absorption;
        this.time = time;
        this.toRemove = false;
    }
}
