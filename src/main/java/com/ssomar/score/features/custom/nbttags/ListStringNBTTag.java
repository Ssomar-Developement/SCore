package com.ssomar.score.features.custom.nbttags;

import com.ssomar.score.SCore;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTList;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBTList;
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
    public boolean applyTo(Object nbtItemOpt, boolean onlyIfDifferent) {
        ReadWriteNBT nbtItem = null;
        if (SCore.hasNBTAPI) {
            nbtItem = (ReadWriteNBT) nbtItemOpt;
        } else return false;

        ReadWriteNBTList<String> list = nbtItem.getStringList(getKey());
        //list.clear();
        boolean different = false;
        for (String s : value) {
            if(onlyIfDifferent && list.contains(s)) continue;
            list.add(s);
            different = true;
        }
        return different;
    }

    @Override
    public boolean applyToComp(Object nbtCompoundOpt, boolean onlyIfDifferent) {
        NBTCompound nbtCompound = null;
        if (SCore.hasNBTAPI) {
            nbtCompound = (NBTCompound) nbtCompoundOpt;
        } else return false;

        NBTList<String> list = nbtCompound.getStringList(getKey());
        //list.clear();
        boolean different = false;
        for (String s : value) {
            if(onlyIfDifferent && list.contains(s)) continue;
            list.add(s);
            different = true;
        }
        return different;
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
