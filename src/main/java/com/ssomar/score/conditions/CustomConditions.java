package com.ssomar.score.conditions;

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
import org.bukkit.plugin.Plugin;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.usedapi.IridiumSkyblockTool;

public class CustomConditions extends Conditions{

	//Custom
	private boolean ifNeedPlayerConfirmation = false;
	private static final String IF_NEED_PLAYER_CONFIRMATION_MSG = " &7➤ Click again to confirm the use of this item";
	private String ifNeedPlayerConfirmationMsg = "";

	private boolean ifPlayerMustBeOnHisIsland = false;
	private static final String IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG = " &cTo active this activator/item, you must be on your Island !";
	private String ifPlayerMustBeOnHisIslandMsg = "";


	public boolean verifConditions(Player p, Player target) {
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
	 *  @param plugin The plugin of the conditions
	 *  @param path The path of the file where we save the conditions
	 *  @param ojectID The ID of the object that contains the conditions
	 *  @param actiID  The ID of the activator that contains the conditions
	 *  @param cC the custom conditions object
	 */
	public static void saveCustomConditions(Plugin plugin, String path, String objectID, String actID, CustomConditions cC) {

		if(!new File(path).exists()) {
			plugin.getLogger().severe("["+plugin.getName()+"] Error can't find the file in the folder ("+objectID+".yml)");
			return;
		}
		File file = new File(path);
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+actID);
		activatorConfig.set("conditions.customConditions.ifNeedPlayerConfirmation", false);

		ConfigurationSection cCConfig = config.getConfigurationSection("activators."+actID+".conditions.customConditions");

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
