package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.score.utils.messages.MessageDesign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import org.jetbrains.annotations.Nullable;

public class ItemConditions extends Conditions{

	//Item
	private String ifDurability;
	private static final String IF_DURABILITY_MSG = " &cThis item must have a valid durability to active the activator: &6%activator% &cof this item!";
	private String ifDurabilityMsg;
	
	private String ifUsage;
	private static final String IF_USAGE_MSG = " &cThis item must have the valid usage to active the activator: &6%activator% &cof this item!";
	private String ifUsageMsg;

	private String ifUsage2;
	private String ifUsage2Msg;

	private Map<Enchantment, Integer> ifHasEnchant;
	private static final String IF_HAS_ENCHANT_MSG = " &cThis item must have the good enchantments to active the activator: &6%activator% &cof this item!";
	private String ifHasEnchantMsg;

	private Map<Enchantment, Integer> ifHasNotEnchant;
	private static final String IF_HAS_NOT_ENCHANT_MSG = " &cThis item must have the good enchantments to active the activator: &6%activator% &cof this item!";
	private String ifHasNotEnchantMsg;

	private boolean ifCrossbowMustBeCharged;
	private static final String IF_CROSSBOW_MUST_BE_CHARGED_MSG = " &cThis crossbow must be charged to active the activator: &6%activator% &cof this item!";
	private String ifCrossbowMustBeChargedMsg;

	private boolean ifCrossbowMustNotBeCharged;
	private static final String IF_CROSSBOW_MUST_NOT_BE_CHARGED_MSG = " &cThis crossbow must not be charged to active the activator: &6%activator% &cof this item!";
	private String ifCrossbowMustNotBeChargedMsg;

	
	//private String ifKillWithItem="";

	//private String ifBlockBreakWithItem="";
	
	@Override
	public void init() {
		this.ifDurability = "";
		this.ifDurabilityMsg = IF_DURABILITY_MSG;
		
		this.ifUsage = "";
		this.ifUsageMsg = IF_USAGE_MSG;
		
		this.ifUsage2 = "";
		this.ifUsage2Msg = IF_USAGE_MSG;

		this.ifHasEnchant = new HashMap<>();
		this.ifHasEnchantMsg = IF_HAS_ENCHANT_MSG;

		this.ifHasNotEnchant = new HashMap<>();
		this.ifHasNotEnchantMsg = IF_HAS_NOT_ENCHANT_MSG;

		this.ifCrossbowMustBeCharged = false;
		this.ifCrossbowMustBeChargedMsg = IF_CROSSBOW_MUST_BE_CHARGED_MSG;

		this.ifCrossbowMustNotBeCharged = false;
		this.ifCrossbowMustNotBeChargedMsg = IF_CROSSBOW_MUST_NOT_BE_CHARGED_MSG;
	}
	
	@SuppressWarnings("deprecation")
	public boolean verifConditions(@Nullable ExecutableItem ei, @Nullable ItemStack item, Player p) {

		ItemStack i;
		if(ei != null) i = ei.getItem();
		else i = item;

		ItemMeta itemMeta = null;
		boolean hasItemMeta = i.hasItemMeta();
		if(hasItemMeta) itemMeta = i.getItemMeta();

		if(this.hasIfDurability()) {
			if(!StringCalculation.calculation(this.ifDurability, i.getDurability())) {
				this.getSm().sendMessage(p, this.getIfDurabilityMsg());
				return false;
			}
		}
		
		if(this.hasIfUsage() && ei != null) {

			int usage = ei.getUsage();
			
			if(!StringCalculation.calculation(this.ifUsage, usage)) {
				this.getSm().sendMessage(p, this.getIfUsageMsg());
				return false;
			}
		}



		if(this.hasIfUsage2() && ei != null) {

			int usage2 = ei.getUsage();
			
			if(!StringCalculation.calculation(this.ifUsage2, usage2)) {
				this.getSm().sendMessage(p, this.getIfUsage2Msg());
				return false;
			}
		}

		if(this.ifHasEnchant.size() != 0){
			if(!hasItemMeta) return false;
			Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
			for(Enchantment enchant : ifHasEnchant.keySet()){
				if(!enchants.containsKey(enchant) || !Objects.equals(ifHasEnchant.get(enchant), enchants.get(enchant))) return false;
			}
		}

		if(this.ifHasNotEnchant.size() != 0){
			if(!hasItemMeta) return false;
			Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
			for(Enchantment enchant : ifHasNotEnchant.keySet()){
				if(enchants.containsKey(enchant) && ifHasNotEnchant.get(enchant).equals(enchants.get(enchant))) return false;
			}
		}

		if(ifCrossbowMustBeCharged || ifCrossbowMustNotBeCharged && i.getType().toString().contains("CROSSBOW")){
			if(hasItemMeta && itemMeta instanceof CrossbowMeta){
				CrossbowMeta cMeta = (CrossbowMeta)itemMeta;
				boolean charged = cMeta.hasChargedProjectiles();
				if(charged && ifCrossbowMustNotBeCharged){
					this.getSm().sendMessage(p, this.getIfCrossbowMustNotBeChargedMsg());
					return false;
				}

				if(!charged && ifCrossbowMustBeCharged){
					this.getSm().sendMessage(p, this.getIfCrossbowMustBeChargedMsg());
					return false;
				}
			}
		}

			
		return true;
	}
	
