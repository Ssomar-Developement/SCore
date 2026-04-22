package com.ssomar.score.features.custom.nbttags;

import com.ssomar.score.utils.placeholders.StringPlaceholder;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class IntNBTTag extends NBTTag {

    /**
     * Has to be saved as {@code String} first to support rand placeholder
     */
    private String valueInt;

    public IntNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public IntNBTTag(String key, String valueInt) {
        super(key);
        this.valueInt = valueInt;
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        int integerValue = Integer.parseInt(StringPlaceholder.replaceRandomPlaceholders(String.valueOf(getValueInt())));
        if (!onlyIfDifferent || nbtItem.getInteger(getKey()) != integerValue) {
            nbtItem.setInteger(getKey(), integerValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        int integerValue = Integer.parseInt(StringPlaceholder.replaceRandomPlaceholders(String.valueOf(getValueInt())));
        if (!onlyIfDifferent || nbtCompound.getInteger(getKey()) != integerValue) {
            nbtCompound.setInteger(getKey(), integerValue);
            return true;
        }
        return false;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "INT");
        configurationSection.set("nbt." + index + ".value", Integer.parseInt(getValueInt()));
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.valueInt = configurationSection.getString("value", "-1");
    }

    @Override
    public String toString() {
        return "INTEGER::" + getKey() + "::" + getValueInt() + (isSaveInPDC() ? "::true" : "");
    }
}
