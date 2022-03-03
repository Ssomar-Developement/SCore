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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.configs.Message;
import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.SCore;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;

public class RequiredEIManager {


	/* GUI method */
	public static void updateRequiredExecutableItems(GUI gui, String itemName, List<RequiredEI> rEIs) {

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
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the object ! ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

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
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the object ! ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

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

	public static List<RequiredEI> getRequiredEIInConfig(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConfigurationSection activatorSection, List<String> error, boolean isPremiumLoading){
		List<RequiredEI> requiredExecutableItems = new ArrayList<>();
		if (activatorSection.contains("requiredExecutableItems")) {
			if (activatorSection.contains("requiredExecutableItems")) {
				ConfigurationSection requiredEISection = activatorSection.getConfigurationSection("requiredExecutableItems");

				for (String rID : requiredEISection.getKeys(false)) {

					if (!isPremiumLoading) {
						error.add(sPlugin.getNameDesign()+" " + sObject.getId()+ " REQUIRE PREMIUM: required ExecutableItems is only in the premium version");
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

	/* VERIF */

	public static boolean verifyRequiredExecutableItems(SActivator activator, Player p, String errorMsg) {

		if(SCore.hasExecutableItems && activator.getRequiredExecutableItems() != null) {
			boolean oneIsNeeded = false;
			StringBuilder sb = new StringBuilder();

			sb.append(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.REQUIRED_EI_FIRST_PART));
			for(RequiredEI rEI : activator.getRequiredExecutableItems()) {

				if(rEI.getItem()==null) {
					if(!ItemManager.getInstance().containsLoadedItemWithID(rEI.getEI_ID())) continue;
					else rEI.setItem(ItemManager.getInstance().getLoadedItemWithID(rEI.getEI_ID()));
				}

				int needed = rEI.getAmount();
				List<Integer> validUsages = rEI.getValidUsages();

				for(ItemStack it : p.getInventory().getContents()) {
					if(it==null) continue;
					Item cIt = null;
					if((cIt = ItemManager.getInstance().getExecutableItem(it)) != null && rEI.getEI_ID().equals(cIt.getIdentification())){
						if(validUsages.isEmpty() || cIt.getUse()==0 || cIt.getUse()==-1) {
							if(needed<=it.getAmount()) {
								needed=0;
							}else {
								needed=needed-it.getAmount();
							}
						}
						else {
							ItemMeta itemMeta2=it.getItemMeta();
							List<String> lore= itemMeta2.getLore();

							if(lore.get(lore.size()-1).contains(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))) {
								int use= Integer.parseInt(lore.get(lore.size()-1).split(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))[1]);
								if(validUsages.contains(use)) {
									if(needed<=it.getAmount()) {
										needed=0;
									}else {
										needed=needed-it.getAmount();
									}
								}
							}
						}
					}
				}
				if(needed>0) {
					if(rEI.getValidUsages().isEmpty() || rEI.getItem().getUse() == 0 || rEI.getItem().getUse() == -1) {
						sb.append("&c")
								.append(rEI.getItem().getName())
								.append(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.REQUIRED_EI_QUANTITY))
								.append(needed)
								.append(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.REQUIRED_EI_SEPARATOR));
					}
					else {
						sb.append("&c")
								.append(rEI.getItem().getName())
								.append(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.REQUIRED_EI_QUANTITY))
								.append(needed)
								.append(" &8&o(Require '")
								.append(StringConverter.decoloredString(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE)))
								.append("' &7&o").append(rEI.getValidUsages().toString()).append("&8&o)")
								.append(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.REQUIRED_EI_SEPARATOR));
					}
					oneIsNeeded = true;
				}

			}
			if(oneIsNeeded) {
				for(int k=0; k<MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.REQUIRED_EI_SEPARATOR).length();k++) {
					sb.deleteCharAt(sb.length()-1);
				}
				if(!errorMsg.isEmpty()) new SendMessage().sendMessage(p, StringConverter.coloredString(errorMsg));
				else new SendMessage().sendMessage(p, StringConverter.coloredString(sb.toString()));
				return false;
			}
		}
		return true;
	}

	/* TAKE */

	public static void takeExecutableItems(SActivator activator, Player p) {
		if(SCore.hasExecutableItems && activator.getRequiredExecutableItems() != null) {

			for(RequiredEI rEI : activator.getRequiredExecutableItems()) {

				if(rEI.getItem()==null) {
					if(!ItemManager.getInstance().containsLoadedItemWithID(rEI.getEI_ID())) continue;
					else rEI.setItem(ItemManager.getInstance().getLoadedItemWithID(rEI.getEI_ID()));
				}

				if(!rEI.isConsume()) continue;
				int needed = rEI.getAmount();
				List<Integer> validUsages = rEI.getValidUsages();

				for(ItemStack it : p.getInventory().getContents()) {
					if(it == null) continue;
					Item cIt = null;
					if(ItemManager.getInstance().getExecutableItem(it) != null){
						cIt = ItemManager.getInstance().getExecutableItem(it);
						if(rEI.getEI_ID().equals(cIt.getIdentification())) {
							if(validUsages.isEmpty() || cIt.getUse()==0 || cIt.getUse()==-1) {
								if(needed<=it.getAmount()) {
									it.setAmount(it.getAmount()-needed);
									break;
								}else {
									it.setAmount(0);
									needed=needed-it.getAmount();
								}
							}
							else {
								ItemMeta itemMeta2 = it.getItemMeta();
								List<String> lore = itemMeta2.getLore();

								if(lore.get(lore.size()-1).contains(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))) {
									int use= Integer.parseInt(lore.get(lore.size()-1).split(MessageMain.getInstance().getMessage(ExecutableItems.plugin, Message.USE))[1]);
									if(validUsages.contains(use)) {
										if(needed<=it.getAmount()) {
											it.setAmount(it.getAmount()-needed);
											break;
										}else {
											it.setAmount(0);
											needed=needed-it.getAmount();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
