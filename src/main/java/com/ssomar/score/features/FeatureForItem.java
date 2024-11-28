package com.ssomar.score.features;

import com.ssomar.score.utils.emums.ResetSetting;
import org.jetbrains.annotations.NotNull;

public interface FeatureForItem {

    boolean isAvailable();

    boolean isApplicable(@NotNull FeatureForItemArgs args);

    void applyOnItemMeta(@NotNull FeatureForItemArgs args);

    void loadFromItemMeta(@NotNull FeatureForItemArgs args);

    ResetSetting getResetSetting();

}
