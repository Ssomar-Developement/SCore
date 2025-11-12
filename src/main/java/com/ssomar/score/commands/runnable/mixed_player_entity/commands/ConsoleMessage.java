package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.utils.logging.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleMessage extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder build = new StringBuilder();

        for (String arg : args) {
            build.append(arg).append(" ");
        }

        Utils.sendConsoleMsg(build.toString());
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CONSOLE_MESSAGE");
        names.add("CONSOLEMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CONSOLE_MESSAGE {text}";
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
