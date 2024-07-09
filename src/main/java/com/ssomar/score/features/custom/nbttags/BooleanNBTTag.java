package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class BooleanNBTTag extends NBTTag {

    private boolean valueBoolean;
    private boolean isValueBoolean;

    public BooleanNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public BooleanNBTTag(String key, boolean valueBoolean) {
        super(key);
        this.valueBoolean = valueBoolean;
        this.isValueBoolean = true;
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtItem.getBoolean(getKey()) != isValueBoolean()) {
            nbtItem.setBoolean(getKey(), isValueBoolean());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtCompound.getBoolean(getKey()) != isValueBoolean()) {
            nbtCompound.setBoolean(getKey(), isValueBoolean);
            return true;
        }
        return false;

    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "BOOLEAN");
        configurationSection.set("nbt." + index + ".value", isValueBoolean());
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.valueBoolean = configurationSection.getBoolean("value", true);
    }

    @Override
    public String toString() {
        return "BOOLEAN::" + getKey() + "::" + isValueBoolean();
    }
}
