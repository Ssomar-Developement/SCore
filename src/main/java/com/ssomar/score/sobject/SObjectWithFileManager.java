package com.ssomar.score.sobject;

import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
            if (path.contains("/")) {
                String ymlPart = "";
                String[] parts = path.split("/");
                ymlPart = parts[parts.length - 1];
                path = path.replace("/" + ymlPart, "");
            } else continue;
            if (path.equalsIgnoreCase(folder)) objects.add(object);
        }
        return objects;
    }

    public List<String> getFoldersNames() {
        List<String> folders = new ArrayList<>();
        for (T item : this.getLoadedObjects()) {
            String path = item.getPath();
            path = path.replace("\\", "/");
            path = path.replace(fileLoader.getConfigsPath(), "");
            if (path.contains("/")) {
                String ymlPart = "";
                String[] parts = path.split("/");
                ymlPart = parts[parts.length - 1];
                path = path.replace("/" + ymlPart, "");
            } else continue;
            if (!folders.contains(path)) folders.add(path);
        }
        return folders;
    }
}
