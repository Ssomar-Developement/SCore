package com.ssomar.score.config;

import com.google.common.base.Charsets;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Config {
    protected final String fileName;
    protected File pdfile;
    protected FileConfiguration config;

    @Getter
    private Map<String, Object> loadedSettings;

    protected Config(String fileName) {
        this.fileName = fileName;
        this.loadedSettings = new HashMap<>();
    }

    public void setup(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        this.pdfile = new File(plugin.getDataFolder(), this.fileName);
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> File: " + this.pdfile.getAbsolutePath());
        if (!this.pdfile.exists()) {
            try {
                this.pdfile.getParentFile().mkdir();
                this.pdfile.createNewFile();
                //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CREATE File: " + this.pdfile.getAbsolutePath());

                BufferedReader br = new BufferedReader(new InputStreamReader(plugin.getResource(this.fileName)));
                String line;

                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.pdfile), StandardCharsets.UTF_8));

                try {
                    while ((line = br.readLine()) != null) {
                        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> WRITE File: " +line);
                        out.write(line);
                        out.write("\n");
                    }
                } finally {
                    br.close();
                    out.close();
                }

            } catch (IOException e) {
                throw new RuntimeException("Unable to create the file: " + this.fileName, e);
            } catch (NullPointerException e) {/* locale */}
        }
        this.config = YamlConfiguration.loadConfiguration(this.pdfile);
        if(converter(this.config)){
            try {
                Writer writer = new OutputStreamWriter(new FileOutputStream(pdfile), Charsets.UTF_8);

                try {
                    writer.write(config.saveToString());
                } finally {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        plugin.reloadConfig();
        load();
    }

    public abstract boolean converter(FileConfiguration config);

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

    public boolean loadBooleanSetting(String setting, boolean defaultValue) {
        boolean value = config.getBoolean(setting, defaultValue);
        loadedSettings.put(setting, value);
        //System.out.println("Setting: " + setting + " Value: " + value);
        return value;
    }

    public boolean getBooleanSetting(String setting) {
        return (boolean) loadedSettings.get(setting);
    }

    public int loadIntSetting(String setting, int defaultValue) {
        int value = config.getInt(setting, defaultValue);
        loadedSettings.put(setting, value);
        //System.out.println("Setting: " + setting + " Value: " + value);
        return value;
    }

    public int getIntSetting(String setting, int defaultValue) {
        if (!loadedSettings.containsKey(setting)) return defaultValue;
        return (int) loadedSettings.get(setting);
    }

    public String loadStringSetting(String setting, String defaultValue) {
        String value = config.getString(setting, defaultValue);
        loadedSettings.put(setting, value);
        //System.out.println("Setting: " + setting + " Value: " + value);
        return value;
    }

    public String getStringSetting(String setting, String defaultValue) {
        if (!loadedSettings.containsKey(setting)) return defaultValue;
        return (String) loadedSettings.get(setting);
    }

    public double loadDoubleSetting(String setting, double defaultValue) {
        double value = config.getDouble(setting, defaultValue);
        loadedSettings.put(setting, value);
        //System.out.println("Setting: " + setting + " Value: " + value);
        return value;
    }

    public double getDoubleSetting(String setting, double defaultValue) {
        if (!loadedSettings.containsKey(setting)) return defaultValue;
        return (double) loadedSettings.get(setting);
    }
}

