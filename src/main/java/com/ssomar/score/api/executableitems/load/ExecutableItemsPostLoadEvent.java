package com.ssomar.score.api.executableitems.load;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ExecutableItemsPostLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    public static HandlerList getHandlerList(){
        return handlers;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    @Override
    public final @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
