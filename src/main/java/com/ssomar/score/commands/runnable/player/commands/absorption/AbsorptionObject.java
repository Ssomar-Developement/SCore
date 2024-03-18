package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.utils.scheduler.ScheduledTask;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class AbsorptionObject {

    private UUID playerUUID;
    private double absorption;
    private long time;
    private ScheduledTask task;
    private boolean toRemove;

    public AbsorptionObject(UUID playerUUID, double absorption, long time) {
        this.playerUUID = playerUUID;
        this.absorption = absorption;
        this.time = time;
        this.toRemove = false;
    }
}
