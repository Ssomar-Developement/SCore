package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsoleMessage extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        StringBuilder build = new StringBuilder();

        for (String arg : args) {
            arg = StringConverter.coloredString(arg);
            build.append(arg + " ");
        }

        System.out.println(build);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CONSOLEMESSAGE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CONSOLEMESSAGE {text}";
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
