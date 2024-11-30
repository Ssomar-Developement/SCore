package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.messages.CenteredMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/* SENDCENTEREDMESSAGE {message} */
public class SendCenteredMessage extends PlayerCommand {

    public SendCenteredMessage() {
        CommandSetting message = new CommandSetting("message", 0, String.class, "&6Hello_world");
        message.setAcceptUnderScoreForLongText(true);
        List<CommandSetting> settings = getSettings();
        settings.add(message);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String baseMessage = (String) sCommandToExec.getSettingValue("message");
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(baseMessage);
        message.append(" ");
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        CenteredMessage.sendCenteredMessage(receiver, message.toString());
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SEND_CENTERED_MESSAGE");
        names.add("SENDCENTEREDMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SEND_CENTERED_MESSAGE message:&6Hello_world";
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
