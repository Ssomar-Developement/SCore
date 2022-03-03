package com.ssomar.score.menu.conditions.worldcdt;

import org.bukkit.Material;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.splugin.SPlugin;

public class WorldConditionsMessagesGUI extends ConditionGUIAbstract{

	public WorldConditionsMessagesGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, WorldConditions conditions, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - World Conditions Messages", 3*9, sPlugin, sObject, sActivator, detail, conditions);
	}

	@Override
	public void loadTheGUI() {
		WorldConditions conditions = (WorldConditions)this.getConditions();
		int i = 0;
		//Main Options
		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+WorldConditionsMessages.IF_WEATHER_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(WorldConditionsMessages.IF_WEATHER_MSG.name, conditions.getIfWeatherMsg());

		createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+WorldConditionsMessages.IF_WORLD_TIME_MSG.name, 	false,	false, "&a✎ Click here to change", "&7actually:");
		i++;
		this.updateMessage(WorldConditionsMessages.IF_WORLD_TIME_MSG.name, conditions.getIfWorldTimeMsg());
		
		createItem(RED, 				1 , 18, "&4&l▶ &cBack to conditions config", 	false, false);
		
		createItem(ORANGE, 				1 , 19, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of world conditions" );
		
		createItem(GREEN, 				1 , 26, "&2&l✔ &aSave", 		false,	false, 	"", "&a&oClick here to save" , "&a&oyour modification of world conditions" );

		createItem(WRITABLE_BOOK, 	1 , 22, 	"&aTo edit conditions", 	false, false, "", "&2>> &a&oSHIFT + CLICK");
		
		createItem(Material.BOOK, 							1 , 24, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getId());
		createItem(Material.BOOK, 							1 , 25, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());
	}
	
	public enum WorldConditionsMessages{
		IF_WEATHER_MSG ("ifWeather message"),
		IF_WORLD_TIME_MSG ("ifWorldTime message");
		
		public String name;

		 WorldConditionsMessages(String name) {
			this.name = name;
		}
	}
}
