package com.ssomar.score.api.executableitems.config;

import com.ssomar.scoretestrecode.features.custom.activators.activator.NewSActivator;
import com.ssomar.scoretestrecode.features.custom.activators.group.ActivatorsFeature;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ExecutableItemInterface {

    /**
     * To place at the end of your itemBuilder , it adds infos for item to be recognized as an ExecutableItem
     * It will take the lore / name of the ExecutableItems and Override yours (But it doesn't override the customModeldata tag)
     *
     * @param item    The item to add the ExecutableItem infos to
     * @param creator The optional creator of the ExecutableItem
     */
    ItemStack addExecutableItemInfos(ItemStack item, Optional<Player> creator);

    /**
     * @param player    The player to whom you want to check the possession of the permission
     * @param showError true if you want to show an error message to the player if he doesn't have the permission
     * @return The name of the ExecutableItem
     */
    boolean hasItemPerm(@NotNull Player player, boolean showError);


    /**
     * Build the ExecutableItem
     *
     * @param amount  The amount of the ExecutableItem
     * @param usage   The optional custom usage of the ExecutableItem, otherwise it will use the default one
     * @param creator The optional creator of the ExecutableItem
     * @return The ExecutableItem
     */
    ItemStack buildItem(int amount, Optional<Integer> usage, Optional<Player> creator);

    /**
     * Build the ExecutableItem
     *
     * @param amount  The amount of the ExecutableItem
     * @param creator The optional creator of the ExecutableItem
     * @return The ExecutableItem with default usage.
     */
    ItemStack buildItem(int amount, Optional<Player> creator);

    /**
     * @return true If the item has the feature to keep the EI on death, false otherwise
     **/
    boolean hasKeepItemOnDeath();

    String getId();

    ActivatorsFeature getActivators();

    Item dropItem(Location location, int amount);

    NewSActivator getActivator(String actID);

    List<String> getDescription();
}
