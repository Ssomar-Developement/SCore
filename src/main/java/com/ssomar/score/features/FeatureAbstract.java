package com.ssomar.score.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
public abstract class FeatureAbstract<T> implements FeatureInterface<T> {

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
        return featureClicked.contains(name);
    }
}
