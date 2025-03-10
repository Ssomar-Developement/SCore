package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.DynamicMeta;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class NBTWriterReader implements ItemKeyWriterReader {

    public void writeString(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setString(key, value);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeStringIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (!nbti.hasKey(key)) {
                nbti.setString(key, value);
                nbti.applyNBT(item);
            }
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public Optional<String> readString(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                return Optional.ofNullable(nbti.getString(key));
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void writeInteger(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setInteger(key, value);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeIntegerIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (!nbti.hasKey(key)) {
                nbti.setInteger(key, value);
                nbti.applyNBT(item);
            }
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public Optional<Integer> readInteger(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                return Optional.ofNullable(nbti.getInteger(key));
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void writeDouble(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setDouble(key, value);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeDoubleIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (!nbti.hasKey(key)) {
                nbti.setDouble(key, value);
                nbti.applyNBT(item);
            }
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public Optional<Double> readDouble(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                return Optional.ofNullable(nbti.getDouble(key));
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void writeList(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setString(key, value.toString());
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeListIfNull(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (!nbti.hasKey(key)) {
                nbti.setString(key, value.toString());
                nbti.applyNBT(item);
            }
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public Optional<List<String>> readList(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                String s = nbti.getString(key);
                if (s != null) {
                    return Optional.of(new ArrayList<>(Arrays.asList(s.substring(1, s.length() - 1).split(", "))));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void removeKey(Plugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.removeKey(key);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public Optional<UUID> readItemOwner(ItemStack item, DynamicMeta dMeta) {
        if (SCore.hasExecutableItems) {
            if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
                NBTItem nbti = new NBTItem(item);
                if (nbti.hasTag("EI-OWNER")) {
                    String ownerUUIDStr = nbti.getString("EI-OWNER");
                    try {
                        return Optional.ofNullable(UUID.fromString(ownerUUIDStr));
                    } catch (IllegalArgumentException e) {
                        return Optional.ofNullable(null);
                    }
                }
            }
        }
        return Optional.ofNullable(null);
    }
}
