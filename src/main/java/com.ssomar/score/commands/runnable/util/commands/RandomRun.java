package com.ssomar.score.commands.runnable.util.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;

import com.ssomar.score.commands.runnable.SCommand;

public class RandomRun extends SCommand {

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("RANDOM RUN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "RANDOM RUN: {number}";
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
        String error = "";
        return  error.isEmpty() ? Optional.empty() : Optional.of(error);
    }
}
