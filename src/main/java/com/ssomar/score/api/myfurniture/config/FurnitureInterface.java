package com.ssomar.score.api.myfurniture.config;

import com.ssomar.myfurniture.furniture.placedfurniture.FurniturePlaced;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.sobject.*;
import com.ssomar.score.utils.place.OverrideMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface FurnitureInterface extends SObjectInterface, SObjectWithActivators, SObjectBuildable, SObjectWithVariables {
    /**
     * @param player    The player to whom you want to check the possession of the permission
     * @param showError true if you want to show an error message to the player if he doesn't have the permission
     * @return The name of the Furniture
     */
    boolean hasFurniturePerm(@NotNull Player player, boolean showError);

    boolean checkIfPlayerCanPlaceAt(@NotNull Player player, @NotNull Location location, boolean showErrorToThePLayer);

    boolean checkIfPlayerCanBreakAt(@NotNull Player player, @NotNull Location location, boolean showErrorToThePLayer);

    void addCooldown(Player player, int cooldown, boolean isInTicks);

    void addCooldown(Player player, int cooldown, boolean isInTicks, String activatorID);

    UncoloredStringFeature getItemModel();

    Optional<FurniturePlaced> place(@NotNull Location location, OverrideMode override, @Nullable Entity placer, @Nullable InternalData overrideInternalData);
}
