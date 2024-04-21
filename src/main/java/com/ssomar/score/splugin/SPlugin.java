package com.ssomar.score.splugin;

import com.ssomar.score.config.Config;
import org.bukkit.plugin.Plugin;

public interface SPlugin {

    String getShortName();

    String getName();

    String getNameWithBrackets();

    String getNameDesign();

    String getNameDesignWithBrackets();

    String getObjectName();

    Plugin getPlugin();

    boolean isLotOfWork();

    int getMaxSObjectsLimit();

    Config getPluginConfig();
}
