package com.ssomar.score.conditions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import com.ssomar.score.utils.SendMessage;

public abstract class Conditions{
	
	@Getter @Setter
	private SendMessage sm = new SendMessage();
	
	public Conditions() {
		init();
	}
	
	public abstract void init();

	public boolean verifConditions(Player p) {
		return false;
	}

}
