package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;
import com.ssomar.score.nofalldamage.NoFallDamageManager;
import com.ssomar.score.utils.Couple;

/* CUSTOMDASH1 {x} {y} {z} */
public class ProjectileCustomDash1 extends PlayerCommandTemplate{

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

		boolean fallDamage = false;
		if(args.size() == 1) {
			try {
				fallDamage = Boolean.valueOf(args.get(0));
			}
			catch(Exception e) {

			}
		}
		
		Projectile proj2 = null;
		for (Entity e: receiver.getNearbyEntities(75, 75, 75)) {
			if(e instanceof Projectile) {
				Projectile proj = (Projectile)e;
				if(proj.getShooter() instanceof Player) {
					Player owner = (Player) proj.getShooter();
					if(owner.getUniqueId().equals(receiver.getUniqueId())) {
						proj2 = proj;
						if(p.getLocation().distance(proj2.getLocation())<2.5) return;
						break;
					}
					else continue;					
				}
				else continue;
			}
			else continue;
		}

		if(proj2 == null) return;
		
		pullEntityToLocation(receiver, proj2.getLocation());

		UUID uuid = UUID.randomUUID();

		if(!fallDamage) {
			BukkitRunnable runnable = new BukkitRunnable() {
				@Override
				public void run() {
					NoFallDamageManager.getInstance().removeNoFallDamage(p, uuid);
				}
			};
			BukkitTask task = runnable.runTaskLater(SCore.getPlugin(), 300);

			NoFallDamageManager.getInstance().addNoFallDamage(receiver, new Couple<UUID, BukkitTask>(uuid, task));
		}

	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		//String customDash1= "PROJECTILE_CUSTOMDASH1 {falldamage true or false}";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("PROJECTILE_CUSTOMDASH1");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "PROJECTILE_CUSTOMDASH1 {fallDamage}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}
}
