package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public class BlockConditions extends Conditions{

	private boolean ifPlantFullyGrown;
	public static final String IF_PLANT_FULLY_GROWN_MSG = " &cThe plant must be fully grown to active the activator: &6%activator% &cof this item!";
	private String ifPlantFullyGrownMsg;
	
	private boolean ifIsPowered;
	public static final String IF_IS_POWERED_MSG = " &cThe must be powered by redstone to active the activator: &6%activator% &cof this item!";
	private String ifIsPoweredMsg;
	
	@Override
	public void init() {
		this.ifPlantFullyGrown = false;
		this.ifPlantFullyGrownMsg = "";	
		
		this.ifIsPowered = false;
		this.ifIsPoweredMsg = "";	
	}

	public boolean verifConditions(Block b, Player p) {

		if(this.isIfPlantFullyGrown() && b.getState().getBlockData() instanceof Ageable) {
			Ageable ageable = (Ageable)b.getState().getBlockData();
			if(ageable.getAge()!=ageable.getMaximumAge()) {
				this.getSm().sendMessage(p, this.getIfPlantFullyGrownMsg());
				return false;
			}
		}
		
		if(this.ifIsPowered) {
			if(!b.isBlockPowered()) {
				this.getSm().sendMessage(p, this.getIfIsPoweredMsg());
				return false;
			}
		}

		return true;
	}
	
	public static BlockConditions getBlockConditions(ConfigurationSection blockCdtSection, List<String> errorList, String pluginName) {

		BlockConditions bCdt = new BlockConditions();

		bCdt.setIfPlantFullyGrown(blockCdtSection.getBoolean("ifPlantFullyGrown", false));
		bCdt.setIfPlantFullyGrownMsg(blockCdtSection.getString("ifPlantFullyGrownMsg", "&4&l"+pluginName+IF_PLANT_FULLY_GROWN_MSG));
		
		bCdt.setIfIsPowered(blockCdtSection.getBoolean("ifIsPowered", false));
		bCdt.setIfIsPoweredMsg(blockCdtSection.getString("ifIsPoweredMsg", "&4&l"+pluginName+IF_IS_POWERED_MSG));

		return bCdt;
	}
	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param bC the blockm conditions object
	 */
	public static void saveBlockConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifDurability", ">50");


		ConfigurationSection pCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(bC.isIfPlantFullyGrown()) pCConfig.set("ifPlantFullyGrown", true); 
		else pCConfig.set("ifPlantFullyGrown", null);
		pCConfig.set("ifPlantFullyGrownMsg", bC.getIfPlantFullyGrownMsg()); 
		
		if(bC.isIfPlantFullyGrown()) pCConfig.set("ifIsPowered", true); 
		else pCConfig.set("ifIsPowered", null);
		pCConfig.set("ifIsPoweredMsg", bC.getIfIsPoweredMsg()); 

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


	public boolean isIfPlantFullyGrown() {
		return ifPlantFullyGrown;
	}

	public void setIfPlantFullyGrown(boolean ifPlantFullyGrown) {
		this.ifPlantFullyGrown = ifPlantFullyGrown;
	}

	public String getIfPlantFullyGrownMsg() {
		return ifPlantFullyGrownMsg;
	}

	public void setIfPlantFullyGrownMsg(String ifPlantFullyGrownMsg) {
		this.ifPlantFullyGrownMsg = ifPlantFullyGrownMsg;
	}

	public boolean isIfIsPowered() {
		return ifIsPowered;
	}

	public void setIfIsPowered(boolean ifIsPowered) {
		this.ifIsPowered = ifIsPowered;
	}

	public String getIfIsPoweredMsg() {
		return ifIsPoweredMsg;
	}

	public void setIfIsPoweredMsg(String ifIsPoweredMsg) {
		this.ifIsPoweredMsg = ifIsPoweredMsg;
	}

}
