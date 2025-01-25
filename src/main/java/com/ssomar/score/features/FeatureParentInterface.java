package com.ssomar.score.features;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public interface FeatureParentInterface<FINAL_VALUE_CLASS, FEATURE_CLASS> extends FeatureInterface<FINAL_VALUE_CLASS, FEATURE_CLASS> {

    List<FeatureInterface> getFeatures();

    FeatureInterface getFeature(FeatureSettingsInterface featureSettings);

    FeatureInterface getFeatureWithName(String featureName);

    String getParentInfo();

    @Nullable
    FeatureParentInterface getParent();

    ConfigurationSection getConfigurationSection();

    File getFile();

    void reload();

    void openEditor(@NotNull Player player);

    void openBackEditor(@NotNull Player player);

    void save();

    boolean isPremium();

    FeatureSettingsInterface getFeatureSettings();

    default FeatureParentInterface cloneParent(FeatureParentInterface parentOfTheParent){
        return (FeatureParentInterface) clone(parentOfTheParent);
    }

}
