package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.safeplace.SafePlace;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/* BURN {timeinsecs} */
public class CropsGrowthBoost extends PlayerCommand{

	private static CropsGrowthBoost instance;


	public CropsGrowthBoost() {	}

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			int radius = Integer.valueOf(args.get(0));
			int delay = Integer.valueOf(args.get(1));
			int duration = Integer.valueOf(args.get(2));
			int chance = Integer.valueOf(args.get(3));
			if(chance > 100) chance = 100;
			if(chance < 0) chance = 0;
			final int finalChance = chance;

			final int[] i = {0};

			BukkitRunnable runnable3 = new BukkitRunnable() {
				@Override
				public void run() {
					if(i[0] >= duration) {
						cancel();
					}
					else {
						grownAgeableBlocks(receiver.getLocation(), radius, finalChance, receiver);
						i[0] = i[0] + delay;
					}
				}
			};
			runnable3.runTaskTimer(SCore.plugin, 0, delay);

		}catch(Exception ignored) {
			ignored.printStackTrace();
		}
	}

	public void grownAgeableBlocks(Location start, int radius, int finalChance, Player receiver){
		for(double x = start.getX() - radius; x <= start.getX() + radius; x++) {
			for (double y = start.getY() - radius; y <= start.getY() + radius; y++) {
				for (double z = start.getZ() - radius; z <= start.getZ() + radius; z++) {
					Location loc = new Location(start.getWorld(), x, y, z);
					Block block = loc.getBlock();
					BlockData data = block.getBlockData();
					if (!(data instanceof Ageable)) continue;

					int random = new Random().nextInt(100);
					//SsomarDev.testMsg(random +" " + finalChance);
					if (random <= finalChance) {
						if (receiver != null && (receiver.isOp() || SafePlace.verifSafePlace(receiver.getUniqueId(), block))) {
							Ageable ageable = (Ageable) data;
							if (ageable.getAge() < ageable.getMaximumAge()) {
								ageable.setAge(ageable.getAge() + 1);
								block.setBlockData(ageable);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String message = "CROPS_GROWTH_BOOST {radius} {delay between two growths in ticks} {total duration in ticks} {chance 0-100}";
		if(args.size() > 4) error = tooManyArgs+message;
		else if(args.size() < 4) error = notEnoughArgs+message;
		else if(!args.get(0).contains("%")){
			try {
				Integer.valueOf(args.get(0));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(0) + " for command: " + message;
			}
		}
		else if(!args.get(1).contains("%")){
			try {
				Integer.valueOf(args.get(1));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(1) + " for command: " + message;
			}
		}
		else if(!args.get(2).contains("%")){
			try {
				Integer.valueOf(args.get(1));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(1) + " for command: " + message;
			}
		}

		else if(!args.get(3).contains("%")){
			try {
				Integer.valueOf(args.get(1));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(1) + " for command: " + message;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("CROPS_GROWTH_BOOST");
		return names;
	}

	@Override
	public String getTemplate() {
		return "CROPS_GROWTH_BOOST {radius} {delay between two growths in ticks} {total duration in ticks} {chance 0-100}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

	public static CropsGrowthBoost getInstance() {
		if(instance == null) instance = new CropsGrowthBoost();
		return instance;
	}
}
