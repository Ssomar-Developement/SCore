package com.ssomar.score.features;

import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FeaturesGroupInterface<T, Y> {

    /** @return The potential errors during that appear during the loading */
    List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config);

    void save(SPlugin plugin, ConfigurationSection config);

    T getValueOf(String string);

    Y getFeature(String string);
}
