package com.ssomar.score.commands.runnable.util.commands;

import com.ssomar.score.commands.runnable.SCommand;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RandomEnd extends SCommand {

    public static final List<String> RANDOM_END_NAMES = Arrays.asList("RANDOM_END", "RANDOM END");

    @Override
    public List<String> getNames() {
        return RANDOM_END_NAMES;
    }

    @Override
    public String getTemplate() {
        return "RANDOM_END";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.GOLD;
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public String getWikiLink() {
        return null;
    }

    public static boolean checkContains(String command) {
        for (String name : RANDOM_END_NAMES) {
            if (command.contains(name)) {
                return true;
            }
        }
        return false;
    }
}
