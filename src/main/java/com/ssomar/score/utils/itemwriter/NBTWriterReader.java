package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.SCore;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.DynamicMeta;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class NBTWriterReader implements ItemKeyWriterReader {

    public void writeString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setString(key, value);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeStringIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, String value) {
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
    public Optional<String> readString(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                return Optional.ofNullable(nbti.getString(key));
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void writeInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setInteger(key, value);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeIntegerIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, int value) {
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
    public Optional<Integer> readInteger(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                return Optional.ofNullable(nbti.getInteger(key));
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void writeDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setDouble(key, value);
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeDoubleIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, double value) {
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
    public Optional<Double> readDouble(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            if (nbti.hasKey(key)) {
                return Optional.ofNullable(nbti.getDouble(key));
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void writeList(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
        item.setItemMeta(dMeta.getMeta());
        if (SCore.hasNBTAPI && !item.getType().equals(Material.AIR)) {
            NBTItem nbti = new NBTItem(item);
            nbti.setString(key, value.toString());
            nbti.applyNBT(item);
        }
        dMeta.setMeta(item.getItemMeta());
    }

    @Override
    public void writeListIfNull(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key, List<String> value) {
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
    public Optional<List<String>> readList(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
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
    public void removeKey(SPlugin splugin, ItemStack item, DynamicMeta dMeta, String key) {
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
                if (nbti.hasKey("EI-OWNER")) {
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
