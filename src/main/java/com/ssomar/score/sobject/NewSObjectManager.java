package com.ssomar.score.sobject;

import com.ssomar.score.sobject.events.SObjectLoadEvent;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public abstract class NewSObjectManager<T extends NewSObject> {

    private SPlugin sPlugin;
    private List<T> loadedObjects;
    private List<T> defaultObjects;
    private List<T> allObjects;
    private String objectName;

    public NewSObjectManager(SPlugin sPlugin) {
        this.sPlugin = sPlugin;
        allObjects = new ArrayList<>();
        defaultObjects = new ArrayList<>();
        loadedObjects = new ArrayList<>();
        objectName = "OBJECT";
    }

    public NewSObjectManager(SPlugin sPlugin, String objectName) {
        this.sPlugin = sPlugin;
        allObjects = new ArrayList<>();
        defaultObjects = new ArrayList<>();
        loadedObjects = new ArrayList<>();
        this.objectName = objectName;
    }

    public void addLoadedObject(T object) {
        addLoadedObject(object, true);
    }

    public void addLoadedObject(T object, boolean generateLoadEvent) {
        loadedObjects.add(object);

        actionOnObjectWhenLoading(object);
        if(generateLoadEvent) generateLoadEvent(object.getId(), object);

        allObjects = new ArrayList<>();
        allObjects.addAll(defaultObjects);
        allObjects.addAll(loadedObjects);
    }

    public void addLoadedObjects(List<T> object) {
       addLoadedObjects(object, true);
    }

    public void addLoadedObjects(List<T> object, boolean generateLoadEvent) {
        loadedObjects.addAll(object);

        for (T o : object) {
            actionOnObjectWhenLoading(o);
            if(generateLoadEvent) generateLoadEvent(o.getId(), o);
        }

        allObjects = new ArrayList<>();
        allObjects.addAll(defaultObjects);
        allObjects.addAll(loadedObjects);
    }

    public abstract void actionOnObjectWhenLoading(T object);

    public void addDefaultLoadedObject(T object) {
        defaultObjects.add(object);
        actionOnObjectWhenLoading(object);
        generateLoadEvent(object.getId(), object);
    }

    public void generateLoadEvent(String id, T object) {
        SObjectLoadEvent event = new SObjectLoadEvent(id, object);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public Optional<T> getDefaultObjectWithID(String ID) {
        for (T o : defaultObjects) {
            if (o.getId().equals(ID)) {
                return Optional.ofNullable(o);
            }
        }
        return Optional.ofNullable(null);
    }

    public Optional<T> getLoadedObjectWithID(String ID) {
        for (T o : allObjects) {
            if (o.getId().equals(ID)) {
                return Optional.ofNullable(o);
            }
        }
        return Optional.ofNullable(null);
    }

    public List<String> getLoadedObjectsIDs() {
        List<String> ids = new ArrayList<>();
        for (T o : allObjects) {
            ids.add(o.getId());
        }
        return ids;
    }

    public void reloadObject(String id) {
        Optional<T> loadedObject = this.getLoadedObjectWithID(id);
        if (loadedObject.isPresent()) {
            T o = loadedObject.get();
            actionOnObjectWhenReloading(o);
            this.allObjects.remove(o);
            this.loadedObjects.remove(o);
        }

        sPlugin.getPlugin().getServer().getLogger().info(sPlugin.getNameDesign() + " reloading of " + id);

        Optional<T> oOpt = methodObjectLoading(id);
        if (oOpt.isPresent()) {
            this.addLoadedObject(oOpt.get());
        } else
            sPlugin.getPlugin().getServer().getLogger().severe(sPlugin.getNameDesign() + " Error when trying to reload the item " + id);
    }

    public void deleteObject(String id) {
        Optional<T> loadedObject = this.getLoadedObjectWithID(id);
        if (loadedObject.isPresent()) {
            T o = loadedObject.get();
            actionOnObjectWhenReloading(o);
            this.allObjects.remove(o);
            this.loadedObjects.remove(o);
            o.delete();
        }
    }

    public void deleteAllLoadedObjects() {
        while (!loadedObjects.isEmpty()) {
            T o = loadedObjects.get(0);
            deleteObject(o.getId());
        }
    }

    public void saveAllLoadedObjects() {
        for (T o : loadedObjects) {
            o.save();
        }
    }

    public abstract void actionOnObjectWhenReloading(T object);

    public abstract Optional<T> methodObjectLoading(String id);


}