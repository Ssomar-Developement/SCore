package com.ssomar.score.menu.conditions.playerCdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.splugin.SPlugin;

public class PlayerConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public static final String IF_SNEAKING_MSG = "ifSneaking message";
	public static final String IF_NOT_SNEAKING_MSG = "ifNotSneaking message";
	public static final String IF_BLOCKING_MSG = "ifBlocking message";
	public static final String IF_NOT_BLOCKING_MSG = "ifNotBlocking message";
	public static final String IF_SWIMMING_MSG = "ifSwimming message";
	public static final String IF_GLIDING_MSG = "ifGliding message";
	public static final String IF_FLYING_MSG = "ifFlying message";
	public static final String IF_IN_WORLD_MSG = "ifInWorld message";
	public static final String IF_NOT_IN_WORLD_MSG = "ifNotInWorld message";
	public static final String IF_IN_BIOME_MSG = "ifInBiome message";
	public static final String IF_NOT_IN_BIOME_MSG = "ifNotInBiome message";
	public static final String IF_IN_REGION_MSG = "ifInRegion message";
	public static final String IF_NOT_IN_REGION_MSG = "ifNotInRegion message";
	public static final String IF_HAS_PERMISSION_MSG = "ifHasPermission message";
	public static final String IF_NOT_HAS_PERMISSION_MSG = "ifNotHasPermission message";
	public static final String IF_TARGET_BLOCK_MSG = "ifTargetBlock message";
	public static final String IF_NOT_TARGET_BLOCK_MSG = "ifNotTargetBlock message";
	public static final String IF_PLAYER_HEALTH_MSG = "ifPlayerHealth message";
	public static final String IF_LIGHT_LEVEL_MSG = "ifLightLevel message";
	public static final String IF_PLAYER_FOOD_LEVEL_MSG = "ifPlayerFoodLevel message";
	public static final String IF_PLAYER_EXP_MSG = "ifPlayerEXP message";
	public static final String IF_PLAYER_LEVEL_MSG = "ifPlayerLevel message";
	public static final String IF_POS_X_MSG = "ifPosX message";
	public static final String IF_POS_Y_MSG = "ifPosY message";
	public static final String IF_POS_Z_MSG = "ifPosZ message";
	
	private PlayerConditions conditions;

	public PlayerConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Player Conditions Messages", 4*9, sPlugin, sObject, sActivator, detail);
		this.conditions = conditions;

		int i =0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_SNEAKING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_SNEAKING_MSG, conditions.getIfSneakingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_SNEAKING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_SNEAKING_MSG, conditions.getIfNotSneakingMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_BLOCKING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_BLOCKING_MSG, conditions.getIfBlockingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_BLOCKING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_BLOCKING_MSG, conditions.getIfNotBlockingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_SWIMMING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_SWIMMING_MSG, conditions.getIfSwimmingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_GLIDING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_GLIDING_MSG, conditions.getIfGlidingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_FLYING_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_FLYING_MSG, conditions.getIfFlyingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_IN_WORLD_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_IN_WORLD_MSG, conditions.getIfInWorldMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_IN_WORLD_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_IN_WORLD_MSG, conditions.getIfNotInWorldMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_IN_BIOME_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_IN_BIOME_MSG, conditions.getIfInBiomeMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_IN_BIOME_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_IN_BIOME_MSG, conditions.getIfNotInBiomeMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_IN_REGION_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_IN_REGION_MSG, conditions.getIfInRegionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_IN_REGION_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_IN_REGION_MSG, conditions.getIfNotInRegionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_HAS_PERMISSION_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_HAS_PERMISSION_MSG, conditions.getIfHasPermissionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_HAS_PERMISSION_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_HAS_PERMISSION_MSG, conditions.getIfNotHasPermissionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_TARGET_BLOCK_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_TARGET_BLOCK_MSG, conditions.getIfTargetBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_NOT_TARGET_BLOCK_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_NOT_TARGET_BLOCK_MSG, conditions.getIfNotTargetBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_PLAYER_HEALTH_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_PLAYER_HEALTH_MSG, conditions.getIfPlayerHealthMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_LIGHT_LEVEL_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_LIGHT_LEVEL_MSG, conditions.getIfLightLevelMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_PLAYER_FOOD_LEVEL_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_PLAYER_FOOD_LEVEL_MSG, conditions.getIfPlayerFoodLevelMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_PLAYER_EXP_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_PLAYER_EXP_MSG, conditions.getIfPlayerEXPMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_PLAYER_LEVEL_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_PLAYER_LEVEL_MSG, conditions.getIfPlayerLevelMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_POS_X_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_POS_X_MSG, conditions.getIfPosXMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_POS_Y_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_POS_Y_MSG, conditions.getIfPosYMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_POS_Z_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_POS_Z_MSG, conditions.getIfPosZMsg());
		

		createItem(RED, 					1 , 27, "&4&l▶ &cBack to conditions config", 	false, false);

		createItem(ORANGE, 					1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of player conditions" );

		createItem(GREEN, 					1 , 35, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of player conditions" );

		createItem(WRITABLE_BOOK, 	1 , 31, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 33, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+sObject.getID());
		createItem(Material.BOOK, 	1 , 34, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+sActivator.getID());
	}

	public PlayerConditions getConditions() {
		return conditions;
	}
}
