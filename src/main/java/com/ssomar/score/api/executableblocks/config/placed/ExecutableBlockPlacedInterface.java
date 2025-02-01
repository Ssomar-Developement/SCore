package com.ssomar.score.api.executableblocks.config.placed;

import com.ssomar.score.api.executableblocks.config.ExecutableBlockInterface;
import com.ssomar.score.sobject.InternalData;
import com.ssomar.score.utils.emums.VariableUpdateType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface ExecutableBlockPlacedInterface {

    Location getLocation();

    ExecutableBlockInterface getExecutableBlockConfig();

    InternalData getInternalData();

    void breakBlock(@Nullable Player player, boolean drop);

    void remove();

    String updateVariable(String variableName, String value, VariableUpdateType type);

    void updateUsage(int usage);

    String getEB_ID();

}
