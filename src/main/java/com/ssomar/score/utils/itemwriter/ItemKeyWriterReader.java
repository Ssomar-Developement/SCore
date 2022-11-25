package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public interface ItemKeyWriterReader {

    //TODO CHANGE THAT TO HAVE ONLY ONE INSTANCE
    static ItemKeyWriterReader init() {
        if (SCore.is1v13Less()) return new NBTWriterReader();
        else return new NameSpaceKeyWriterReader();
    }

    void writeString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value);

    void writeStringIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value);

    Optional<String> readString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    void writeInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value);

    void writeIntegerIfNull(SPlugin var1, ItemStack var2, DynamicMeta var3, String var4, int var5);

    Optional<Integer> readInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    void writeDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value);

    void writeDoubleIfNull(SPlugin var1, ItemStack var2, DynamicMeta var3, String var4, double var5);

    Optional<Double> readDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    void removeKey(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    Optional<UUID> readItemOwner(ItemStack item, DynamicMeta dMeta);
}
