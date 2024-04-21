package com.ssomar.score.api.executableitems.config;

import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.features.custom.activators.group.ActivatorsFeature;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
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
     * @param variables The initialization of the variables of the ExecutableItem
     * @return The ExecutableItem
     */
    ItemStack buildItem(int amount, Optional<Integer> usage, Optional<Player> creator, Map<String, String> variables);

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
     * Build the ExecutableItem
     *
     * @param amount  The amount of the ExecutableItem
     * @param creator The optional creator of the ExecutableItem
     * @param settings The settings of the ExecutableItem :
     *                 The variables of the ExecutableItem
     *                 - key "Variables" | Value Map<String -> variableId, String ->  variableValue>
     *                 - key "Usage" | Value Integer -> usage
     * @return The ExecutableItem with default usage.
     */
    ItemStack buildItem(int amount, Optional<Player> creator, Map<String, Object> settings);

    /**
     * @return true If the item has the feature to keep the EI on death, false otherwise
     **/
    boolean hasKeepItemOnDeath();

    void addCooldown(Player player, int cooldown, boolean isInTicks);

    void addCooldown(Player player, int cooldown, boolean isInTicks, String activatorID);

    void addGlobalCooldown(int cooldown, boolean isInTicks);

    void addGlobalCooldown(int cooldown, boolean isInTicks, String activatorID);

    String getId();

    ActivatorsFeature getActivators();

    Item dropItem(Location location, int amount);

    SActivator getActivator(String actID);

    List<String> getDescription();
}
