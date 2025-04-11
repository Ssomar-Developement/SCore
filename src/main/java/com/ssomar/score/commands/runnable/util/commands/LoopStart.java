package com.ssomar.score.commands.runnable.util.commands;

import com.ssomar.score.commands.runnable.SCommand;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LoopStart extends SCommand {

    public static final List<String> LOOP_START_NAMES =  Arrays.asList("LOOP_START", "LOOP START:");

    @Override
    public List<String> getNames() {
        return LOOP_START_NAMES;
    }

    @Override
    public String getTemplate() {
        return "LOOP_START loopCount:{number}";
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

    public static boolean checkContains(String command){
        for (String name : LOOP_START_NAMES) {
            if (command.contains(name+ " ")) {
                return true;
            }
        }
        return false;
    }

    public static String[] splitCommand(String command) {
        for (String name : LOOP_START_NAMES) {
            command = command.replaceAll(name + " ", "PART_TO_SPLIT_");
        }
        return command.split("PART_TO_SPLIT_");
    }

    @Nullable
    public static String extractIterations(String command) {
        for (String name : LOOP_START_NAMES) {
            if (command.contains(name + " ")) {
                String [] parts = command.split(name + " ");
                if (parts.length > 1) {
                    String iterationsPart = parts[1].trim();
                    if (iterationsPart.contains(":")) {
                        return iterationsPart.split(":")[1].trim();
                    }
                    // Old syntax without :
                    return iterationsPart.trim();
                }
            }
        }
        return null;
    }

}
