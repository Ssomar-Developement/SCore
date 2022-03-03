package com.ssomar.score.menu.conditions.playercdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.splugin.SPlugin;

public class PlayerConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public PlayerConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Player Conditions Messages", 5*9, sPlugin, sObject, sActivator, detail, conditions);
	}

	@Override
	public void loadTheGUI() {
		PlayerConditions conditions = (PlayerConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_SNEAKING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_SNEAKING_MSG.name, conditions.getIfSneakingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_SNEAKING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_SNEAKING_MSG.name, conditions.getIfNotSneakingMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_BLOCKING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_BLOCKING_MSG.name, conditions.getIfBlockingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_BLOCKING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_BLOCKING_MSG.name, conditions.getIfNotBlockingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_SPRINTING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_SPRINTING_MSG.name, conditions.getIfSprintingMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_SWIMMING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_SWIMMING_MSG.name, conditions.getIfSwimmingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_GLIDING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_GLIDING_MSG.name, conditions.getIfGlidingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_FLYING_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_FLYING_MSG.name, conditions.getIfFlyingMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_IS_IN_THE_AIR_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_IS_IN_THE_AIR_MSG.name, conditions.getIfIsInTheAirMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_IS_ON_THE_BLOCK_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_IS_ON_THE_BLOCK_MSG.name, conditions.getIfIsOnTheBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_IS_NOT_ON_THE_BLOCK_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_IS_NOT_ON_THE_BLOCK_MSG.name, conditions.getIfIsNotOnTheBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_IN_WORLD_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_IN_WORLD_MSG.name, conditions.getIfInWorldMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_IN_WORLD_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_IN_WORLD_MSG.name, conditions.getIfNotInWorldMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_IN_BIOME_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_IN_BIOME_MSG.name, conditions.getIfInBiomeMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_IN_BIOME_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_IN_BIOME_MSG.name, conditions.getIfNotInBiomeMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_IN_REGION_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_IN_REGION_MSG.name, conditions.getIfInRegionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_IN_REGION_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_IN_REGION_MSG.name, conditions.getIfNotInRegionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_HAS_PERMISSION_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_HAS_PERMISSION_MSG.name, conditions.getIfHasPermissionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_HAS_PERMISSION_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_HAS_PERMISSION_MSG.name, conditions.getIfNotHasPermissionMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_TARGET_BLOCK_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_TARGET_BLOCK_MSG.name, conditions.getIfTargetBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_NOT_TARGET_BLOCK_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_NOT_TARGET_BLOCK_MSG.name, conditions.getIfNotTargetBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_PLAYER_HEALTH_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_PLAYER_HEALTH_MSG.name, conditions.getIfPlayerHealthMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_LIGHT_LEVEL_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_LIGHT_LEVEL_MSG.name, conditions.getIfLightLevelMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_PLAYER_FOOD_LEVEL_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_PLAYER_FOOD_LEVEL_MSG.name, conditions.getIfPlayerFoodLevelMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_PLAYER_EXP_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_PLAYER_EXP_MSG.name, conditions.getIfPlayerEXPMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_PLAYER_LEVEL_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_PLAYER_LEVEL_MSG.name, conditions.getIfPlayerLevelMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_POS_X_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_POS_X_MSG.name, conditions.getIfPosXMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_POS_Y_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_POS_Y_MSG.name, conditions.getIfPosYMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_POS_Z_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_POS_Z_MSG.name, conditions.getIfPosZMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_HAS_EFFECT_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_HAS_EFFECT_MSG.name, conditions.getIfPlayerHasEffectMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+PlayerConditionsMessages.IF_HAS_EFFECT_EQUALS_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(PlayerConditionsMessages.IF_HAS_EFFECT_EQUALS_MSG.name, conditions.getIfPlayerHasEffectEqualsMsg());
		

		createItem(RED, 					1 , 36, "&4&l▶ &cBack to conditions config", 	false, false);

		createItem(ORANGE, 					1 , 37, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of player conditions" );

		createItem(GREEN, 					1 , 44, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of player conditions" );

		createItem(WRITABLE_BOOK, 	1 , 40, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 	1 , 42, 	COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 	1 , 43, 	COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}
	
	public enum PlayerConditionsMessages{
		IF_SNEAKING_MSG ("ifSneaking message"),
		IF_NOT_SNEAKING_MSG ("ifNotSneaking message"),
		IF_BLOCKING_MSG ("ifBlocking message"),
		IF_NOT_BLOCKING_MSG ("ifNotBlocking message"),
		IF_SPRINTING_MSG ("ifSprinting message"),
		IF_SWIMMING_MSG ("ifSwimming message"),
		IF_GLIDING_MSG ("ifGliding message"),
		IF_FLYING_MSG ("ifFlying message"),
		IF_IS_IN_THE_AIR_MSG ("ifIsInTheAir message"),
		IF_IS_ON_THE_BLOCK_MSG ("ifIsOnTheBlock message"),
		IF_IS_NOT_ON_THE_BLOCK_MSG ("ifIsNotOnTheBlock message"),
		IF_IN_WORLD_MSG ("ifInWorld message"),
		IF_NOT_IN_WORLD_MSG ("ifNotInWorld message"),
		IF_IN_BIOME_MSG ("ifInBiome message"),
		IF_NOT_IN_BIOME_MSG ("ifNotInBiome message"),
		IF_IN_REGION_MSG ("ifInRegion message"),
		IF_NOT_IN_REGION_MSG ("ifNotInRegion message"),
		IF_HAS_PERMISSION_MSG ("ifHasPermission message"),
		IF_NOT_HAS_PERMISSION_MSG ("ifNotHasPermission message"),
		IF_TARGET_BLOCK_MSG ("ifTargetBlock message"),
		IF_NOT_TARGET_BLOCK_MSG ("ifNotTargetBlock message"),
		IF_PLAYER_HEALTH_MSG ("ifPlayerHealth message"),
		IF_LIGHT_LEVEL_MSG ("ifLightLevel message"),
		IF_PLAYER_FOOD_LEVEL_MSG ("ifPlayerFoodLevel message"),
		IF_PLAYER_EXP_MSG ("ifPlayerEXP message"),
		IF_PLAYER_LEVEL_MSG ("ifPlayerLevel message"),
		IF_POS_X_MSG ("ifPosX message"),
		IF_POS_Y_MSG ("ifPosY message"),
		IF_POS_Z_MSG ("ifPosZ message"),
		IF_HAS_EFFECT_MSG ("(1) ifHasEffect message"),
		IF_HAS_EFFECT_EQUALS_MSG ("(2) ifHasEffectEquals message");
		
		public String name;

		PlayerConditionsMessages(String name) {
			this.name = name;
		}
	}
}
