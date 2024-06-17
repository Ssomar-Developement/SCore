package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTList;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

@Getter
public class ListStringNBTTag extends NBTTag {

    private List<String> value;

    public ListStringNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public ListStringNBTTag(String key, List<String> value) {
        super(key);
        this.value = value;
    }

    @Override
    public void applyTo(NBTItem nbtItem) {
        NBTList<String> list = nbtItem.getStringList(getKey());
        list.clear();
        for (String s : value) {
            list.add(s);
        }
    }

    @Override
    public void applyTo(NBTCompound nbtCompound) {
        NBTList<String> list = nbtCompound.getStringList(getKey());
        list.clear();
        for (String s : value) {
            list.add(s);
        }
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "STRING_LIST");
        configurationSection.set("nbt." + index + ".value", value);
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.value = configurationSection.getStringList("value");
    }
}
