package com.ssomar.score.sobject.sactivator;

import java.util.Map;

import org.bukkit.Material;

public class DetailedBlock {

	private Material material;
	
	private Map<String, String> states;

	public DetailedBlock(Material material, Map<String, String> states) {
		super();
		this.material = material;
		this.states = states;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Map<String, String> getStates() {
		return states;
	}

	public void setStates(Map<String, String> states) {
		this.states = states;
	}
	
}
