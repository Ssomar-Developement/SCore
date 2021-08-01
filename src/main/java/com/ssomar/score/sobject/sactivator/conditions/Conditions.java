package com.ssomar.score.sobject.sactivator.conditions;

import java.io.Serializable;

import org.bukkit.entity.Player;

import com.ssomar.score.utils.SendMessage;

public abstract class Conditions implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SendMessage sm = new SendMessage();
	
	public Conditions() {
		init();
	}
	
	public abstract void init();

	public boolean verifConditions(Player p) {
		return false;
	}

	public SendMessage getSm() {
		return sm;
	}
	public void setSm(SendMessage sm) {
		this.sm = sm;
	}
}
