package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;
import com.ssomar.score.usedapi.WorldGuardAPI;

/* REPLACEBLOCK {material} */
public class ReplaceBlock extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			Block block = receiver.getTargetBlock(null, 5);

			if(block.getType()!=Material.AIR) {
				if(Material.matchMaterial(args.get(0).toUpperCase())!=null) {
					if(SCore.hasWorldGuard) {
						if(new WorldGuardAPI().canBuild(receiver, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
							block.setType(Material.valueOf(args.get(0)));
						}
					}
					else {
						block.setType(Material.valueOf(args.get(0)));
					}
				}
				else {
					RunConsoleCommand.runConsoleCommand("execute at "+receiver.getName()+" run setblock "+block.getX()+" "+block.getY()+" "+block.getZ()+" "+args.get(0).toLowerCase(), silenceOutput);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String replaceblock= "REPLACEBLOCK {material}";
		if(args.size()<1) error = notEnoughArgs+replaceblock;
		else if(!(args.size()==1 || args.size()==2)) error= tooManyArgs+replaceblock;

		return error;
	}
}
