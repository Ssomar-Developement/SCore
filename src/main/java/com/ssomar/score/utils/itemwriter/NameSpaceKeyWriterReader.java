package com.ssomar.score.utils.itemwriter;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;
import java.util.UUID;

public class NameSpaceKeyWriterReader implements ItemKeyWriterReader {

    public void writeString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        meta.getPersistentDataContainer().set(key3, PersistentDataType.STRING, value);
    }

    @Override
    public void writeStringIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        if (meta.getPersistentDataContainer().get(key4, PersistentDataType.STRING) == null) {
            meta.getPersistentDataContainer().set(key4, PersistentDataType.STRING, value);
        }
    }

    @Override
    public Optional<String> readString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        //SsomarDev.testMsg("readString key: " + key+ "> " + meta.getPersistentDataContainer().get(key3, PersistentDataType.STRING));
        return Optional.ofNullable(meta.getPersistentDataContainer().get(key3, PersistentDataType.STRING));
    }

    public void writeInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        meta.getPersistentDataContainer().set(key3, PersistentDataType.INTEGER, value);
    }

    @Override
    public void writeIntegerIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        if (meta.getPersistentDataContainer().get(key4, PersistentDataType.INTEGER) == null) {
            meta.getPersistentDataContainer().set(key4, PersistentDataType.INTEGER, value);
        }
    }

    @Override
    public Optional<Integer> readInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        return Optional.ofNullable(meta.getPersistentDataContainer().get(key3, PersistentDataType.INTEGER));
    }

    @Override
    public void writeDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        meta.getPersistentDataContainer().set(key3, PersistentDataType.DOUBLE, value);
    }

    @Override
    public void writeDoubleIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key4 = new NamespacedKey(splugin.getPlugin(), key);
        if (meta.getPersistentDataContainer().get(key4, PersistentDataType.DOUBLE) == null) {
            meta.getPersistentDataContainer().set(key4, PersistentDataType.DOUBLE, value);
        }
    }

    @Override
    public Optional<Double> readDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key3 = new NamespacedKey(splugin.getPlugin(), key);
        return Optional.ofNullable(meta.getPersistentDataContainer().get(key3, PersistentDataType.DOUBLE));
    }

    @Override
    public void removeKey(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        ItemMeta meta = dMeta.getMeta();
        NamespacedKey key1 = new NamespacedKey(splugin.getPlugin(), key);
        meta.getPersistentDataContainer().remove(key1);
    }

    @Override
    public Optional<UUID> readItemOwner(ItemStack item, DynamicMeta dMeta) {
        if (SCore.hasExecutableItems) {
            NamespacedKey key = new NamespacedKey(ExecutableItems.getPluginSt(), "EI-OWNER");
            PersistentDataContainer pDC = dMeta.getMeta().getPersistentDataContainer();
            String ownerUUIDStr;
            if ((ownerUUIDStr = pDC.get(key, PersistentDataType.STRING)) != null) {
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
