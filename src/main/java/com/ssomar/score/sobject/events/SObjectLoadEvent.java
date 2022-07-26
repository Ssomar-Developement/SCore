package com.ssomar.score.sobject.events;

import com.ssomar.score.sobject.NewSObject;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SObjectLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Getter
    private String id;
    @Getter
    private NewSObject object;

    public SObjectLoadEvent(String id, NewSObject object) {
        this.id = id;
        this.object = object;
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
}
