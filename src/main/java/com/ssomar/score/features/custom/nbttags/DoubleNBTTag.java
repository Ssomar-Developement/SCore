package com.ssomar.score.features.custom.nbttags;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class DoubleNBTTag extends NBTTag {

    /**
     * Has to be saved as {@code String} first to support rand placeholder
     */
    private String valueDouble;
    private boolean isValueDouble;

    public DoubleNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public DoubleNBTTag(String key, String valueDouble) {
        super(key);
        this.valueDouble = valueDouble;
        this.isValueDouble = true;
    }

    @Override
    public boolean applyTo(Object nbtItemOpt, boolean onlyIfDifferent) {
        ReadWriteNBT nbtItem = null;
        if (SCore.hasNBTAPI) {
            nbtItem = (ReadWriteNBT) nbtItemOpt;
        } else return false;

        double doubleValue = Double.parseDouble(StringPlaceholder.replaceRandomPlaceholders(String.valueOf(getValueDouble())));
        if (!onlyIfDifferent || nbtItem.getDouble(getKey()) != doubleValue) {
            nbtItem.setDouble(getKey(), doubleValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean applyToComp(Object nbtCompoundOpt, boolean onlyIfDifferent) {
        NBTCompound nbtCompound = null;
        if (SCore.hasNBTAPI) {
            nbtCompound = (NBTCompound) nbtCompoundOpt;
        } else return false;

        double doubleValue = Double.parseDouble(StringPlaceholder.replaceRandomPlaceholders(String.valueOf(getValueDouble())));
        if (!onlyIfDifferent || nbtCompound.getDouble(getKey()) != doubleValue) {
            nbtCompound.setDouble(getKey(), doubleValue);
            return true;
        }
        return false;
    }

    /**
     * Extra work is required for int and double to support random placeholders
     * @param configurationSection the config pointer in the yml file. May get longer if the plugin is trying to save a really long NBT Compound
     * @param index usually starts at 0. Goes up if there are sibling NBTs in the nest level of a primary NBT's children
     */
    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        try {
            configurationSection.set("nbt." + index + ".type", "DOUBLE");
            configurationSection.set("nbt." + index + ".value", Double.valueOf(getValueDouble()));
        } catch (NumberFormatException e) {
            configurationSection.set("nbt." + index + ".type", "DOUBLE");
            configurationSection.set("nbt." + index + ".value", getValueDouble());
        }


    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.valueDouble = configurationSection.getString("value", "-1");
    }

    @Override
    public String toString() {
        return "DOUBLE::" + getKey() + "::" + getValueDouble() + (isSaveInPDC() ? "::true" : "");
    }
}
