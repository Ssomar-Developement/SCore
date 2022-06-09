package com.ssomar.testRecode.features;

import com.google.common.base.Charsets;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(parent == null && (this instanceof FeatureParentInterface)) {
            this.parent = (FeatureParentInterface) this;
        }
        this.name = name;
        this.editorName = editorName;
        this.editorDescription = editorDescription;
        this.editorMaterial = editorMaterial;
        this.requirePremium = requirePremium;
    }

    public <M> FeatureReturnCheckPremium<M> checkPremium(String featureTypeName, M value, Optional<M> defaultValue, boolean isPremiumLoading){
        if(requirePremium() && !isPremiumLoading) {
            if(defaultValue.isPresent()){
                if(!defaultValue.get().equals(value)){
                    return new FeatureReturnCheckPremium<>("&cERROR, Couldn't load the "+featureTypeName+" value of " + this.getName() + " from config, value: " + value+ " &7&o"+getParent().getParentInfo()+" &6>> Because it's a premium feature ! (reset to default: "+defaultValue.get()+")", defaultValue.get());
                }
            }
            else{
                return new FeatureReturnCheckPremium<>("&cERROR, Couldn't load the "+featureTypeName+" value of " + this.getName() + " from config, value: " + value+ " &7&o"+getParent().getParentInfo()+" &6>> Because it's a premium feature !", null);
            }
        }
        return new FeatureReturnCheckPremium<>();
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.equals(editorName);
    }

    public void initAndUpdateItemParentEditor(GUI gui, int slot) {
        initItemParentEditor(gui, slot).updateItemParentEditor(gui);
    }

    public void save() {
        ConfigurationSection config = parent.getConfigurationSection();
        save(config);
        while(config.getParent() != null) {
            config = config.getParent();
        }
        writeInFile(config);
    }

    public void writeInFile(ConfigurationSection config) {
        File file = parent.getFile();
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

            try {
                writer.write(((FileConfiguration)config).saveToString());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean requirePremium() {
        return requirePremium;
    }

    @Override
    public abstract Y clone();
}
