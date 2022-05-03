package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class LocatedLaunch extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		/* postivite value = front , negative = back */
		double frontValue = 0;

		/* postivite value = right , negative = left */
		double rightValue = 0;

		double yValue = 0;

		double velocity = 1;
		double rotationy = 0;
		double rotationz = 0;

		try {
			frontValue = Double.parseDouble(args.get(1));
		}catch(Exception ignored) {}

		try {
			rightValue = Double.parseDouble(args.get(2));
		}catch(Exception ignored) {}

		try {
			yValue = Double.parseDouble(args.get(3));
		}catch(Exception ignored) {}

		try {
			velocity = Double.parseDouble(args.get(4));
		}catch(Exception ignored) {}

		Location eyeLoc = receiver.getEyeLocation();
		Vector front =  eyeLoc.getDirection().clone().setY(0).multiply(frontValue);
		Vector right = eyeLoc.getDirection().clone().setY(0).rotateAroundY(270 * Math.PI/180).multiply(rightValue);
		Vector calcul = front.add(right);

		Location recLoc = receiver.getLocation();
		double newX = recLoc.getX()+calcul.getX();
		double newY = recLoc.getY()+calcul.getY();
		double newZ = recLoc.getZ()+calcul.getZ();
		Location toLaunchLoc = new Location(recLoc.getWorld(), newX, newY, newZ);
		toLaunchLoc.add(0, yValue, 0);
		//SsomarDev.testMsg("x: "+newX+" y: "+newY+" z: "+newZ);

		try {
			Projectile entity = null;


			if(args.get(0).equalsIgnoreCase("ARROW")) entity = recLoc.getWorld().spawn(toLaunchLoc,Arrow.class);
			else if(args.get(0).equalsIgnoreCase("DRAGONFIREBALL")) entity = recLoc.getWorld().spawn(toLaunchLoc,DragonFireball.class);
			else if(args.get(0).equalsIgnoreCase("EGG")) entity = recLoc.getWorld().spawn(toLaunchLoc,Egg.class);
			else if(args.get(0).equalsIgnoreCase("ENDERPEARL")) entity = recLoc.getWorld().spawn(toLaunchLoc,EnderPearl.class);
			else if(args.get(0).equalsIgnoreCase("FIREBALL")) entity = recLoc.getWorld().spawn(toLaunchLoc,Fireball.class);
				//else if(args.get(0).toUpperCase().contains("FIREWORK")) recLoc.getWorld().spawn(toLaunchLoc,Firework.class);
				//else if(args.get(0).toUpperCase().contains("FISHHOOK")) entity = recLoc.getWorld().spawn(toLaunchLoc,FishHook.class);
			else if(args.get(0).equalsIgnoreCase("LARGEFIREBALL")) entity = recLoc.getWorld().spawn(toLaunchLoc,LargeFireball.class);
			else if(args.get(0).equalsIgnoreCase("LINGERINGPOTION")) entity = recLoc.getWorld().spawn(toLaunchLoc,LingeringPotion.class);
			else if(args.get(0).equalsIgnoreCase("LLAMASPIT")) entity = recLoc.getWorld().spawn(toLaunchLoc,LlamaSpit.class);
				/* No movement because shulker bullet need to have a target */
			else if(args.get(0).equalsIgnoreCase("SHULKERBULLET")){
				entity = recLoc.getWorld().spawn(toLaunchLoc,ShulkerBullet.class);
				ShulkerBullet bullet = (ShulkerBullet) entity;
			}
			else if(args.get(0).equalsIgnoreCase("SIZEDFIREBALL")) entity = recLoc.getWorld().spawn(toLaunchLoc,SizedFireball.class);
			else if(args.get(0).equalsIgnoreCase("SNOWBALL")) entity = recLoc.getWorld().spawn(toLaunchLoc,Snowball.class);
			else if(args.get(0).equalsIgnoreCase("TRIDENT")) entity = recLoc.getWorld().spawn(toLaunchLoc,Trident.class);
			else if(args.get(0).equalsIgnoreCase("WITHERSKULL")) entity = recLoc.getWorld().spawn(toLaunchLoc,WitherSkull.class);
			else if(ProjectilesManager.getInstance().containsProjectileWithID(args.get(0))) {
				SProjectiles projectile = ProjectilesManager.getInstance().getProjectileWithID(args.get(0));

				switch(projectile.getIdentifierType()) {
					case "ARROW":
						entity = recLoc.getWorld().spawn(toLaunchLoc,Arrow.class);
						break;
					case "DRAGON_FIREBALL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,DragonFireball.class);
						break;
					case "EGG":
						entity = recLoc.getWorld().spawn(toLaunchLoc,Egg.class);
						break;
					case "ENDER_PEARL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,EnderPearl.class);
						break;
					case "FIREBALL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,Fireball.class);
						break;
					case "LARGE_FIREBALL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,LargeFireball.class);
						break;
					case "LINGERING_POTION":
						entity = recLoc.getWorld().spawn(toLaunchLoc,LingeringPotion.class);
						break;
					case "SPLASH_POTION":
						entity = recLoc.getWorld().spawn(toLaunchLoc,SplashPotion.class);
						break;
					case "LLAMA_SPIT":
						entity = recLoc.getWorld().spawn(toLaunchLoc,LlamaSpit.class);
						break;
					case "SHULKER_BULLET":
						entity = recLoc.getWorld().spawn(toLaunchLoc,ShulkerBullet.class);
						break;
					case "SIZED_FIREBALL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,SizedFireball.class);
						break;
					case "SNOWBALL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,Snowball.class);
						break;
					case "TRIDENT":
						entity = recLoc.getWorld().spawn(toLaunchLoc,Trident.class);
						break;
					case "WITHER_SKULL":
						entity = recLoc.getWorld().spawn(toLaunchLoc,WitherSkull.class);
						break;
					default:
						entity = recLoc.getWorld().spawn(toLaunchLoc,Arrow.class);
						break;
				}
				projectile.executeTransformTheProjectile(entity, receiver);

			}

			//	SsomarDev.testMsg("null entity: " + (entity==null));

			if(entity != null) {
				entity.setShooter(receiver);
				Vector v = null;
				Location loc = null;
				boolean searchBlockOrEntity = true;
				Location eyeLoc2 = receiver.getEyeLocation();
				int multiply = 2;
				while(searchBlockOrEntity && multiply < 100) {
					v = eyeLoc2.getDirection().clone();
					v = v.multiply(multiply);

					loc = new Location(receiver.getWorld(), eyeLoc2.getX()+v.getX(), eyeLoc2.getY()+v.getY(), eyeLoc2.getZ()+v.getZ());
					if (!loc.getBlock().isEmpty()) {
						searchBlockOrEntity = false;
					}
					else{
						for(Entity e : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)){
							if(e.isOnGround()) {
								searchBlockOrEntity = false;
								break;
							}
						}
					}
					multiply++;
				}

				Vector last = loc.toVector().subtract(toLaunchLoc.toVector());
				last = last.normalize();
				last = last.multiply(velocity);


				if(!SCore.is1v13Less()) {
					if(entity instanceof Fireball) {
						Fireball fireball = (Fireball) entity;
						fireball.setDirection(last);
					}
					else if(entity instanceof DragonFireball) {
						DragonFireball fireball = (DragonFireball) entity;
						fireball.setDirection(last);
					}
					else {
						entity.setVelocity(last);
					}
				}

				if(SCore.hasExecutableItems && aInfo.getExecutableItem() != null) {
					ProjectileInfo pInfo = new ProjectileInfo(receiver, entity.getUniqueId(), Optional.ofNullable(aInfo.getExecutableItem()), aInfo.getSlot(), System.currentTimeMillis());
					ProjectilesHandler.getInstance().addProjectileInfo(pInfo);
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}


	}
	

	@Override
	public String verify(List<String> args) {	
		String error = "";
		String launch = "LOCATED_LAUNCH {projectileType} {frontValue positive=front , negative=back} {rightValue right=positive, negative=left} {yValue} {velocity}";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("LOCATED_LAUNCH");
		return names;
	}

	@Override
	public String getTemplate() {
		return "LOCATED_LAUNCH {projectileType} {frontValue positive=front , negative=back} {rightValue right=positive, negative=left} {yValue} {velocity}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}
}
