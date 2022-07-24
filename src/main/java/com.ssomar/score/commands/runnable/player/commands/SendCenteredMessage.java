package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.messages.CenteredMessage;

/* SENDCENTEREDMESSAGE {message} */
public class SendCenteredMessage extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        StringBuilder message = new StringBuilder();
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        CenteredMessage.sendCenteredMessage(receiver, message.toString());
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if(args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SENDCENTEREDMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SENDCENTEREDMESSAGE {message}";
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
