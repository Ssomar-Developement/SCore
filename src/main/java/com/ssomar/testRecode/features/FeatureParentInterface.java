package com.ssomar.testRecode.features;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FeatureParentInterface {

    List<FeatureInterface> getFeatures();

    String getParentInfo();

    ConfigurationSection getConfigurationSection();

    void reload();

    void openEditor(@NotNull Player player);

}
