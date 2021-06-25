package com.ssomar.score.menu.conditions.entityCdt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class EntityConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_GLOWING = "ifGlowing";
	public static final String IF_ADULT = "ifAdult";
	public static final String IF_BABY = "ifBaby";
	public static final String IF_INVULNERABLE = "ifInvulnerable";
	public static final String IF_POWERED = "ifPowered";
	public static final String IF_NAME = "ifName";
	public static final String IF_NOT_ENTITY_TYPE = "ifNotEntityType";
	public static final String IF_ENTITY_HEALTH = "ifEntityHealth";
	
	private EntityConditions conditions;

	public EntityConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Entity Conditions", 4*9, sPlugin, sObject, sAct, detail);
		this.conditions = conditions;
		
		int i =0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_GLOWING, 	false,	false, "&7&oThe entity must glow ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_GLOWING, conditions.isIfGlowing());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_ADULT, 	false,	false, "&7&oThe entity must be adult ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_ADULT, conditions.isIfAdult());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_BABY, 	false,	false, "&7&oThe entity must be baby ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_BABY, conditions.isIfBaby());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_INVULNERABLE, 	false,	false, "&7&oThe entity must be invulnerable ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_INVULNERABLE, conditions.isIfInvulnerable());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_POWERED, 	false,	false, "&7&oThe entity must be powered ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_POWERED, conditions.isIfPowered());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NAME, 	false,	false, "&7&oThe entity name must be in this list", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfName(conditions.getIfName());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_ENTITY_TYPE, 	false,	false, "&7&oThe entity name mustn't be in this list", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfNotEntityType(conditions.getIfNotEntityType());
				
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_ENTITY_HEALTH, 	false,	false, "&7&oThe entity health must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfEntityHealth(conditions.getIfEntityHealth());
		
		createItem(RED, 			1 , 27, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 			1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of entity conditions" );
		
		createItem(GREEN, 			1 , 35, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of entity conditions" );
		
		
		createItem(WRITABLE_BOOK, 	1 , 31, 	"&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 33, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 34, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sAct.getID());
	}	
	
	public void updateIfNotEntityType(List<EntityType> list) {
		ItemStack item = this.getByName(IF_NOT_ENTITY_TYPE);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eALL ENTITYTYPE IS VALID"));
		else {
			for(EntityType eT: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+eT.toString()));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}
	
	public List<EntityType> getIfNotEntityType(){
		ItemStack item = this.getByName(IF_NOT_ENTITY_TYPE);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<EntityType> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("ALL ENTITYTYPE IS VALID")) {
				return new ArrayList<>();
			}else {
				try {
					result.add(EntityType.valueOf(line.replaceAll("➤ ", "").toUpperCase()));
				}catch(Exception e) {}
			}
		}
		return result;
	}
	
	public void updateIfName(List<String> list) {
		this.updateConditionList("ifName", list, "&6➤ &eALL NAME IS VALID");
	}
	
	public List<String> getIfName(){
		return this.getConditionList("ifName", "ALL NAME IS VALID");
	}
	
	public void updateIfEntityHealth(String condition){
		this.updateCondition(IF_ENTITY_HEALTH, condition);
	}
	
	public String getIfEntityHealth() {
		return this.getCondition(IF_ENTITY_HEALTH);
	}

	public EntityConditions getConditions() {
		return conditions;
	}
}
