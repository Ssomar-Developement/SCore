package com.ssomar.score.sobject.sactivator.cooldowns;

import java.util.UUID;

import com.ssomar.score.SsomarDev;
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
	
	private boolean isNull;

	private static final boolean DEBUG = false;

	public Cooldown(SPlugin sPlugin, SObject sO, SActivator sAct, UUID entityUUID, int cooldown, boolean isInTick, long time, boolean global) {
		super();
		this.id = sPlugin.getShortName()+":"+sO.getId()+":"+sAct.getID();
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
		SsomarDev.testMsg("delayInt: "+delayInt, DEBUG);

		int timeLeft = getCooldown() - delayInt;

		SsomarDev.testMsg("timeLeft: "+timeLeft, DEBUG);
		double result = timeLeft;
		SsomarDev.testMsg("pre  result: "+result/200, DEBUG);
		if(isInTick()) result = result/200;
		SsomarDev.testMsg("Cooldown: "+result, DEBUG);
		return result;
	}
	
	@Override
	public String toString() {
		return id+" >>>> "+entityUUID+" >>>> "+cooldown+" >>>> "+isInTick+" >>>> "+time;
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

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
}
