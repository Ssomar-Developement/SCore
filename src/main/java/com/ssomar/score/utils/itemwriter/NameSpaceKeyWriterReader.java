package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NameSpaceKeyWriterReader implements ItemKeyWriterReader {

    public void writeString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeString(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeStringIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeStringIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<String> readString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readString(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    public void writeInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeInteger(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeIntegerIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeIntegerIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<Integer> readInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readInteger(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public void writeDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeDouble(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeDoubleIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeDoubleIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<Double> readDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readDouble(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public void writeList(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeList(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public void writeListIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.writeListIfNull(splugin, dMeta.getMeta().getPersistentDataContainer(), key, value);
    }

    @Override
    public Optional<List<String>> readList(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readList(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public void removeKey(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.removeKey(splugin, dMeta.getMeta().getPersistentDataContainer(), key);
    }

    @Override
    public Optional<UUID> readItemOwner(ItemStack item, DynamicMeta dMeta) {
        return com.ssomar.score.utils.writer.NameSpaceKeyWriterReader.readItemOwner(dMeta.getMeta().getPersistentDataContainer());
    }
}
