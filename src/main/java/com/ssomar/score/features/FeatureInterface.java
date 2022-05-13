package com.ssomar.score.features;

import com.plotsquared.core.configuration.ConfigurationSection;
import com.ssomar.score.splugin.SPlugin;

import java.util.List;

public interface FeatureInterface<T> {

    /** @return The potential errors during that appear during the loading */
    List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config);

    void save(SPlugin plugin, ConfigurationSection config);

    T getValue();
}
