package com.ssomar.score.sobject.sactivator.requiredei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.configs.api.PlaceholderAPI;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class RequiredEIManager {


	/* GUI method */
	public void updateRequiredExecutableItems(GUI gui, String itemName, List<RequiredEI> rEIs) {

		List<String> convert = new ArrayList<>();
		for (RequiredEI rEI : rEIs) {
			convert.add(StringConverter.coloredString("&6➤ &eID: &a" + rEI.getEI_ID() + " &eQty: &a" + rEI.getAmount()));
			if (rEI.getValidUsages().isEmpty())
				convert.add(StringConverter.coloredString("  &eConsume?:&a " + rEI.isConsume() + " &eValidUsages: &aALL"));
			else
				convert.add(StringConverter.coloredString(
						"  &eConsume?: &a" + rEI.isConsume() + " &eValidUsages: &a" + rEI.getValidUsages().toString()));
		}
		ItemStack item = gui.getByName(itemName);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate = toChange.getLore().subList(0, 2);
		if (convert.isEmpty())
			loreUpdate.add(StringConverter.coloredString("&cEMPTY"));
		else
			loreUpdate.addAll(convert);
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}


	/* SAVE */

	public static void saveRequiredEI(SPlugin sPlugin, SObject sObject, SActivator activator, RequiredEI rEI) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the object ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+activator.getID());

		activatorConfig.set("requiredExecutableItems."+rEI.getId()+".id", rEI.getEI_ID());
		activatorConfig.set("requiredExecutableItems."+rEI.getId()+".amount", rEI.getAmount());
		activatorConfig.set("requiredExecutableItems."+rEI.getId()+".consume", rEI.isConsume());
		activatorConfig.set("requiredExecutableItems."+rEI.getId()+".validUsages", rEI.getValidUsages());

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

	/* DELETE */

	public static void deleteRequiredEI(SPlugin sPlugin, SObject sObject, SActivator sActivator, String id) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the object ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("requiredExecutableItems."+id, null);

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

	/* CONFIG */

	public static List<RequiredEI> getRequiredEIInConfig(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConfigurationSection activatorSection, List<String> error, boolean isDefaultObject){
		List<RequiredEI> requiredExecutableItems = new ArrayList<>();
		if (activatorSection.contains("requiredExecutableItems")) {
			if (activatorSection.contains("requiredExecutableItems")) {
				ConfigurationSection requiredEISection = activatorSection.getConfigurationSection("requiredExecutableItems");

				for (String rID : requiredEISection.getKeys(false)) {

					if (PlaceholderAPI.isLotOfWork() && !isDefaultObject) {
						error.add(sPlugin.getNameDesign()+" " + sObject.getID()+ " REQUIRE PREMIUM: required ExecutableItems is only in the premium version");
						break;
					}
					RequiredEI rEI = new RequiredEI(rID);
					rEI.setEI_ID(requiredEISection.getString(rID + ".id"));
					rEI.setAmount(requiredEISection.getInt(rID + ".amount", 1));
					rEI.setConsume(requiredEISection.getBoolean(rID + ".consume"));
					rEI.setValidUsages(requiredEISection.getIntegerList(rID + ".validUsages"));
					requiredExecutableItems.add(rEI);
				}
			}
		}
		return requiredExecutableItems;
	}
}
