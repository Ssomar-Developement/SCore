package com.ssomar.score.utils.backward_compatibility;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.MapUtil;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AttributeUtils {

    private final static boolean DEBUG = false;

    public static Map<Object, String> getAttributes() {
        Map<Object, String> list = new HashMap<>();
        if (SCore.is1v21v2Plus()) {
            for (Keyed l : Registry.ATTRIBUTE) {
                NamespacedKey key = l.getKey();
                if (key.getNamespace().equals("minecraft")) {
                    list.put(l, l.getKey().getKey().toUpperCase());
                } else list.put(l, l.getKey().toString());
            }
        } else if (!SCore.is1v8()) {
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
        for (Map.Entry<Object, String> entry : getAttributes().entrySet()) {
            if (entry.getValue().equalsIgnoreCase(string)) {
                return (Attribute) entry.getKey();
            }
        }
        switch (string.toUpperCase()) {
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
            case "GENERIC_FALL_DAMAGE_MULTIPLIER":
                return Attribute.FALL_DAMAGE_MULTIPLIER;
            case "GENERIC_MAX_ABSORPTION":
                return Attribute.MAX_ABSORPTION;
            case "GENERIC_SAFE_FALL_DISTANCE":
                return Attribute.SAFE_FALL_DISTANCE;
            case "GENERIC_SCALE":
                return Attribute.SCALE;
            case "GENERIC_STEP_HEIGHT":
                return Attribute.STEP_HEIGHT;
            case "GENERIC_GRAVITY":
                return Attribute.GRAVITY;
            case "GENERIC_BURNING_TIME":
                return Attribute.BURNING_TIME;
            case "GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE":
                return Attribute.EXPLOSION_KNOCKBACK_RESISTANCE;
            case "GENERIC_MOVEMENT_EFFICIENCY":
                return Attribute.MOVEMENT_EFFICIENCY;
            case "GENERIC_OXYGEN_BONUS":
                return Attribute.OXYGEN_BONUS;
            case "GENERIC_WATER_MOVEMENT_EFFICIENCY":
                return Attribute.WATER_MOVEMENT_EFFICIENCY;
            case "PLAYER_BLOCK_INTERACTION_RANGE":
                return Attribute.BLOCK_INTERACTION_RANGE;
            case "PLAYER_ENTITY_INTERACTION_RANGE":
                return Attribute.ENTITY_INTERACTION_RANGE;
            case "PLAYER_BLOCK_BREAK_SPEED":
                return Attribute.BLOCK_BREAK_SPEED;
            case "PLAYER_MINING_EFFICIENCY":
                return Attribute.MINING_EFFICIENCY;
            case "PLAYER_SNEAKING_SPEED":
                return Attribute.SNEAKING_SPEED;
            case "PLAYER_SUBMERGED_MINING_SPEED":
                return Attribute.SUBMERGED_MINING_SPEED;
            case "PLAYER_SWEEPING_DAMAGE_RATIO":
                return Attribute.SWEEPING_DAMAGE_RATIO;
            case "ZOMBIE_SPAWN_REINFORCEMENTS":
                return Attribute.SPAWN_REINFORCEMENTS;
            case "MAX_HEALTH":
                return Attribute.MAX_HEALTH;
            case "FOLLOW_RANGE":
                return Attribute.FOLLOW_RANGE;
            case "KNOCKBACK_RESISTANCE":
                return Attribute.KNOCKBACK_RESISTANCE;
            case "MOVEMENT_SPEED":
                return Attribute.MOVEMENT_SPEED;
            case "FLYING_SPEED":
                return Attribute.FLYING_SPEED;
            case "ATTACK_DAMAGE":
                return Attribute.ATTACK_DAMAGE;
            case "ATTACK_KNOCKBACK":
                return Attribute.ATTACK_KNOCKBACK;
            case "ATTACK_SPEED":
                return Attribute.ATTACK_SPEED;
            case "ARMOR":
                return Attribute.ARMOR;
            case "ARMOR_TOUGHNESS":
                return Attribute.ARMOR_TOUGHNESS;
            case "LUCK":
                return Attribute.LUCK;
            case "JUMP_STRENGTH":
                return Attribute.JUMP_STRENGTH;
            case "FALL_DAMAGE_MULTIPLIER":
                return Attribute.FALL_DAMAGE_MULTIPLIER;
            case "MAX_ABSORPTION":
                return Attribute.MAX_ABSORPTION;
            case "SAFE_FALL_DISTANCE":
                return Attribute.SAFE_FALL_DISTANCE;
            case "SCALE":
                return Attribute.SCALE;
            case "STEP_HEIGHT":
                return Attribute.STEP_HEIGHT;
            case "GRAVITY":
                return Attribute.GRAVITY;
            case "BURNING_TIME":
                return Attribute.BURNING_TIME;
            case "EXPLOSION_KNOCKBACK_RESISTANCE":
                return Attribute.EXPLOSION_KNOCKBACK_RESISTANCE;
            case "MOVEMENT_EFFICIENCY":
                return Attribute.MOVEMENT_EFFICIENCY;
            case "OXYGEN_BONUS":
                return Attribute.OXYGEN_BONUS;
            case "WATER_MOVEMENT_EFFICIENCY":
                return Attribute.WATER_MOVEMENT_EFFICIENCY;
            case "BLOCK_INTERACTION_RANGE":
                return Attribute.BLOCK_INTERACTION_RANGE;
            case "ENTITY_INTERACTION_RANGE":
                return Attribute.ENTITY_INTERACTION_RANGE;
            case "BLOCK_BREAK_SPEED":
                return Attribute.BLOCK_BREAK_SPEED;
            case "MINING_EFFICIENCY":
                return Attribute.MINING_EFFICIENCY;
            case "SNEAKING_SPEED":
                return Attribute.SNEAKING_SPEED;
            case "SUBMERGED_MINING_SPEED":
                return Attribute.SUBMERGED_MINING_SPEED;
            case "SWEEPING_DAMAGE_RATIO":
                return Attribute.SWEEPING_DAMAGE_RATIO;
            case "SPAWN_REINFORCEMENTS":
                return Attribute.SPAWN_REINFORCEMENTS;
        }
        return null;
    }

    public static void addAttributeOnItemMeta(@NotNull ItemMeta meta, Material itemType, LinkedHashMap<Attribute, AttributeModifier> attributes, boolean keepDefaultItemAttributes, boolean keepExistingAttributes, boolean overrideExistingAttributes) {
        addAttributeOnItemMeta(meta, itemType, attributes, keepDefaultItemAttributes, keepExistingAttributes, overrideExistingAttributes, false);
    }

    public static void addAttributeOnItemMeta(@NotNull ItemMeta meta, Material itemType, LinkedHashMap<Attribute, AttributeModifier> attributes, boolean keepDefaultItemAttributes, boolean keepExistingAttributes, boolean overrideExistingAttributes,boolean stackOnExistingAttributes) {

        /* remove ExistingAttributes if needed */
        if (!keepExistingAttributes) {
            SsomarDev.testMsg("REMOVE ALL EXISTING ATTRIBUTES", DEBUG);
            Multimap<Attribute, AttributeModifier> reset = LinkedHashMultimap.create();
            meta.setAttributeModifiers(reset);
        }

        /* remove ExistingAttributes if needed */
        if (!keepDefaultItemAttributes) {
            SsomarDev.testMsg("REMOVE ALL DEFAULT ATTRIBUTES", DEBUG);
            if (!meta.hasAttributeModifiers()) {
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    Multimap<Attribute, AttributeModifier> invert = itemType.getDefaultAttributeModifiers(equipmentSlot);
                    if (invert.isEmpty()) continue;
                    SsomarDev.testMsg("add blank attribute for EquipmentSlot: " + equipmentSlot, DEBUG);
                    for (Attribute col : invert.keySet()) {
                        for (AttributeModifier att : invert.get(col)) {
                            meta.addAttributeModifier(col, new AttributeModifier(att.getUniqueId(), att.getName(), 0, att.getOperation(), att.getSlot()));
                        }
                    }
                }
            }
            //Multimap<Attribute, AttributeModifier> reset = LinkedHashMultimap.create();
            //meta.setAttributeModifiers(reset);
        }

        //if (meta.hasAttributeModifiers()) SsomarDev.testMsg("GET DEFAULT DAMAGE: " + meta.getAttributeModifiers(AttributeUtils.getAttribute("GENERIC_ATTACK_DAMAGE")), DEBUG);

        /* add new attributes */
        for (Attribute att : attributes.keySet()) {
            AttributeModifier attModifier = attributes.get(att);
            EquipmentSlot slot = attModifier.getSlot();
            AtomicReference<AttributeModifier> toRemove = new AtomicReference<>();
            // stack value if needed
            AtomicReference<Double> stackValue = new AtomicReference<>((double) 0);
            // On supprime l'existant si besoin
            meta.getAttributeModifiers(slot).forEach((attribute, modifier) -> {
                if (attribute.equals(att)) {
                    if (overrideExistingAttributes) {
                        toRemove.set(modifier);
                    }
                    if(stackOnExistingAttributes && modifier.getOperation() == attModifier.getOperation() && modifier.getSlotGroup() == attModifier.getSlotGroup()) {
                        toRemove.set(modifier);
                        stackValue.set(modifier.getAmount());
                    }
                }
            });
            if (toRemove.get() != null) {
                meta.removeAttributeModifier(att, toRemove.get());
            }
            // New value with stack
            if(stackOnExistingAttributes){
                meta.addAttributeModifier(att,new AttributeModifier(attModifier.getKey(), attModifier.getAmount()+stackValue.get(), attModifier.getOperation(), attModifier.getSlotGroup()));
            }
            else meta.addAttributeModifier(att, attModifier);
        }

        /* add default attributes if needed */
        if (SCore.is1v19Plus() && keepDefaultItemAttributes) {
            if (meta.hasAttributeModifiers()) {
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    SsomarDev.testMsg("add default attributes for EquipmentSlot: " + equipmentSlot, DEBUG);
                    Multimap<Attribute, AttributeModifier> defaultAttributes = itemType.getDefaultAttributeModifiers(equipmentSlot);
                    for (Attribute col : defaultAttributes.keySet()) {
                        for (AttributeModifier att : defaultAttributes.get(col)) {
                            try {
                                meta.addAttributeModifier(col, att);
                            } catch (IllegalArgumentException e) {
                                SsomarDev.testMsg("No add default attribute because already exist: " + col + " " + att, DEBUG);
                            }
                        }
                    }
                }
            }
           /* else {
                else if (SCore.is1v19Plus() && !config.getAttributes().getKeepDefaultAttributes().getValue() && item.getType().isItem() && !SCore.is1v21Plus()) {
                    Material material = item.getType();
                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        SsomarDev.testMsg("add attributes for EquipmentSlot: " + equipmentSlot, DEBUG);
                        Multimap<Attribute, AttributeModifier> invert = material.getDefaultAttributeModifiers(equipmentSlot);
                        // Invert the default attributes *-1
                        for (Attribute col : invert.keySet()) {
                            for (AttributeModifier att : invert.get(col)) {
                                result.put(col, new AttributeModifier(att.getUniqueId(), att.getName(), 0, att.getOperation(), att.getSlot()));
                            }
                        }
                    }
                }
            }*/
        }

    }
}
