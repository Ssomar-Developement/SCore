package com.ssomar.score.menu.conditions.itemCdt;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;

public class ItemConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_DURABILITY = "ifDurability";
	public static final String IF_USAGE = "ifUsage";
	public static final String IF_USAGE2 = "ifUsage2";

	private ItemConditions conditions;
	
	public ItemConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Item Conditions", 3*9, sPlugin, sObject, sActivator, detail);
		this.conditions = conditions;

		int i =0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_DURABILITY, 	false,	false, "&7&oThe durability must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfDurability(conditions.getIfDurability());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_USAGE, 	false,	false, "&7&oThe usage must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfUsage(conditions.getIfUsage());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_USAGE2, 	false,	false, "&7&oThe usage must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfUsage2(conditions.getIfUsage2());
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of item conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of item conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 			1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 			1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sActivator.getID());
	
	}
		
	public void updateIfDurability(String condition){
		ItemStack item = this.getByName(IF_DURABILITY);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfDurability() {
		if(this.getActually(this.getByName(IF_DURABILITY)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_DURABILITY));
	}
	
	public void updateIfUsage(String condition){
		ItemStack item = this.getByName(IF_USAGE);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfUsage() {
		if(this.getActually(this.getByName(IF_USAGE)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_USAGE));
	}
	
	public void updateIfUsage2(String condition){
		ItemStack item = this.getByName(IF_USAGE2);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfUsage2() {
		if(this.getActually(this.getByName(IF_USAGE2)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_USAGE2));
	}

	public ItemConditions getConditions() {
		return conditions;
	}
}
