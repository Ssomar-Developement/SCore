package com.ssomar.score.api.executableitems.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RemoveItemInPlayerInventoryEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    @Getter
    private ItemStack item;
    @Getter
    private int slot;


    /**
     * @param player The player who put on / removed the armor.
     */
    public RemoveItemInPlayerInventoryEvent(final Player player, ItemStack item, int slot) {
        super(player);
        this.item = item;
        this.slot = slot;
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
    public final @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
