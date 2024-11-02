package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/* SENDMESSAGE {message} */
public class SendMessage extends PlayerCommand {


    public SendMessage() {
        CommandSetting message = new CommandSetting("message", 0, String.class, "&6Hello_world");
        List<CommandSetting> settings = getSettings();
        settings.add(message);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String baseMessage = (String) sCommandToExec.getSettingValue("message");
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(baseMessage);
        int remove = 0;
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
            remove=1;
        }
        message = new StringBuilder(message.substring(0, message.length() - remove));
        if(!message.toString().isEmpty())
            sm.sendMessage(receiver, message.toString(), false);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SEND_MESSAGE");
        names.add("SENDMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SEND_MESSAGE message:&6Hello_world";
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
