package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

/*public class BlockConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public BlockConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Block Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}

	@Override
	public void loadTheGUI() {
		BlockConditions conditions = (BlockConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_PLANT_FULLY_GROWN_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_PLANT_FULLY_GROWN_MSG.name, conditions.getIfPlantFullyGrownMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_AGE_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_AGE_MSG.name, conditions.getIfBlockAgeMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_IS_POWERED_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_IS_POWERED_MSG.name, conditions.getIfIsPoweredMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_MUST_BE_NOT_POWERED_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_MUST_BE_NOT_POWERED_MSG.name, conditions.getIfMustBeNotPoweredMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_MUST_BE_NATURAL_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_MUST_BE_NATURAL_MSG.name, conditions.getIfMustBeNaturalMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	"&c&oAROUND CDT MESSAGES NOT HERE", 	false,	false);
		i++;
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_LOCATION_X_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_X_MSG.name, conditions.getIfBlockLocationXMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_LOCATION_X2_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_X2_MSG.name, conditions.getIfBlockLocationX2Msg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_LOCATION_Y_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Y_MSG.name, conditions.getIfBlockLocationYMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_LOCATION_Y2_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Y2_MSG.name, conditions.getIfBlockLocationY2Msg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_LOCATION_Z_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Z_MSG.name, conditions.getIfBlockLocationZMsg());
		
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_BLOCK_LOCATION_Z2_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_BLOCK_LOCATION_Z2_MSG.name, conditions.getIfBlockLocationZ2Msg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_PLAYER_MUST_BE_ON_THE_BLOCK_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_PLAYER_MUST_BE_ON_THE_BLOCK_MSG.name, conditions.getIfPlayerMustBeOnTheBlockMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+BlockConditionsMessages.IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(BlockConditionsMessages.IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK_MSG.name, conditions.getIfNoPlayerMustBeOnTheBlockMsg());
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of block conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of block conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}	
	
	public enum BlockConditionsMessages{
		IF_PLANT_FULLY_GROWN_MSG ("ifPlantFullyGrown message"),
		IF_BLOCK_AGE_MSG ("if block age message"),
		IF_IS_POWERED_MSG ("ifIsPowered message"),
		IF_MUST_BE_NOT_POWERED_MSG ("ifMustBeNotPowered message"),
		IF_MUST_BE_NATURAL_MSG ("ifMustBeNatural message"),
		IF_BLOCK_LOCATION_X_MSG ("if block location X message"),
		IF_BLOCK_LOCATION_X2_MSG ("if block location X 2 message"),
		IF_BLOCK_LOCATION_Y_MSG ("if block location Y message"),
		IF_BLOCK_LOCATION_Y2_MSG ("if block location Y 2 message"),
		IF_BLOCK_LOCATION_Z_MSG ("if block location Z message"),
		IF_BLOCK_LOCATION_Z2_MSG ("if block location Z 2 message"),
		IF_PLAYER_MUST_BE_ON_THE_BLOCK_MSG ("if player must be on the block condition message"),
		IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK_MSG ("if no player must be on the block condition message");
		
		public String name;

		BlockConditionsMessages(String name) {
			this.name = name;
		}
	}
}
*/