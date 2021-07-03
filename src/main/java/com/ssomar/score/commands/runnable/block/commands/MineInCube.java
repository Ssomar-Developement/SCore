package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;
import com.ssomar.score.usedapi.WorldGuardAPI;

/* MINEINCUBE {radius} {ActiveDrop true or false} */
public class MineInCube extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		/* Cancel a Loop of blockBreakEvent that MineInCbe can create */
		if(aInfo.isEventCallByMineInCube()) return;
		try {
			int radius = Integer.valueOf(args.get(0));
			Boolean drop = true;
			if(args.size()==2) drop = Boolean.valueOf(args.get(1));
			if(radius<10) {
				for(int y = -radius; y < Integer.valueOf(radius)+1; y++) {
					for(int x = -Integer.valueOf(radius); x < Integer.valueOf(radius)+1; x++) {
						for(int z = -Integer.valueOf(radius); z < Integer.valueOf(radius)+1; z++) {

							Location toBreakLoc = new Location(block.getWorld(), block.getX()+x, block.getY()+y, block.getZ()+z);
							Block toBreak = block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z);

							if(!toBreak.getType().equals(Material.BEDROCK) && !toBreak.getType().equals(Material.AIR)) {

								if((SCore.hasWorldGuard && new WorldGuardAPI().canBuild(p, toBreakLoc)) || !SCore.hasWorldGuard ) {
									
									BlockBreakEvent bbE = new BlockBreakEvent(toBreak, p);
									bbE.setCancelled(false);
									/* */
									bbE.setExpToDrop(-666666);
									Bukkit.getPluginManager().callEvent(bbE);

									if(!bbE.isCancelled()) {
										if(drop) toBreak.breakNaturally(p.getInventory().getItemInMainHand());
										else toBreak.setType(Material.AIR);
									}
								}
								else continue;
							}
						}
					}
				}
			}
		}catch(Exception err) {}
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("MINEINCUBE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "MINEINCUBE {radius} {ActiveDrop true or false}";
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
