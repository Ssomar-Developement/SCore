package com.ssomar.score.commands.runnable.util.commands;

import com.ssomar.score.commands.runnable.SCommand;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DelayTick extends SCommand {

    public static final List<String> DELAY_TICK_NAMES =  Arrays.asList("DELAY_TICK", "DELAYTICK");

    @Override
    public List<String> getNames() {
        return DELAY_TICK_NAMES;
    }

    @Override
    public String getTemplate() {
        return "DELAY_TICK {number}";
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
        for (String name : DELAY_TICK_NAMES) {
            if (command.contains(name+ " ")) {
                return true;
            }
        }
        return false;
    }

    public static String replaceCommand(String command) {
        for (String name : DELAY_TICK_NAMES) {
            command = command.replaceAll(name + " ", "");
        }
        return command;
    }
}
