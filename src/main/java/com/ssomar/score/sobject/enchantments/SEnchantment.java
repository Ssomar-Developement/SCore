package com.ssomar.score.sobject.enchantments;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.io.*;

public class SEnchantment {

	private Enchantment enchantment;

	private String id;

	public SEnchantment(Enchantment enchantment, String id) {
		this.enchantment = enchantment;
		this.id = id;
	}

	
	public Enchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment enchantment) {
		this.enchantment = enchantment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void delete(SPlugin sPlugin, SObject sObject){
		deleteEnchantment(sPlugin, sObject, id);
	}

	public void saveEnchantment(SPlugin sPlugin, SObject sObject, int level) {

		// TODO not general string
		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the item in the folder items ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		config.set("enchantments."+id, null);

			if (!SCore.is1v12()) {
				config.set("enchantments." + id + ".enchantment", enchantment.getKey().toString().split("minecraft:")[1]);
			} else config.set("enchantments." +id + ".enchantment", enchantment.getName());
			config.set("enchantments." + id + ".level", level);

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

	public static void deleteEnchantment(SPlugin sPlugin, SObject sObject, String id) {

		// TODO not general string
		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the item in the folder items ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		config.set("enchantments."+id, null);

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

	//TODO TRANSFER ALL ADD / create / import / delete method realted to SEnchant here
}
