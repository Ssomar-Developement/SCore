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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.configs.Message;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;

public class ItemConditions extends Conditions{

	//Item
	private String ifDurability;
	private static final String IF_DURABILITY_MSG = " &cThis item must haven't a valid durability to active the activator: &6%activator% &cof this item!";
	private String ifDurabilityMsg;
	
	private String ifUsage;
	private static final String IF_USAGE_MSG = " &cThis item must haven't a valid usage to active the activator: &6%activator% &cof this item!";
	private String ifUsageMsg;

	private String ifUsage2;
	private String ifUsage2Msg;

	
	//private String ifKillWithItem="";

	//private String ifBlockBreakWithItem="";
	
	public ItemConditions() {
		this.ifDurability = "";
		this.ifDurabilityMsg = "";
		
		this.ifUsage = "";
		this.ifUsageMsg = "";
		
		this.ifUsage2 = "";
		this.ifUsage2Msg = "";
	}

	
	@SuppressWarnings("deprecation")
	public boolean verifConditions(ItemStack i, Item infoItem, Player p) {		
		if(this.hasIfDurability()) {
			if(!StringCalculation.calculation(this.ifDurability, i.getDurability())) {
				this.getSm().sendMessage(p, this.getIfDurabilityMsg());
				return false;
			}
		}
		
		if(this.hasIfUsage()) {
			ItemMeta itemMeta =i.getItemMeta();
			List<String> lore = itemMeta.getLore();
			int usage;

			if(lore.get(lore.size()-1).contains(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))) usage= Integer.valueOf(lore.get(lore.size()-1).split(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))[1]);
			else if(infoItem.getUse() == -1) usage = -1;
			else usage = 1;
			
			if(!StringCalculation.calculation(this.ifUsage, usage)) {
				this.getSm().sendMessage(p, this.getIfUsageMsg());
				return false;
			}
		}
		
		if(this.hasIfUsage2()) {
			ItemMeta itemMeta =i.getItemMeta();
			List<String> lore = itemMeta.getLore();
			int usage2;

			if(lore.get(lore.size()-1).contains(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))) usage2= Integer.valueOf(lore.get(lore.size()-1).split(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))[1]);
			else if(infoItem.getUse() == -1) usage2 = -1;
			else usage2 = 1;
			
			if(!StringCalculation.calculation(this.ifUsage2, usage2)) {
				this.getSm().sendMessage(p, this.getIfUsage2Msg());
				return false;
			}
		}
			
		return true;
	}
	
	public static ItemConditions getItemConditions(ConfigurationSection itemCdtSection, List<String> errorList, String pluginName) {

		ItemConditions iCdt = new ItemConditions();

		iCdt.setIfDurability(itemCdtSection.getString("ifDurability", ""));
		iCdt.setIfDurabilityMsg(itemCdtSection.getString("ifDurabilityMsg", "&4&l"+pluginName+IF_DURABILITY_MSG));

		iCdt.setIfUsage(itemCdtSection.getString("ifUsage", ""));
		iCdt.setIfUsageMsg(itemCdtSection.getString("ifUsageMsg", "&4&l"+pluginName+IF_USAGE_MSG));

		iCdt.setIfUsage2(itemCdtSection.getString("ifUsage2", ""));
		iCdt.setIfUsage2Msg(itemCdtSection.getString("ifUsage2Msg", "&4&l"+pluginName+IF_USAGE_MSG));

		
		return iCdt;
	}
	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param iC the item conditions object
	 */
	public static void saveItemConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions iC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifDurability", ">50");


		ConfigurationSection pCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(iC.hasIfDurability()) pCConfig.set("ifDurability", iC.getIfDurability()); 
		else pCConfig.set("ifDurability", null);
		
		if(iC.hasIfUsage()) pCConfig.set("ifUsage", iC.getIfUsage()); 
		else pCConfig.set("ifUsage", null);
		
		if(iC.hasIfUsage2()) pCConfig.set("ifUsage2", iC.getIfUsage2()); 
		else pCConfig.set("ifUsage2", null);


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


	public String getIfDurability() {
		return ifDurability;
	}
	public void setIfDurability(String ifDurability) {
		this.ifDurability = ifDurability;
	}
	public boolean hasIfDurability() {
		return ifDurability.length()!=0;
	}

	public String getIfUsage() {
		return ifUsage;
	}
	public void setIfUsage(String ifUsage) {
		this.ifUsage = ifUsage;
	}
	public boolean hasIfUsage() {
		return ifUsage.length()!=0;
	}
	
	public String getIfUsage2() {
		return ifUsage2;
	}
	public void setIfUsage2(String ifUsage2) {
		this.ifUsage2 = ifUsage2;
	}
	public boolean hasIfUsage2() {
		return ifUsage2.length()!=0;
	}


	public String getIfDurabilityMsg() {
		return ifDurabilityMsg;
	}
	public void setIfDurabilityMsg(String ifDurabilityMsg) {
		this.ifDurabilityMsg = ifDurabilityMsg;
	}
	public String getIfUsageMsg() {
		return ifUsageMsg;
	}
	public String getIfUsage2Msg() {
		return ifUsage2Msg;
	}
	public void setIfUsageMsg(String ifUsageMsg) {
		this.ifUsageMsg = ifUsageMsg;
	}
	public void setIfUsage2Msg(String ifUsage2Msg) {
		this.ifUsage2Msg = ifUsage2Msg;
	}
	
	
	
}
