package com.ssomar.score.sobject.sactivator;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;

public class DetailedBlocks extends ArrayList<DetailedBlock> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean cancelEventIfNotDetailedBlocks;
	
	public DetailedBlocks() {
		cancelEventIfNotDetailedBlocks = false;
	}
	
	public boolean verification(Material material, String statesStr, @NotNull Event event) {
		//SsomarDev.testMsg("mat: "+material.toString());
		if(!this.verification(material, statesStr)) {
			if(cancelEventIfNotDetailedBlocks && event instanceof Cancellable) ((Cancellable)event).setCancelled(true);
			return false;
		}
		
		return true;
	}
	
	public boolean verification(Material material, String statesStr) {
		Map<String, String> states = new HashMap<>();
		try {
			if(statesStr.contains("[")) {
				/* States are store like that TORCH[STATE1=VALUE1,STATE2=VALUE2] */

				String [] spliter1 = statesStr.split("\\]");							
				String [] spliter2 = spliter1[0].split("\\[");

				String [] spliterStates = spliter2[1].split("\\,");

				for(String state : spliterStates) {
					String [] spliterState = state.split("\\=");
					states.put(spliterState[0], spliterState[1]);
				}	
			}	
		} catch (Exception ignored) {}

		boolean valid = true;
		int i = 0;
		for(DetailedBlock dB : this) {
			Material matVerif = dB.getMaterial();
			Map<String, String> statesVerif = dB.getStates();
			if(matVerif.equals(material)) {
				if(statesVerif.isEmpty()) break;
				else {
					for(String stateVerif : statesVerif.keySet()) {
						if(states.containsKey(stateVerif)) {
							if(!states.get(stateVerif).equals(statesVerif.get(stateVerif))) {
								valid = false;
								break;
							}
						}
						else {
							valid = false;
							break;
						}
					}
				}
				break;
			}
			//else SsomarDev.testMsg("not equals mat : "+matVerif.toString()+ " || "+material.toString());
			i++;
		}
		return valid && this.size() != i;
	}
	/*
	 * @param error if error is null sPlugin and sObject can be null
	 */
	public void load(ConfigurationSection config, @Nullable List<String> error, @Nullable SPlugin sPlugin, @Nullable SObject sObject) {
				
		List<String> blocks = config.getStringList("detailedBlocks");
		
		this.load(blocks, config.getBoolean("cancelEventIfNotDetailedBlocks", false), error, sPlugin, sObject);
	}
	
	public void load(List<String> blocks, boolean cancelEventIfNotDetailedBlocks, @Nullable List<String> error, @Nullable SPlugin sPlugin, @Nullable SObject sObject) {
		
		this.cancelEventIfNotDetailedBlocks = cancelEventIfNotDetailedBlocks;
		
		for (String str : blocks) {
			/* StateId / value */
			Map<String, String> states = new HashMap<>();
			Material material;
			try {
				str = str.toUpperCase();
				if(str.contains("[")) {
					/* States are store like that TORCH[STATE1=VALUE1,STATE2=VALUE2] */

					String [] spliter1 = str.split("\\]");							
					String [] spliter2 = spliter1[0].split("\\[");
					material = Material.valueOf(spliter2[0]);

					String [] spliterStates = spliter2[1].split("\\,");

					for(String state : spliterStates) {
						String [] spliterState = state.split("\\=");
						states.put(spliterState[0], spliterState[1]);
					}	
				}	
				else material = Material.valueOf(str);
				this.add(new DetailedBlock(material, states));
			} catch (Exception e) {
				if(error != null) error.add(sPlugin.getNameDesign()+" Invalid material: " + str + " for item: " + sObject.getId());
				continue;
			}
		}
	}

	public boolean isCancelEventIfNotDetailedBlocks() {
		return cancelEventIfNotDetailedBlocks;
	}

	public void setCancelEventIfNotDetailedBlocks(boolean cancelEventIfNotDetailedBlocks) {
		this.cancelEventIfNotDetailedBlocks = cancelEventIfNotDetailedBlocks;
	}
	
}
