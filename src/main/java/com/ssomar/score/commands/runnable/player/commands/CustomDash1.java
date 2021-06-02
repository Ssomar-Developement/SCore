package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;
import com.ssomar.score.nofalldamage.NoFallDamageManager;
import com.ssomar.score.utils.Couple;

/* CUSTOMDASH1 {x} {y} {z} */
public class CustomDash1 extends PlayerCommandTemplate{

	private static void pullEntityToLocation(Entity e, Location loc) {
		Location entityLoc = e.getLocation();
		entityLoc.setY(entityLoc.getY() + 0.5D);
		e.teleport(entityLoc);
		double g = -0.08D;
		double d = loc.distance(entityLoc);
		double t = d;
		double v_x = (1.0D + 0.07D * t) * (loc.getX() - entityLoc.getX()) / t;
		double v_y = (1.0D + 0.03D * t) * (loc.getY() - entityLoc.getY()) / t - 0.5D * g * t;
		double v_z = (1.0D + 0.07D * t) * (loc.getZ() - entityLoc.getZ()) / t;
		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		double x;
		double y;
		double z;

		if(args.size()==3) {
			try {
				x = Double.valueOf(args.get(0));
				y = Double.valueOf(args.get(1));
				z = Double.valueOf(args.get(2));
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}

			Location loc = new Location(receiver.getWorld(), x, y, z);
			pullEntityToLocation(receiver, loc);
			
			
			UUID uuid = UUID.randomUUID();

			BukkitRunnable runnable = new BukkitRunnable() {
				@Override
				public void run() {
					NoFallDamageManager.getInstance().removeNoFallDamage(p, uuid);
				}
			};
			BukkitTask task = runnable.runTaskLater(SCore.getPlugin(), 300);
			
			NoFallDamageManager.getInstance().addNoFallDamage(receiver, new Couple<UUID, BukkitTask>(uuid, task));
		}
		else return;
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String customDash1= "CUSTOMDASH1 {x} {y} {z}";

		if(args.size()>3) {
			error = tooManyArgs+customDash1;
			return error;
		}
		else if(args.size()<3) {
			error = notEnoughArgs+customDash1;
		}
		else {
			if(!args.get(0).contains("%")) {
				try {
					Double.valueOf(args.get(0));
				}catch(Exception e) {
					error = invalidCoordinate+args.get(0)+" for command: "+customDash1;
					return error;
				}
			}
			if(!args.get(1).contains("%")) {
				try {
					Double.valueOf(args.get(1));
				}catch(Exception e) {
					error = invalidCoordinate+args.get(1)+" for command: "+customDash1;
					return error;
				}
			}
			if(!args.get(2).contains("%")) {
				try {
					Double.valueOf(args.get(2));
				}catch(Exception e) {
					error = invalidCoordinate+args.get(2)+" for command: "+customDash1;
					return error;
				}
			}
		}

		return error;
	}
}
