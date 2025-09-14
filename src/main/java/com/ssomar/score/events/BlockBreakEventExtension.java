package com.ssomar.score.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakEventExtension extends BlockBreakEvent {

    public enum BreakCause {
        MINE_IN_CUBE,
        SMELT,
        OTHER
    }

    private boolean isFromCustomBreakCommand = false;

    @Getter
    private BreakCause breakCause = BreakCause.OTHER;

    public BlockBreakEventExtension(@NotNull Block theBlock, @NotNull Player player, boolean isFromCustomBreakCommand, BreakCause breakCause) {
        super(theBlock, player);
        this.isFromCustomBreakCommand = isFromCustomBreakCommand;
        this.breakCause = breakCause;
    }

    public boolean isFromCustomBreakCommand() {
        return isFromCustomBreakCommand;
    }

}
