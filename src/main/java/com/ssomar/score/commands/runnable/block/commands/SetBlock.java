package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetBlock extends BlockCommand{

	@Override
	public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
		try {
			String mat = args.get(0).toUpperCase();
			if(Material.matchMaterial(mat) != null) {
				if(SCore.hasWorldGuard && p != null) {
					if(new WorldGuardAPI().canBuild(p, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
						block.setType(Material.valueOf(mat));
					}
				}	
				else {
					block.setType(Material.valueOf(mat));
				}
			}else {
				World w = block.getWorld();
				List<Entity> entities = w.getEntities();

				if(entities.size() > 0)
				RunConsoleCommand.runConsoleCommand("execute at "+entities.get(0)+" run setblock "+block.getX()+" "+block.getY()+" "+block.getZ()+" "+args.get(0).toLowerCase()+" replace", aInfo.isSilenceOutput());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}



	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		/* Delete verification to not interfer with the vanilla setblock command */
//		String setblock = "SETBLOCK {material}";
//		if(args.size()<1) error = notEnoughArgs+setblock;
//		else if(args.size()>1)error = tooManyArgs+setblock;
		
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
