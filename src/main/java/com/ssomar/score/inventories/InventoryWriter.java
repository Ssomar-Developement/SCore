package com.ssomar.score.inventories;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;

public class InventoryWriter {
	
	
	public static void writeNormalInv(Player p, Inventory inv) {
		write(p, inv, "normalInv");
	}
	
	public static void writeRunInv(Player p, Inventory inv) {
		write(p, inv, "runInv");
	}
	
	public static void writeWithoutArmorsNormalInv(Player p, Inventory inv) {
		writeWithoutArmors(p, inv, "normalInv");
	}
	
	public static void writeWithoutArmorsRunInv(Player p, Inventory inv) {
		writeWithoutArmors(p, inv, "runInv");
	}
	
public static void writeArmorsRunInv(Player p, Map<Integer, ItemStack> armors) {
		writeArmors(p, armors, "runInv");
	}
	
	public static void write(Player p, Inventory inv, String folder) {    	
    	File normalInvFolder = new File(SCore.getPlugin().getDataFolder() + "/"+folder);
    	normalInvFolder.mkdirs();

		File file = null;
			try {
				file = new File(SCore.getPlugin().getDataFolder() + "/"+folder+"/"+p.getName()+".yml");
				file.createNewFile();
			} catch (IOException e) {}

		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		config.set("inventory.36", null);
		config.set("inventory.37", null);
		config.set("inventory.38", null);
		config.set("inventory.39", null);
		
		int i = 0;
		for(ItemStack is : inv.getContents()) {
			if(is == null) {
				i++;
				continue;
			}

			config.set("inventory."+i+".slot", i);
			config.set("inventory."+i+".item", is);
			
			i++;
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
	
	public static void writeWithoutArmors(Player p, Inventory inv, String folder) {    	
    	File normalInvFolder = new File(SCore.getPlugin().getDataFolder() + "/"+folder);
    	normalInvFolder.mkdirs();

		File file = null;
			try {
				file = new File(SCore.getPlugin().getDataFolder() + "/"+folder+"/"+p.getName()+".yml");
				file.createNewFile();
			} catch (IOException e) {}

		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		int i = 0;
		for(ItemStack is : inv.getContents()) {
			if(is == null) {
				config.set("inventory."+i, null);
				i++;
				continue;
			}

			config.set("inventory."+i+".slot", i);
			config.set("inventory."+i+".item", is);
			
			i++;
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
	
	public static void writeArmors(Player p, Map<Integer, ItemStack> armors, String folder) {    	
    	File normalInvFolder = new File(SCore.getPlugin().getDataFolder() + "/"+folder);
    	normalInvFolder.mkdirs();

		File file = null;
			try {
				file = new File(SCore.getPlugin().getDataFolder() + "/"+folder+"/"+p.getName()+".yml");
				file.createNewFile();
			} catch (IOException e) {}

		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		
		for(int slot : armors.keySet()) {
			ItemStack is = armors.get(slot);
			if(is == null) {
				continue;
			}
		
			config.set("inventory."+slot+".slot", slot);
			config.set("inventory."+slot+".item", is);
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
