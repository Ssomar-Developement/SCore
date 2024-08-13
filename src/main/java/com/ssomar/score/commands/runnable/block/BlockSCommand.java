package com.ssomar.score.commands.runnable.block;

import com.ssomar.score.commands.runnable.SCommandToExec;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BlockSCommand {

    void run(@Nullable Player p, @NotNull Block block, SCommandToExec sCommandToExec);

}
