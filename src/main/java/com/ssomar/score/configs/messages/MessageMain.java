package com.ssomar.score.configs.messages;


import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageMain {

    private static MessageMain instance;

    private String fileName = "";

    private Map<Plugin, Map<MessageInterface, String>> messages = new HashMap<>();

    public static MessageMain getInstance() {
        if (instance == null) instance = new MessageMain();
        return instance;
    }

    public void load() {
        messages = new HashMap<>();
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Language for in-game messages setup on &6"+ GeneralConfig.getInstance().getLocale());
        fileName = "/locale/Locale_" + GeneralConfig.getInstance().getLocale() + ".yml";
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
                throw new RuntimeException("Unable to create the file: " + this.fileName + " for the plugin: " + plugin.getName(), e);
            } catch (NullPointerException e) {/* locale */}
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(pdfile);

        for (MessageInterface msgI : messagesEnum) {
            formMessages.put(msgI, this.loadMessage(plugin, pdfile, config, msgI.getName()));
        }
        messages.put(plugin, formMessages);
    }

    public String loadMessage(Plugin plugin, File pdFile, FileConfiguration config, String message) {
        //this.loadDefaultMessages();
        if (config.getString(message) != null) return StringConverter.coloredString(config.getString(message));
        else return StringConverter.coloredString(write(plugin, pdFile, config, message));
    }

    public String write(Plugin plugin, File pdFile, FileConfiguration config, String what) {

        String insert = "Can't load the string (" + what + ") for the plugin > " + plugin.getName() + " in language: " + GeneralConfig.getInstance().getLocale() + ", contact the developer";
        try {

            InputStream flux = plugin.getClass().getResourceAsStream("/com/ssomar/" + plugin.getName().toLowerCase() + "/configs/locale/Locale_" + GeneralConfig.getInstance().getLocale() + ".yml");
            if(flux == null) {
                Utils.sendConsoleMsg("&c"+SCore.plugin.getNameWithBrackets() + " &cERROR LOAD MESSAGE &6"+ what + " &cfor the plugin > &6" + plugin.getName() + " &cin language: &6" + GeneralConfig.getInstance().getLocale()+" &c(Message in jar not found");
                return insert;
            }
            InputStreamReader lecture = new InputStreamReader(flux, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            boolean isNotUpdate = true;
            while ((ligne = buff.readLine()) != null && isNotUpdate) {
                if (ligne.contains(what + ":")) {
                    Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Update of &6" + what + " &7in your for the plugin > &6" + plugin.getName() + " &7in language: &6" + GeneralConfig.getInstance().getLocale());
                    insert = ligne.split("\"")[1];
                    config.set(what, insert);
                    config.save(pdFile);
                    isNotUpdate = false;
                }
            }
            buff.close();
            if (isNotUpdate) {
                Utils.sendConsoleMsg("&c"+SCore.plugin.getNameWithBrackets() + " &cERROR LOAD MESSAGE &6" + what + " &cfor the plugin > &6" + plugin.getName() + " &cin language: &6" + GeneralConfig.getInstance().getLocale());
            }
        } catch (Exception e) {
            Utils.sendConsoleMsg("&c"+SCore.plugin.getNameWithBrackets() + " &cERROR LOAD MESSAGE &6"+ what + " &cfor the plugin > &6" + plugin.getName() + " &cin language: &6" + GeneralConfig.getInstance().getLocale());
            //e.printStackTrace();
        }

        return insert;
    }

    /* To bait directleaks */
    /* public void loadDefaultMessages() {
        System.out.println(SCore.NAME_2 + " DEFAUKT MESSAGE INCR SILENCE OUTPUT: ");
        FilterManager.getInstance().incCurrentlyInRun();
        if (SCore.hasExecutableItems) {
            try {
                Class clazz = ExecutableItems.class;
                clazz.getDeclaredField("uid");
                SCore.plugin.getServer().getPluginManager().disablePlugin(SCore.plugin);

            } catch (Exception ignored) {
            }
        }
        FilterManager.getInstance().decrCurrentlyInRun();
    } */

    public String getMessage(Plugin plugin, MessageInterface message) {
        if (messages.containsKey(plugin) && messages.get(plugin).containsKey(message)) {
            return messages.get(plugin).get(message);
        }
        return "";
    }
}
