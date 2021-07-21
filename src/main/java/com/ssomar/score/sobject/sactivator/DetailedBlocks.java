package com.ssomar.score.sobject.sactivator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;

public class DetailedBlocks extends ArrayList<DetailedBlock>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		} catch (Exception e) {}

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
			i++;
		}
		if(!valid || this.size() == i) return false;
		
		return true;
	}
	
	public void load(List<String> blocks, List<String> error, SPlugin sPlugin, SObject sObject) {
		
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
				error.add(sPlugin.getNameDesign()+" Invalid material: " + str + " for item: " + sObject.getID());
				continue;
			}
		}
	}
}
