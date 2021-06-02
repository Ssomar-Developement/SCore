package com.ssomar.score.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.ssomar.score.SCore;

public class ToolsListMaterial {
	
	private static ToolsListMaterial instance;
	
	private List<Material> plantWithGrowth;
	
	public ToolsListMaterial() {
		plantWithGrowth = new ArrayList<>();
		if(SCore.is1v12()) {
			plantWithGrowth.add(Material.valueOf("CROPS"));
			plantWithGrowth.add(Material.valueOf("NETHER_WARTS"));
			plantWithGrowth.add(Material.valueOf("POTATO"));
			plantWithGrowth.add(Material.valueOf("CARROT"));
			plantWithGrowth.add(Material.valueOf("BEETROOT_BLOCK"));
		}
		else{
			plantWithGrowth.add(Material.WHEAT);
			plantWithGrowth.add(Material.CARROTS);
			plantWithGrowth.add(Material.BEETROOTS);
			plantWithGrowth.add(Material.POTATOES);
			plantWithGrowth.add(Material.NETHER_WART);
		}
		
		
	}
	
	public static ToolsListMaterial getInstance() {
		if(instance == null) return new ToolsListMaterial();
		return instance;
	}

	public List<Material> getPlantWithGrowth() {
		return plantWithGrowth;
	}

	public void setPlantWithGrowth(List<Material> plantWithGrowth) {
		this.plantWithGrowth = plantWithGrowth;
	}
	
	

}
