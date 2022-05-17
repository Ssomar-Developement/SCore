package com.ssomar.testRecode.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.menu.NewGUIManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FeatureWithHisOwnEditor<Y extends GUI, T extends NewGUIManager<Y>> {

    void openEditor(@NotNull Player player);
}
