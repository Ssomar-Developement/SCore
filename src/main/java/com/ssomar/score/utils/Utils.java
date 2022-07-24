package com.ssomar.score.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.ssomar.score.SCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void sendConsoleMsg(String msg) {
        SCore.plugin.getServer().getConsoleSender().sendMessage(format(msg));
    }

    public static String format(String msg) {
        Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");

        if (msg != null) {
            if (SCore.is1v16() || SCore.is1v17()) {
                Matcher match = pattern.matcher(msg);
                while (match.find()) {
                    String color = msg.substring(match.start(), match.end());
                    msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                    match = pattern.matcher(msg);
                }
            }
            return ChatColor.translateAlternateColorCodes('&', msg);
        }
        return msg;
    }

    public static Location blockLocation(Location loc) {
        loc.setX(Math.floor(loc.getX()));
        loc.setY(Math.floor(loc.getY()));
        loc.setZ(Math.floor(loc.getZ()));
        return loc;
    }

    public void sendMsg(Player p, String msg) {
        p.sendMessage(format(msg));
    }

    public void sendTitleMsg(Player p, String title, String subTitle, int fadein, int stay, int fadeout) {
        p.sendTitle(format(title), format(subTitle), fadein, stay, fadeout);
    }

    public List<String> formatList(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String str : list) {
            result.add(format(str));
        }
        return result;
    }

    public ItemStack mkItem(Material material, String displayName, String localizedName, List<String> lore, String texture) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(format(displayName));
        im.setLocalizedName(localizedName);
        if (lore != null)
            im.setLore(formatList(lore));
        is.setItemMeta(im);

        if (texture != null && material.equals(Material.PLAYER_HEAD))
            setSkullItemSkin(is, texture);

        return is;
    }

    public void setSkullItemSkin(ItemStack is, String texture) {

        ItemMeta meta = is.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field field;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
            x.printStackTrace();
        }
        is.setItemMeta(meta);
    }
}
