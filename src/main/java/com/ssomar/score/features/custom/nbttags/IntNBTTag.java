package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class IntNBTTag extends NBTTag {

    private int valueInt;

    public IntNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public IntNBTTag(String key, int valueInt) {
        super(key);
        this.valueInt = valueInt;
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtItem.getInteger(getKey()) != getValueInt()) {
            nbtItem.setInteger(getKey(), getValueInt());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtCompound.getInteger(getKey()) != getValueInt()) {
            nbtCompound.setInteger(getKey(), getValueInt());
            return true;
        }
        return false;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "INT");
        configurationSection.set("nbt." + index + ".value", getValueInt());
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.valueInt = configurationSection.getInt("value", 0);
    }

    @Override
    public String toString() {
        return "INTEGER::" + getKey() + "::" + getValueInt();
    }
}
