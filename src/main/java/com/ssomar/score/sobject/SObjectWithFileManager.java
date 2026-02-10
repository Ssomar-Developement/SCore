package com.ssomar.score.sobject;

import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public abstract class SObjectWithFileManager<T extends SObjectWithFile> extends SObjectManager<T> {


    private SObjectWithFileLoader<T> fileLoader;

    public SObjectWithFileManager(SPlugin sPlugin) {
        super(sPlugin);
    }

    public SObjectWithFileManager(SPlugin sPlugin, String objectName) {
        super(sPlugin, objectName);
    }

    public List<T> getObjectsOfFolder(String folder) {
        List<T> objects = new ArrayList<>();
        for (T object : this.getLoadedObjects()) {
            String path = object.getPath();
            path = path.replace("\\", "/");
            path = path.replace(fileLoader.getConfigsPath(), "");
            int lastSlash = path.lastIndexOf("/");
            if (lastSlash < 0) continue;
            path = path.substring(0, lastSlash);
            if (path.equalsIgnoreCase(folder)) objects.add(object);
        }
        return objects;
    }

    public List<String> getFoldersNames() {
        Set<String> folders = new LinkedHashSet<>();
        for (T item : this.getLoadedObjects()) {
            String path = item.getPath();
            path = path.replace("\\", "/");
            path = path.replace(fileLoader.getConfigsPath(), "");
            int lastSlash = path.lastIndexOf("/");
            if (lastSlash < 0) continue;
            folders.add(path.substring(0, lastSlash));
        }
        return new ArrayList<>(folders);
    }
}
