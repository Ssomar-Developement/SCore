package com.ssomar.score.configs.messages;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.utils.StringConverter;

public class MessageMain {

	private static MessageMain instance;
	
	private String fileName = "";

	private Map<Plugin, Map<MessageInterface, String>> messages = new HashMap<>();

	public void load() {
		messages = new HashMap<>();
		SCore.plugin.getServer().getLogger().info(SCore.NAME_2+" Language setup on "+GeneralConfig.getInstance().getLocale());
		fileName = "/locale/Locale_"+GeneralConfig.getInstance().getLocale()+".yml";
	}
	
	public void loadMessagesOf(Plugin plugin, List<MessageInterface> messagesEnum) {
		
		Map<MessageInterface, String> formMessages = new HashMap<>();
		
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir(); 
		File pdfile = new File(plugin.getDataFolder(), fileName);
		if (!pdfile.exists()) {
			try {
				pdfile.getParentFile().mkdir();
				pdfile.createNewFile();	
				
				BufferedReader br = new BufferedReader(new InputStreamReader(plugin.getResource(fileName)));
			    String line;

			    Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pdfile), StandardCharsets.UTF_8));

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
				throw new RuntimeException("Unable to create the file: " + this.fileName +" for the plugin: "+plugin.getName(), e);
			} 
			catch (NullPointerException e) {/* locale */} 
		} 
		FileConfiguration config = YamlConfiguration.loadConfiguration(pdfile);
		
		for(MessageInterface msgI : messagesEnum) {
			formMessages.put(msgI, this.loadMessage(plugin, pdfile, config, msgI.getName()));
		}
		messages.put(plugin, formMessages);
	}
	
	
	
	public String loadMessage(Plugin plugin, File pdFile, FileConfiguration config, String message) {
		if(config.getString(message)!=null) return StringConverter.coloredString(config.getString(message));
		else return StringConverter.coloredString(write(plugin, pdFile, config, message));
	}

	public String write(Plugin plugin, File pdFile, FileConfiguration config, String what) {

		String insert="Can't load the string ("+what+") for the plugin > "+plugin.getName()+" in language: "+GeneralConfig.getInstance().getLocale()+", contact the developper";
		try{
			
			InputStream flux = plugin.getClass().getResourceAsStream("/com/ssomar/"+plugin.getName().toLowerCase()+"/configs/locale/Locale_"+GeneralConfig.getInstance().getLocale()+".yml");
			InputStreamReader lecture = new InputStreamReader(flux, StandardCharsets.UTF_8);
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			boolean isNotUpdate =true;
			while ((ligne = buff.readLine()) != null && isNotUpdate){
				if(ligne.contains(what+":")) {
					SCore.plugin.getServer().getLogger().info(SCore.NAME_2+" Update of "+what+" in your for the plugin > "+plugin.getName()+" in language: "+GeneralConfig.getInstance().getLocale());
					insert = ligne.split("\"")[1];
					config.set(what, insert);
					config.save(pdFile);
					isNotUpdate=false;
				}
			}
			buff.close(); 
			if(isNotUpdate) {
				SCore.plugin.getServer().getLogger().severe(SCore.NAME_2+" ERROR LOAD MESSAGE "+what+" for the plugin > "+plugin.getName()+" in language: "+GeneralConfig.getInstance().getLocale());
			}
		}		
		catch (Exception e){
			SCore.plugin.getServer().getLogger().severe(SCore.NAME_2+" ERROR LOAD MESSAGE ");
			e.printStackTrace();
		}

		return insert;
	}
	
	public String getMessage(Plugin plugin, MessageInterface message) {	
		if(messages.containsKey(plugin) && messages.get(plugin).containsKey(message)) {
			return messages.get(plugin).get(message);
		}
		return "";
	}
	
	public static MessageMain getInstance() {
		if (instance == null) instance = new MessageMain(); 
		return instance;
	}	
}
