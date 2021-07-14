package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.GriefDefenderAPI;
import com.ssomar.score.usedapi.GriefPreventionAPI;
import com.ssomar.score.usedapi.IridiumSkyblockTool;
import com.ssomar.score.usedapi.LandsIntegrationAPI;

public class CustomEIConditions extends Conditions{

	//Custom
	private boolean ifNeedPlayerConfirmation;
	private static final String IF_NEED_PLAYER_CONFIRMATION_MSG = " &7➤ Click again to confirm the use of this item";
	private String ifNeedPlayerConfirmationMsg;

	private boolean ifOwnerOfTheEI;
	private static final String IF_OWNER_OF_THE_EI_MSG = " &cYou must be the owner of the item to active the activator: &6%activator% &c!";
	private String ifOwnerOfTheEIMsg;

	private boolean ifNotOwnerOfTheEI;
	private static final String IF_NOT_OWNER_OF_THE_EI_MSG = " &cYou must not be the owner of the item to active the activator: &6%activator% &c!";
	private String ifNotOwnerOfTheEIMsg;

	private boolean ifPlayerMustBeOnHisIsland;
	private static final String IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG = " &cTo active this activator/item, you must be on your Island !";
	private String ifPlayerMustBeOnHisIslandMsg;
	
	private boolean ifPlayerMustBeOnHisClaim;
	private static final String IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG = " &cTo active this activator/item, you must be on your Claim or friend claim !";
	private String ifPlayerMustBeOnHisClaimMsg;
	
	@Override
	public void init() {
		this.ifNeedPlayerConfirmation = false;
		this.ifNeedPlayerConfirmationMsg = IF_NEED_PLAYER_CONFIRMATION_MSG;

		this.ifPlayerMustBeOnHisIsland = false;
		this.ifPlayerMustBeOnHisIslandMsg = IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG;

		this.ifOwnerOfTheEI = false;
		this.ifOwnerOfTheEIMsg = IF_OWNER_OF_THE_EI_MSG;

		this.ifNotOwnerOfTheEI = false;
		this.ifNotOwnerOfTheEIMsg = IF_NOT_OWNER_OF_THE_EI_MSG;
		
		this.ifPlayerMustBeOnHisClaim = false;
		this.ifPlayerMustBeOnHisClaimMsg = IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG;
	}

	public boolean verifConditions(Player p, ItemStack item) {
		if(SCore.hasIridiumSkyblock) {
			if(this.ifPlayerMustBeOnHisIsland) {
				if(!IridiumSkyblockTool.playerIsOnHisIsland(p)) {
					this.getSm().sendMessage(p, this.getIfPlayerMustBeOnHisIslandMsg());
					return false;
				}
			}
		}
		if(SCore.hasLands) {
			if(this.isIfPlayerMustBeOnHisClaim()) {
				LandsIntegrationAPI lands = new LandsIntegrationAPI(SCore.plugin);
				if(!lands.playerIsInHisClaim(p, p.getLocation())) {
					this.getSm().sendMessage(p, this.getIfPlayerMustBeOnHisClaimMsg());
					return false;
				}
			}
		}
		if(SCore.hasGriefPrevention) {
			if(this.isIfPlayerMustBeOnHisClaim()) {
				if(!GriefPreventionAPI.playerIsInHisClaim(p, p.getLocation())) {
					this.getSm().sendMessage(p, this.getIfPlayerMustBeOnHisClaimMsg());
					return false;
				}
			}
		}
		if(SCore.hasGriefDefender) {
			if(this.isIfPlayerMustBeOnHisClaim()) {
				if(!GriefDefenderAPI.playerIsInHisClaim(p, p.getLocation())) {
					this.getSm().sendMessage(p, this.getIfPlayerMustBeOnHisClaimMsg());
					return false;
				}
			}
		}
		if(this.isIfOwnerOfTheEI()) {
			if(item.hasItemMeta()) {
				ItemMeta iM = item.getItemMeta();

				NamespacedKey key = new NamespacedKey(ExecutableItems.getPluginSt(), "EI-OWNER");
				String uuidStr = iM.getPersistentDataContainer().get(key, PersistentDataType.STRING);
				boolean invalid = false;
				UUID uuid = null;
				try {
					uuid = UUID.fromString(uuidStr);
				}catch(Exception e ) {
					invalid = true;
				}
				if(invalid || !uuid.equals(p.getUniqueId())) {
					this.getSm().sendMessage(p, this.ifOwnerOfTheEIMsg);
					return false;
				}
			}
		}
		if(this.isIfNotOwnerOfTheEI()) {
			if(item.hasItemMeta()) {
				ItemMeta iM = item.getItemMeta();

				NamespacedKey key = new NamespacedKey(ExecutableItems.getPluginSt(), "EI-OWNER");
				String uuidStr = iM.getPersistentDataContainer().get(key, PersistentDataType.STRING);
				boolean invalid = false;
				UUID uuid = null;
				try {
					uuid = UUID.fromString(uuidStr);
				}catch(Exception e ) {
					invalid = true;
				}
				if(!invalid && uuid.equals(p.getUniqueId())) {
					this.getSm().sendMessage(p, this.ifNotOwnerOfTheEIMsg);
					return false;
				}
			}
		}
		return true;
	}

