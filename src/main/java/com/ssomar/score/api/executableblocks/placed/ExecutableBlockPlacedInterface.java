package com.ssomar.score.api.executableblocks.placed;

import com.ssomar.score.utils.emums.VariableUpdateType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public interface ExecutableBlockPlacedInterface {

    String getExecutableBlockID();

    Location getLocation();

    Location getTitleLocation();

    Optional<UUID> getOwnerUUID();

    /**
     * Break the ExecutableBlockPlaced
     *
     * @param player The player who broke the ExecutableBlockPlaced
     * @param drop   Whether or not to drop the ExecutableBlock
     */
    void breakBlock(@Nullable Player player, boolean drop);

    /**
     * Remove the ExecutableBlockPlaced without any checks and does not drop the ExecutableBlock
     */
    void remove();

    int getUsage();

    void updateUsage(int usage);

    HashMap<String, String> getVariablesValues();

    String updateVariable(String variableName, String value, VariableUpdateType type);
}
