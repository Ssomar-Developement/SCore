package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.IridiumSkyblockTool;

public class CustomConditions extends Conditions{

	//Custom
	private boolean ifNeedPlayerConfirmation;
	private static final String IF_NEED_PLAYER_CONFIRMATION_MSG = " &7➤ Click again to confirm the use of this item";
	private String ifNeedPlayerConfirmationMsg;

	private boolean ifPlayerMustBeOnHisIsland;
	private static final String IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG = " &cTo active this activator/item, you must be on your Island !";
	private String ifPlayerMustBeOnHisIslandMsg;

	public CustomConditions() {
		this.ifNeedPlayerConfirmation = false;
		this.ifNeedPlayerConfirmationMsg = "";

		this.ifPlayerMustBeOnHisIsland = false;
		this.ifNeedPlayerConfirmationMsg = "";
	}

	@Override
	public boolean verifConditions(Player p) {
		if(SCore.hasIridiumSkyblock) {
			if(this.ifPlayerMustBeOnHisIsland) {
				if(!IridiumSkyblockTool.playerIsOnHisIsland(p)) {
					this.getSm().sendMessage(p, this.getIfPlayerMustBeOnHisIslandMsg());
					return false;
				}
			}
		}
		return true;
	}

	public static CustomConditions getCustomConditions(ConfigurationSection customCdtSection, List<String> errorList, String pluginName) {

		CustomConditions cCdt = new CustomConditions();

		cCdt.setIfNeedPlayerConfirmation(customCdtSection.getBoolean("ifNeedPlayerConfirmation", false));
		cCdt.setIfNeedPlayerConfirmationMsg(customCdtSection.getString("ifNeedPlayerConfirmationMsg",
				"&8&l"+pluginName+IF_NEED_PLAYER_CONFIRMATION_MSG));

		cCdt.setIfPlayerMustBeOnHisIsland(customCdtSection.getBoolean("ifPlayerMustBeOnHisIsland", false));
		cCdt.setIfPlayerMustBeOnHisIslandMsg(customCdtSection.getString("ifPlayerMustBeOnHisIslandMsg",
				"&4&l"+pluginName+IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG));

		return cCdt;

	}	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param cC the custom conditions object
	 */
	public static void saveCustomConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, CustomConditions cC) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions.customConditions.ifNeedPlayerConfirmation", false);

		ConfigurationSection cCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions.customConditions");

		if(cC.hasIfNeedPlayerConfirmation()) cCConfig.set("ifNeedPlayerConfirmation", true); 
		else cCConfig.set("ifNeedPlayerConfirmation", null);

		if(cC.isIfPlayerMustBeOnHisIsland()) cCConfig.set("ifPlayerMustBeOnHisIsland", true); 
		else cCConfig.set("ifPlayerMustBeOnHisIsland", null);

		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

			try {
				writer.write(config.saveToString());
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean hasCustomCondition() {
		return this.ifNeedPlayerConfirmation || this.ifPlayerMustBeOnHisIsland;
	}

	public boolean isIfNeedPlayerConfirmation() {
		return ifNeedPlayerConfirmation;
	}

	public void setIfNeedPlayerConfirmation(boolean ifNeedPlayerConfirmation) {
		this.ifNeedPlayerConfirmation = ifNeedPlayerConfirmation;
	}

	public boolean hasIfNeedPlayerConfirmation() {
		return ifNeedPlayerConfirmation;
	}

	public String getIfNeedPlayerConfirmationMsg() {
		return ifNeedPlayerConfirmationMsg;
	}
	public void setIfNeedPlayerConfirmationMsg(String ifNeedPlayerConfirmationMsg) {
		this.ifNeedPlayerConfirmationMsg = ifNeedPlayerConfirmationMsg;
	}

	public boolean isIfPlayerMustBeOnHisIsland() {
		return ifPlayerMustBeOnHisIsland;
	}

	public void setIfPlayerMustBeOnHisIsland(boolean ifPlayerMustBeOnHisIsland) {
		this.ifPlayerMustBeOnHisIsland = ifPlayerMustBeOnHisIsland;
	}

	public String getIfPlayerMustBeOnHisIslandMsg() {
		return ifPlayerMustBeOnHisIslandMsg;
	}

	public void setIfPlayerMustBeOnHisIslandMsg(String ifPlayerMustBeOnHisIslandMsg) {
		this.ifPlayerMustBeOnHisIslandMsg = ifPlayerMustBeOnHisIslandMsg;
	}

}
