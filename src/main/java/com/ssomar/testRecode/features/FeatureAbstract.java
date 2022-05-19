package com.ssomar.testRecode.features;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public abstract class FeatureAbstract<T, Y extends FeatureInterface<T, Y>> implements FeatureInterface<T, Y> {

    private FeatureParentInterface parent;
    private String name;
    private String editorName;
    private String [] editorDescription;
    private Material editorMaterial;
    private boolean requirePremium;

    public FeatureAbstract(FeatureParentInterface parent, String name, String editorName, String [] editorDescription, Material editorMaterial, boolean requirePremium) {
        this.parent = parent;
        this.name = name;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.editorMaterial = editorMaterial;
        this.requirePremium = requirePremium;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(editorName);
    }

    public void save() {
        save(parent.getConfigurationSection());
    }

    @Override
    public boolean requirePremium() {
        return requirePremium;
    }
}
