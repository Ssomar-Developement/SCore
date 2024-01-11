package com.ssomar.score.utils.writerreader;

import com.ssomar.score.splugin.SPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class WriterReaderPersistentDataContainer{

    public abstract PersistentDataContainer getPersistentDataContainer();

    public String readAllInfo() {
        StringBuilder sb = new StringBuilder();
        for(NamespacedKey key : getPersistentDataContainer().getKeys()){
            // NEED TO IMPROVE THAT X)
            sb.append(key.getKey()).append(": ");
            try{
                sb.append(getPersistentDataContainer().get(key, PersistentDataType.STRING)).append("\n");
            }
            catch (IllegalArgumentException e){
                try{
                    sb.append(getPersistentDataContainer().get(key, PersistentDataType.INTEGER)).append("\n");
                }
                catch (IllegalArgumentException e2){
                    try {
                        sb.append(getPersistentDataContainer().get(key, PersistentDataType.DOUBLE)).append("\n");
                    }
                    catch (IllegalArgumentException  e3){}
                }
            }
        }
        return sb.toString();
    }

    public void writeString(SPlugin splugin, String key, String value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        getPersistentDataContainer().set(key3, PersistentDataType.STRING, value);
    }

    public void writeStringIfNull(SPlugin splugin, String key, String value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (getPersistentDataContainer().get(key4, PersistentDataType.STRING) == null) {
                getPersistentDataContainer().set(key4, PersistentDataType.STRING, value);
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            getPersistentDataContainer().set(key4, PersistentDataType.STRING, value);
        }
    }

    public Optional<String> readString(SPlugin splugin, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        //SsomarDev.testMsg("readString key: " + key+ "> " + meta.getPersistentDataContainer().get(key3, PersistentDataType.STRING));
        return Optional.ofNullable(getPersistentDataContainer().get(key3, PersistentDataType.STRING));
    }

    public void writeInteger(SPlugin splugin, String key, int value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        getPersistentDataContainer().set(key3, PersistentDataType.INTEGER, value);
    }

    public void writeIntegerIfNull(SPlugin splugin, String key, int value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (getPersistentDataContainer().get(key4, PersistentDataType.INTEGER) == null) {
                getPersistentDataContainer().set(key4, PersistentDataType.INTEGER, value);
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            getPersistentDataContainer().set(key4, PersistentDataType.INTEGER, value);
        }
    }

    public Optional<Integer> readInteger(SPlugin splugin, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        return Optional.ofNullable(getPersistentDataContainer().get(key3, PersistentDataType.INTEGER));
    }

    public void writeDouble(SPlugin splugin, String key, double value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        getPersistentDataContainer().set(key3, PersistentDataType.DOUBLE, value);
    }

    public void writeDoubleIfNull(SPlugin splugin, String key, double value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (getPersistentDataContainer().get(key4, PersistentDataType.DOUBLE) == null) {
                getPersistentDataContainer().set(key4, PersistentDataType.DOUBLE, value);
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            getPersistentDataContainer().set(key4, PersistentDataType.DOUBLE, value);
        }
    }

    public Optional<Double> readDouble(SPlugin splugin, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        return Optional.ofNullable(getPersistentDataContainer().get(key3, PersistentDataType.DOUBLE));
    }

    public void writeList(SPlugin splugin, String key, List<String> value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        getPersistentDataContainer().set(key3, PersistentDataType.STRING, value.toString());
    }

    public void writeListIfNull(SPlugin splugin, String key, List<String> value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (getPersistentDataContainer().get(key4, PersistentDataType.STRING) == null) {
                getPersistentDataContainer().set(key4, PersistentDataType.STRING, value.toString());
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            getPersistentDataContainer().set(key4, PersistentDataType.STRING, value.toString());
        }
    }

    public Optional<List<String>> readList(SPlugin splugin, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        //SsomarDev.testMsg("readString key: " + key+ "> " + meta.getPersistentDataContainer().get(key3, PersistentDataType.STRING));
        String s = getPersistentDataContainer().get(key3, PersistentDataType.STRING);
        if (s != null) {
            return Optional.of(new ArrayList<>(Arrays.asList(s.substring(1, s.length() - 1).split(", "))));
        }
        return Optional.empty();
    }

    public void removeKey(SPlugin splugin, String key) {
        NamespacedKey key1 = new NamespacedKey(splugin.getPlugin(), key);
        getPersistentDataContainer().remove(key1);
    }
}
