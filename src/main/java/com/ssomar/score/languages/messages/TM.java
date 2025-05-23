package com.ssomar.score.languages.messages;


import com.ssomar.score.SCore;
import com.ssomar.score.config.Config;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.logging.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TM {

    private static TM instance;

    private String fileName = "";

    private Map<TextInterface, String> messages = new HashMap<>();
    private Map<TextInterface, String[]> messagesArray = new HashMap<>();

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
        fileName = "language_" + GeneralConfig.getInstance().getLocale().toString().toLowerCase() + ".yml";
    }

    public void reload() {
        messages = new HashMap<>();
        messagesArray = new HashMap<>();
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Language of the editor setup on &6"+ GeneralConfig.getInstance().getLocale());
        fileName = "language_" + GeneralConfig.getInstance().getLocale().toString().toLowerCase() + ".yml";
        loadTexts();
        GUI.init();
    }

    public void loadTexts() {

        if (!SCore.dataFolder.exists()) SCore.dataFolder.mkdir();
        File pdfile = new File(SCore.dataFolder, "languages"+File.separator+fileName);
        if (!pdfile.exists()) {
            try {
                pdfile.getParentFile().mkdir();
                pdfile.createNewFile();

                BufferedReader br = new BufferedReader(new InputStreamReader(Config.getResource(SCore.classLoader, fileName)));
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
                throw new RuntimeException("Unable to create the file: " + this.fileName + " for the plugin: " + SCore.NAME, e);
            } catch (NullPointerException e) {/* locale */}
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(pdfile);

        for (TextInterface msgI : Text.values()) {
            this.loadMessage(msgI, SCore.classLoader, SCore.NAME, pdfile, config, msgI.getKey());
        }
        /* print all keys values */
        /* for(TextInterface msgI : Text.values()) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7"+msgI.getKey()+" : "+messages.get(msgI));
        } */
    }

    public void loadMessage(TextInterface msgI, ClassLoader classLoader, String pluginName, File pdFile, FileConfiguration config, String message) {

        if(config.isList(message)){
            List<String> object = config.getStringList(message);
            messagesArray.put(msgI, object.toArray(new String[0]));
        }
        else if(config.isString(message)){
            String object = config.getString(message);
            messages.put(msgI, object);
        }
        else write(msgI, classLoader, pluginName, pdFile, config, message);
    }

    public void write(TextInterface msgI, ClassLoader classLoader, String pluginName, File pdFile, FileConfiguration config, String what) {

        String insert = "Can't load the string (" + what + ") for the plugin > " + pluginName + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developper";
        try {
            String defaultLanguage = GeneralConfig.getInstance().getLocale().toString();
            String defaultFileName = "language_" + defaultLanguage.toLowerCase() + ".yml";

            InputStreamReader lecture = new InputStreamReader(Config.getResource(classLoader, defaultFileName), StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(lecture);

            FileConfiguration real = YamlConfiguration.loadConfiguration(buff);
            boolean isNotUpdate = false;

            if(real.isList(what)){
                List<String> object = real.getStringList(what);
                messagesArray.put(msgI, object.toArray(new String[0]));
                config.set(what, object);
            }
            else if(real.isString(what)){
                String object = real.getString(what);
                messages.put(msgI, object);
                config.set(what, object);
            }
            else {
                isNotUpdate = true;
                messages.put(msgI, insert);
            }

            if(!isNotUpdate){
                Utils.sendConsoleMsg(SCore.NAME_COLOR+ " &7Update of &6" + what + " &7in your for the plugin > &6" + pluginName + " &7in language: &6" + GeneralConfig.getInstance().getLocale());
                config.save(pdFile);
            } else{
                Utils.sendConsoleMsg(SCore.NAME_COLOR + "&c ERROR LOAD MESSAGE &6" + what + "&c for the plugin > &6" + pluginName + "&c in language: &6" + GeneralConfig.getInstance().getLocale());
                if(msgI.getType() == TypeText.STRING) messages.put(msgI, msgI.getDefaultValueString());
                else messagesArray.put(msgI, msgI.getDefaultValueArray());
            }
            buff.close();
        } catch (Exception e) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cERROR LOAD MESSAGE &6"+what+" &cfor the plugin > &6" + pluginName + "&c in language: &6" + GeneralConfig.getInstance().getLocale());
            e.printStackTrace();
        }
    }

    public String getText(TextInterface message) {
        if(messages.containsKey(message)) return messages.get(message);
        else if(messagesArray.containsKey(message)) {
            String[] array = messagesArray.get(message);
            StringBuilder combine = new StringBuilder();
            for(String s : array) {
                combine.append(s).append("\n");
            }
            return combine.toString();
        }
        else return "Can't load the string (" + message.getKey() + ") for the plugin > " + SCore.NAME + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developer";
    }

    public String [] getArray(TextInterface message) {
        if(messagesArray.containsKey(message)) return messagesArray.get(message);
        else if(messages.containsKey(message)) return new String[]{messages.get(message)};
        else return new String[]{"Can't load the string (" + message.getKey() + ") for the plugin > " + SCore.NAME + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developer"};
    }

    public static String g(TextInterface message) {
        return getInstance().getText(message);
    }

    public static String[] gA(TextInterface message) {
        return getInstance().getArray(message);
    }
}
