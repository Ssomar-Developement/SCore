package com.ssomar.score.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class Config {
	protected File pdfile;

	protected FileConfiguration config;

	protected String fileName;

	protected Config(String fileName) {	  
		this.fileName = fileName;
	}

	public void setup(Plugin plugin) {
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir(); 
		this.pdfile = new File(plugin.getDataFolder(), this.fileName);
		if (!this.pdfile.exists()) {
			try {
				this.pdfile.getParentFile().mkdir();
				this.pdfile.createNewFile();	
				
				BufferedReader br = new BufferedReader(new InputStreamReader(plugin.getResource(this.fileName)));
			    String line;

			    Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.pdfile), "UTF-8"));

			    try {
			        while ((line = br.readLine()) != null) {
			            out.write(line);
			            out.write("\n");
			        }
			    } finally {
			        br.close();
			        out.close();
			    }

			} catch (IOException e) {
				throw new RuntimeException("Unable to create the file: " + this.fileName, e);
			} 
			catch (NullPointerException e) {/* locale */} 
		} 
		this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.pdfile);
		plugin.reloadConfig();
		load();
	}

	public abstract void load();

	public void save() {
		try {
			this.config.save(this.pdfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe("Could not save " + this.fileName + "!");
		} 
	}


	public FileConfiguration getConfig() {
		return this.config;
	}

	public String getFileName() {
		return this.fileName;
	}
}

