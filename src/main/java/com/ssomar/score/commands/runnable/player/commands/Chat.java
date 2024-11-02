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
        String baseMessage = (String) sCommandToExec.getSettingValue("text");
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(baseMessage);
        int remove = 0;
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
            remove = 1;
        }
        message = new StringBuilder(message.substring(0, message.length() - remove));
        receiver.chat(StringConverter.coloredString(message.toString()));
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
