package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;

/* SETBLOCK {material} */
public class SetBlock extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			Set<Material> set = new TreeSet<>();
			set.add(Material.WATER);
			set.add(Material.LAVA);
			set.add(Material.AIR);

			Block block = receiver.getTargetBlock(set, 5);

			if(block.getType() != Material.AIR) {

				block = block.getRelative(BlockFace.valueOf(args.get(0)));
				
				if(Material.matchMaterial(args.get(1).toUpperCase()) != null) {
					if(SCore.hasWorldGuard) {
						if(new WorldGuardAPI().canBuild(receiver, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
							block.setType(Material.valueOf(args.get(1)));
						}
					}
					else {
						block.setType(Material.valueOf(args.get(1)));
						
					}
				}
				else {
					RunConsoleCommand.runConsoleCommand("execute at "+receiver.getName()+" run setblock "+block.getX()+" "+block.getY()+" "+block.getZ()+" "+args.get(0).toLowerCase(), aInfo.isSilenceOutput());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String setblock= "SETBLOCK {material}";
		if(args.size()<1) error = notEnoughArgs+setblock;
		else if(args.size()!=1) error= tooManyArgs+setblock;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SETBLOCK");
		return names;
	}

	@Override
	public String getTemplate() {
		return "SETBLOCK {material}";
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
