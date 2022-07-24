package com.ssomar.scoretestrecode.features.custom.cooldowns;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.features.custom.activators.activator.NewSActivator;
import com.ssomar.scoretestrecode.sobject.NewSObject;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
public class Cooldown {

    private static final boolean DEBUG = false;
    private String id;
    @Nullable /* If global == null */
    private UUID entityUUID;
    private int cooldown;
    private boolean isInTick;
    private long time;
    /* Affect all players or not */
    private boolean global;
    private boolean isNull;

    public Cooldown(SPlugin sPlugin, NewSObject sO, NewSActivator sAct, UUID entityUUID, int cooldown, boolean isInTick, long time, boolean global) {
        super();
        this.id = sPlugin.getShortName() + ":" + sO.getId() + ":" + sAct.getId();
        this.entityUUID = entityUUID;
        this.cooldown = cooldown;
        this.isInTick = isInTick;
        this.time = time;
        this.global = global;
        isNull = false;
    }

    public Cooldown(SPlugin sPlugin, String id, UUID entityUUID, int cooldown, boolean isInTick, long time, boolean global) {
        super();
        this.id = id;
        this.entityUUID = entityUUID;
        this.cooldown = cooldown;
        this.isInTick = isInTick;
        this.time = time;
        this.global = global;
        isNull = false;
    }

    public Cooldown(String id, UUID entityUUID, int cooldown, boolean isInTick, long time, boolean global) {
        super();
        this.id = id;
        this.entityUUID = entityUUID;
        this.cooldown = cooldown;
        this.isInTick = isInTick;
        this.time = time;
        this.global = global;
        isNull = false;
    }

    public double getTimeLeft() {
        long current = System.currentTimeMillis();
        long delay = current - getTime();
        int div = 1000;
        if (isInTick()) div = 50;
        int delayInt = (int) (delay / div);
        SsomarDev.testMsg("delayInt: " + delayInt, DEBUG);

        int timeLeft = getCooldown() - delayInt;

        SsomarDev.testMsg("timeLeft: " + timeLeft, DEBUG);
        double result = timeLeft;
        SsomarDev.testMsg("pre  result: " + result / 200, DEBUG);
        if (isInTick()) result = result / 200;
        SsomarDev.testMsg("Cooldown: " + result, DEBUG);
        return result;
    }

    @Override
    public String toString() {
        return id + " >>>> " + entityUUID + " >>>> " + cooldown + " >>>> " + isInTick + " >>>> " + time;
    }
}
