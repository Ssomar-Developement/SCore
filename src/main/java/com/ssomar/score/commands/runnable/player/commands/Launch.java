package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class Launch extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		double rotationy = 0;
		double rotationz = 0;

		if(args.size() == 0) {
			receiver.launchProjectile(Arrow.class);
		}
		else {
			try {
				rotationy = Double.parseDouble(args.get(1));
				rotationy = rotationy * Math.PI/180;
			}catch(Exception ignored) {}

			try {
				rotationz = Double.parseDouble(args.get(2));
				rotationz = rotationz * Math.PI/180;
			}catch(Exception ignored) {}
			try {
				Entity entity = null;

				receiver.setMetadata("cancelProjectileEvent", new FixedMetadataValue(SCore.plugin, 7772));
				
				if(args.get(0).equalsIgnoreCase("ARROW")) entity = receiver.launchProjectile(Arrow.class);
				else if(args.get(0).equalsIgnoreCase("SPECTRALARROW")) entity = receiver.launchProjectile(SpectralArrow.class);
				else if(args.get(0).equalsIgnoreCase("DRAGONFIREBALL")) entity = receiver.launchProjectile(DragonFireball.class);
				else if(args.get(0).equalsIgnoreCase("EGG")) entity = receiver.launchProjectile(Egg.class);
				else if(args.get(0).equalsIgnoreCase("ENDERPEARL")) entity = receiver.launchProjectile(EnderPearl.class);
				else if(args.get(0).equalsIgnoreCase("FIREBALL")) entity = receiver.launchProjectile(Fireball.class);
				//else if(args.get(0).toUpperCase().contains("FIREWORK")) receiver.launchProjectile(Firework.class);
				//else if(args.get(0).toUpperCase().contains("FISHHOOK")) entity = receiver.launchProjectile(FishHook.class);
				else if(args.get(0).equalsIgnoreCase("LARGEFIREBALL")) entity = receiver.launchProjectile(LargeFireball.class);
				else if(args.get(0).equalsIgnoreCase("LINGERINGPOTION")) entity = receiver.launchProjectile(LingeringPotion.class);
				else if(args.get(0).equalsIgnoreCase("LLAMASPIT")) entity = receiver.launchProjectile(LlamaSpit.class);
				/* No movement because shulker bullet need to have a target */
				else if(args.get(0).equalsIgnoreCase("SHULKERBULLET")){
					entity = receiver.launchProjectile(ShulkerBullet.class);
					ShulkerBullet bullet = (ShulkerBullet) entity;
				}
				else if(args.get(0).equalsIgnoreCase("SIZEDFIREBALL")) entity = receiver.launchProjectile(SizedFireball.class);
				else if(args.get(0).equalsIgnoreCase("SNOWBALL")) entity = receiver.launchProjectile(Snowball.class);
				else if(args.get(0).equalsIgnoreCase("TRIDENT")) entity = receiver.launchProjectile(Trident.class);
				else if(args.get(0).equalsIgnoreCase("WITHERSKULL")) entity = receiver.launchProjectile(WitherSkull.class);
				else if(ProjectilesManager.getInstance().containsProjectileWithID(args.get(0))) {
					SProjectiles projectile = ProjectilesManager.getInstance().getProjectileWithID(args.get(0));

					switch(projectile.getIdentifierType()) {
					case "ARROW":
						entity = receiver.launchProjectile(Arrow.class);
						break;
					case "SPECTRALARROW":
							entity = receiver.launchProjectile(SpectralArrow.class);
							break;
					case "DRAGON_FIREBALL":
						entity = receiver.launchProjectile(DragonFireball.class);
						break;
					case "EGG":
						entity = receiver.launchProjectile(Egg.class);
						break;
					case "ENDER_PEARL":
						entity = receiver.launchProjectile(EnderPearl.class);
						break;
					case "FIREBALL":
						entity = receiver.launchProjectile(Fireball.class);
						break;
					case "LARGE_FIREBALL":
						entity = receiver.launchProjectile(LargeFireball.class);
						break;
					case "LINGERING_POTION":
						entity = receiver.launchProjectile(LingeringPotion.class);
						break;
					case "SPLASH_POTION":
						entity = receiver.launchProjectile(SplashPotion.class);
						break;
					case "LLAMA_SPIT":
						entity = receiver.launchProjectile(LlamaSpit.class);
						break;
					case "SHULKER_BULLET":
						entity = receiver.launchProjectile(ShulkerBullet.class);
						break;
					case "SIZED_FIREBALL":
						entity = receiver.launchProjectile(SizedFireball.class);
						break;
					case "SNOWBALL":
						entity = receiver.launchProjectile(Snowball.class);
						break;
					case "TRIDENT":
						entity = receiver.launchProjectile(Trident.class);
						break;
					case "WITHER_SKULL":
						entity = receiver.launchProjectile(WitherSkull.class);
						break;
					default:
						entity = receiver.launchProjectile(Arrow.class);
						break;
					}
					projectile.executeTransformTheProjectile(entity, receiver);

				}	
				
			//	SsomarDev.testMsg("null entity: " + (entity==null));

				if(entity != null) {
					if(!SCore.is1v13Less()) {
						Vector v;
						if(entity instanceof Fireball) {
							Fireball fireball = (Fireball) entity;
							v = fireball.getDirection();
							v.rotateAroundY(rotationy);
							v.rotateAroundZ(rotationz);
							fireball.setDirection(v);
						}
						else if(entity instanceof DragonFireball) {
							DragonFireball fireball = (DragonFireball) entity;
							v = fireball.getDirection();
							v.rotateAroundY(rotationy);
							v.rotateAroundZ(rotationz);
							fireball.setDirection(v);
						}
						else {
							v = entity.getVelocity();
							v.rotateAroundY(rotationy);
							v.rotateAroundZ(rotationz);
							entity.setVelocity(v);
						}
						//SsomarDev.testMsg("rotation: "+ rotation);
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
	}
	

	@Override
	public String verify(List<String> args) {	
		String error = "";
		String launch = "LAUNCH {projectileType} [angle rotation y] [angle rotation z]";
		if(args.size()<1) error = notEnoughArgs+launch;

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("LAUNCH");
		return names;
	}

	@Override
	public String getTemplate() {
		return "LAUNCH {projectileType} [angle rotation y] [angle rotation z]";
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
