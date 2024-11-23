package com.ssomar.score.features;

import org.jetbrains.annotations.NotNull;

public interface FeatureForBlock {

    boolean isAvailable();

    boolean isApplicable(@NotNull FeatureForBlockArgs args);

    void applyOnBlockData(@NotNull FeatureForBlockArgs args);

    void loadFromBlockData(@NotNull FeatureForBlockArgs args);

}
