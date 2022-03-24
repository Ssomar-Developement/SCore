package com.ssomar.score.menu.conditions.blockcdt;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditionsGUI extends ConditionGUIAbstract{
	
	public static final String IF_PLANT_FULLY_GROWN = "ifPlantFullyGrown";
	public static final String IF_BLOCK_AGE = "if block age condition";
	public static final String IF_IS_POWERED = "ifIsPowered";
	public static final String IF_MUST_BE_NOT_POWERED = "ifMustbeNotPowered";
	public static final String IF_MUST_BE_NATURAL = "ifMustBeNatural";
	public static final String AROUND_BLOCK_CDT = "Around block conditions";
	public static final String BLOCK_X_CDT = "if block location X condition";
	public static final String BLOCK_X_CDT2 = "if block location X 2 condition";
	public static final String BLOCK_Y_CDT = "if block location Y condition";
	public static final String BLOCK_Y_CDT2 = "if block location Y 2 condition";
	public static final String BLOCK_Z_CDT = "if block location Z condition";
	public static final String BLOCK_Z_CDT2 = "if block location Z 2 condition";
	public static final String IF_PLAYER_MUST_BE_ON_THE_BLOCK = "if player must be on the block condition";
	public static final String IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK = "if no player must be on the block condition";
	
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

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_BLOCK_AGE, 	false,	false, "&7&oThe block age must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfAge(conditions.getIfBlockAge());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_IS_POWERED, 	false,	false, "&7&oThe block must be powered ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_IS_POWERED, conditions.isIfIsPowered());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_MUST_BE_NOT_POWERED, 	false,	false, "&7&oThe block must be not powered ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_MUST_BE_NOT_POWERED, conditions.isIfMustBeNotPowered());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_MUST_BE_NATURAL, 	false,	false, "&7&oThe block must be natural ?", "&c&oREQUIRE COREPROTECT", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_MUST_BE_NATURAL, conditions.isIfMustBeNatural());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_X_CDT, 	false,	false, "&7&oThe block location X must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosX(conditions.getIfBlockLocationX());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_X_CDT2, 	false,	false, "&7&oThe block location X must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosX2(conditions.getIfBlockLocationX2());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_Y_CDT, 	false,	false, "&7&oThe block location Y must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosY(conditions.getIfBlockLocationY());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_Y_CDT2, 	false,	false, "&7&oThe block location Y must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosY2(conditions.getIfBlockLocationY2());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_Z_CDT, 	false,	false, "&7&oThe block location Z must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosZ(conditions.getIfBlockLocationZ());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+BLOCK_Z_CDT2, 	false,	false, "&7&oThe block location Z must be ...", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateIfPosZ2(conditions.getIfBlockLocationZ2());
		
		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
		i++;

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_PLAYER_MUST_BE_ON_THE_BLOCK, 	false,	false, "&7&oA player must be on the block ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_PLAYER_MUST_BE_ON_THE_BLOCK, conditions.isIfPlayerMustBeOnTheBlock());

		createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK, 	false,	false, "&7&oNo player must be on the block ?", "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateBoolean(IF_NO_PLAYER_MUST_BE_ON_THE_BLOCK, conditions.isIfNoPlayerMustBeOnTheBlock());


		createItem(RED, 					1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 					1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of block conditions" );
		
		createItem(GREEN, 					1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of block conditions" );

		createItem(WRITABLE_BOOK, 			1 , 22, "&aTo edit messages of Conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 			1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 			1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}


	public void updateIfAge(String condition){
		ItemStack item = this.getByName(IF_BLOCK_AGE);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfAge() {
		if(this.getActually(this.getByName(IF_BLOCK_AGE)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(IF_BLOCK_AGE));
	}

	public void updateIfPosX(String condition){
		ItemStack item = this.getByName(BLOCK_X_CDT);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPosX() {
		if(this.getActually(this.getByName(BLOCK_X_CDT)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(BLOCK_X_CDT));
	}
	
	public void updateIfPosX2(String condition){
		ItemStack item = this.getByName(BLOCK_X_CDT2);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPosX2() {
		if(this.getActually(this.getByName(BLOCK_X_CDT2)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(BLOCK_X_CDT2));
	}
	
	public void updateIfPosY(String condition){
		ItemStack item = this.getByName(BLOCK_Y_CDT);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getIfPosY() {
		if(this.getActually(this.getByName(BLOCK_Y_CDT)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(BLOCK_Y_CDT));
	}
	
	public void updateIfPosY2(String condition){
		ItemStack item = this.getByName(BLOCK_Y_CDT2);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfPosY2() {
		if(this.getActually(this.getByName(BLOCK_Y_CDT2)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(BLOCK_Y_CDT2));
	}
	
	public void updateIfPosZ(String condition){
		ItemStack item = this.getByName(BLOCK_Z_CDT);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfPosZ() {
		if(this.getActually(this.getByName(BLOCK_Z_CDT)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(BLOCK_Z_CDT));
	}
	
	public void updateIfPosZ2(String condition){
		ItemStack item = this.getByName(BLOCK_Z_CDT2);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}
	
	public String getIfPosZ2() {
		if(this.getActually(this.getByName(BLOCK_Z_CDT2)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(BLOCK_Z_CDT2));
	}
}
