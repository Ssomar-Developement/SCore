package com.ssomar.score.config;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.logging.Utils;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Getter
public class GeneralConfig extends Config {

    private static GeneralConfig instance;

    private String locale;

    private boolean useMySQL;

    private String dbIP;

    private int dbPort;

    private String dbName;

    private String dbUser;

    private String dbPassword;

    private List<String> silenceOutputs;

    private boolean reduceDamageIndicatorWithProtolcolLib;

    private boolean debugCheckDamages;

    private boolean loopKillMode;

    private boolean enableDetectionEntitiesFromSpawner;

    private boolean disableCustomMetadataOnEntities;

    public GeneralConfig() {
        super("config.yml");
        super.setup(SCore.plugin);
    }

    public static GeneralConfig getInstance() {
        if (instance == null) instance = new GeneralConfig();
        return instance;
    }

    public void reload() {
        super.setup(SCore.plugin);
    }

    @Override
    public boolean converter(FileConfiguration config) {
        return false;
    }

    @Override
    public void load() {
        /* Locale config (language) */
        locale = config.getString("locale", "EN");
        if (locale.equals("FR")
                || locale.equals("EN")
                || locale.equals("ES")
                || locale.equals("HU")
                || locale.equals("ptBR")
                || locale.equals("DE")
                || locale.equals("UK")
                || locale.equals("RU")
                || locale.equals("ZH")) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Locale setup: &6" + locale);
        } else {
            SCore.plugin.getServer().getLogger().severe("[SCore] Invalid locale name: " + locale);
            locale = "EN";
        }

        useMySQL = config.getBoolean("useMySQL", false);
        dbIP = config.getString("dbIP", "");
        dbPort = config.getInt("dbPort", 3306);
        dbName = config.getString("dbName", "");
        dbUser = config.getString("dbUser", "");
        dbPassword = config.getString("dbPassword", "");
        reduceDamageIndicatorWithProtolcolLib = config.getBoolean("reduceDamageIndicatorWithProtolcolLib", false);
       // jetMinionsGenerateBreakActivator = config.getBoolean("jetMinionsGenerateBreakActivator", false);
        silenceOutputs = config.getStringList("silenceOutputs");
        debugCheckDamages = config.getBoolean("debugCheckDamages", false);
        enableDetectionEntitiesFromSpawner = config.getBoolean("enableDetectionEntitiesFromSpawner", true);
        loopKillMode = config.getBoolean("loopKillMode", false);
        disableCustomMetadataOnEntities = config.getBoolean("disableCustomMetadataOnEntities", false);

    }

}
