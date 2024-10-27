package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Chat extends PlayerCommand {

    public Chat() {
        CommandSetting text = new CommandSetting("text", 0, String.class, "&eHello_world");
        List<CommandSetting> settings = getSettings();
        settings.add(text);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String text = (String) sCommandToExec.getSettingValue("text");
        receiver.chat(StringConverter.coloredString(text));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CHAT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CHAT text:&eHello_world";
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
