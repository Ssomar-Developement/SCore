package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

/* DAMAGE {amount} */
public class Damage extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			int amount= Integer.valueOf(args.get(0));
			if(amount>0 && !entity.isDead() && entity instanceof LivingEntity) {
				LivingEntity e = (LivingEntity) entity;
				e.damage(Integer.valueOf(amount));
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String damage= "DAMAGE {amount}";
		if(args.size()<1) error = notEnoughArgs+damage;
		else if(args.size()==1) {	
			try {
				Integer.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidQuantity+args.get(0)+" for command: "+damage;
			}
		}
		else error= tooManyArgs+damage;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DAMAGE {amount}";
	}

}
