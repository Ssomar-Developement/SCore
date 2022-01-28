package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class Launch extends BlockCommand {

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {

		BlockData data;
		Directional directional;
		double speed = 2;
		if((data = block.getBlockData()) == null || !(data instanceof Directional)) return;

		directional = (Directional) data;


		if( args.size() == 0 ){
			launchProjectile(block, directional, Arrow.class, speed);
		}
		else if(args.size() == 2) {
			try{
				speed = Double.parseDouble(args.get(1));
			}
			catch (NumberFormatException e){}
		}
		else {
			try {
				Entity entity = null;
				if(args.get(0).equalsIgnoreCase("ARROW")) entity = launchProjectile(block, directional, Arrow.class, speed);
				else if(args.get(0).equalsIgnoreCase("SPECTRALARROW")) entity = launchProjectile(block, directional, SpectralArrow.class, speed);
				else if(args.get(0).equalsIgnoreCase("DRAGONFIREBALL")) entity = launchProjectile(block, directional, DragonFireball.class, speed);
				else if(args.get(0).equalsIgnoreCase("EGG")) entity = launchProjectile(block, directional, Egg.class, speed);
				else if(args.get(0).equalsIgnoreCase("ENDERPEARL")) entity = launchProjectile(block, directional, EnderPearl.class, speed);
				else if(args.get(0).equalsIgnoreCase("FIREBALL")) entity = launchProjectile(block, directional, Fireball.class, speed);
				//else if(args.get(0).toUpperCase().contains("FIREWORK")) receiver.launchProjectile(Firework.class);
				//else if(args.get(0).toUpperCase().contains("FISHHOOK")) entity = receiver.launchProjectile(FishHook.class);
				else if(args.get(0).equalsIgnoreCase("LARGEFIREBALL")) entity = launchProjectile(block, directional, LargeFireball.class, speed);
				else if(args.get(0).equalsIgnoreCase("LINGERINGPOTION")) entity = launchProjectile(block, directional, LingeringPotion.class, speed);
				else if(args.get(0).equalsIgnoreCase("LLAMASPIT")) entity = launchProjectile(block, directional, LlamaSpit.class, speed);
				/* No movement because shulker bullet need to have a target */
				else if(args.get(0).equalsIgnoreCase("SHULKERBULLET"))entity = launchProjectile(block, directional, ShulkerBullet.class, speed);
				else if(args.get(0).equalsIgnoreCase("SIZEDFIREBALL")) entity = launchProjectile(block, directional, SizedFireball.class, speed);
				else if(args.get(0).equalsIgnoreCase("SNOWBALL")) entity = launchProjectile(block, directional, Snowball.class, speed);
				else if(args.get(0).equalsIgnoreCase("TRIDENT")) entity = launchProjectile(block, directional, Trident.class, speed);
				else if(args.get(0).equalsIgnoreCase("WITHERSKULL")) entity = launchProjectile(block, directional, WitherSkull.class, speed);
				else if(ProjectilesManager.getInstance().containsProjectileWithID(args.get(0))) {
					SProjectiles projectile = ProjectilesManager.getInstance().getProjectileWithID(args.get(0));

					switch(projectile.getIdentifierType()) {
					case "ARROW":
						entity = launchProjectile(block, directional, Arrow.class, speed);
						break;
					case "SPECTRALARROW":
							entity = launchProjectile(block, directional, SpectralArrow.class, speed);
							break;
					case "DRAGON_FIREBALL":
						entity = launchProjectile(block, directional, DragonFireball.class, speed);
						break;
					case "EGG":
						entity = launchProjectile(block, directional, Egg.class, speed);
						break;
					case "ENDER_PEARL":
						entity = launchProjectile(block, directional, EnderPearl.class, speed);
						break;
					case "FIREBALL":
						entity = launchProjectile(block, directional, Fireball.class, speed);
						break;
					case "LARGE_FIREBALL":
						entity = launchProjectile(block, directional, LargeFireball.class, speed);
						break;
					case "LINGERING_POTION":
						entity = launchProjectile(block, directional, LingeringPotion.class, speed);
						break;
					case "SPLASH_POTION":
						entity = launchProjectile(block, directional, SplashPotion.class, speed);
						break;
					case "LLAMA_SPIT":
						entity = launchProjectile(block, directional, LlamaSpit.class, speed);
						break;
					case "SHULKER_BULLET":
						entity = launchProjectile(block, directional, ShulkerBullet.class, speed);
						break;
					case "SIZED_FIREBALL":
						entity = launchProjectile(block, directional, SizedFireball.class, speed);
						break;
					case "SNOWBALL":
						entity = launchProjectile(block, directional, Snowball.class, speed);
						break;
					case "TRIDENT":
						entity = launchProjectile(block, directional, Trident.class, speed);
						break;
					case "WITHER_SKULL":
						entity = launchProjectile(block, directional, WitherSkull.class, speed);
						break;
					default:
						entity = launchProjectile(block, directional, Arrow.class, speed);
						break;
					}
					projectile.executeTransformTheProjectile(entity, p);

				}	
				
			//	SsomarDev.testMsg("null entity: " + (entity==null));

				if(entity != null) {
					if(SCore.hasExecutableItems && aInfo.getItemID() != null) {
						ProjectileInfo pInfo = new ProjectileInfo(p, entity.getUniqueId(), new ExecutableItem(ItemManager.getInstance().getLoadedItemWithID(aInfo.getItemID())), aInfo.getSlot(), System.currentTimeMillis());
						ProjectilesHandler.getInstance().addProjectileInfo(pInfo);
					}
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public <T extends Projectile> Entity launchProjectile(Block block, Directional directional, Class<? extends T> projectile, double speed){
		Vector direction = directional.getFacing().getDirection();
		Location bLoc = block.getLocation().add(0.5,0.5,0.5);

		Entity projLaunched = block.getWorld().spawn(bLoc.add(direction.clone().multiply(0.6)), projectile);
		projLaunched.setVelocity(direction.multiply(speed));
		return projLaunched;
	}
	

	@Override
	public String verify(List<String> args) {	
		String error = "";
		String launch = "LAUNCH {projectileType} [speed]";
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
		return "LAUNCH {projectileType} [speed]";
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
