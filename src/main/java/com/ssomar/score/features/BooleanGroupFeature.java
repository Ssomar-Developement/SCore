package com.ssomar.score.features;

import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooleanGroupFeature implements FeaturesGroupInterface<Boolean, BooleanFeature> {

    private Map<String, BooleanFeature> features;

    public BooleanGroupFeature(Map<String, BooleanFeature> features) {
        this.features = features;
    }

    @Override
    public List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config) {
        for(String featureName : features.keySet()) {
            BooleanFeature feature = features.get(featureName);
            features.put(featureName, new BooleanFeature(feature.getName(), feature.isDefaultValue(), feature.getEditorName(), feature.getEditorDescription(), feature.getEditorMaterial()));
        }
        return new ArrayList<>();
    }

    @Override
    public void save(SPlugin plugin, ConfigurationSection config) {

    }

    @Override
    public Boolean getValueOf(String string) {
        return features.get(string).getValue();
    }

    @Override
    public BooleanFeature getFeature(String string) {
        return features.get(string);
    }

    @Override
    public Map<String, BooleanFeature> getFeatures() {
        return features;
    }
}
