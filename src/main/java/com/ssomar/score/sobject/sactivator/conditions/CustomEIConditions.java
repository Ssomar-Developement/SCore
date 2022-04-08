package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

import com.ssomar.score.usedapi.*;
import com.ssomar.score.utils.messages.MessageDesign;
import lombok.Getter;
import lombok.Setter;
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

@Getter @Setter
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

	private boolean ifPlayerMustBeOnHisPlot;
	private static final String IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG = " &cTo active this activator/item, you must be on your Plot or friend plot !";
	private String ifPlayerMustBeOnHisPlotMsg;
	
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

		this.ifPlayerMustBeOnHisPlot = false;
		this.ifPlayerMustBeOnHisPlotMsg = IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG;

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

		if(SCore.hasPlotSquared) {
			if(this.isIfPlayerMustBeOnHisPlot()) {
				if(!PlotSquaredAPI.playerIsInHisPlot(p, p.getLocation())) {
					this.getSm().sendMessage(p, this.getIfPlayerMustBeOnHisPlotMsg());
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
		cCdt.setIfNeedPlayerConfirmationMsg(customCdtSection.getString("ifNeedPlayerConfirmationMsg", "&8&l"+pluginName+IF_NEED_PLAYER_CONFIRMATION_MSG));

		cCdt.setIfOwnerOfTheEI(customCdtSection.getBoolean("ifOwnerOfTheEI", false));
		cCdt.setIfOwnerOfTheEIMsg(customCdtSection.getString("ifOwnerOfTheEIMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_OWNER_OF_THE_EI_MSG));

		cCdt.setIfNotOwnerOfTheEI(customCdtSection.getBoolean("ifNotOwnerOfTheEI", false));
		cCdt.setIfNotOwnerOfTheEIMsg(customCdtSection.getString("ifNotOwnerOfTheEIMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_NOT_OWNER_OF_THE_EI_MSG));

		cCdt.setIfPlayerMustBeOnHisIsland(customCdtSection.getBoolean("ifPlayerMustBeOnHisIsland", false));
		cCdt.setIfPlayerMustBeOnHisIslandMsg(customCdtSection.getString("ifPlayerMustBeOnHisIslandMsg",
				MessageDesign.ERROR_CODE_FIRST+pluginName+IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG));
		
		cCdt.setIfPlayerMustBeOnHisClaim(customCdtSection.getBoolean("ifPlayerMustBeOnHisClaim", false));
		cCdt.setIfPlayerMustBeOnHisClaimMsg(customCdtSection.getString("ifPlayerMustBeOnHisClaimMsg",
				MessageDesign.ERROR_CODE_FIRST+pluginName+IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG));

		cCdt.setIfPlayerMustBeOnHisPlot(customCdtSection.getBoolean("ifPlayerMustBeOnHisPlot", false));
		cCdt.setIfPlayerMustBeOnHisPlotMsg(customCdtSection.getString("ifPlayerMustBeOnHisPlotMsg",
				MessageDesign.ERROR_CODE_FIRST+pluginName+IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG));

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
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifNeedPlayerConfirmation", false);

		String pluginName = sPlugin.getNameDesign();

		ConfigurationSection cCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(cC.isIfNeedPlayerConfirmation()) cCConfig.set("ifNeedPlayerConfirmation", true);
		else cCConfig.set("ifNeedPlayerConfirmation", null);
		if(cC.getIfNeedPlayerConfirmationMsg().contains(cC.IF_NEED_PLAYER_CONFIRMATION_MSG)) cCConfig.set("ifNeedPlayerConfirmationMsg", null);
		else cCConfig.set("ifNeedPlayerConfirmationMsg", cC.getIfNeedPlayerConfirmationMsg());


		if(cC.isIfOwnerOfTheEI()) cCConfig.set("ifOwnerOfTheEI", true); 
		else cCConfig.set("ifOwnerOfTheEI", null);
		if(cC.getIfOwnerOfTheEIMsg().contains(cC.IF_OWNER_OF_THE_EI_MSG)) cCConfig.set("ifOwnerOfTheEIMsg", null);
		else cCConfig.set("ifOwnerOfTheEIMsg", cC.getIfOwnerOfTheEIMsg());

		if(cC.isIfNotOwnerOfTheEI()) cCConfig.set("ifNotOwnerOfTheEI", true); 
		else cCConfig.set("ifNotOwnerOfTheEI", null);
		if(cC.getIfNotOwnerOfTheEIMsg().contains(cC.IF_NOT_OWNER_OF_THE_EI_MSG)) cCConfig.set("ifNotOwnerOfTheEIMsg", null);
		else cCConfig.set("ifNotOwnerOfTheEIMsg", cC.getIfNotOwnerOfTheEIMsg());

		if(cC.isIfPlayerMustBeOnHisIsland()) cCConfig.set("ifPlayerMustBeOnHisIsland", true); 
		else cCConfig.set("ifPlayerMustBeOnHisIsland", null);
		if(cC.getIfPlayerMustBeOnHisIslandMsg().contains(cC.IF_PLAYER_MUST_BE_ON_HIS_ISLAND_MSG)) cCConfig.set("ifPlayerMustBeOnHisIslandMsg", null);
		else cCConfig.set("ifPlayerMustBeOnHisIslandMsg", cC.getIfPlayerMustBeOnHisIslandMsg());

		if(cC.isIfPlayerMustBeOnHisClaim()) cCConfig.set("ifPlayerMustBeOnHisClaim", true); 
		else cCConfig.set("ifPlayerMustBeOnHisClaim", null);
		if(cC.getIfPlayerMustBeOnHisClaimMsg().contains(cC.IF_PLAYER_MUST_BE_ON_HIS_CLAIM_MSG)) cCConfig.set("ifPlayerMustBeOnHisClaimMsg", null);
		else cCConfig.set("ifPlayerMustBeOnHisClaimMsg", cC.getIfPlayerMustBeOnHisClaimMsg());

		if(cC.isIfPlayerMustBeOnHisPlot()) cCConfig.set("ifPlayerMustBeOnHisPlot", true);
		else cCConfig.set("ifPlayerMustBeOnHisPlot", null);
		if(cC.getIfPlayerMustBeOnHisPlotMsg().contains(cC.IF_PLAYER_MUST_BE_ON_HIS_PLOT_MSG)) cCConfig.set("ifPlayerMustBeOnHisPlotMsg", null);
		else cCConfig.set("ifPlayerMustBeOnHisPlotMsg", cC.getIfPlayerMustBeOnHisPlotMsg());

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
}
