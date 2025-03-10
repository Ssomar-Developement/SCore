package com.ssomar.score.features;

import org.bukkit.Material;

public interface FeatureSettingsInterface {

    String getIdentifier();

    String getName();

    void setName(String name);

    String getEditorName();

    String[] getEditorDescription();

    String[] getEditorDescriptionBrut();

    Material getEditorMaterial();

    boolean isRequirePremium();

    void setRequirePremium(boolean requirePremium);

    SavingVerbosityLevel getSavingVerbosityLevel();

    void setSavingVerbosityLevel(SavingVerbosityLevel savingVerbosityLevel);
}
