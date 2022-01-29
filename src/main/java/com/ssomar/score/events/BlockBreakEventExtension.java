package com.ssomar.score.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakEventExtension extends BlockBreakEvent {

    private boolean isMineInCubeCommand = false;

    public BlockBreakEventExtension(@NotNull Block theBlock, @NotNull Player player, boolean isMineInCubeCommand) {
        super(theBlock, player);
        this.isMineInCubeCommand = isMineInCubeCommand;
    }

    public boolean isMineInCubeCommand() {
        return isMineInCubeCommand;
    }
}
