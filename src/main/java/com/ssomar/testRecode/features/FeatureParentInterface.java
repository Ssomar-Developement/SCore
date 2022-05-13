package com.ssomar.testRecode.features;

import org.bukkit.configuration.ConfigurationSection;

public interface FeatureParentInterface {

    String getParentInfo();

    ConfigurationSection getConfigurationSection();

    void reload();

}
