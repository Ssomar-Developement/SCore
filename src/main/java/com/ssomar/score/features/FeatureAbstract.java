package com.ssomar.score.features;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public abstract class FeatureAbstract<T, Y> implements FeatureInterface<T, Y> {

    private String name;
    private String editorName;
    private String [] editorDescription;
    private Material editorMaterial;

    public FeatureAbstract(String name, String editorName, String [] editorDescription, Material editorMaterial) {
        this.name = name;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.editorMaterial = editorMaterial;
    }

    @Override
    public boolean isTheFeatureClicked(String featureClicked) {
        return featureClicked.contains(editorName);
    }
}