	public static ItemConditions getItemConditions(ConfigurationSection itemCdtSection, List<String> errorList, String pluginName) {

		ItemConditions iCdt = new ItemConditions();

		iCdt.setIfDurability(itemCdtSection.getString("ifDurability", ""));
		iCdt.setIfDurabilityMsg(itemCdtSection.getString("ifDurabilityMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_DURABILITY_MSG));

		iCdt.setIfUsage(itemCdtSection.getString("ifUsage", ""));
		iCdt.setIfUsageMsg(itemCdtSection.getString("ifUsageMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_USAGE_MSG));

		iCdt.setIfUsage2(itemCdtSection.getString("ifUsage2", ""));
		iCdt.setIfUsage2Msg(itemCdtSection.getString("ifUsage2Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_USAGE_MSG));

		Map<Enchantment, Integer> hasEnchants = transformEnchants(itemCdtSection.getStringList("ifHasEnchant"));
		iCdt.setIfHasEnchant(hasEnchants);
		iCdt.setIfHasEnchantMsg(itemCdtSection.getString("ifHasEnchantMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_HAS_ENCHANT_MSG));

		Map<Enchantment, Integer> hasNotEnchants = transformEnchants(itemCdtSection.getStringList("ifHasNotEnchant"));
		iCdt.setIfHasNotEnchant(hasNotEnchants);
		iCdt.setIfHasNotEnchantMsg(itemCdtSection.getString("ifHasNotEnchantMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_HAS_NOT_ENCHANT_MSG));

		iCdt.setIfCrossbowMustBeCharged(itemCdtSection.getBoolean("ifCrossbowMustBeCharged", false));
		iCdt.setIfCrossbowMustBeChargedMsg(itemCdtSection.getString("ifCrossbowMustBeChargedMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+ IF_CROSSBOW_MUST_BE_CHARGED_MSG));

		iCdt.setIfCrossbowMustNotBeCharged(itemCdtSection.getBoolean("ifCrossbowMustNotBeCharged", false));
		iCdt.setIfCrossbowMustNotBeChargedMsg(itemCdtSection.getString("ifCrossbowMustNotBeChargedMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+ IF_CROSSBOW_MUST_NOT_BE_CHARGED_MSG));

		return iCdt;
	}

	public static Map<Enchantment, Integer> transformEnchants(List<String> enchantsConfig){
		Map<Enchantment, Integer> result = new HashMap<>();
		for(String s : enchantsConfig){
			Enchantment enchant;
			int level;
			String [] decomp;

			if(s.contains(":")){
				decomp = s.split(":");
				try {
					enchant = Enchantment.getByName(decomp[0]);
					level = Integer.parseInt(decomp[1]);
					result.put(enchant, level);
				}catch(Exception e){ e.printStackTrace();}
			}
		}
		return result;
	}
	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param iC the item conditions object
	 */
	@SuppressWarnings("deprecation")
	public static void saveItemConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions iC, String detail) {

		if (!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign() + " Error can't find the file in the folder ! (" + sObject.getId() + ".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators." + sActivator.getID());
		activatorConfig.set("conditions." + detail + ".ifDurability", ">50");

		String pluginName = sPlugin.getNameDesign();

		ConfigurationSection pCConfig = config.getConfigurationSection("activators." + sActivator.getID() + ".conditions." + detail);

		if (iC.hasIfDurability()) pCConfig.set("ifDurability", iC.getIfDurability());
		else pCConfig.set("ifDurability", null);
		if(iC.getIfDurabilityMsg().contains(iC.IF_DURABILITY_MSG)) pCConfig.set("ifDurabilityMsg", null);
		else pCConfig.set("ifDurabilityMsg", iC.getIfDurabilityMsg());

		if (iC.hasIfUsage()) pCConfig.set("ifUsage", iC.getIfUsage());
		else pCConfig.set("ifUsage", null);
		if(iC.getIfUsageMsg().contains(iC.IF_USAGE_MSG)) pCConfig.set("ifUsageMsg", null);
		else pCConfig.set("ifUsageMsg", iC.getIfUsageMsg());

		if (iC.hasIfUsage2()) pCConfig.set("ifUsage2", iC.getIfUsage2());
		else pCConfig.set("ifUsage2", null);
		if(iC.getIfUsage2Msg().contains(iC.IF_USAGE_MSG)) pCConfig.set("ifUsage2Msg", null);
		else pCConfig.set("ifUsage2Msg", iC.getIfUsage2Msg());

		if (iC.ifHasEnchant.size() != 0) {
			List<String> result = new ArrayList<>();
			for(Enchantment enchant : iC.ifHasEnchant.keySet()){
				result.add(enchant.getName() +":"+iC.ifHasEnchant.get(enchant));
			}
			pCConfig.set("ifHasEnchant", result);
		}
		else pCConfig.set("ifHasEnchant", null);
		if(iC.getIfHasEnchantMsg().contains(iC.IF_HAS_ENCHANT_MSG)) pCConfig.set("ifHasEnchantMsg", null);
		else pCConfig.set("ifHasEnchantMsg", iC.getIfHasEnchantMsg());

		if (iC.ifHasNotEnchant.size() != 0) {
			List<String> result = new ArrayList<>();
			for(Enchantment enchant : iC.ifHasNotEnchant.keySet()){
				result.add(enchant.getName() +":"+iC.ifHasNotEnchant.get(enchant));
			}
			pCConfig.set("ifHasNotEnchant", result);
		}
		else pCConfig.set("ifHasNotEnchant", null);
		if(iC.getIfHasNotEnchantMsg().contains(iC.IF_HAS_NOT_ENCHANT_MSG)) pCConfig.set("ifHasNotEnchantMsg", null);
		else pCConfig.set("ifHasNotEnchantMsg", iC.getIfHasNotEnchantMsg());

		if (iC.isIfCrossbowMustBeCharged()) pCConfig.set("ifCrossbowMustBeCharged", iC.isIfCrossbowMustBeCharged());
		else pCConfig.set("ifCrossbowMustBeCharged", null);
		if(iC.getIfCrossbowMustBeChargedMsg().contains(iC.IF_CROSSBOW_MUST_BE_CHARGED_MSG)) pCConfig.set("ifCrossbowMustBeChargedMsg", null);
		else pCConfig.set("ifCrossbowMustBeChargedMsg", iC.getIfCrossbowMustBeChargedMsg());

		if (iC.isIfCrossbowMustNotBeCharged()) pCConfig.set("ifCrossbowMustNotBeCharged", iC.isIfCrossbowMustNotBeCharged());
		else pCConfig.set("ifCrossbowMustNotBeCharged", null);
		if(iC.getIfCrossbowMustNotBeChargedMsg().contains(iC.IF_CROSSBOW_MUST_NOT_BE_CHARGED_MSG)) pCConfig.set("ifCrossbowMustNotBeChargedMsg", null);
		else pCConfig.set("ifCrossbowMustNotBeChargedMsg", iC.getIfCrossbowMustNotBeChargedMsg());

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

	public Map<Enchantment, Integer> getIfHasEnchant() {
		return ifHasEnchant;
	}

	public void setIfHasEnchant(Map<Enchantment, Integer> ifHasEnchant) {
		this.ifHasEnchant = ifHasEnchant;
	}

	public String getIfHasEnchantMsg() {
		return ifHasEnchantMsg;
	}

	public void setIfHasEnchantMsg(String ifHasEnchantMsg) {
		this.ifHasEnchantMsg = ifHasEnchantMsg;
	}

	public Map<Enchantment, Integer> getIfHasNotEnchant() {
		return ifHasNotEnchant;
	}

	public void setIfHasNotEnchant(Map<Enchantment, Integer> ifHasNotEnchant) {
		this.ifHasNotEnchant = ifHasNotEnchant;
	}

	public String getIfHasNotEnchantMsg() {
		return ifHasNotEnchantMsg;
	}

	public void setIfHasNotEnchantMsg(String ifHasNotEnchantMsg) {
		this.ifHasNotEnchantMsg = ifHasNotEnchantMsg;
	}

	public boolean isIfCrossbowMustBeCharged() {
		return ifCrossbowMustBeCharged;
	}

	public void setIfCrossbowMustBeCharged(boolean ifCrossbowMustBeCharged) {
		this.ifCrossbowMustBeCharged = ifCrossbowMustBeCharged;
	}

	public String getIfCrossbowMustBeChargedMsg() {
		return ifCrossbowMustBeChargedMsg;
	}

	public void setIfCrossbowMustBeChargedMsg(String ifCrossbowMustBeChargedMsg) {
		this.ifCrossbowMustBeChargedMsg = ifCrossbowMustBeChargedMsg;
	}

	public boolean isIfCrossbowMustNotBeCharged() {
		return ifCrossbowMustNotBeCharged;
	}

	public void setIfCrossbowMustNotBeCharged(boolean ifCrossbowMustNotBeCharged) {
		this.ifCrossbowMustNotBeCharged = ifCrossbowMustNotBeCharged;
	}

	public String getIfCrossbowMustNotBeChargedMsg() {
		return ifCrossbowMustNotBeChargedMsg;
	}

	public void setIfCrossbowMustNotBeChargedMsg(String ifCrossbowMustNotBeChargedMsg) {
		this.ifCrossbowMustNotBeChargedMsg = ifCrossbowMustNotBeChargedMsg;
	}
}
