package com.ssomar.score.features;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.menu.GUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class FeatureWithHisOwnEditor<A, B extends FeatureInterface<A, B>, Y extends GUI, T extends NewGUIManager<Y>> extends FeatureAbstract<A, B> implements FeatureParentInterface, Serializable {

    public FeatureWithHisOwnEditor(FeatureParentInterface parent, FeatureSettingsInterface featureSettingsSCore) {
        super(parent, featureSettingsSCore);
    }

    public abstract void openEditor(@NotNull Player player);

    public boolean isPremium() {
        if (getParent() == null || getParent() == this) return true;
        return getParent().isPremium();
    }

}
