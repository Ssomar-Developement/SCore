package com.ssomar.score.commands.runnable.mixed_player_entity.commands;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;

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

import javax.xml.stream.events.Namespace;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * When used, it gives the target player/entity a given attribute for a set period of time.
 * The attribute provided by this custom command does not set the value, but gives a modifier.
 */
public class AddTemporaryAttribute extends MixedCommand  {

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
        Attribute attrCheck = AttributeUtils.getAttribute(args.get(0).toUpperCase());

        if (attrCheck == null) {
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
        switch (args.get(2).toUpperCase()) {
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
            attrInstance = playerEntity.getAttribute(AttributeUtils.getAttribute(args.get(0).toUpperCase()));
        } else {
            LivingEntity livingEntity = (LivingEntity) entity;
            attrInstance = livingEntity.getAttribute((AttributeUtils.getAttribute(args.get(0).toUpperCase())));
        }

        // make a randomized key to allow spamming of ADD_TEMPORARY_ATTRIBUTE
        NamespacedKey key = new NamespacedKey(SCore.plugin, "mod_" + UUID.randomUUID());
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
