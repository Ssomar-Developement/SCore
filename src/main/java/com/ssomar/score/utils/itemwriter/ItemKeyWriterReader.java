package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemKeyWriterReader {

    //TODO CHANGE THAT TO HAVE ONLY ONE INSTANCE
    static ItemKeyWriterReader init() {
        if (SCore.is1v13Less()) return new NBTWriterReader();
        else return new NameSpaceKeyWriterReader();
    }

    void writeString(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value);

    void writeStringIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value);

    Optional<String> readString(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    void writeInteger(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value);

    void writeIntegerIfNull(Plugin var1, ItemStack var2, DynamicMeta var3, String var4, int var5);

    Optional<Integer> readInteger(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    void writeDouble(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value);

    void writeDoubleIfNull(Plugin var1, ItemStack var2, DynamicMeta var3, String var4, double var5);

    Optional<Double> readDouble(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key);


    void writeList(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value);

    void writeListIfNull(Plugin var1, ItemStack var2, DynamicMeta var3, String var4, List<String> var5);

    Optional<List<String>> readList(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    void removeKey(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key);

    Optional<UUID> readItemOwner(ItemStack item, DynamicMeta dMeta);
}
