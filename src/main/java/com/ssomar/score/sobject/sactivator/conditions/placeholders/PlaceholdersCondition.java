package com.ssomar.score.sobject.sactivator.conditions.placeholders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.Conditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;

import me.clip.placeholderapi.PlaceholderAPI;
import org.jetbrains.annotations.Nullable;

public class PlaceholdersCondition extends Conditions{
	
	private String id;

	private PlaceholdersCdtType type;

	private String message;

	private String part1;

	private Comparator comparator;

	private String part2String;

	private double part2Number;
	
	private boolean cancelEvent;
	
	public PlaceholdersCondition(String id) {
		this.id = id;
		init();
	}

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
	
	@Override
	public void init() {
		this.type = PlaceholdersCdtType.PLAYER_STRING;
		this.message = "";
		this.part1 = "";
		this.comparator = Comparator.EQUALS;
		this.part2String = "";
		this.cancelEvent = false;
	}

	public boolean verify(Player player, Player target) {
		return  verify(player, target, null);
	}

	public boolean verify(Player player, Player target, @Nullable StringPlaceholder sp) {
		String aPart1 = "";
		String aPart2 = "";

		if(sp != null) {
			aPart1 = sp.replacePlaceholder(part1, false);
			aPart2 = sp.replacePlaceholder(part2String, false);
		}
		else{
			aPart1 = part1;
			aPart2 = part2String;
		}
		
		if(SCore.hasPlaceholderAPI) {

			// replace placeholders in first part
			if (PlaceholdersCdtType.getpCdtTypeWithPlayer().contains(type) && player != null) {
				aPart1 = PlaceholderAPI.setPlaceholders(player, aPart1);
			}
			else if (target != null) aPart1 = PlaceholderAPI.setPlaceholders(target, aPart1);

			// replace placeholders in second part
			if (PlaceholdersCdtType.PLAYER_PLAYER.equals(type) && player != null) {
				aPart2 = PlaceholderAPI.setPlaceholders(player, aPart2);
			}
			else if ((PlaceholdersCdtType.TARGET_TARGET.equals(type) || PlaceholdersCdtType.PLAYER_TARGET.equals(type)) && target != null) {
				aPart2 = PlaceholderAPI.setPlaceholders(target, aPart2);
			}
		}

		// verification
		switch(type) {
		
		case PLAYER_NUMBER: case TARGET_NUMBER:
			if(NTools.isNumber(aPart1)) {
				double nPart1 = Double.parseDouble(aPart1);
				if(!comparator.verify(nPart1, part2Number)) return false;
			}
			else return false;
			break;
			
		case PLAYER_STRING: case TARGET_STRING:
			if(!comparator.verify(aPart1, part2String)) return false;
			break;
			
		case PLAYER_PLAYER: case TARGET_TARGET: case PLAYER_TARGET:
			if(NTools.isNumber(aPart1) && NTools.isNumber(aPart2)) {
				double nPart1 = Double.parseDouble(aPart1);
				double nPart2 = Double.parseDouble(aPart2);
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
	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param pC the player conditions object
	 */
	public static void savePlaceholdersCdt(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlaceholdersCondition pC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());

		activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".type", pC.getType().toString());
		activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".part1", pC.getPart1());
		activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".comparator", pC.getComparator().toString());
		
		if(PlaceholdersCdtType.getpCdtTypeWithNumber().contains(pC.getType())) {
			activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".part2", pC.getPart2Number());
		}
		else activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".part2", pC.getPart2String());
		activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".messageIfNotValid", pC.getMessage());
		activatorConfig.set("conditions.placeholdersConditions."+pC.getId()+".cancelEventIfNotValid", pC.isCancelEvent());

		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

			try {
				writer.write(config.saveToString());
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param pC the player conditions object
	 */
	public static void deletePlaceholdersCdt(SPlugin sPlugin, SObject sObject, SActivator sActivator, String id, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+"."+id, null);

		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

			try {
				writer.write(config.saveToString());
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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
