package com.ssomar.score.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;

import com.ssomar.score.SCore;

public class ToolsListMaterial {
	
	private static ToolsListMaterial instance;
	
	private List<Material> plantWithGrowth;
	
	public ToolsListMaterial() {
		plantWithGrowth = new ArrayList<>();
		if(SCore.is1v12Less()) {
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
	
	@Nullable
	public static Material getRealMaterialOfBlock(Material material) {
		if(SCore.is1v12Less()) {
			if(material.equals(Material.valueOf("CROPS"))){
				return Material.valueOf("SEEDS");
			}
			else if(material.equals(Material.valueOf("NETHER_WARTS"))){
				return Material.valueOf("NETHER_WARTS");
			}
			else if(material.equals(Material.valueOf("POTATO"))){
				return Material.valueOf("POTATO_ITEM");
			}
			else if(material.equals(Material.valueOf("CARROT"))){
				return Material.valueOf("CARROT_ITEM");
			}
			else if(material.equals(Material.valueOf("BEETROOT_BLOCK"))){
				return Material.valueOf("BEETROOT_SEEDS");
			}
			else return material;
		}
		else{
			if(material.equals(Material.WHEAT)){
				return Material.WHEAT_SEEDS;
			}
			else if(material.equals(Material.CARROTS)){
				return Material.CARROT;
			}
			else if(material.equals(Material.POTATOES)){
				return Material.POTATO;
			}
			else if(material.equals(Material.BEETROOTS)){
				return Material.BEETROOT_SEEDS;
			}
			else if(material.equals(Material.NETHER_WART)){
				return Material.NETHER_WART;
			}
			else return material;
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
