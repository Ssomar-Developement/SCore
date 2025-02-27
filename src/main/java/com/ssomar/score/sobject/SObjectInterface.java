package com.ssomar.score.sobject;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SObjectInterface {

    String getId();

    List<FeatureInterface> getFeatures();

    @Nullable
    FeatureInterface getFeature(FeatureSettingsInterface featureSettings);
}
