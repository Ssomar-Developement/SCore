package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ssomar.score.usedapi.MultiverseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.executableblocks.blocks.ExecutableBlockManager;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.jetbrains.annotations.NotNull;

public class SetExecutableBlock extends BlockCommand{

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {

		if(SCore.hasExecutableBlocks) {
			
			if(!ExecutableBlockManager.getInstance().containsBlockWithID(args.get(0))) {
				ExecutableBlocks.plugin.getLogger().severe("There is no ExecutableBlock associate with the ID: "+args.get(0)+" for the command SETEXECUTABLEBLOCK (object: "+aInfo.getName()+")");
				return;
			}

			double x;
			double y;
			double z;
			try {
				x = Double.parseDouble(args.get(1));
			}catch(Exception e) {
				return ;
			}

			try {
				y = Double.parseDouble(args.get(2));
			}catch(Exception e) {

				return;
			}

			try {
				z = Double.parseDouble(args.get(3));
			}catch(Exception e) {
				return;
			}

			World world = null;
			String worldStr = args.get(4);
			if(worldStr.isEmpty()) return;
			else {
				if(SCore.hasMultiverse) {
					world = MultiverseAPI.getWorld(worldStr);
				}
				else {
					if((world = Bukkit.getWorld(worldStr)) == null) return;
				}
			}	

			boolean replace = false;
			try {
				replace = Boolean.parseBoolean(args.get(5));
			}
			catch(Exception ignored) {}

			UUID ownerUUID = null;
			try {
				ownerUUID = UUID.fromString(args.get(6));
			}
			catch(Exception ignored) {}

			Location loc = new Location(world, x, y , z);
			ExecutableBlockPlacedManager.getInstance().placeExecutableBlock(args.get(0), ownerUUID, loc, replace);
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String setEB = this.getTemplate();

		if(args.size()>7) {
			error = tooManyArgs+setEB;
			return error;
		}
		else if(args.size()<6) {
			error = notEnoughArgs+setEB;
		}
		else {

			if(SCore.hasExecutableBlocks) {
				/* Ne pas verif car ca peut bloquer si on veut poser le block qui est associé à l'activator */
//				if(!ExecutableBlockManager.getInstance().containsBlockWithID(args.get(0))) {
//					error = "There is no ExecutableBlock associate with the ID: "+args.get(0)+" for the command"+setEB;
//					return error;
//				}
			}
			else {
				error = "You must have ExecutableBlock for the command"+setEB;
				return error;
			}

			if(!args.get(1).contains("%")) {
				try {
					Double.valueOf(args.get(1));
				}catch(Exception e) {
					error = invalidCoordinate+args.get(1)+" for command: "+setEB;
					return error;
				}
			}
			if(!args.get(2).contains("%")) {
				try {
					Double.valueOf(args.get(2));
				}catch(Exception e) {
					error = invalidCoordinate+args.get(2)+" for command: "+setEB;
					return error;
				}
			}
			if(!args.get(3).contains("%")) {
				try {
					Double.valueOf(args.get(3));
				}catch(Exception e) {
					error = invalidCoordinate+args.get(3)+" for command: "+setEB;
					return error;
				}
			}

			String worldStr = args.get(4);
			if(!args.get(4).contains("%")) {
				if(worldStr.isEmpty()) return invalidWorld+args.get(4)+" for the command: "+setEB;
				else {
					if(SCore.hasMultiverse) {
						if(MultiverseAPI.getWorld(worldStr) == null) {
							return invalidWorld+args.get(4)+" for the command: "+setEB;
						}
					}
					else {
						if(Bukkit.getWorld(worldStr) == null) return invalidWorld+args.get(4)+" for the command: "+setEB;
					}
				}	
			}

			try {
				Boolean.valueOf(args.get(5));
			}
			catch(Exception e) {
				return invalidBoolean+args.get(5)+" for the command: "+setEB;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SETEXECUTABLEBLOCK");
		return names;
	}

	@Override
	public String getTemplate() {
		return "SETEXECUTABLEBLOCK {id} {x} {y} {z} {world} {replace true or false} [ownerUUID]";
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
