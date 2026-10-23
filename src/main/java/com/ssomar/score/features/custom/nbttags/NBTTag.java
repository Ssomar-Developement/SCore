package com.ssomar.score.features.custom.nbttags;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Base class for different types of nbt. <br/>
 * <br/>
 * The reason why the applyTo() and applyToComp() requires an {@code Object} class is because
 * the original code directly utilizes classes from NBT API
 */
@Getter
public abstract class NBTTag {

    private String key;

    /**
     * When {@code true} this tag will be written to the item's Persistent Data
     * Container instead of the raw NBT compound.  Only simple types (STRING,
     * INTEGER, DOUBLE, BOOLEAN, BYTE, STRING_LIST) are supported in the PDC;
     * COMPOUND and COMPOUND_LIST tags that carry this flag will be silently
     * skipped and a console warning will be logged.
     *
     * <p>Requires Minecraft 1.14+. On older server versions this flag is
     * ignored and the tag is written as raw NBT regardless.
     */
    private boolean saveInPDC = false;

    public NBTTag(String key) {
        this.key = key;
    }

    /**
     * Saves the values from the source of changes towards the item config. <br/>
     * @param configurationSection
     */
    public NBTTag(ConfigurationSection configurationSection) {
        this.key = configurationSection.getString("key");
        this.saveInPDC = configurationSection.getBoolean("saveInPDC", false);
        loadValueFromConfig(configurationSection);
    }

    /**
     * This method is used to apply nbt details to items. Will be more likely to be executed in cases such as
     * when you give yourself items via <code>/ei give</code>
     * @param readWriteNbt {@code de.tr7zw.nbtapi.iface.ReadWriteNBT}
     * @param onlyIfDifferent main executor's default value is {@code true}. It's to prevent accidental rewrite of nbt because previous reports complained about nbt getting shuffled around and having the wrong order
     * @return
     */
    public abstract boolean applyTo(Object readWriteNbt, boolean onlyIfDifferent);

    /**
     * This method is used mainly by ListCompoundNBT to write child nbt tags to items
     * @param nbtCompound {@code de.tr7zw.nbtapi.NBTCompound}
     * @param onlyIfDifferent main executor's default value is {@code true}. It's to prevent accidental rewrite of nbt because previous reports complained about nbt getting shuffled around and having the wrong order
     * @return
     */
    public abstract boolean applyToComp(Object nbtCompound, boolean onlyIfDifferent);

    /**
     * When the user makes changes to the nbt list in the ingame editor or other ways, this method is called to save the changes to the item config.
     * @param configurationSection
     * @param index
     */
    public void saveInConfig(ConfigurationSection configurationSection, Integer index) {
        configurationSection.set("nbt." + index + ".key", getKey());
        if (saveInPDC) configurationSection.set("nbt." + index + ".saveInPDC", true);
        saveValueInConfig(configurationSection, index);
    }

    /**
     * This method is used to save custom nbt details to an ExecutableItem's yml config.
     * @param configurationSection the config pointer in the yml file. May get longer if the plugin is trying to save a really long NBT Compound
     * @param index usually starts at 0. Goes up if there are sibling NBTs in the nest level of a primary NBT's children
     */
    public abstract void saveValueInConfig(ConfigurationSection configurationSection, Integer index);

    /**
     * Gets executed during plugin load/reload. This is used when class inheritors try to read the nbt field of an item config
     * and save it in their instance variable: {@code List<NBTTag> nbtTags} for later utilization in {@link NBTTag#applyTo(ReadWriteNBT, boolean)}
     * and
     * @param configurationSection
     */
    public abstract void loadValueFromConfig(ConfigurationSection configurationSection);
}
