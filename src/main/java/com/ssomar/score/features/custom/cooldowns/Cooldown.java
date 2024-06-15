package com.ssomar.score.features.custom.cooldowns;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.features.custom.conditions.placeholders.group.PlaceholderConditionGroupFeature;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The ID in my plugins is generally SCore + ":" + ObjectID + ":" + ActivatorID;
 **/
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

    /* Pause features */
    private boolean pauseWhenOffline;
    private PlaceholderConditionGroupFeature pausePlaceholdersConditions;
    private boolean isPaused;

    public Cooldown(SPlugin sPlugin, SObject sO, SActivator sAct, UUID entityUUID, int cooldown, boolean isInTick, long time, boolean global) {
        super();
        this.id = sPlugin.getShortName() + ":" + sO.getId() + ":" + sAct.getId();
        this.entityUUID = entityUUID;
        this.cooldown = cooldown;
        this.isInTick = isInTick;
        this.time = time;
        this.global = global;
        isNull = false;
        pauseWhenOffline = false;
        pausePlaceholdersConditions = new PlaceholderConditionGroupFeature(null, FeatureSettingsSCore.pausePlaceholdersConditions);
        isPaused = false;
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
        pauseWhenOffline = false;
        pausePlaceholdersConditions = new PlaceholderConditionGroupFeature(null, FeatureSettingsSCore.pausePlaceholdersConditions);
        isPaused = false;
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
        pauseWhenOffline = false;
        pausePlaceholdersConditions = new PlaceholderConditionGroupFeature(null, FeatureSettingsSCore.pausePlaceholdersConditions);
        isPaused = false;
    }


    public Cooldown setPauseFeatures(boolean pauseWhenOffline, PlaceholderConditionGroupFeature pausePlaceholdersConditions) {
        this.pauseWhenOffline = pauseWhenOffline;
        this.pausePlaceholdersConditions = pausePlaceholdersConditions;
        return this;
    }

    public int getTimeLeftFlatValue() {

        if (isPaused) return cooldown;

        long current = System.currentTimeMillis();
        long delay = current - getTime();
        int div = 1000;
        if (isInTick()) div = 50;
        int delayInt = (int) (delay / div);
        SsomarDev.testMsg("delayInt: " + delayInt, DEBUG);

        return getCooldown() - delayInt;
    }

    public double getTimeLeft() {
        int timeLeft = getTimeLeftFlatValue();

        SsomarDev.testMsg("timeLeft: " + timeLeft, DEBUG);
        double result = timeLeft;
        SsomarDev.testMsg("pre  result: " + result / 20, DEBUG);
        if (isInTick()) result = result / 20;
        SsomarDev.testMsg("Cooldown: " + result, DEBUG);
        return result;
    }

    public Cooldown updatePlayerDisconnect() {
        if (!global && pauseWhenOffline && !isPaused) {
            this.cooldown = getTimeLeftFlatValue();
            this.time = System.currentTimeMillis();
            this.isPaused = true;
        }
        return this;
    }

    public Cooldown updatePlayerReconnect() {
        if (!global && pauseWhenOffline && isPaused) {
            this.time = System.currentTimeMillis();
            this.isPaused = false;
        }
        return this;
    }

    public Cooldown updatePausePlaceholdersConditions() {
        SsomarDev.testMsg("updatePausePlaceholdersConditions not global ?" + (!global) + " +++ not empty?" + (!pausePlaceholdersConditions.getPlaceholdersConditions().isEmpty()), DEBUG);
        if (!global && !pausePlaceholdersConditions.getPlaceholdersConditions().isEmpty()) {
            Player player = Bukkit.getPlayer(entityUUID);
            if (player != null) {
                boolean result = pausePlaceholdersConditions.verifConditions(player, new ArrayList<>());
                SsomarDev.testMsg("updatePausePlaceholdersConditions RESULT >> " + result + " PAUSED ?" + isPaused, DEBUG);
                if (!isPaused && result) {
                    SsomarDev.testMsg("updatePausePlaceholdersConditions PAUSED", DEBUG);
                    this.cooldown = getTimeLeftFlatValue();
                    this.time = System.currentTimeMillis();
                    this.isPaused = true;
                } else if (isPaused && !result) {
                    SsomarDev.testMsg("updatePausePlaceholdersConditions UNPAUSED", DEBUG);
                    this.time = System.currentTimeMillis();
                    this.isPaused = false;
                }
            }

        }
        return this;
    }

    @Override
    public String toString() {
        return "id:" + id + " >>>> entityUUID:" + entityUUID + " >>>> coolodwn:" + cooldown + " >>>> isInTick:" + isInTick + " >>>> time:" + time + " isNull ? " + isNull + " >>>> timeLeft:" + getTimeLeft() + " >>>> global ?" + global + " >>>> pauseWhenOffline:" + pauseWhenOffline + " >>>> pausePlaceholdersCdts size:" + pausePlaceholdersConditions.getPlaceholdersConditions().size() + " >>>> paused ?" + isPaused;
    }
}
