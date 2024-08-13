package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SENDMESSAGE {message} */
public class SendMessage extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder();
        if (args.size() > 0) {
            for (String s : args) {
                //SsomarDev.testMsg("cmdarg> "+s);
                message.append(s).append(" ");
            }
            message = new StringBuilder(message.substring(0, message.length() - 1));
            sm.sendMessage(receiver, message.toString(), false);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SENDMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SENDMESSAGE {message}";
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
