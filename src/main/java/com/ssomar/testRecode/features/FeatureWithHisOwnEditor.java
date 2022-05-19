package com.ssomar.testRecode.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.testRecode.menu.NewGUIManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class FeatureWithHisOwnEditor<A, B extends FeatureInterface<A, B>, Y extends GUI, T extends NewGUIManager<Y>> extends FeatureAbstract<A, B> implements FeatureParentInterface {

    public FeatureWithHisOwnEditor(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract void openEditor(@NotNull Player player);
}
