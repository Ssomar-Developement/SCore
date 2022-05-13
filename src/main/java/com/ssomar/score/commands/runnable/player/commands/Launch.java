package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class Launch extends PlayerCommand{

	private  Map<String, Class> projectiles;

	public Launch() {
		projectiles = new HashMap<>();
		projectiles.put("ARROW", Arrow.class);
		projectiles.put("SPECTRALARROW", SpectralArrow.class);
		projectiles.put("SPECTRAL_ARROW", SpectralArrow.class);
		projectiles.put("DRAGONFIREBALL", DragonFireball.class);
		projectiles.put("DRAGON_FIREBALL", DragonFireball.class);
		projectiles.put("FIREBALL", Fireball.class);
		projectiles.put("SMALLFIREBALL", SmallFireball.class);
		projectiles.put("LARGEFIREBALL", LargeFireball.class);
		projectiles.put("LARGE_FIREBALL", LargeFireball.class);
		projectiles.put("SIZEDFIREBALL", SizedFireball.class);
		projectiles.put("SIZED_FIREBALL", SizedFireball.class);
		projectiles.put("SNOWBALL", Snowball.class);
		projectiles.put("THROWNEXPBOTTLE", ThrownExpBottle.class);
		projectiles.put("WITHERSKULL", WitherSkull.class);
		projectiles.put("WITHER_SKULL", WitherSkull.class);
		projectiles.put("EGG", Egg.class);
		projectiles.put("ENDERPEARL", EnderPearl.class);
		projectiles.put("ENDER_PEARL", EnderPearl.class);
		projectiles.put("LINGERINGPOTION", LingeringPotion.class);
		projectiles.put("LINGERING_POTION", LingeringPotion.class);
		projectiles.put("SPLASHPOTION", SplashPotion.class);
		projectiles.put("SPLASH_POTION", SplashPotion.class);
		projectiles.put("LLAMASPIT", LlamaSpit.class);
		projectiles.put("LLAMA_SPIT", LlamaSpit.class);
		projectiles.put("SHULKERBULLET", ShulkerBullet.class);
		projectiles.put("SHULKER_BULLET", ShulkerBullet.class);
		projectiles.put("TRIDENT", Trident.class);
	}

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

				String type = args.get(0);
				if(projectiles.containsKey(type)) {
					entity = receiver.launchProjectile(projectiles.get(type));
				}
				else if(ProjectilesManager.getInstance().containsProjectileWithID(type)) {
					SProjectiles projectile = ProjectilesManager.getInstance().getProjectileWithID(type);
					entity = receiver.launchProjectile(projectiles.get(projectile.getIdentifierType()));
					projectile.executeTransformTheProjectile(entity, receiver);

				}	

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
