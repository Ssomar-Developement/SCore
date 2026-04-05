package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public abstract class NBTTag {

    private String key;

    /**
     * When {@code true} this tag will be written to the item's Persistent Data
     * Container instead of the raw NBT compound.  Only simple types (STRING,
     * INTEGER, DOUBLE, BOOLEAN, BYTE, STRING_LIST) are supported in the PDC;
     * COMPOUND and COMPOUND_LIST tags that carry this flag will be silently
     * skipped and a console warning will be logged.
     *
     * <p>Requires Minecraft 1.14+. On older server versions this flag is
     * ignored and the tag is written as raw NBT regardless.
     */
    private boolean saveInPDC = false;

    public NBTTag(String key) {
        this.key = key;
    }

    public NBTTag(ConfigurationSection configurationSection) {
        this.key = configurationSection.getString("key");
        this.saveInPDC = configurationSection.getBoolean("saveInPDC", false);
        loadValueFromConfig(configurationSection);
    }

    public abstract boolean applyTo(ReadWriteNBT readWriteNbt, boolean onlyIfDifferent);

    public abstract boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent);

    public void saveInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".key", getKey());
        if (saveInPDC) configurationSection.set("nbt." + index + ".saveInPDC", true);
        saveValueInConfig(configurationSection, index);
    }

    public abstract void saveValueInConfig(ConfigurationSection configurationSection, Integer index);

    public abstract void loadValueFromConfig(ConfigurationSection configurationSection);
}
