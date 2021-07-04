package com.ssomar.score.menu.conditions.worldcdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;

public class WorldConditionsMessagesGUI extends ConditionGUIAbstract{
	
	public static final String IF_WEATHER_MSG = "ifWeather message";
	public static final String IF_WORLD_TIME_MSG = "ifWorldTime message";
	
	private WorldConditions conditions;

	public WorldConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - World Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions);
		this.conditions = conditions;
	}

	public WorldConditions getConditions() {
		return conditions;
	}

	@Override
	public void loadTheGUI() {
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_WEATHER_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_WEATHER_MSG, conditions.getIfWeatherMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+IF_WORLD_TIME_MSG, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(IF_WORLD_TIME_MSG, conditions.getIfWorldTimeMsg());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of world conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of world conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	
	}
}
