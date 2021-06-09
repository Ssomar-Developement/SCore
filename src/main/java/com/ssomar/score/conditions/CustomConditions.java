package com.ssomar.score.conditions;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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
