package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetPlayerWeather extends PlayerCommand {

    public SetPlayerWeather() {
        CommandSetting weather = new CommandSetting("weather", 0, String.class, "CLEAR");
        List<CommandSetting> settings = getSettings();
        settings.add(weather);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String weather = ((String) sCommandToExec.getSettingValue("weather")).toUpperCase();
        if (weather.equals("RESET")) {
            receiver.resetPlayerWeather();
        } else {
            try {
                receiver.setPlayerWeather(WeatherType.valueOf(weather));
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_PLAYER_WEATHER");
        names.add("SETPLAYERWEATHER");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_PLAYER_WEATHER weather:CLEAR";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }
}
