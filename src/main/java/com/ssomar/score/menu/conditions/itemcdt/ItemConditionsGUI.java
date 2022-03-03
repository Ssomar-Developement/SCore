package com.ssomar.score.menu.conditions.itemcdt;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_DURABILITY = "ifDurability";
	public static final String IF_USAGE = "(1) ifUsage";
	public static final String IF_USAGE2 = "(2) ifUsage";
	public static final String IF_HAS_ENCHANT = "ifHasEnchant";
	public static final String IF_HAS_NOT_ENCHANT = "ifHasNotEnchant";
	public static final String IF_CROSSBOW_MUST_BE_CHARGED = "ifCrossbowMustBeCharged";
	public static final String IF_CROSSBOW_MUST_NOT_BE_CHARGED = "ifCrossbowMustNotBeCharged";

	
	public ItemConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Item Conditions", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}
	
	@Override
	public void loadTheGUI() {
		ItemConditions conditions = (ItemConditions)this.getConditions();
		int i = 0;
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

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_HAS_ENCHANT, 	false,	false, "&7&oThe item must have..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfHasEnchant(conditions.getIfHasEnchant(), false);

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_HAS_NOT_ENCHANT, 	false,	false, "&7&oThe item must have..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfHasEnchant(conditions.getIfHasNotEnchant(), true);

		createItem(Material.ARROW,							1 , i, 	TITLE_COLOR+IF_CROSSBOW_MUST_BE_CHARGED, 	false,	false, "", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_CROSSBOW_MUST_BE_CHARGED, conditions.isIfCrossbowMustBeCharged());

		createItem(Material.ARROW,							1 , i, 	TITLE_COLOR+IF_CROSSBOW_MUST_NOT_BE_CHARGED, 	false,	false, "", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_CROSSBOW_MUST_NOT_BE_CHARGED, conditions.isIfCrossbowMustNotBeCharged());
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of item conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of item conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 			1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 			1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
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

	@SuppressWarnings("deprecation")
	public void updateIfHasEnchant(Map<Enchantment, Integer> enchants, boolean not){
		String name;
		List<String> enchantsStr = new ArrayList<>();
		if(!not) name = IF_HAS_ENCHANT;
		else name = IF_HAS_NOT_ENCHANT;

		for(Enchantment enchant : enchants.keySet()){
			enchantsStr.add(enchant.getName() +":"+enchants.get(enchant));
		}

		this.updateConditionList(name, enchantsStr, "&cNO ENCHANTS REQUIRED");
	}

	public Map<Enchantment, Integer> getIfHasEnchant(boolean not){
		String name;
		if(!not) name = IF_HAS_ENCHANT;
		else name = IF_HAS_NOT_ENCHANT;
		List<String> enchantsStr = this.getConditionList(name, "NO ENCHANTS REQUIRED");
		return ItemConditions.transformEnchants(enchantsStr);
	}
}
