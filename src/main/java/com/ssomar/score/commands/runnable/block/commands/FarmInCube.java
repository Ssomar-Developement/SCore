package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.ToolsListMaterial;

/* FARMINCUBE {radius} {ActiveDrop true or false} */
public class FarmInCube extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		List<Material> validMaterial = ToolsListMaterial.getInstance().getPlantWithGrowth();
		
		if(!validMaterial.contains(oldMaterial)) return;

		try {
			int radius = Integer.valueOf(args.get(0));
			
			Boolean drop = true;
			if(args.size()==2) drop= Boolean.valueOf(args.get(1));
			
			Boolean onlyMaxAge = true;
			if(args.size()==3) onlyMaxAge= Boolean.valueOf(args.get(2));
			
			if(radius<10) {
				for(int y = -radius ; y<radius+1 ; y++) {
					for(int x = -radius ; x<radius+1 ; x++) {
						for(int z = -radius ; z<radius+1 ; z++) {

							Block toDestroy = block.getWorld().getBlockAt(block.getX()+x, block.getY()+y, block.getZ()+z);
														
							this.destroyTheBlock(toDestroy, onlyMaxAge,  drop,  p);
						}
					}
				}
			}
		}catch(Exception e) {}
	}
	
	public void destroyTheBlock(Block toDestroy, boolean onlyMaxAge, boolean drop, Player p) {
		
		BlockData data = toDestroy.getState().getBlockData();
		
		if(onlyMaxAge && data instanceof Ageable) {
			Ageable ageable = (Ageable)data;
			if(ageable.getAge()!=ageable.getMaximumAge()) return;
		}
		
		if(ToolsListMaterial.getInstance().getPlantWithGrowth().contains(toDestroy.getType())) {
			ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
			boolean destroy = false;
			if(SCore.hasWorldGuard) {
				if(new WorldGuardAPI().canBuild(p, toDestroy.getLocation())) destroy = true;
				else return;
			}
			else destroy = true;

			if(destroy) {
				if(drop) toDestroy.breakNaturally(itemInMainHand);
				else toDestroy.setType(Material.AIR);
			}
		}
	}
	

	@Override
	public String verify(List<String> args) {
		return "";
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("FARMINCUBE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "FARMINCUBE {radius} {ActiveDrop true or false}";
	}

}
