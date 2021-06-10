package com.ssomar.score.sobject.sactivator.conditions.placeholders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.NTools;

import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholdersCondition {
	
	private String id;

	private PlaceholdersCdtType type;

	private String message;

	private String part1;

	private Comparator comparator;

	private String part2String;

	private double part2Number = -1;
	
	private boolean cancelEvent = false;

	public PlaceholdersCondition(String id, PlaceholdersCdtType type, String message, String part1, Comparator comparator,
			String part2String) {
		super();
		this.id = id;
		this.type = type;
		this.message = message;
		this.part1 = part1;
		this.comparator = comparator;
		this.part2String = part2String;
	}

	public PlaceholdersCondition(String id, PlaceholdersCdtType type, String message, String part1, Comparator comparator,
			double part2Number) {
		super();
		this.id = id;
		this.type = type;
		this.message = message;
		this.part1 = part1;
		this.comparator = comparator;
		this.part2Number = part2Number;
	}


	public boolean verify(Player player, Player target) {
		String aPart1 = "";
		String aPart2 ="";
		
		if(!SCore.hasPlaceholderAPI) return false;

		// replace placeholders in first part
		if(PlaceholdersCdtType.getpCdtTypeWithPlayer().contains(type)) {
			aPart1 = PlaceholderAPI.setPlaceholders(player, part1);
		}
		else if(target != null) aPart1 = PlaceholderAPI.setPlaceholders(target, part1);

		// replace placeholders in second part
		if(PlaceholdersCdtType.PLAYER_PLAYER.equals(type)) {
			aPart2 = PlaceholderAPI.setPlaceholders(player, part2String);
		}
		else if((PlaceholdersCdtType.TARGET_TARGET.equals(type) || PlaceholdersCdtType.PLAYER_TARGET.equals(type)) && target != null){	
			aPart2 = PlaceholderAPI.setPlaceholders(target, part2String);
		}

		// verification
		switch(type) {
		
		case PLAYER_NUMBER: case TARGET_NUMBER:
			if(NTools.isNumber(aPart1)) {
				double nPart1 = Double.valueOf(aPart1);
				if(!comparator.verify(nPart1, part2Number)) return false;
			}
			else return false;
			break;
			
		case PLAYER_STRING: case TARGET_STRING:
			if(!comparator.verify(aPart1, part2String)) return false;
			break;
		case PLAYER_PLAYER: case TARGET_TARGET: case PLAYER_TARGET:
			if(NTools.isNumber(aPart1) && NTools.isNumber(aPart2)) {
				double nPart1 = Double.valueOf(aPart1);
				double nPart2 = Double.valueOf(aPart2);
				if(!comparator.verify(nPart1, nPart2)) return false;
			}
			else if(!comparator.verify(aPart1, aPart2)) return false;
			break;
			
		default:
			break;
		}

		return true;

	}
	
	public static List<PlaceholdersCondition> getPlaceholdersConditions(ConfigurationSection placeholdersCdtSection, List<String> errorList, String pluginName) {

		List<PlaceholdersCondition> list = new ArrayList<>();

		for(String id : placeholdersCdtSection.getKeys(false)) {
			
			ConfigurationSection placeholderCdtSection = placeholdersCdtSection.getConfigurationSection(id);
			
			PlaceholdersCdtType type;
			try {
				type = PlaceholdersCdtType.valueOf(placeholderCdtSection.getString("type"));
			}catch(IllegalArgumentException e) {
				errorList.add(pluginName+" Invalid PlaceholderCdtType for the placeholders condition: " + id + " Check the wiki !");
				continue;
			}
			
			String part1 = placeholderCdtSection.getString("part1", "");
			if(part1.isEmpty()) {
				errorList.add(pluginName+" Invalid part1 (empty) for the placeholders condition: " + id + " Check the wiki !");
				continue;
			}
			
			Comparator comparator;
			try {
				comparator = Comparator.valueOf(placeholderCdtSection.getString("comparator"));
			}catch(IllegalArgumentException e) {
				errorList.add(pluginName+" Invalid Comparator for the placeholders condition: " + id + " Check the wiki !");
				continue;
			}
			
			
			String message = placeholderCdtSection.getString("messageIfNotValid", "");
			
			boolean cancelEvent = placeholderCdtSection.getBoolean("cancelEventIfNotValid", false);
			
			PlaceholdersCondition pC;
			if(PlaceholdersCdtType.getpCdtTypeWithNumber().contains(type)) {
				
				double part2 = placeholderCdtSection.getDouble("part2");
				
				pC = new PlaceholdersCondition(id, type, message, part1, comparator, part2);
				
			}
			else {
				String part2 = placeholderCdtSection.getString("part2", "");
				if(part2.isEmpty()) {
					errorList.add(pluginName+" Invalid part1 (empty) for the placeholders condition: " + id + " Check the wiki !");
					continue;
				}
				
				pC = new PlaceholdersCondition(id, type, message, part1, comparator, part2);
			}
			pC.setCancelEvent(cancelEvent);
			
			list.add(pC);
			
		}

		return list;

	}

	public PlaceholdersCdtType getType() {
		return type;
	}

	public void setType(PlaceholdersCdtType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPart1() {
		return part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public String getPart2String() {
		return part2String;
	}

	public void setPart2String(String part2String) {
		this.part2String = part2String;
	}

	public double getPart2Number() {
		return part2Number;
	}

	public void setPart2Number(double part2Number) {
		this.part2Number = part2Number;
	}

	public boolean isCancelEvent() {
		return cancelEvent;
	}

	public void setCancelEvent(boolean cancelEvent) {
		this.cancelEvent = cancelEvent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
