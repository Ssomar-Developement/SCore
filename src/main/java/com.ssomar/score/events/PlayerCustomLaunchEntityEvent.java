package com.ssomar.score.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerCustomLaunchEntityEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    @Setter
    private boolean cancelEvent = false;
    private final Entity launchedEntity;

    public PlayerCustomLaunchEntityEvent(final Player player, final @NotNull Entity launchedEntity) {
        super(player);
        this.launchedEntity = launchedEntity;
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
