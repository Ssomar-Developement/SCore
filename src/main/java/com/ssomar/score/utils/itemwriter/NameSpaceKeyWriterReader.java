package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NameSpaceKeyWriterReader implements ItemKeyWriterReader {

    public void writeString(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeString(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeStringIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeStringIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<String> readString(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readString(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    public void writeInteger(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeInteger(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeIntegerIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeIntegerIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<Integer> readInteger(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readInteger(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public void writeDouble(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeDouble(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeDoubleIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeDoubleIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<Double> readDouble(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readDouble(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public void writeList(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeList(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeListIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeListIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<List<String>> readList(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readList(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public void removeKey(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.removeKey(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public Optional<UUID> readItemOwner(ItemStack item, DynamicMeta dMeta) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readItemOwner(dMeta.getMeta().getPersistentDataContainer());
    }
}
