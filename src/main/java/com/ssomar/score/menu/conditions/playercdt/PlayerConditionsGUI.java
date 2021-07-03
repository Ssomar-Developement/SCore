package com.ssomar.score.menu.conditions.playercdt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class PlayerConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_SNEAKING = "ifSneaking";
	public static final String IF_NOT_SNEAKING = "ifNotSneaking";
	public static final String IF_BLOCKING = "ifBlocking";
	public static final String IF_NOT_BLOCKING = "ifNotBlocking";
	public static final String IF_SWIMMING = "ifSwimming";
	public static final String IF_GLIDING = "ifGliding";
	public static final String IF_FLYING = "ifFlying";
	public static final String IF_IN_WORLD = "ifInWorld";
	public static final String IF_NOT_IN_WORLD = "ifNotInWorld";
	public static final String IF_IN_BIOME = "ifInBiome";
	public static final String IF_NOT_IN_BIOME = "ifNotInBiome";
	public static final String IF_IN_REGION = "ifInRegion";
	public static final String IF_NOT_IN_REGION = "ifNotInRegion";
	public static final String IF_HAS_PERMISSION = "ifHasPermission";
	public static final String IF_NOT_HAS_PERMISSION = "ifNotHasPermission";
	public static final String IF_TARGET_BLOCK = "ifTargetBlock";
	public static final String IF_NOT_TARGET_BLOCK = "ifNotTargetBlock";
	public static final String IF_PLAYER_HEALTH = "ifPlayerHealth";
	public static final String IF_LIGHT_LEVEL = "ifLightLevel";
	public static final String IF_PLAYER_FOOD_LEVEL = "ifPlayerFoodLevel";
	public static final String IF_PLAYER_EXP = "ifPlayerEXP";
	public static final String IF_PLAYER_LEVEL = "ifPlayerLevel";
	public static final String IF_POS_X = "ifPosX";
	public static final String IF_POS_Y = "ifPosY";
	public static final String IF_POS_Z = "ifPosZ";
	
	
	private PlayerConditions conditions;

	public PlayerConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Player Conditions", 4*9, sPlugin, sObject, sActivator, detail);
		this.conditions = conditions;

		int i =0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_SNEAKING, 	false,	false, "&7&oThe player must sneak ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_SNEAKING, conditions.isIfSneaking());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_SNEAKING, 	false,	false, "&7&oThe player must not sneak ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_NOT_SNEAKING, conditions.isIfNotSneaking());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_BLOCKING, 	false,	false, "&7&oThe player must block with shield ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_BLOCKING, conditions.isIfBlocking());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_BLOCKING, 	false,	false, "&7&oThe player must not block with shield ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_NOT_BLOCKING, conditions.isIfNotBlocking());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_SWIMMING, 	false,	false, "&7&oThe player must swim ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_SWIMMING, conditions.isIfSwimming());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_GLIDING, 	false,	false, "&7&oThe player must glide ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_GLIDING, conditions.isIfGliding());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_FLYING, 	false,	false, "&7&oThe player must fly ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_FLYING, conditions.isIfFlying());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_IN_WORLD, 	false,	false, "&7&oThe player must be in this world", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfInWorld(conditions.getIfInWorld());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_IN_WORLD, 	false,	false, "&7&oThe player must not be in this world", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfNotInWorld(conditions.getIfNotInWorld());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_IN_BIOME, 	false,	false, "&7&oThe player must be in this biome", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfInBiome(conditions.getIfInBiome());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_IN_BIOME, 	false,	false, "&7&oThe player must not be in this biome", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfNotInBiome(conditions.getIfNotInBiome());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_IN_REGION, 	false,	false, "&7&oThe player must be in WorldGuard region", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfInRegion(conditions.getIfInRegion());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_IN_REGION, 	false,	false, "&7&oThe player must not be in WorldGuard region", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfNotInRegion(conditions.getIfNotInRegion());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_HAS_PERMISSION, 	false,	false, "&7&oThe player must have the permission(s)..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfHasPermission(conditions.getIfHasPermission());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_HAS_PERMISSION, 	false,	false, "&7&oThe player must not have the permission(s)..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfNotHasPermission(conditions.getIfNotHasPermission());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_TARGET_BLOCK, 	false,	false, "&7&oThe player must target one of this blocks.", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfTargetBlock(conditions.getIfTargetBlock());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NOT_TARGET_BLOCK, 	false,	false, "&7&oThe player must not target one of this blocks.", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfNotTargetBlock(conditions.getIfNotTargetBlock());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_HEALTH, 	false,	false, "&7&oThe player health must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPlayerHealth(conditions.getIfPlayerHealth());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_LIGHT_LEVEL, 	false,	false, "&7&oThe light level must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfLightLevel(conditions.getIfLightLevel());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_FOOD_LEVEL, 	false,	false, "&7&oThe player food level must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPlayerFoodLevel(conditions.getIfPlayerFoodLevel());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_EXP, 	false,	false, "&7&oThe player EXP must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPlayerEXP(conditions.getIfPlayerEXP());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_LEVEL, 	false,	false, "&7&oThe player level must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPlayerLevel(conditions.getIfPlayerLevel());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_POS_X, 	false,	false, "&7&oThe player coord X must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosX(conditions.getIfPosX());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_POS_Y, 	false,	false, "&7&oThe player coord Y must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosY(conditions.getIfPosY());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_POS_Z, 	false,	false, "&7&oThe player coord Z must be..", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosZ(conditions.getIfPosZ());
		
		

		createItem(RED, 					1 , 27, "&4&l▶ &cBack to conditions config", 	false, false);

		createItem(ORANGE, 					1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of player conditions" );

		createItem(GREEN, 					1 , 35, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of player conditions" );

		createItem(WRITABLE_BOOK, 	1 , 31, 	"&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 33, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 34, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sActivator.getID());
	}


	public void updateIfInWorld(List<String> list) {
		ItemStack item = this.getByName(IF_IN_WORLD);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eALL WORLD IS VALID"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfInWorld(){
		ItemStack item = this.getByName(IF_IN_WORLD);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("ALL WORLD IS VALID")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfNotInWorld(List<String> list) {
		ItemStack item = this.getByName(IF_NOT_IN_WORLD);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO WORLD IS INVALID"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfNotInWorld(){
		ItemStack item = this.getByName(IF_NOT_IN_WORLD);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO WORLD IS INVALID")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfInBiome(List<String> list) {
		ItemStack item = this.getByName(IF_IN_BIOME);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eALL BIOME IS VALID"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfInBiome(){
		ItemStack item = this.getByName(IF_IN_BIOME);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("ALL BIOME IS VALID")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfNotInBiome(List<String> list) {
		ItemStack item = this.getByName(IF_NOT_IN_BIOME);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO BIOME IS INVALID"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfNotInBiome(){
		ItemStack item = this.getByName(IF_NOT_IN_BIOME);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO BIOME IS INVALID")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}



	public void updateIfInRegion(List<String> list) {
		ItemStack item = this.getByName(IF_IN_REGION);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eALL REGION IS VALID"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfInRegion(){
		ItemStack item = this.getByName(IF_IN_REGION);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("ALL REGION IS VALID")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfNotInRegion(List<String> list) {
		ItemStack item = this.getByName(IF_NOT_IN_REGION);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO REGION IS BLACKLISTED"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfNotInRegion(){
		ItemStack item = this.getByName(IF_NOT_IN_REGION);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO REGION IS BLACKLISTED")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfHasPermission(List<String> list) {
		ItemStack item = this.getByName(IF_HAS_PERMISSION);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO PERMISSION IS REQUIRED"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfHasPermission(){
		ItemStack item = this.getByName(IF_HAS_PERMISSION);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO PERMISSION IS REQUIRED")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfNotHasPermission(List<String> list) {
		ItemStack item = this.getByName(IF_NOT_HAS_PERMISSION);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO PERMISSION IS BLACKLISTED"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getIfNotHasPermission(){
		ItemStack item = this.getByName(IF_NOT_HAS_PERMISSION);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO PERMISSION IS BLACKLISTED")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	public void updateIfTargetBlock(List<Material> list) {
		ItemStack item = this.getByName(IF_TARGET_BLOCK);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO TARGET BLOCK IS REQUIRED"));
		else {
			for(Material mat: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+mat));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<Material> getIfTargetBlock(){
		ItemStack item = this.getByName(IF_TARGET_BLOCK);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<Material> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO TARGET BLOCK IS REQUIRED")) {
				return new ArrayList<>();
			}else {
				try {
					result.add(Material.valueOf(line.replaceAll("➤ ", "")));
				}catch(Exception e) {}
			}
		}
		return result;
	}

	public void updateIfNotTargetBlock(List<Material> list) {
		ItemStack item = this.getByName(IF_NOT_TARGET_BLOCK);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eNO TARGET BLOCK IS BLACKLISTED"));
		else {
			for(Material mat: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+mat));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<Material> getIfNotTargetBlock(){
		ItemStack item = this.getByName(IF_NOT_TARGET_BLOCK);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate= iM.getLore().subList(3, iM.getLore().size());
		List<Material> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("NO TARGET BLOCK IS BLACKLISTED")) {
				return new ArrayList<>();
			}else {
				try {
					result.add(Material.valueOf(line.replaceAll("➤ ", "")));
				}catch(Exception e) {}
			}
		}
		return result;
	}

	public void updateIfPlayerHealth(String condition){
		ItemStack item = this.getByName(IF_PLAYER_HEALTH);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPlayerHealth() {
		if(this.getActually(this.getByName(IF_PLAYER_HEALTH)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_PLAYER_HEALTH));
	}
	
	public void updateIfLightLevel(String condition){
		ItemStack item = this.getByName(IF_LIGHT_LEVEL);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfLightLevel() {
		if(this.getActually(this.getByName(IF_LIGHT_LEVEL)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_LIGHT_LEVEL));
	}

	public void updateIfPlayerFoodLevel(String condition){
		ItemStack item = this.getByName(IF_PLAYER_FOOD_LEVEL);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPlayerFoodLevel() {
		if(this.getActually(this.getByName(IF_PLAYER_FOOD_LEVEL)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_PLAYER_FOOD_LEVEL));
	}

	public void updateIfPlayerEXP(String condition){
		ItemStack item = this.getByName(IF_PLAYER_EXP);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPlayerEXP() {
		if(this.getActually(this.getByName(IF_PLAYER_EXP)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_PLAYER_EXP));
	}

	public void updateIfPlayerLevel(String condition){
		ItemStack item = this.getByName(IF_PLAYER_LEVEL);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPlayerLevel() {
		if(this.getActually(this.getByName(IF_PLAYER_LEVEL)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_PLAYER_LEVEL));
	}
	
	public void updateIfPosX(String condition){
		ItemStack item = this.getByName(IF_POS_X);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPosX() {
		if(this.getActually(this.getByName(IF_POS_X)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_POS_X));
	}
	
	public void updateIfPosY(String condition){
		ItemStack item = this.getByName(IF_POS_Y);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPosY() {
		if(this.getActually(this.getByName(IF_POS_Y)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_POS_Y));
	}
	
	public void updateIfPosZ(String condition){
		ItemStack item = this.getByName(IF_POS_Z);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPosZ() {
		if(this.getActually(this.getByName(IF_POS_Z)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_POS_Z));
	}
	


	public PlayerConditions getConditions() {
		return conditions;
	}
}
