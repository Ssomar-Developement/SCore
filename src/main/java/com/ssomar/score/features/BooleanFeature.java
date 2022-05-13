package com.ssomar.score.features;

import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BooleanFeature implements FeatureInterface<Boolean> {

    private String name;
    private boolean value;
    private boolean defaultValue;

    public BooleanFeature(String name, boolean defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config) {
        this.value = config.getBoolean(this.name, this.defaultValue);
        return new ArrayList<>();
    }

    @Override
    public void save(SPlugin plugin, ConfigurationSection config) {

    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
