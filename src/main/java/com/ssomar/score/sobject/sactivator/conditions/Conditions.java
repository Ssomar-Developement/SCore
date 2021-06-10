package com.ssomar.score.sobject.sactivator.conditions;

import org.bukkit.entity.Player;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;

public abstract class Conditions {
	
	private SendMessage sm = new SendMessage();
	
	public static StringConverter sc = new StringConverter();	

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
