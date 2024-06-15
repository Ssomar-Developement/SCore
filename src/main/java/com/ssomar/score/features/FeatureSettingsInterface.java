package com.ssomar.score.features;

import org.bukkit.Material;

public interface FeatureSettingsInterface {


    String getName();

    String getEditorName();

    String[] getEditorDescription();

    Material getEditorMaterial();

    boolean isRequirePremium();
}
