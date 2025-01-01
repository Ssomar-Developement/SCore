package com.ssomar.score.features;

import com.ssomar.score.SCore;
import org.jetbrains.annotations.NotNull;

public interface FeatureForItemNewPaperComponents extends FeatureForItem {

    // For 1.21.4 + and paper only
    void applyOnItem(@NotNull FeatureForItemArgs args);

    void loadFromItem(@NotNull FeatureForItemArgs args);

    default boolean isAvailableForNewComponents() {
        return isAvailable() && SCore.is1v21v4Plus() && SCore.isPaperOrFork();
    }

}
