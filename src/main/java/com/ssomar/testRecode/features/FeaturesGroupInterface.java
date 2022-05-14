package com.ssomar.testRecode.features;

import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;

public interface FeaturesGroupInterface<T, Y> {

    /** @return The potential errors during that appear during the loading */
    List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading);

    void save(SPlugin plugin, ConfigurationSection config);

    T getValueOf(String string);

    Y getFeature(String string);

    Map<String, Y> getFeatures();
}
