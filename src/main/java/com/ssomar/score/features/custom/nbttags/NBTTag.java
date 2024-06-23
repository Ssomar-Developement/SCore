package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public abstract class NBTTag {

    private String key;

    public NBTTag(String key) {
        this.key = key;
    }

    public NBTTag(ConfigurationSection configurationSection) {
        this.key = configurationSection.getString("key");
        loadValueFromConfig(configurationSection);
    }

    public abstract void applyTo(ReadWriteNBT readWriteNbt);

    public abstract void applyTo(NBTCompound nbtCompound);

    public void saveInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".key", getKey());
        saveValueInConfig(configurationSection, index);
    }

    public abstract void saveValueInConfig(ConfigurationSection configurationSection, Integer index);

    public abstract void loadValueFromConfig(ConfigurationSection configurationSection);
}
