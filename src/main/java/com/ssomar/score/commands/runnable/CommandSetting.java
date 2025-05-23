package com.ssomar.score.commands.runnable;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.EntityBuilder;
import com.ssomar.score.utils.backward_compatibility.AttributeAdditionMode;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public class CommandSetting {

    private List<String> names;

    private int oldSystemIndex;
    private boolean oldSystemOptional = false;
    private Object type;
    private Object defaultValue;

    private final boolean DEBUG = false;


    // Specifc for type
    @Setter
    private boolean acceptPercentage = false;
    @Setter
    private boolean isSlot = false;
    @Setter
    private boolean acceptUnderScoreForLongText = false;

    public CommandSetting(String name, int oldSystemIndex, Object type, Object defaultValue) {
        this.names = Collections.singletonList(name);
        this.oldSystemIndex = oldSystemIndex;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public CommandSetting(String name, int oldSystemIndex, Object type, Object defaultValue, boolean oldSystemOptional) {
        this.names = Collections.singletonList(name);
        this.oldSystemIndex = oldSystemIndex;
        this.type = type;
        this.defaultValue = defaultValue;
        this.oldSystemOptional = oldSystemOptional;
    }

    public CommandSetting(List<String> names, int oldSystemIndex, Object type, Object defaultValue) {
        this.names = names;
        this.oldSystemIndex = oldSystemIndex;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public CommandSetting(List<String> names, int oldSystemIndex, Object type, Object defaultValue, boolean oldSystemOptional) {
        this.names = names;
        this.oldSystemIndex = oldSystemIndex;
        this.type = type;
        this.defaultValue = defaultValue;
        this.oldSystemOptional = oldSystemOptional;
    }

    public Object getValue(String value) {
        SsomarDev.testMsg("CommandSetting getValue value: "+value+" >> type:"+type+" >> defaultValue: "+defaultValue, DEBUG);
        if(value == null) return defaultValue;
        if(type == Double.class) return Double.parseDouble(value);
        else if(type == Integer.class) return Double.valueOf(value).intValue();
        else if(type == Float.class) return Float.parseFloat(value);
        else if(type == Boolean.class) return Boolean.parseBoolean(value);
        else if(type == Enchantment.class) return Enchantment.getByKey(NamespacedKey.minecraft(value.toLowerCase()));
        else if(type == AttributeUtils.class) {
            return AttributeUtils.getAttribute(value);
        }
        else if(type == AttributeAdditionMode.class) {
            try {
                return AttributeAdditionMode.valueOf(value.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        }
        else if(type == EquipmentSlot.class) {
            try {
                return EquipmentSlot.valueOf(value.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        }
        else if(!SCore.is1v11Less() && type == BarColor.class) return BarColor.valueOf(value.toUpperCase());
        else if(type == Material.class) {
            try {
                return Material.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        else if(type == BlockFace.class) {
            try {
                return BlockFace.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        else if(type == UUID.class) {
            try {
                return UUID.fromString(value);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        else if(type == EntityType.class){
            try {
                return EntityType.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        else if(type == EntityBuilder.class){
            return new EntityBuilder(value);
        }
        if(acceptUnderScoreForLongText && !value.contains("http")) return value.replaceAll("_", " ");
        return value;
    }

    public ArgumentChecker checkValue(String value, String commandTemplate){
        if(type == Double.class) return SCommand.checkDouble(value, false, commandTemplate, acceptPercentage);
        else if(type == Integer.class)
            if(isSlot) return SCommand.checkSlot(value, false, commandTemplate);
            else return SCommand.checkInteger(value, false, commandTemplate);
        else if(type == Float.class) return SCommand.checkFloat(value, false, commandTemplate, acceptPercentage);
        else if(type == Boolean.class) return SCommand.checkBoolean(value, false, commandTemplate);
        else if(type == Enchantment.class) return SCommand.checkEnchantment(value, false, commandTemplate);
        else if(type == AttributeUtils.class) return SCommand.checkAttribute(value, false, commandTemplate);
        else if(type == AttributeAdditionMode.class) return SCommand.checkAttributeAdditionMode(value, false, commandTemplate);
        else if(type == EquipmentSlot.class) return SCommand.checkEquipmentSlot(value, false, commandTemplate);
        else if(!SCore.is1v11Less() && type == BarColor.class) return SCommand.checkBarColor(value, false, commandTemplate);
        else if(type == Material.class) return SCommand.checkMaterial(value, false, commandTemplate);
        else if(type == BlockFace.class) return SCommand.checkBlockFace(value, false, commandTemplate);
        else if(type == UUID.class) return SCommand.checkUUID(value, false, commandTemplate);
        else if(type == EntityType.class) return SCommand.checkEntity(value, false, commandTemplate);
        return null;
    }
}
