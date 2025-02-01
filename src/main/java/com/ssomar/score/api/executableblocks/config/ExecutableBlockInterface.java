package com.ssomar.score.api.executableblocks.config;

import com.ssomar.score.api.executableblocks.config.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.sobject.*;
import com.ssomar.score.utils.place.OverrideMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ExecutableBlockInterface extends SObjectInterface, SObjectWithActivators, SObjectBuildable, SObjectWithVariables {


    /**
     * @param player    The player to whom you want to check the possession of the permission
     * @param showError true if you want to show an error message to the player if he doesn't have the permission
     * @return The name of the ExecutableBlock
     */
    boolean hasBlockPerm(@NotNull Player player, boolean showError);


    void addCooldown(Player player, int cooldown, boolean isInTicks);

    void addCooldown(Player player, int cooldown, boolean isInTicks, String activatorID);

    Item dropItem(Location location, int amount);

    Optional<ExecutableBlockPlacedInterface> place(@NotNull Location location, boolean placeBlock, OverrideMode overrideMode, @Nullable Entity placer, @Nullable InternalData overrideInternalData);

}
