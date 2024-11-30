package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SendBlankMessage extends PlayerCommand {

    public SendBlankMessage() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String message = " ";
        receiver.sendMessage(message);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SEND_BLANK_MESSAGE");
        names.add("SENDBLANKMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SEND_BLANK_MESSAGE";
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
