package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class StringNBTTag extends NBTTag {

    private String valueString;
    private boolean isValueString;

    public StringNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public StringNBTTag(String key, String valueString) {
        super(key);
        this.valueString = valueString;
        this.isValueString = true;
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        if (!onlyIfDifferent || !nbtItem.getString(getKey()).equals(getValueString())) {
            nbtItem.setString(getKey(), getValueString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        //SsomarDev.testMsg("StringNBTTag: " + getKey() + " " + getValueString(), true);
        if (!onlyIfDifferent || !nbtCompound.getString(getKey()).equals(getValueString())) {
            nbtCompound.setString(getKey(), getValueString());
            return true;
        }
        return false;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "STRING");
        configurationSection.set("nbt." + index + ".value", getValueString());
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.valueString = configurationSection.getString("value", "");
    }

    @Override
    public String toString() {
        return "STRING::" + getKey() + "::" + getValueString();
    }

}
