package com.ssomar.score.features.custom.nbttags;

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
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        double doubleValue = Double.parseDouble(StringPlaceholder.replaceRandomPlaceholders(String.valueOf(getValueDouble())));
        if (!onlyIfDifferent || nbtItem.getDouble(getKey()) != doubleValue) {
            nbtItem.setDouble(getKey(), doubleValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        double doubleValue = Double.parseDouble(StringPlaceholder.replaceRandomPlaceholders(String.valueOf(getValueDouble())));
        if (!onlyIfDifferent || nbtCompound.getDouble(getKey()) != doubleValue) {
            nbtCompound.setDouble(getKey(), doubleValue);
            return true;
        }
        return false;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "DOUBLE");
        configurationSection.set("nbt." + index + ".value", Double.valueOf(getValueDouble()));
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
