package com.ssomar.score.sobject.sactivator.cooldowns;

import java.util.UUID;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public class Cooldown {
	
	private String id;
	
	private UUID entityUUID;
	
	private int cooldown;
	
	private boolean isInTick;
	
	private long time;
	
	/* Affect all players or not */
	private boolean global;

	public Cooldown(SPlugin sPlugin, SObject sO, SActivator sAct, UUID entityUUID, int cooldown, boolean isInTick, long time, boolean global) {
		super();
		this.id = sPlugin.getShortName()+":"+sO.getID()+":"+sAct.getID();
		this.entityUUID = entityUUID;
		this.cooldown = cooldown;
		this.isInTick = isInTick;
		this.time = time;
		this.global = global;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UUID getEntityUUID() {
		return entityUUID;
	}

	public void setEntityUUID(UUID entityUUID) {
		this.entityUUID = entityUUID;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public boolean isInTick() {
		return isInTick;
	}

	public void setInTick(boolean isInTick) {
		this.isInTick = isInTick;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}
}
