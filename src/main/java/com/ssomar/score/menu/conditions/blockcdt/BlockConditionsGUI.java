package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_PLANT_FULLY_GROWN = "ifPlantFullyGrown";
	public static final String IF_IS_POWERED = "ifIsPowered";
	public static final String AROUND_BLOCK_CDT = "Around block conditions";
	
	public BlockConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Block Conditions", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}
	
	@Override
	public void loadTheGUI() {
		BlockConditions conditions = (BlockConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLANT_FULLY_GROWN, 	false,	false, "&7&oThe plant must be fully grown ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_PLANT_FULLY_GROWN, conditions.isIfPlantFullyGrown());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_IS_POWERED, 	false,	false, "&7&oThe block must be powered ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_IS_POWERED, conditions.isIfIsPowered());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
		i++;
		
		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of block conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of block conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 			1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 			1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}
}
