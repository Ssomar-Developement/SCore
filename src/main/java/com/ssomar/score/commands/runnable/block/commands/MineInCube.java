package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.sobject.sactivator.DetailedBlocks;
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

								UUID pUUID = null;
								if(p != null) pUUID = p.getUniqueId();
								SafeBreak.breakBlockWithEvent(toBreak, pUUID, aInfo.getSlot(), drop, createBBEvent, true);
							}
						}
					}
				}
			}
		}catch(Exception err) {
			err.printStackTrace();
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
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

}
