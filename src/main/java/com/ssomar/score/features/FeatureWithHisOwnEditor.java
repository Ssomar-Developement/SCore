package com.ssomar.score.features;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.menu.GUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class FeatureWithHisOwnEditor<FINAL_VALUE_CLASS, FEATURE_CLASS, Y extends GUI, T extends NewGUIManager<Y>> extends FeatureAbstract<FINAL_VALUE_CLASS, FEATURE_CLASS> implements FeatureParentInterface<FINAL_VALUE_CLASS, FEATURE_CLASS>, Serializable {

    public FeatureWithHisOwnEditor(FeatureParentInterface parent, FeatureSettingsInterface featureSettingsSCore) {
        super(parent, featureSettingsSCore);
    }

    public abstract void openEditor(@NotNull Player player);

    public boolean isPremium() {
        if (getParent() == null || getParent() == this) return true;
        return getParent().isPremium();
    }

}
