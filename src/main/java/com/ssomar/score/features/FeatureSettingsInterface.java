package com.ssomar.score.features;

import org.bukkit.Material;

public interface FeatureSettingsInterface {

    String getIdentifier();

    String getName();

    void setName(String name);

    String getEditorName();

    String[] getEditorDescription();

    Material getEditorMaterial();

    boolean isRequirePremium();

    void setRequirePremium(boolean requirePremium);
}
