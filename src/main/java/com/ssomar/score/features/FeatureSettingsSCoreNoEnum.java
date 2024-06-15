package com.ssomar.score.features;

import org.bukkit.Material;

public class FeatureSettingsSCoreNoEnum implements FeatureSettingsInterface {

    private String configName;
    private String editorName;
    private String[] editorDescription;
    private Material editorMaterial;
    private boolean requirePremium;

    public FeatureSettingsSCoreNoEnum(String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        this.configName = name;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.editorMaterial = editorMaterial;
        this.requirePremium = requirePremium;
    }

    @Override
    public String getName() {
        return configName;
    }

    @Override
    public String getEditorName() {
        return editorName;
    }

    @Override
    public String[] getEditorDescription() {
        return editorDescription;
    }

    @Override
    public Material getEditorMaterial() {
        return editorMaterial;
    }

    @Override
    public boolean isRequirePremium() {
        return requirePremium;
    }
}
