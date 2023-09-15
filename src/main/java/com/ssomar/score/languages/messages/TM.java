package com.ssomar.score.languages.messages;


import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.utils.logging.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TM {

    private static TM instance;

    private String fileName = "";

    private Map<TextInterface, String> messages = new HashMap<>();

    public static TM getInstance() {
        if (instance == null) instance = new TM();
        return instance;
    }

    public static TM i() {
        if (instance == null) instance = new TM();
        return instance;
    }

    public void load() {
        messages = new HashMap<>();
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Language of the editor setup on &6"+ GeneralConfig.getInstance().getLocale());
        fileName = "/languages/language_" + GeneralConfig.getInstance().getLocale() + ".yml";
    }

    public void loadTexts() {

        Map<TextInterface, String> formMessages = new HashMap<>();

        if (!SCore.plugin.getDataFolder().exists()) SCore.plugin.getDataFolder().mkdir();
        File pdfile = new File(SCore.plugin.getDataFolder(), fileName);
        if (!pdfile.exists()) {
            try {
                pdfile.getParentFile().mkdir();
                pdfile.createNewFile();

                BufferedReader br = new BufferedReader(new InputStreamReader(SCore.plugin.getResource(fileName)));
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
                throw new RuntimeException("Unable to create the file: " + this.fileName + " for the plugin: " + SCore.plugin.getName(), e);
            } catch (NullPointerException e) {/* locale */}
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(pdfile);

        for (TextInterface msgI : Text.values()) {
            formMessages.put(msgI, this.loadMessage(SCore.plugin, pdfile, config, msgI.getKey()));
        }
        messages = formMessages;
        /* print all keys values */
        /* for(TextInterface msgI : Text.values()) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7"+msgI.getKey()+" : "+messages.get(msgI));
        } */
    }

    public String loadMessage(Plugin plugin, File pdFile, FileConfiguration config, String message) {
        if (config.getString(message) != null) return config.getString(message);
        else return write(plugin, pdFile, config, message);
    }

    public String write(Plugin plugin, File pdFile, FileConfiguration config, String what) {

        String insert = "Can't load the string (" + what + ") for the plugin > " + plugin.getName() + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developper";
        try {

            // The for the moment only english
            String defaultLanguage = "EN";
            // defaultLanguage = GeneralConfig.getInstance().getLocale();

            InputStream flux = plugin.getClass().getResourceAsStream("/com/ssomar/" + plugin.getName().toLowerCase() + "/configs/languages/language_" + defaultLanguage.toLowerCase() + ".yml");
            InputStreamReader lecture = new InputStreamReader(flux, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(lecture);

            FileConfiguration real = YamlConfiguration.loadConfiguration(buff);
            boolean isNotUpdate = true;
            if(real.getString(what) != null){
                SCore.plugin.getServer().getLogger().info(SCore.NAME_2 + " Update of " + what + " in your for the plugin > " + plugin.getName() + " in language: " + GeneralConfig.getInstance().getLocale());
                insert = real.getString(what);
                config.set(what, insert);
                config.save(pdFile);
                isNotUpdate = false;
            }
            buff.close();
            if (isNotUpdate) {
                SCore.plugin.getServer().getLogger().severe(SCore.NAME_2 + " ERROR LOAD MESSAGE " + what + " for the plugin > " + plugin.getName() + " in language: " + GeneralConfig.getInstance().getLocale());
            }
        } catch (Exception e) {
            SCore.plugin.getServer().getLogger().severe(SCore.NAME_2 + " ERROR LOAD MESSAGE ");
            e.printStackTrace();
        }

        return insert;
    }

    public String getText(TextInterface message) {
        if(messages.containsKey(message)) return messages.get(message);
        else return "Can't load the string (" + message.getKey() + ") for the plugin > " + SCore.plugin.getName() + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developper";
    }

    public String [] getArray(TextInterface message) {
        if(messages.containsKey(message)) return new String[]{messages.get(message)};
        else return new String[]{"Can't load the string (" + message.getKey() + ") for the plugin > " + SCore.plugin.getName() + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developper"};
    }

    public static String g(TextInterface message) {
        return getInstance().getText(message);
    }

    public static String[] gA(TextInterface message) {
        return getInstance().getArray(message);
    }
}
