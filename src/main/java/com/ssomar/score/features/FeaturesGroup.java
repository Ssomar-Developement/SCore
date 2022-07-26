package com.ssomar.score.features;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FeaturesGroup<T extends FeatureInterface> {

    void createNewFeature(@NotNull Player editor);

    void deleteFeature(@NotNull Player editor, T feature);

    @Nullable
    T getTheChildFeatureClickedParentEditor(String featureClicked);
}
