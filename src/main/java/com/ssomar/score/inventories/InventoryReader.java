package com.ssomar.score.inventories;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;

public class InventoryReader {
	
	
	public static void autoUpdateSavedNormalInv(Player p) {
		autoUpdateSavedInv(p, "normalInv");
	}
	
	public static void autoUpdateSavedRunInv(Player p) {
		autoUpdateSavedInv(p, "runInv");
	}
	
	public static Inventory getSavedNormalInv(Player p) {
		return getSavedInv(p, "normalInv");
	}
	
	public static Inventory getSavedRunInv(Player p) {
		return getSavedInv(p, "runInv");
	}
	
	public static Map<Integer, ItemStack> getSavedArmorNormalInv(Player p) {
		return getSavedArmorsInv(p, "normalInv");
	}
	

	public static void autoUpdateSavedInv(Player p, String folder) {    	

		Inventory inv = p.getInventory();
		inv.clear();

		File file = new File(SCore.getPlugin().getDataFolder() + "/"+folder+"/"+p.getName()+".yml");
		if(file.exists()) {
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			if(config.contains("inventory")) {

				ConfigurationSection invSection = config.getConfigurationSection("inventory");

				inv.setItem(36, null);
				inv.setItem(37, null);
				inv.setItem(38, null);
				inv.setItem(39, null);
				
				for(String s : invSection.getKeys(false)) {

					ConfigurationSection detailsSection = invSection.getConfigurationSection(s);

					int slot = detailsSection.getInt("slot", 0);

					ItemStack is = detailsSection.getItemStack("item");

					inv.setItem(slot, is);
				}

				config.set("inventory", null);
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
	}
	
	public static Inventory getSavedInv(Player p, String folder) {    	

		Inventory inv = Bukkit.createInventory(null, 4*9, "");

		File file = new File(SCore.getPlugin().getDataFolder() + "/"+folder+"/"+p.getName()+".yml");
		if(file.exists()) {
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			if(config.contains("inventory")) {

				ConfigurationSection invSection = config.getConfigurationSection("inventory");
				
				for(String s : invSection.getKeys(false)) {

					ConfigurationSection detailsSection = invSection.getConfigurationSection(s);

					int slot = detailsSection.getInt("slot", 0);

					ItemStack is = detailsSection.getItemStack("item");

					if(slot != 36 
							&& slot != 37
							&& slot != 38
							&& slot != 38)
					inv.setItem(slot, is);
				}
			}
		}
		
		return inv;
	}
	
	public static Map<Integer, ItemStack> getSavedArmorsInv(Player p, String folder) {    	

		Map<Integer, ItemStack> armors = new HashMap<>();
		
		File file = new File(SCore.getPlugin().getDataFolder() + "/"+folder+"/"+p.getName()+".yml");
		if(file.exists()) {
			FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
			if(config.contains("inventory")) {

				ConfigurationSection invSection = config.getConfigurationSection("inventory");

				for(String s : invSection.getKeys(false)) {

					ConfigurationSection detailsSection = invSection.getConfigurationSection(s);

					ItemStack is = detailsSection.getItemStack("item");
					
					int slot = detailsSection.getInt("slot", 0);
					if(slot == 36 || slot == 37 || slot == 38 || slot == 39) {
						armors.put(slot, is);
					}
				}

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
		
		return armors;
	}

	
	
}
