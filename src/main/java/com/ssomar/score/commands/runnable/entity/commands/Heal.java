package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

/* HEAL */
public class Heal extends EntityCommandTemplate{

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			if(args.size()==1) {
				int amount = Integer.valueOf(args.get(0));
				if(amount>0 && !entity.isDead() && entity instanceof LivingEntity) {
					LivingEntity e = (LivingEntity) entity;
					if(!SCore.is1v12()) {
						if(e.getHealth()+amount>= e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
							e.setHealth(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						}
						else e.setHealth(e.getHealth()+amount);
					}
					else {
						if(e.getHealth()+amount>= e.getMaxHealth()) {
							e.setHealth(e.getMaxHealth());
						}
						else e.setHealth(e.getHealth()+amount);
					}
				}
			}
			else {
				if (!entity.isDead()) {
					LivingEntity e = (LivingEntity) entity;
					if (!SCore.is1v12()) {
						e.setHealth(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					} else {
						e.setHealth(e.getMaxHealth());
					} 
				} 
			}
		}catch(Exception err) {}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String heal="HEAL {amount}";
		if(args.size()>1) error= tooManyArgs+heal;
		else if(args.size()==1) {
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidQuantity+args.get(0)+" for command: "+heal;
			}
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("HEAL");
		return names;
	}

	@Override
	public String getTemplate() {
		return "HEAL {amount}";
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
