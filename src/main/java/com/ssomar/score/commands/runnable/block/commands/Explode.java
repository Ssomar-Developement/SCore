package com.ssomar.score.commands.runnable.block.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;
import com.ssomar.score.usedapi.WorldGuardAPI;

/* EXPLODE */
public class Explode extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		if(SCore.hasWorldGuard) {
			if(new WorldGuardAPI().canBuild(p, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
				this.validBreak(block);
			}
		}
		else {
			this.validBreak(block);
		}
	}
	
	public void validBreak(Block block) {
		Location bLoc = block.getLocation();
		bLoc.add(0.5, 0.5, 0.5);
		
		if(SCore.hasExecutableBlocks) {
			ExecutableBlockPlaced eBP;
			if((eBP = ExecutableBlockPlacedManager.getInstance().getExecutableBlockPlaced(bLoc)) != null) {
				ExecutableBlockPlacedManager.getInstance().removeExecutableBlockPlaced(eBP);
			}
		}
		
		block.breakNaturally();
		block.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

}
