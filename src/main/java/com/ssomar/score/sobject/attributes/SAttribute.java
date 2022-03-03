package com.ssomar.score.sobject.attributes;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class SAttribute {
	
	private Attribute attribute;
	
	private String id;

	public SAttribute(Attribute attribute, String id) {
		super();
		this.attribute = attribute;
		this.id = id;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public void delete(SPlugin sPlugin, SObject sObject) {
		deleteAttribute(sPlugin, sObject, id);
	}

	public void saveAttribute(SPlugin sPlugin, SObject sObject, AttributeModifier attm) {

		if(!new File(sObject.getPath()).exists()) {
			SCore.plugin.getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the item in the folder items ! ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		config.set("attributes."+id, null);

			config.set("attributes." + id + ".attribute", attribute.toString());
			config.set("attributes." + id + ".name", attm.getName());
			config.set("attributes." + id + ".uuid", attm.getUniqueId().toString());
			config.set("attributes." + id + ".amount", attm.getAmount());
			config.set("attributes." + id + ".operation", attm.getOperation().toString());
			if (attm.getSlot() != null) config.set("attributes." + id + ".slot", attm.getSlot().toString());
			else config.set("attributes." + id + ".slot", null);
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

	public static void deleteAttribute(SPlugin sPlugin, SObject sObject, String id) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getPlugin()+" Error can't find the file of the item in the folder items ! ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		config.set("attributes."+id, null);

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
