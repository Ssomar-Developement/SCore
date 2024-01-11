/*
 * Copyright (c) 2022 Alexander Majka (mfnalex) / JEFF Media GbR
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * If you need help or have any suggestions, feel free to join my Discord and head to #programming-help:
 *
 * Discord: https://discord.jeff-media.com/
 *
 * If you find this library helpful or if you're using it one of your paid plugins, please consider leaving a donation
 * to support the further development of this project :)
 *
 * Donations: https://paypal.me/mfnalex
 */

package com.ssomar.customblockdata.events;

import com.ssomar.customblockdata.events.fallingblock.FallingBlockDestroyedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an event that removes, changes or moves CustomBlockData due to regular Bukkit Events.
 *
 * This event gets called during the underlying Bukkit Event's MONITOR listening phase. If the Bukkit Event
 * was already cancelled, this event will not be called.
 *
 * If this event is cancelled, CustomBlockData will not be removed, changed or moved.
 */
public class CustomBlockDataAsEntityEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    final @NotNull Plugin plugin;
    final @NotNull Entity entity;
    final @NotNull PersistentDataContainer pDC;
    final @NotNull Event bukkitEvent;
    boolean isCancelled = false;

    protected CustomBlockDataAsEntityEvent(@NotNull Plugin plugin, @NotNull Entity entity, @NotNull Event bukkitEvent) {
        this.plugin = plugin;
        this.entity = entity;
        this.bukkitEvent = bukkitEvent;
        this.pDC = entity.getPersistentDataContainer();
    }

    /**
     * Gets the entity block involved in this event.
     */
    public @NotNull Entity getEntity() {
        return entity;
    }

    /**
     * Gets the underlying Bukkit Event that has caused this event to be called. The Bukkit Event is currently listened
     * on in MONITOR priority.
     */
    public @NotNull Event getBukkitEvent() {
        return bukkitEvent;
    }

    /**
     * Gets the PersistentDatContainer involved in this event.
     */
    public @NotNull PersistentDataContainer getCustomBlockData() {
        return pDC;
    }

    /**
     * Gets the cancellation status of this event.
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets the cancellation status of this event. If the event is cancelled, the CustomBlockData will not be removed, changed or moved.
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    /**
     * Gets the reason for this change of CustomBlockData
     */
    public @NotNull Reason getReason() {
        if (bukkitEvent == null) return Reason.UNKNOWN;
        for (Reason reason : Reason.values()) {
            if (reason == Reason.UNKNOWN) continue;
            if (reason.eventClasses.stream().anyMatch(clazz -> clazz.equals(bukkitEvent.getClass()))) return reason;
        }
        return Reason.UNKNOWN;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Represents the reason for a change of CustomBlockData
     */
    public enum Reason {


        FALLING_BLOCK_DESTROYED(FallingBlockDestroyedEvent.class),

        UNKNOWN((Class<? extends Event>) null);

        private final @NotNull List<Class<? extends Event>> eventClasses;

        @SafeVarargs
        Reason(Class<? extends Event>... eventClasses) {
            this.eventClasses = Arrays.asList(eventClasses);
        }

        /**
         * Gets a list of Bukkit Event classes that are associated with this Reason
         */
        public @NotNull List<Class<? extends Event>> getApplicableEvents() {
            return this.eventClasses;
        }
    }
}
