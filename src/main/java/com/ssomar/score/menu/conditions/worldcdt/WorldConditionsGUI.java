package com.ssomar.score.menu.conditions.worldcdt;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;

public class WorldConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_WEATHER = "ifWeather";
	public static final String IF_WORLD_TIME = "ifWorldTime";
	
	private WorldConditions conditions;

	public WorldConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - World Conditions", 3*9, sPlugin, sObject, sActivator, detail);
		this.conditions = conditions;

		int i =0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_WEATHER, 	false,	false, "&7&oThe weather must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfWeather(conditions.getIfWeather());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_WORLD_TIME, 	false,	false, "&7&oThe worldTime must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfWorldTime(conditions.getIfWorldTime());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of world conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of world conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sActivator.getID());
	
	}
	
	public void updateIfWeather(List<String> list) {
		this.updateConditionList(IF_WEATHER, list, "&6➤ &eNO WEATHER IS REQUIRED");
	}
	
	public List<String> getIfWeather(){
		return this.getConditionList(IF_WEATHER, "NO WEATHER IS REQUIRED");
	}	
	
	public void updateIfWorldTime(String condition){
		ItemStack item = this.getByName(IF_WORLD_TIME);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfWorlTime() {
		if(this.getActually(this.getByName(IF_WORLD_TIME)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_WORLD_TIME));
	}

	public WorldConditions getConditions() {
		return conditions;
	}
}