	public static CustomEIConditions getCustomConditions(ConfigurationSection customCdtSection, List<String> errorList, String pluginName) {

		CustomEIConditions cCdt = new CustomEIConditions();

		cCdt.setIfNeedPlayerConfirmation(customCdtSection.getBoolean("ifNeedPlayerConfirmation", false));
		cCdt.setIfNeedPlayerConfirmationMsg(customCdtSection.getString("ifNeedPlayerConfirmationMsg",
				"&8&l"+pluginName+IF_NEED_PLAYER_CONFIRMATION_MSG));

		cCdt.setIfOwnerOfTheEI(customCdtSection.getBoolean("ifOwnerOfTheEI", false));
		cCdt.setIfOwnerOfTheEIMsg(customCdtSection.getString("ifOwnerOfTheEIMsg", "&4&l"+pluginName+IF_OWNER_OF_THE_EI_MSG));

		cCdt.setIfNotOwnerOfTheEI(customCdtSection.getBoolean("ifNotOwnerOfTheEI", false));
		cCdt.setIfNotOwnerOfTheEIMsg(customCdtSection.getString("ifNotOwnerOfTheEIMsg", "&4&l"+pluginName+IF_NOT_OWNER_OF_THE_EI_MSG));

		cCdt.setIfPlayerMustBeOnHisIsland(customCdtSection.getBoolean("ifPlayerMustBeOnHisIsland", false));
		cCdt.setIfPlayerMustBeOnHisIslandMsg(customCdtSection.getString("ifPlayerMustBeOnHisIslandMsg",
				"&4&l"+pluginName+IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG));
		
		cCdt.setIfPlayerMustBeOnHisClaim(customCdtSection.getBoolean("ifPlayerMustBeOnHisClaim", false));
		cCdt.setIfPlayerMustBeOnHisClaimMsg(customCdtSection.getString("ifPlayerMustBeOnHisClaimMsg",
				"&4&l"+pluginName+IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG));

		return cCdt;

	}	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param cC the custom conditions object
	 */
	public static void saveCustomConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, CustomEIConditions cC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifNeedPlayerConfirmation", false);

		ConfigurationSection cCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(cC.hasIfNeedPlayerConfirmation()) cCConfig.set("ifNeedPlayerConfirmation", true); 
		else cCConfig.set("ifNeedPlayerConfirmation", null);
		cCConfig.set("ifNeedPlayerConfirmationMsg", cC.getIfNeedPlayerConfirmationMsg());


		if(cC.isIfOwnerOfTheEI()) cCConfig.set("ifOwnerOfTheEI", true); 
		else cCConfig.set("ifOwnerOfTheEI", null);
		cCConfig.set("ifOwnerOfTheEIMsg", cC.getIfOwnerOfTheEIMsg()); 

		if(cC.isIfNotOwnerOfTheEI()) cCConfig.set("ifNotOwnerOfTheEI", true); 
		else cCConfig.set("ifNotOwnerOfTheEI", null);
		cCConfig.set("ifNotOwnerOfTheEIMsg", cC.getIfNotOwnerOfTheEIMsg()); 

		if(cC.isIfPlayerMustBeOnHisIsland()) cCConfig.set("ifPlayerMustBeOnHisIsland", true); 
		else cCConfig.set("ifPlayerMustBeOnHisIsland", null);
		cCConfig.set("ifPlayerMustBeOnHisIslandMsg", cC.getIfPlayerMustBeOnHisIslandMsg()); 

		if(cC.isIfPlayerMustBeOnHisClaim()) cCConfig.set("ifPlayerMustBeOnHisClaim", true); 
		else cCConfig.set("ifPlayerMustBeOnHisClaim", null);
		cCConfig.set("ifPlayerMustBeOnHisClaimMsg", cC.getIfPlayerMustBeOnHisClaimMsg()); 
		
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

	public boolean isIfOwnerOfTheEI() {
		return ifOwnerOfTheEI;
	}

	public void setIfOwnerOfTheEI(boolean ifOwnerOfTheEI) {
		this.ifOwnerOfTheEI = ifOwnerOfTheEI;
	}

	public String getIfOwnerOfTheEIMsg() {
		return ifOwnerOfTheEIMsg;
	}

	public void setIfOwnerOfTheEIMsg(String ifOwnerOfTheEIMsg) {
		this.ifOwnerOfTheEIMsg = ifOwnerOfTheEIMsg;
	}

	public boolean isIfNotOwnerOfTheEI() {
		return ifNotOwnerOfTheEI;
	}

	public void setIfNotOwnerOfTheEI(boolean ifNotOwnerOfTheEI) {
		this.ifNotOwnerOfTheEI = ifNotOwnerOfTheEI;
	}

	public String getIfNotOwnerOfTheEIMsg() {
		return ifNotOwnerOfTheEIMsg;
	}

	public void setIfNotOwnerOfTheEIMsg(String ifNotOwnerOfTheEIMsg) {
		this.ifNotOwnerOfTheEIMsg = ifNotOwnerOfTheEIMsg;
	}

	public boolean isIfPlayerMustBeOnHisClaim() {
		return ifPlayerMustBeOnHisClaim;
	}

	public void setIfPlayerMustBeOnHisClaim(boolean ifPlayerMustBeOnHisClaim) {
		this.ifPlayerMustBeOnHisClaim = ifPlayerMustBeOnHisClaim;
	}

	public String getIfPlayerMustBeOnHisClaimMsg() {
		return ifPlayerMustBeOnHisClaimMsg;
	}

	public void setIfPlayerMustBeOnHisClaimMsg(String ifPlayerMustBeOnHisClaimMsg) {
		this.ifPlayerMustBeOnHisClaimMsg = ifPlayerMustBeOnHisClaimMsg;
	}

}
