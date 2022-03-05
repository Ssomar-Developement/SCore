package com.ssomar.score.config;

import com.ssomar.score.SCore;

public class GeneralConfig extends Config {
	
	private static GeneralConfig instance;
	
	private String locale;
	
	private boolean useMySQL;
	
	private String dbIP;
	
	private int dbPort;
	
	private String dbName;
	
	private String dbUser;
	
	private String dbPassword;
	
	public GeneralConfig() {
		super("config.yml");
		super.setup(SCore.plugin);
	}
	
	public void reload() {
		super.setup(SCore.getPlugin());
	}
	
	@Override
	public void load() {
		/* Locale config (language) */
		locale = config.getString("locale", "EN");
		if (locale.equals("FR") || locale.equals("EN") || locale.equals("ES") || locale.equals("HU") || locale.equals("ptBR") || locale.equals("DE")) {
			SCore.plugin.getServer().getLogger().info("[SCore] Locale setup: " + locale);
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
		
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}	
	
	public static GeneralConfig getInstance() {
		if(instance == null) instance = new GeneralConfig();
		return instance;
	}

	public boolean isUseMySQL() {
		return useMySQL;
	}

	public void setUseMySQL(boolean useMySQL) {
		this.useMySQL = useMySQL;
	}

	public String getDbIP() {
		return dbIP;
	}

	public void setDbIP(String dbIP) {
		this.dbIP = dbIP;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}
	
}
