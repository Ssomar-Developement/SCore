package com.ssomar.score.features.custom.nbttags;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CompoundNBTTag extends NBTTag {

    @Getter
    List<NBTTag> nbtTags;

    public CompoundNBTTag(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public CompoundNBTTag(String key, List<NBTTag> nbtTags) {
        super(key);
        this.nbtTags = nbtTags;
    }

    public CompoundNBTTag(String key, NBTCompound nbtCompound) {
        super(key);
        this.nbtTags = new ArrayList<>();

        for (String s : nbtCompound.getKeys()) {
            if (NBTTags.blackListedTags().contains(s)) continue;

            NBTType type = nbtCompound.getType(s);

            switch (type) {
                case NBTTagInt:
                    this.nbtTags.add(new IntNBTTag(s, nbtCompound.getInteger(s)));
                    break;
                case NBTTagByte:
                    this.nbtTags.add(new ByteNBTTag(s, nbtCompound.getByte(s)));
                    break;
                case NBTTagByteArray:
                    break;
                case NBTTagCompound:
                    this.nbtTags.add(new CompoundNBTTag(s, nbtCompound.getCompound(s)));
                    break;
                case NBTTagDouble:
                    this.nbtTags.add(new DoubleNBTTag(s, nbtCompound.getDouble(s)));
                    break;
                case NBTTagEnd:
                    break;
                case NBTTagFloat:
                    break;
                case NBTTagIntArray:
                    break;
                case NBTTagList:
                    break;
                case NBTTagLong:
                    break;
                case NBTTagShort:
                    break;
                case NBTTagString:
                    this.nbtTags.add(new StringNBTTag(s, nbtCompound.getString(s)));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean applyTo(ReadWriteNBT nbtItem, boolean onlyIfDifferent) {
        ReadWriteNBT compound = nbtItem.getOrCreateCompound(getKey());

        boolean different = false;
        for (NBTTag nbtTag : nbtTags) {
            different = different || nbtTag.applyTo(compound, onlyIfDifferent);
        }
        return different;
    }

    @Override
    public boolean applyTo(NBTCompound nbtCompound, boolean onlyIfDifferent) {
        //SsomarDev.testMsg(">>> BULD CompoundNBTTag: " + getKey(), true);
        NBTCompound compound = nbtCompound.addCompound(getKey());

        boolean different = false;
        for (NBTTag nbtTag : nbtTags) {
            //SsomarDev.testMsg(">>> chldren CompoundNBTTag: " + nbtTag.getKey(), true);
            different = different || nbtTag.applyTo(compound, onlyIfDifferent);
        }
        return different;
    }

    @Override
    public void saveValueInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".type", "COMPOUND");
        if (nbtTags.isEmpty()) configurationSection.set("nbt." + index + ".value", null);
        else {
            ConfigurationSection newSection = configurationSection.createSection("nbt." + index + ".value");
            Integer subIndex = 0;
            for (NBTTag nbtTag : nbtTags) {
                nbtTag.saveInConfig(newSection, subIndex);
                subIndex++;
            }
        }
    }

    @Override
    public void loadValueFromConfig(ConfigurationSection configurationSection) {
        this.nbtTags = new ArrayList<>();
        ConfigurationSection subSection = configurationSection.getConfigurationSection("value");

        if (subSection != null && subSection.contains("nbt")) {

            ConfigurationSection nbtSection = subSection.getConfigurationSection("nbt");
            for (String nbtId : nbtSection.getKeys(false)) {
                ConfigurationSection tagSection = nbtSection.getConfigurationSection(nbtId);

                String type = tagSection.getString("type").toUpperCase();

                NBTTag tag = null;

                switch (type) {
                    case "BOOLEAN":
                    case "BOOL":
                        tag = new BooleanNBTTag(tagSection);
                        break;
                    case "STRING":
                    case "STR":
                        tag = new StringNBTTag(tagSection);
                        break;
                    case "DOUBLE":
                        tag = new DoubleNBTTag(tagSection);
                        break;
                    case "INTEGER":
                    case "INT":
                        tag = new IntNBTTag(tagSection);
                        break;
                    case "BYTE":
                        tag = new ByteNBTTag(tagSection);
                        break;
                    case "COMPOUND":
                        tag = new CompoundNBTTag(tagSection);
                        break;
                }
                if (tag != null) this.nbtTags.add(tag);
            }
        }
    }

}
