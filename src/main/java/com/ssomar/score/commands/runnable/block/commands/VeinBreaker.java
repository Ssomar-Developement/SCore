package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.jetbrains.annotations.NotNull;

public class VeinBreaker  extends BlockCommand{

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
				
		if(args.size() == 2) {
			if (!oldMaterial.toString().equals(args.get(1)))
				return;
		}
		else{
			if (!(oldMaterial.toString().contains("ORE")
					|| oldMaterial.toString().contains("LOG")
					|| oldMaterial.toString().contains("WOOD")
					/* 1.16 woods */
					|| oldMaterial.toString().contains("HYPHAE")
					|| oldMaterial.toString().contains("WARPED_STEM")
					|| oldMaterial.toString().contains("CRIMSON_STEM")))
				return;
		}
		
		int veinSize = 120;
		try {
			veinSize = Integer.parseInt(args.get(0));
		}catch(Exception ignored) {}
		
		List<Block> vein;
		if(SCore.hasWorldGuard) {
			if(new WorldGuardAPI().canBuild(p, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
				this.validBreak(block);
			}
			else return;	
		}
		else this.validBreak(block);

		vein = this.getVein(block, oldMaterial, veinSize);

		for(Block b : vein) {
			if(SCore.hasWorldGuard) {
				if(new WorldGuardAPI().canBuild(p, new Location(b.getWorld(), b.getX(), b.getY(), b.getZ()))) {
					this.validBreak(b);
				}
				else continue;	
			}
			else this.validBreak(b);
		}
	}

	public List<Block> getVein(Block block, Material oldMaterial, int veinSize){
		List<Block> result = new ArrayList<>();
		this.fillVein(result, block, oldMaterial, veinSize);
		return result;
	}

	public void fillVein(List<Block> vein, Block block, Material oldMaterial, int veinSize) {
		
		Location loc = block.getLocation();
		int radius = 1;
		for(int y = -radius; y < radius +1; y++) {
			for(int x = -radius; x < radius +1; x++) {
				for(int z = -radius; z < radius +1; z++) {

					if( y == 0 && z == 0 && x == 0) continue;
					
					if(vein.size() > veinSize) return;

					Location newLoc = loc.clone();
					newLoc.add(x, y, z);
					Block newBlock;

					if((newBlock = newLoc.getBlock()).getType().equals(oldMaterial)) {
						if(!vein.contains(newBlock)) {
							vein.add(newBlock);
							this.fillVein(vein, newBlock, oldMaterial, veinSize);
						}
					}
				}
			}
		}
	}

	public void validBreak(Block block) {
		Location bLoc = block.getLocation();
		bLoc.add(0.5, 0.5, 0.5);

		if(SCore.hasExecutableBlocks) {
			ExecutableBlockPlaced eBP;
			if((eBP = ExecutableBlockPlacedManager.getInstance().getExecutableBlockPlaced(bLoc)) != null) {
				ExecutableBlockPlacedManager.getInstance().removeExecutableBlockPlaced(eBP);
				// TODO add the drop of the EB here
			}
		}

		block.breakNaturally();
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("VEIN_BREAKER");
		return names;
	}

	@Override
	public String getTemplate() {
		return "VEIN_BREAKER [Max_vein_size] [block_type, no need for LOG, ORE and WOOD]";
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
