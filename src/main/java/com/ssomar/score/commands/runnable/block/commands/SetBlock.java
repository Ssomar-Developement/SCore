package com.ssomar.score.commands.runnable.block.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;
import com.ssomar.score.usedapi.WorldGuardAPI;

public class SetBlock extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			String mat = args.get(0).toUpperCase();
			if(Material.matchMaterial(mat)!=null) {
				if(SCore.isHasWorldGuard()) {
					if(new WorldGuardAPI().canBuild(p, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
						block.setType(Material.valueOf(mat));
					}
				}	
				else {
					block.setType(Material.valueOf(mat));
				}
			}else {
				RunConsoleCommand.runConsoleCommand("execute at "+p.getName()+" run setblock "+block.getX()+" "+block.getY()+" "+block.getZ()+" "+args.get(0).toLowerCase()+" replace", silenceOutput);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		
		String setblock = "SETBLOCK {material}";
		if(args.size()<1) error = notEnoughArgs+setblock;
		else if(args.size()>1)error= tooManyArgs+setblock;
		
		return error;
	}

}
