package com.ssomar.score.features;

import com.plotsquared.core.configuration.ConfigurationSection;
import com.ssomar.score.splugin.SPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooleanGroupFeature implements FeatureInterface<Map<String, BooleanFeature>> {

    private Map<String, BooleanFeature> features;

    public BooleanGroupFeature(Map<String, BooleanFeature> features) {
        this.features = features;
    }

    @Override
    public List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config) {
        this.features = new HashMap<>();
        for(String featureName : features.keySet()) {
            BooleanFeature feature = features.get(featureName);
            features.put(featureName, new BooleanFeature(feature.getName(), feature.isDefaultValue()));
        }
        return new ArrayList<>();
    }

    @Override
    public void save(SPlugin plugin, ConfigurationSection config) {

    }

    @Override
    public Map<String, BooleanFeature> getValue() {
        return features;
    }

    public boolean getValueOf(String string) {
        return features.get(string).getValue();
    }
}
