package com.ssomar.score.utils.events;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakNoPlayerEvent extends BlockEvent implements Cancellable {

    private boolean cancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public BlockBreakNoPlayerEvent(@NotNull Block theBlock) {
        super(theBlock);
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
