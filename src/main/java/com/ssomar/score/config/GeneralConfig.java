package com.ssomar.score.config;

import com.ssomar.score.SCore;

public class GeneralConfig extends Config {
	
	private static GeneralConfig instance;
	
	private String locale;
	
	public GeneralConfig() {
		super("config.yml");
		super.setup(SCore.getPlugin());
	}
	
	public void reload() {
		super.setup(SCore.getPlugin());
	}
	
	@Override
	public void load() {
		/* Locale config (language) */
		locale = config.getString("locale", "EN");
		if (locale.equals("FR") || locale.equals("EN") || locale.equals("ES") || locale.equals("HU")) {
			SCore.plugin.getServer().getLogger().info("[SCore] Locale setup: " + locale);
		} else {
			SCore.plugin.getServer().getLogger().severe("[SCore] Invalid locale name: " + locale);
			locale = "EN";
		}
		
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
	
}
