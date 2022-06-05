package com.ssomar.testRecode.features;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface FeaturesGroup<T extends FeatureInterface> {

    void createNewFeature(@NotNull Player editor);

    void deleteFeature(@NotNull Player editor, T feature);
}
