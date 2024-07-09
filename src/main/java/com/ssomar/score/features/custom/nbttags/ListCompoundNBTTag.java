package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTListCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBTCompoundList;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Getter
public class

ListCompoundNBTTag extends NBTTag {

    private List<CompoundNBTTag> values;

    public ListCompoundNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public ListCompoundNBTTag(String key, NBTCompoundList entries) {
        super(key);
        values = new ArrayList<>();
        //SsomarDev.testMsg("ListCompoundNBTTag: " + key, true);
        int i = 0;
        for (Object s : entries.toArray()) {
            //SsomarDev.testMsg("ListCompoundNBTTag ssss : " + s, true);
            NBTCompound compound = (NBTCompound) s;
            values.add(new CompoundNBTTag("comp"+i, compound));
            i++;
        }
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        ReadWriteNBTCompoundList list = nbtItem.getCompoundList(getKey());
        //list.clear();

        boolean different = false;
        for (CompoundNBTTag s : values) {
            ReadWriteNBT nbtListCompound = list.addCompound();
            for (NBTTag t : s.getNbtTags()) {
                different = different || t.applyTo(nbtListCompound, onlyIfDifferent);
            }
        }
        return different;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        NBTCompoundList list = nbtCompound.getCompoundList(getKey());
        //list.clear();

        boolean different = false;
        for (CompoundNBTTag s : values) {
            NBTListCompound nbtListCompound = list.addCompound();
            for (NBTTag t : s.getNbtTags()) {
                different = different || t.applyTo(nbtListCompound, onlyIfDifferent);
            }
        }
        return different;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "COMPOUND_LIST");
        int i = 0;
        for (CompoundNBTTag s : values) {
            ConfigurationSection section = configurationSection.createSection("nbt." + index + ".value." + i);
            section.set("key", "comp" + i);
            s.saveValueInConfig(section, 0);
            i++;
        }
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        values = new ArrayList<>();
        //SsomarDev.testMsg(" BASE PATH: " + configurationSection.getCurrentPath(), true);
        ConfigurationSection section = configurationSection.getConfigurationSection("value");
        for (String s : section.getKeys(false)) {
            //SsomarDev.testMsg(" D / " + s, true);
            String key = section.getString(s + ".key");
            if(section.isConfigurationSection(s + ".nbt.0")) {
                ConfigurationSection tagSection = section.getConfigurationSection(s + ".nbt.0");
                // transfer the key, because its not well made
                tagSection.set("key", key);
                //SsomarDev.testMsg("SAVEEE: " + tagSection.getCurrentPath(), true);
                values.add(new CompoundNBTTag(tagSection));
            }
            //else SsomarDev.testMsg("A SECTION IS INCORRECTLY MADE: "+ s, true);
        }
    }
}
