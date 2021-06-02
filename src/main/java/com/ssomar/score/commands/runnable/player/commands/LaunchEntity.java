package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* LAUNCHENTITY {entityType} */
public class LaunchEntity extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		Location loc = receiver.getEyeLocation();
		//loc.setY(loc.getY()-1);
		EntityType entityType = EntityType.PIG;
		double speed = 1;

		if(args.size()>=1) {
			try {
				entityType = EntityType.valueOf(args.get(0).toUpperCase());
			}catch(Exception e) {

			}
		}
		if(args.size()==2) {
			try {
				speed = Double.valueOf(args.get(1));
			}catch(Exception e) {}
		}

		Entity entity = receiver.getWorld().spawnEntity(loc, entityType);
		Vector v = receiver.getEyeLocation().getDirection();
		v.multiply(speed);
		entity.setVelocity(v);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String launch= "LAUNCHENTITY {entityType}";
		if(args.size()<1) error = notEnoughArgs+launch;
		else if(args.size()>2) error= tooManyArgs+launch;

		return error;
	}
}
