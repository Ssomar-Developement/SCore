package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * When used, it gives the target player/entity a given attribute for a set period of time.
 * The attribute provided by this custom command does not set the value, but gives a modifier.
 */
public class AddTemporaryAttribute extends MixedCommand  {
    private HashMap<String, Attribute> attributeHashMap = new HashMap<>();

    {
        attributeHashMap.put("ARMOR", Attribute.ARMOR);
        attributeHashMap.put("ARMOR_TOUGHNESS", Attribute.ARMOR_TOUGHNESS);
        attributeHashMap.put("ATTACK_DAMAGE", Attribute.ATTACK_DAMAGE);
        attributeHashMap.put("ATTACK_KNOCKBACK", Attribute.ATTACK_KNOCKBACK);
        attributeHashMap.put("ATTACK_SPEED", Attribute.ATTACK_SPEED);
        attributeHashMap.put("BLOCK_BREAK_SPEED", Attribute.BLOCK_BREAK_SPEED);
        attributeHashMap.put("BLOCK_INTERACTION_RANGE", Attribute.BLOCK_INTERACTION_RANGE);
        attributeHashMap.put("BURNING_TIME", Attribute.BURNING_TIME);
        attributeHashMap.put("ENTITY_INTERACTION_RANGE", Attribute.ENTITY_INTERACTION_RANGE);
        attributeHashMap.put("EXPLOSION_KNOCKBACK_RESISTANCE", Attribute.EXPLOSION_KNOCKBACK_RESISTANCE);
        attributeHashMap.put("FALL_DAMAGE_MULTIPLIER", Attribute.FALL_DAMAGE_MULTIPLIER);
        attributeHashMap.put("FLYING_SPEED", Attribute.FLYING_SPEED);
        attributeHashMap.put("FOLLOW_RANGE", Attribute.FOLLOW_RANGE);
        attributeHashMap.put("GRAVITY", Attribute.GRAVITY);
        attributeHashMap.put("JUMP_STRENGTH", Attribute.JUMP_STRENGTH);
        attributeHashMap.put("KNOCKBACK_RESISTANCE", Attribute.KNOCKBACK_RESISTANCE);
        attributeHashMap.put("LUCK", Attribute.LUCK);
        attributeHashMap.put("MAX_ABSORPTION", Attribute.MAX_ABSORPTION);
        attributeHashMap.put("MAX_HEALTH", Attribute.MAX_HEALTH);
        attributeHashMap.put("MINING_EFFICIENCY", Attribute.MINING_EFFICIENCY);
        attributeHashMap.put("MOVEMENT_EFFICIENCY", Attribute.MOVEMENT_EFFICIENCY);
        attributeHashMap.put("MOVEMENT_SPEED", Attribute.MOVEMENT_SPEED);
        attributeHashMap.put("OXYGEN_BONUS", Attribute.OXYGEN_BONUS);
        attributeHashMap.put("SAFE_FALL_DISTANCE", Attribute.SAFE_FALL_DISTANCE);
        attributeHashMap.put("SCALE", Attribute.SCALE);
        attributeHashMap.put("SPAWN_REINFORCEMENTS", Attribute.SPAWN_REINFORCEMENTS);
        attributeHashMap.put("SNEAKING_SPEED", Attribute.SNEAKING_SPEED);
        attributeHashMap.put("STEP_HEIGHT", Attribute.STEP_HEIGHT);
        attributeHashMap.put("SUBMERGED_MINING_SPEED", Attribute.SUBMERGED_MINING_SPEED);
        attributeHashMap.put("SWEEPING_DAMAGE_RATIO", Attribute.SWEEPING_DAMAGE_RATIO);
        attributeHashMap.put("TEMPT_RANGE", Attribute.TEMPT_RANGE);
        attributeHashMap.put("WATER_MOVEMENT_EFFICIENCY", Attribute.WATER_MOVEMENT_EFFICIENCY);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_TEMPORARY_ATTRIBUTE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_TEMPORARY_ATTRIBUTE {attribute} {amount} {operation} {time in ticks}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();

        // arg0: attribute
        // arg1: amount
        // arg2: operation
        Operation operation;
        // arg3: timeinticks

        // invalid attribute checker
        if (!attributeHashMap.containsKey(args.get(0))) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute: "+args.get(0));
            return;
        }
        // invalid double checker for temp attribute amount
        try {
            Double.parseDouble(args.get(1));
        } catch (Exception e) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute amount: "+args.get(1));
            return;
        }
        // invalid operation checker
        switch (args.get(2)) {
            case "ADD_NUMBER": {
                operation = Operation.ADD_NUMBER;
                break;
            }
            case "ADD_SCALAR": {
                operation = Operation.ADD_SCALAR;
                break;
            }
            case "MULTIPLY_SCALAR_1": {
                operation = Operation.MULTIPLY_SCALAR_1;
                break;
            }
            default: {
                SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute operation: "+args.get(2));
                return;
            }

        }
        // invalid long vlaue checker for temp attribute tick duration
        try {
            Long.parseLong(args.get(3));
        } catch (Exception e) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field tick duration: "+args.get(2));
            return;
        }


        AttributeInstance attrInstance = null;

        // the entity arg in the method arguments refer to the target of the command and the player arg represents the caster.
        // if the target is a Player, cast the entity as a player.
        if (entity instanceof Player) {
            Player playerEntity = (Player) entity;
            attrInstance = playerEntity.getAttribute(attributeHashMap.get(args.get(0)));
        } else {
            LivingEntity livingEntity = (LivingEntity) entity;
            attrInstance = livingEntity.getAttribute((attributeHashMap.get(args.get(0))));
        }

        NamespacedKey key = NamespacedKey.minecraft(args.get(0).toLowerCase());
        AttributeModifier tempModifier = new AttributeModifier(key, Double.valueOf(args.get(1)), operation);

        attrInstance.addModifier(tempModifier);

        AttributeInstance finalAttrInstance = attrInstance;
        new BukkitRunnable() {
            @Override
            public void run() {
                finalAttrInstance.removeModifier(tempModifier);
            }
        }.runTaskLater(SCore.plugin, Long.valueOf(args.get(3)));
    }

}
