package com.ssomar.score.utils.writer;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class NameSpaceKeyWriterReader {


    public static String readAllInfo(PersistentDataContainer dataContainer) {
        StringBuilder sb = new StringBuilder();
        for(NamespacedKey key : dataContainer.getKeys()){
            // NEED TO IMPROVE THAT X)
            sb.append(key.getKey()).append(": ");
            try{
                sb.append(dataContainer.get(key, PersistentDataType.STRING)).append("\n");
            }
            catch (IllegalArgumentException e){
                try{
                    sb.append(dataContainer.get(key, PersistentDataType.INTEGER)).append("\n");
                }
                catch (IllegalArgumentException e2){
                    try {
                        sb.append(dataContainer.get(key, PersistentDataType.DOUBLE)).append("\n");
                    }
                    catch (IllegalArgumentException ignored){}
                }
            }
        }
        return sb.toString();
    }

    public static void writeString(SPlugin splugin, PersistentDataContainer dataContainer, String key, String value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        dataContainer.set(key3, PersistentDataType.STRING, value);
    }

    public static void writeStringIfNull(SPlugin splugin, PersistentDataContainer dataContainer, String key, String value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (dataContainer.get(key4, PersistentDataType.STRING) == null) {
                dataContainer.set(key4, PersistentDataType.STRING, value);
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            dataContainer.set(key4, PersistentDataType.STRING, value);
        }
    }

    public static Optional<String> readString(SPlugin splugin, PersistentDataContainer dataContainer, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        //SsomarDev.testMsg("readString key: " + key+ "> " + meta.getPersistentDataContainer().get(key3, PersistentDataType.STRING));
        return Optional.ofNullable(dataContainer.get(key3, PersistentDataType.STRING));
    }

    public static void writeInteger(SPlugin splugin, PersistentDataContainer dataContainer, String key, int value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        dataContainer.set(key3, PersistentDataType.INTEGER, value);
    }

    public static void writeIntegerIfNull(SPlugin splugin, PersistentDataContainer dataContainer, String key, int value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (dataContainer.get(key4, PersistentDataType.INTEGER) == null) {
                dataContainer.set(key4, PersistentDataType.INTEGER, value);
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
           dataContainer.set(key4, PersistentDataType.INTEGER, value);
        }
    }

    public static Optional<Integer> readInteger(SPlugin splugin, PersistentDataContainer dataContainer, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        return Optional.ofNullable(dataContainer.get(key3, PersistentDataType.INTEGER));
    }


    public static void writeDouble(SPlugin splugin, PersistentDataContainer dataContainer, String key, double value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        dataContainer.set(key3, PersistentDataType.DOUBLE, value);
    }

    public static void writeDoubleIfNull(SPlugin splugin, PersistentDataContainer dataContainer, String key, double value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (dataContainer.get(key4, PersistentDataType.DOUBLE) == null) {
                dataContainer.set(key4, PersistentDataType.DOUBLE, value);
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            dataContainer.set(key4, PersistentDataType.DOUBLE, value);
        }
    }

    public static Optional<Double> readDouble(SPlugin splugin, PersistentDataContainer dataContainer, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        return Optional.ofNullable(dataContainer.get(key3, PersistentDataType.DOUBLE));
    }

    public static void writeList(SPlugin splugin, PersistentDataContainer dataContainer, String key, List<String> value) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        dataContainer.set(key3, PersistentDataType.STRING, value.toString());
    }

    public static void writeListIfNull(SPlugin splugin, PersistentDataContainer dataContainer, String key, List<String> value) {
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        try {
            if (dataContainer.get(key4, PersistentDataType.STRING) == null) {
                dataContainer.set(key4, PersistentDataType.STRING, value.toString());
            }
        }
        // Appear when the tag already exists but with a different type
        catch (IllegalArgumentException e){
            dataContainer.set(key4, PersistentDataType.STRING, value.toString());
        }
    }

    public static Optional<List<String>> readList(SPlugin splugin, PersistentDataContainer dataContainer, String key) {
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        //SsomarDev.testMsg("readString key: " + key+ "> " + meta.getPersistentDataContainer().get(key3, PersistentDataType.STRING));
        String s = dataContainer.get(key3, PersistentDataType.STRING);
        if (s != null) {
            return Optional.of(new ArrayList<>(Arrays.asList(s.substring(1, s.length() - 1).split(", "))));
        }
        return Optional.empty();
    }

    public static void removeKey(SPlugin splugin, PersistentDataContainer dataContainer, String key) {
        NamespacedKey key1 = new NamespacedKey(splugin.getPlugin(), key);
        dataContainer.remove(key1);
    }

    public static Optional<UUID> readItemOwner(PersistentDataContainer dataContainer) {
        if (SCore.hasExecutableItems) {
            NamespacedKey key = new NamespacedKey(ExecutableItems.getPluginSt(), "EI-OWNER");
            String ownerUUIDStr;
            if ((ownerUUIDStr = dataContainer.get(key, PersistentDataType.STRING)) != null) {
                try {
                    return Optional.ofNullable(UUID.fromString(ownerUUIDStr));
                } catch (IllegalArgumentException e) {
                    return Optional.ofNullable(null);
                }
            }
        }
        return Optional.ofNullable(null);
    }
}
