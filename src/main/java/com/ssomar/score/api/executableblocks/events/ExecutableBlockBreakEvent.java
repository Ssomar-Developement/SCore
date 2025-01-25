package com.ssomar.score.api.executableblocks.events;

import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class ExecutableBlockBreakEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final @Nullable Player player;

    private final Block block;

    private final ExecutableBlockPlaced.BreakMethod breakMethod;

    private final Event sourceEvent;

    private boolean cancelled = false;

    public ExecutableBlockBreakEvent(@Nullable Player player, Block block, @Nullable Event sourceEvent, ExecutableBlockPlaced.BreakMethod breakMethod) {
        this.player = player;
        this.block = block;
        this.sourceEvent = sourceEvent;
        this.breakMethod = breakMethod;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    @Override
    public final @NotNull
    HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
