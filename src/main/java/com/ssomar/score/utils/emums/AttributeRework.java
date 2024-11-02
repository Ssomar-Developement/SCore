package com.ssomar.score.utils.emums;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.MapUtil;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AttributeRework {

    public static Map<Object, String> getAttributes() {
        Map<Object, String> list = new HashMap<>();
        if (SCore.is1v21v2Plus()) {
            for (Keyed l : Registry.ATTRIBUTE) {
                NamespacedKey key = l.getKey();
                if(key.getNamespace().equals("minecraft")) {
                    list.put(l, l.getKey().getKey().toUpperCase());
                }
                else list.put(l, l.getKey().toString());
            }
        } else {
            for (Object o : Attribute.class.getEnumConstants()) {
                // Use reflection to get .name()
                try {
                    String name = (String) o.getClass().getMethod("name").invoke(o);
                    list.put(o, name);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // Sort the list
        return MapUtil.sortByValue(list);
    }

    public static Attribute getAttribute(String string) {
        string = string.replace("minecraft:", "");
        for(Map.Entry<Object, String> entry : getAttributes().entrySet()) {
            if(entry.getValue().equalsIgnoreCase(string)) {
                return (Attribute) entry.getKey();
            }
        }
        switch (string.toUpperCase()){
            case "GENERIC_MAX_HEALTH":
                return Attribute.MAX_HEALTH;
            case "GENERIC_FOLLOW_RANGE":
                return Attribute.FOLLOW_RANGE;
            case "GENERIC_KNOCKBACK_RESISTANCE":
                return Attribute.KNOCKBACK_RESISTANCE;
            case "GENERIC_MOVEMENT_SPEED":
                return Attribute.MOVEMENT_SPEED;
            case "GENERIC_FLYING_SPEED":
                return Attribute.FLYING_SPEED;
            case "GENERIC_ATTACK_DAMAGE":
                return Attribute.ATTACK_DAMAGE;
            case "GENERIC_ATTACK_KNOCKBACK":
                return Attribute.ATTACK_KNOCKBACK;
            case "GENERIC_ATTACK_SPEED":
                return Attribute.ATTACK_SPEED;
            case "GENERIC_ARMOR":
                return Attribute.ARMOR;
            case "GENERIC_ARMOR_TOUGHNESS":
                return Attribute.ARMOR_TOUGHNESS;
            case "GENERIC_LUCK":
                return Attribute.LUCK;
            case "GENERIC_JUMP_STRENGTH":
                return Attribute.JUMP_STRENGTH;
        }
        return null;
    }
}
