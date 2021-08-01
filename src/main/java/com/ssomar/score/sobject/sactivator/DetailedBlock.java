package com.ssomar.score.sobject.sactivator;

import java.io.Serializable;
import java.util.Map;

import org.bukkit.Material;

public class DetailedBlock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Material of the block */
	private String materialName;
	
	/* States of the block , example STATE1 = Value1*/
	private Map<String, String> states;

	public DetailedBlock(Material material, Map<String, String> states) {
		super();
		this.materialName = material.toString();
		this.states = states;
	}

	public Material getMaterial() {
		return Material.valueOf(materialName);
	}

	public void setMaterial(Material material) {
		this.materialName = material.toString();
	}

	public Map<String, String> getStates() {
		return states;
	}

	public void setStates(Map<String, String> states) {
		this.states = states;
	}
	
}
