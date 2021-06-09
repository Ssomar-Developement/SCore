package com.ssomar.score.conditions;

import java.util.List;

import org.bukkit.WeatherType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.ssomar.score.utils.StringCalculation;

public class WorldConditions extends Conditions{
	
	//STORM, RAIN, CLEAR
	private List<String> ifWeather;
	private static final String IF_WEATHER_MSG = " &cThe weather must change to active the activator: &6%activator% &cof this item!";
	private String ifWeatherMsg;
	
	private String ifWorldTime;
	private static final String IF_WORLD_TIME_MSG = " &cThe world time is not valid to active the activator: &6%activator% &cof this item!";
	private String ifWorldTimeMsg;
	
	public boolean verifConditions(Player p) {
		
		if(this.hasIfWorldTime()) {
			if(!StringCalculation.calculation(this.ifWorldTime, p.getWorld().getTime())) {
				this.getSm().sendMessage(p, this.getIfWorldTimeMsg());
				return false;
			}
		}
		if(this.hasIfWeather()) {
			String currentW="";
			if(p.getWorld().isThundering() || p.getWorld().isThundering()) currentW="STORM";
			else if(p.getPlayerWeather()==WeatherType.DOWNFALL) currentW= "RAIN";
			else currentW="CLEAR";
			
			if(!this.ifWeather.contains(currentW)) {
				this.getSm().sendMessage(p, this.getIfWeatherMsg());
				return false;
			}
		}
		return true;
	}
	
	public static WorldConditions getWorldConditions(ConfigurationSection worldCdtSection, List<String> errorList, String pluginName) {

		WorldConditions wCdt = new WorldConditions();

		wCdt.setIfWeather(worldCdtSection.getStringList("ifWeather"));
		wCdt.setIfWeatherMsg(worldCdtSection.getString("ifWeatherMsg", "&4&l"+pluginName+IF_WEATHER_MSG));

		wCdt.setIfWorldTime(worldCdtSection.getString("ifWorldTime", ""));
		wCdt.setIfWorldTimeMsg(worldCdtSection.getString("ifWorldTimeMsg", "&4&l"+pluginName+IF_WORLD_TIME_MSG));

		return wCdt;

	}
	
	public List<String> getIfWeather() {
		return ifWeather;
	}

	public void setIfWeather(List<String> ifWeather) {
		this.ifWeather = ifWeather;
	}

	public boolean hasIfWeather() {
		return ifWeather.size()!=0;
	}
	
	public String getIfWorldTime() {
		return ifWorldTime;
	}

	public void setIfWorldTime(String ifWorldTime) {
		this.ifWorldTime = ifWorldTime;
	}

	public boolean hasIfWorldTime() {
		return ifWorldTime.length()!=0;
	}
	
	
	
	public String getIfWeatherMsg() {
		return ifWeatherMsg;
	}

	public void setIfWeatherMsg(String ifWeatherMsg) {
		this.ifWeatherMsg = ifWeatherMsg;
	}
	
	public String getIfWorldTimeMsg() {
		return ifWorldTimeMsg;
	}

	public void setIfWorldTimeMsg(String ifWorldTimeMsg) {
		this.ifWorldTimeMsg = ifWorldTimeMsg;
	}

}
