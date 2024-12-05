package com.ssomar.score.utils.emums;

import org.bukkit.inventory.EquipmentSlot;

public enum AttributeSlot {
    BODY,
    CHEST,
    FEET,
    HAND,
    HEAD,
    LEGS,
    OFF_HAND,
    ALL_SLOTS;


    public static AttributeSlot fromEquipmentSlot(EquipmentSlot equipmentSlot){
        switch(equipmentSlot){
            case CHEST:
                return AttributeSlot.CHEST;
            case FEET:
                return AttributeSlot.FEET;
            case HAND:
                return AttributeSlot.HAND;
            case HEAD:
                return AttributeSlot.HEAD;
            case LEGS:
                return AttributeSlot.LEGS;
            case OFF_HAND:
                return AttributeSlot.OFF_HAND;
            case BODY:
                return AttributeSlot.BODY;
            default:
                return AttributeSlot.ALL_SLOTS;
        }
    }
}
