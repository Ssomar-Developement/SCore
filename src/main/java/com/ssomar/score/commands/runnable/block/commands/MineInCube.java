package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;
import com.ssomar.score.usedapi.WorldGuardAPI;

/* MINEINCUBE {radius} {ActiveDrop true or false} */
public class MineInCube extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			int radius = Integer.valueOf(args.get(0));
			Boolean drop = true;
			if(args.size()==2) drop= Boolean.valueOf(args.get(1));
			if(radius<10) {
				for(int y= -radius; y<Integer.valueOf(radius)+1; y++) {
					for(int x= -Integer.valueOf(radius); x<Integer.valueOf(radius)+1; x++) {
						for(int z= -Integer.valueOf(radius); z<Integer.valueOf(radius)+1; z++) {
							if(block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z).getType()!=Material.BEDROCK) {

								if(SCore.hasWorldGuard) {
									if(new WorldGuardAPI().canBuild(p, new Location(block.getWorld(), block.getX()+x, block.getY()+y, block.getZ()+z))) {
										if(drop) block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z).breakNaturally(p.getInventory().getItemInMainHand());
										else block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z).setType(Material.AIR);
									}
									else continue;
								}
								else {
									if(drop) block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z).breakNaturally(p.getInventory().getItemInMainHand());
									else block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z).setType(Material.AIR);
								}
							}
						}
					}
				}
			}
		}catch(Exception e) {}
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

}
