package com.ssomar.score.commands.runnable.util.commands;

import com.ssomar.score.commands.runnable.SCommand;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RandomRun extends SCommand {

    public static final List<String> RANDOM_RUN_NAMES = Arrays.asList("RANDOM_RUN", "RANDOM RUN:");

    @Override
    public List<String> getNames() {
        return RANDOM_RUN_NAMES;
    }

    @Override
    public String getTemplate() {
        return "RANDOM_RUN selectionCount:{number}";
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
        for (String name : RANDOM_RUN_NAMES) {
            if (command.contains(name + " ")) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static String extractSelectionCount(String command) {
        for (String name : RANDOM_RUN_NAMES) {
            if (command.contains(name + " ")) {
                String [] parts = command.split(name + " ");
                if (parts.length > 1) {
                    String selectionPart = parts[1].trim();
                    if (selectionPart.contains(":")) {
                        return selectionPart.split(":")[1].trim();
                    }
                    // Old syntax without :
                    return selectionPart.trim();
                }
            }
        }
        return null;
    }
}
