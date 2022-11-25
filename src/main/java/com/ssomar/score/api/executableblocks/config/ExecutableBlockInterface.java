package com.ssomar.score.api.executableblocks.config;

import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ExecutableBlockInterface {

    /**
     * Build the ExecutableBlock in its ItemStack form
     *
     * @param quantity The quantity of the ExecutableBlock
     * @param creator  The optional creator of the ExecutableBlock
     * @return The itemStack form of the ExecutableBlock
     */
    ItemStack buildItem(int quantity, Optional<Player> creator);

    /**
     * To place at the end of your itemBuilder , it adds infos for item to be recognized as an ExecutableBlock
     * If the ExecutableBlock is also an ExecutableItems, it will take the lore / name of the ExecutableItems and Override yours (It doesnt override the customModeldata tag)
     *
     * @param item    The item to add the ExecutableBlock infos to
     * @param creator The optional creator of the ExecutableBlock
     */
    ItemStack addExecutableBlockInfos(ItemStack item, Optional<Player> creator);

    /**
     * @param owner    The optional owner of the ExecutableBlock, if optional is null there is no owner
     * @param location The location where the ExecutableBlock will be placed
     * @param place    true if you want that the block will be placed, false if you have already place the block and you want only place the configuration of the EB on this block
     * @return The configuration of the ExecutableBlock placed
     */
    ExecutableBlockPlaced place(Optional<Player> owner, @NotNull Location location, boolean place);

    /**
     * @param owner    The optional owner of the ExecutableBlock, if optional is null there is no owner
     * @param location The location where the ExecutableBlock will be placed
     * @param place    true if you want that the block will be placed, false if you have already place the block and you want only place the configuration of the EB on this block
     * @param variablesCustom Allow you to set custom values for the variables of the ExecutableBlock
     * @return The configuration of the ExecutableBlock placed
     */
    ExecutableBlockPlaced place(Optional<Player> owner, @NotNull Location location, boolean place, Map<String, String> variablesCustom);

    /**
     * @param ownerUUID The optional ownerUUID of the ExecutableBlock, if optional is null there is no owner
     * @param location  The location where the ExecutableBlock will be placed
     * @param place     true if you want that the block will be placed, false if you have already place the block and you want only place the configuration of the EB on this block
     * @return The configuration of the ExecutableBlock placed
     */
    ExecutableBlockPlaced place2(Optional<UUID> ownerUUID, @NotNull Location location, boolean place);

    /**
     * @param player    The player to whom you want to check the possession of the permission
     * @param showError true if you want to show an error message to the player if he doesn't have the permission
     * @return The name of the ExecutableBlock
     */
    boolean hasBlockPerm(@NotNull Player player, boolean showError);

}
