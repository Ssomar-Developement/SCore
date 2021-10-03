package com.ssomar.score.sobject.attributes;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.attribute.Attribute;
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

	public static void deleteAttribute(SPlugin sPlugin, SObject sObject, String id) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getPlugin()+" Error can't find the file of the item in the folder items ! ("+sObject.getID()+".yml)");
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
