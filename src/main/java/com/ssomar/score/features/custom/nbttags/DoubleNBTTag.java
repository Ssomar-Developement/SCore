package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class DoubleNBTTag extends NBTTag {

    private double valueDouble;
    private boolean isValueDouble;

    public DoubleNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public DoubleNBTTag(String key, double valueDouble) {
        super(key);
        this.valueDouble = valueDouble;
        this.isValueDouble = true;
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtItem.getDouble(getKey()) != getValueDouble()) {
            nbtItem.setDouble(getKey(), getValueDouble());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtCompound.getDouble(getKey()) != getValueDouble()) {
            nbtCompound.setDouble(getKey(), getValueDouble());
            return true;
        }
        return false;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "DOUBLE");
        configurationSection.set("nbt." + index + ".value", getValueDouble());
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.valueDouble = configurationSection.getDouble("value", 0);
    }

    @Override
    public String toString() {
        return "DOUBLE::" + getKey() + "::" + getValueDouble();
    }
}
