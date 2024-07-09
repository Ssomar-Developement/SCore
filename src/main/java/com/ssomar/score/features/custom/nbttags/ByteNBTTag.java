package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class ByteNBTTag extends NBTTag {

    private byte valueByte;
    private boolean isValueByte;

    public ByteNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public ByteNBTTag(String key, byte valueByte) {
        super(key);
        this.valueByte = valueByte;
        this.isValueByte = true;
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtItem.getByte(getKey()) != getValueByte()) {
            nbtItem.setByte(getKey(), getValueByte());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || nbtCompound.getByte(getKey()) != getValueByte()) {
            nbtCompound.setByte(getKey(), getValueByte());
            return true;
        }
        return false;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "BYTE");
        configurationSection.set("nbt." + index + ".value", getValueByte());
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        byte b = 0;
        int i2 = configurationSection.getInt("value", 0);
        if (i2 == 1) b = 1;
        this.valueByte = b;
    }
}
