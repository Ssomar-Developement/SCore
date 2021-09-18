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
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.sobject.sactivator.DetailedBlocks;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.jetbrains.annotations.NotNull;

/* MINEINCUBE {radius} {ActiveDrop true or false} */
public class MineInCube extends BlockCommand{

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
		/* Cancel a Loop of blockBreakEvent that MineInCbe can create */
		if(aInfo.isEventCallByMineInCube()) return;

		try {
			int radius = Integer.parseInt(args.get(0));
			boolean drop = true;
			if(args.size() >= 2) drop = Boolean.parseBoolean(args.get(1));
			
			boolean createBBEvent = true;
			if(args.size() >= 3) createBBEvent = Boolean.parseBoolean(args.get(2));

			List<Material> blackList = new ArrayList<>();
			blackList.add(Material.BEDROCK);
			blackList.add(Material.AIR);

			if(radius < 10) {
				for(int y = -radius; y < radius +1; y++) {
					for(int x = -radius; x < radius +1; x++) {
						for(int z = -radius; z < radius +1; z++) {

							Location toBreakLoc = new Location(block.getWorld(), block.getX()+x, block.getY()+y, block.getZ()+z);
							Block toBreak = block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z);

							DetailedBlocks whiteList;
							if((whiteList = aInfo.getDetailedBlocks()) != null) {
								if(!whiteList.isEmpty()) {
									String statesStr = "";
									if(!SCore.is1v12()) statesStr = toBreak.getBlockData().getAsString(true);
									if(!whiteList.verification(toBreak.getType(), statesStr)) continue;
								}
							}

							if(!blackList.contains(toBreak.getType())) {

								if (!SCore.hasWorldGuard) {
									if (createBBEvent) breakBlockWithEvent(toBreak, p, drop);
									else {
										if (drop) toBreak.breakNaturally(p.getInventory().getItemInMainHand());
										else toBreak.setType(Material.AIR);
									}
								} else if (new WorldGuardAPI().canBuild(p, toBreakLoc)) {
									if (createBBEvent) breakBlockWithEvent(toBreak, p, drop);
									else {
										if (drop) toBreak.breakNaturally(p.getInventory().getItemInMainHand());
										else toBreak.setType(Material.AIR);
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception err) {
			err.printStackTrace();
		}
	}

	public static void breakBlockWithEvent(final Block block, final Player player, final boolean drop) {

		BlockBreakEvent bbE = new BlockBreakEvent(block, player);
		bbE.setCancelled(false);
		/* */
		bbE.setExpToDrop(-666666);
		Bukkit.getPluginManager().callEvent(bbE);

		if(!bbE.isCancelled()) {
			if(drop) block.breakNaturally(player.getInventory().getItemInMainHand());
			else block.setType(Material.AIR);
		}

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
		return "MINEINCUBE {radius} {ActiveDrop true or false} {create blockBreakEvent true or false}";
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
