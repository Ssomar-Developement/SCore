package com.ssomar.score.sobject;


import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public abstract class SObjectWithFile<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends SObject<X, Y, Z> {

    private String path;
    private SObjectWithFileLoader sObjectWithFileLoader;

    public SObjectWithFile(String id, FeatureSettingsInterface featureSettings, String path, SObjectWithFileLoader sObjectWithFileLoader) {
        super(id, null, featureSettings);
        this.path = path;
        this.sObjectWithFileLoader = sObjectWithFileLoader;
    }

    /**
     * Useful to have an option to set a parent for the clone option
     **/
    public SObjectWithFile(String id,FeatureParentInterface parent, FeatureSettingsInterface featureSettings, String path, SObjectWithFileLoader sObjectWithFileLoader) {
        super(id, parent, featureSettings);
        this.path = path;
        this.sObjectWithFileLoader = sObjectWithFileLoader;
    }

    @Override
    public boolean delete() {
        File file = null;
        if ((file = new File(getPath())) != null) {
            file.delete();
            return true;
        }
        return false;

    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        File file = getFile();
        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return config;
    }

    @Override
    public File getFile() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path).createNewFile();
                file = sObjectWithFileLoader.searchFileOfObject(getId());
            } catch (IOException ignored) {
                return null;
            }
        }
        return file;
    }
}
