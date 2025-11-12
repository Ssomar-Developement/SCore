package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.utils.scheduler.ScheduledTask;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class AbsorptionObject {

    private UUID absorptionUUID;
    private UUID playerUUID;
    private double absorption;
    private long expiryTime;
    private ScheduledTask task;
    private boolean toRemove;

    public AbsorptionObject(UUID absorptionUUID, UUID playerUUID, double absorption, long time) {
        this.absorptionUUID = absorptionUUID;
        this.playerUUID = playerUUID;
        this.absorption = absorption;
        this.expiryTime = time;
        this.toRemove = false;
    }

    public AbsorptionObject(UUID playerUUID, double absorption, long time) {
        this.playerUUID = playerUUID;
        this.absorption = absorption;
        this.expiryTime = time;
        this.toRemove = false;
    }
}
