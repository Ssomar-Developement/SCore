package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* CONTENT_cLEAR */
public class OpenDoor extends BlockCommand {

    private static final boolean DEBUG = true;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, SCommandToExec sCommandToExec) {

        if (block.getBlockData() instanceof Openable) {
            BlockState blockState = block.getState();
            Openable openable = (Openable) block.getBlockData();
            if (openable.isOpen()) {
                openable.setOpen(false);
            } else {
                openable.setOpen(true);
            }

            blockState.setBlockData(openable);
            blockState.update();
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("OPENDOOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "OPENDOOR";
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
