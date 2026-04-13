package com.ssomar.score.events;

import com.ssomar.score.utils.emums.ResetSetting;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Custom Event requested by altephcomputer to allow other plugins to detect details of an item refresh to happen.
 * All the event calls are done in ExecutableItems codebase.
 */
public class RefreshExecutableItemEvent extends Event implements Cancellable {


    private static final HandlerList HANDLERS = new HandlerList();
    private final String executableItemID;
    private final List<ResetSetting> settings;
    private final Player player;
    /**
     * Possible outputs: <br/>
     * - REFRESHCMD<br/>
     * - VARIABLEMODIFCMD<br/>
     */
    private final String refreshSource;


    public RefreshExecutableItemEvent(String executableItemID, List<ResetSetting> settings, Player player, String refreshSource) {
        this.executableItemID = executableItemID;
        this.settings = settings;
        this.player = player;
        this.refreshSource = refreshSource;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
