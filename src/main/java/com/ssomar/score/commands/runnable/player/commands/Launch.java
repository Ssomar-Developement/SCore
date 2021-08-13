package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.SizedFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.Trident;
import org.bukkit.entity.WitherSkull;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesEvt;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.executableitems.projectiles.CustomProjectileTemplate;
import com.ssomar.executableitems.projectiles.ProjectilesManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class Launch extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		double rotation = 0;

		if(args.size() == 0) {
			receiver.launchProjectile(Arrow.class);
		}
		else {
			try {
				rotation = Double.valueOf(args.get(1));
				rotation = rotation * Math.PI/180;
			}catch(Exception e) {}
			try {
				Entity entity = null;

				receiver.setMetadata("cancelProjectileEvent", new FixedMetadataValue(SCore.plugin, 7772));
				
				if(args.get(0).equalsIgnoreCase("ARROW")) entity = receiver.launchProjectile(Arrow.class);
				else if(args.get(0).equalsIgnoreCase("DRAGONFIREBALL")) entity = receiver.launchProjectile(DragonFireball.class);
				else if(args.get(0).equalsIgnoreCase("EGG")) entity = receiver.launchProjectile(Egg.class);
				else if(args.get(0).equalsIgnoreCase("ENDERPEARL")) entity = receiver.launchProjectile(EnderPearl.class);
				else if(args.get(0).equalsIgnoreCase("FIREBALL")) entity = receiver.launchProjectile(Fireball.class);
				//else if(args.get(0).toUpperCase().contains("FIREWORK")) receiver.launchProjectile(Firework.class);
				//else if(args.get(0).toUpperCase().contains("FISHHOOK")) entity = receiver.launchProjectile(FishHook.class);
				else if(args.get(0).equalsIgnoreCase("LARGEFIREBALL")) entity = receiver.launchProjectile(LargeFireball.class);
				else if(args.get(0).equalsIgnoreCase("LINGERINGPOTION")) entity = receiver.launchProjectile(LingeringPotion.class);
				else if(args.get(0).equalsIgnoreCase("LLAMASPIT")) entity = receiver.launchProjectile(LlamaSpit.class);
				else if(args.get(0).equalsIgnoreCase("SHULKERBULLET")) entity = receiver.launchProjectile(ShulkerBullet.class);
				else if(args.get(0).equalsIgnoreCase("SIZEDFIREBALL")) entity = receiver.launchProjectile(SizedFireball.class);
				else if(args.get(0).equalsIgnoreCase("SNOWBALL")) entity = receiver.launchProjectile(Snowball.class);
				else if(args.get(0).equalsIgnoreCase("TRIDENT")) entity = receiver.launchProjectile(Trident.class);
				else if(args.get(0).equalsIgnoreCase("WITHERSKULL")) entity = receiver.launchProjectile(WitherSkull.class);
				else if(ProjectilesManager.getInstance().containsProjectileWithID(args.get(0))) {
					CustomProjectileTemplate projectile = ProjectilesManager.getInstance().getProjectileWithID(args.get(0));

					switch(projectile.getIdentifierType()) {
					case "ARROW":
						entity = receiver.launchProjectile(Arrow.class);
						break;
					case "DRAGONFIREBALL":
						entity = receiver.launchProjectile(DragonFireball.class);
						break;
					case "EGG":
						entity = receiver.launchProjectile(Egg.class);
						break;
					case "ENDERPEARL":
						entity = receiver.launchProjectile(EnderPearl.class);
						break;
					case "FIREBALL":
						entity = receiver.launchProjectile(Fireball.class);
						break;
					case "LARGEFIREBALL":
						entity = receiver.launchProjectile(LargeFireball.class);
						break;
					case "LINGERINGPOTION":
						entity = receiver.launchProjectile(LingeringPotion.class);
						break;
					case "SPLASHPOTION":
						entity = receiver.launchProjectile(SplashPotion.class);
						break;
					case "LLAMASPIT":
						entity = receiver.launchProjectile(LlamaSpit.class);
						break;
					case "SHULKERBULLET":
						entity = receiver.launchProjectile(ShulkerBullet.class);
						break;
					case "SIZEDFIREBALL":
						entity = receiver.launchProjectile(SizedFireball.class);
						break;
					case "SNOWBALL":
						entity = receiver.launchProjectile(Snowball.class);
						break;
					case "TRIDENT":
						entity = receiver.launchProjectile(Trident.class);
						break;
					case "WITHERSKULL":
						entity = receiver.launchProjectile(WitherSkull.class);
						break;
					default:
						entity = receiver.launchProjectile(Arrow.class);
						break;
					}
					projectile.transformTheProjectile(entity, receiver);

				}	

				if(entity != null) {
					if(!SCore.is1v12() && !SCore.is1v13()) {
						Vector v = entity.getVelocity();
						v.rotateAroundY(rotation);
						entity.setVelocity(v);
					}

					if(SCore.hasExecutableItems && aInfo.getItemID() != null) {
						ProjectileInfo pInfo = new ProjectileInfo(receiver, entity.getUniqueId(), null, ItemManager.getInstance().getLoadedItemWithID(aInfo.getItemID()), aInfo.getSlot());
						ProjectilesEvt.getInstance().addProjectileInfo(pInfo);
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
		String launch = "LAUNCH {projectileType} [angle rotation y]";
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
		// TODO Auto-generated method stub
		return "LAUNCH {projectileType} [angle rotation y]";
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
