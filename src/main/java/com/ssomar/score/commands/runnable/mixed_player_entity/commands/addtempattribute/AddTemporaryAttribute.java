package com.ssomar.score.commands.runnable.mixed_player_entity.commands.addtempattribute;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.TemporaryAttributeQuery;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * When used, it gives the target player/entity a given attribute for a set period of time.
 * The attribute provided by this custom command does not set the value, but gives a modifier.
 */
public class AddTemporaryAttribute extends MixedCommand  {

    public AddTemporaryAttribute() {

        CommandSetting attribute = new CommandSetting("attribute", 0, String.class, "SCALE");
        CommandSetting amount = new CommandSetting("amount", 1, Double.class, 1);
        CommandSetting operation = new CommandSetting("operation", 2, String.class, "ADD_NUMBER");
        CommandSetting timeinticks = new CommandSetting("timeinticks", 3, Long.class, 20);
        List<CommandSetting> settings = getSettings();
        settings.add(attribute);
        settings.add(amount);
        settings.add(operation);
        settings.add(timeinticks);
        setNewSettingsMode(true);
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
        // arg0: attribute
        // arg1: amount
        // arg2: operation
        Operation operation;
        // arg3: timeinticks

        // invalid attribute checker
        Attribute attrType = AttributeUtils.getAttribute((String) sCommandToExec.getSettingValue("attribute"));
        String attrTypeID = "";
        double amount = 0;
        long expiry_time = 0;

        if (attrType == null) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute: "+sCommandToExec.getSettingValue("attribute"));
            return;
        }
        attrTypeID = (String) sCommandToExec.getSettingValue("attribute");
        // invalid double checker for temp attribute amount
        try {
            amount = Double.parseDouble(sCommandToExec.getSettingValue("amount").toString());
        } catch (Exception e) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute amount: "+sCommandToExec.getSettingValue("amount").toString());
            return;
        }
        // invalid operation checker
        switch (sCommandToExec.getSettingValue("operation").toString().toUpperCase()) {
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
                SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute operation: "+sCommandToExec.getSettingValue("operation").toString().toUpperCase());
                return;
            }

        }
        // invalid long value checker for temp attribute tick duration
        try {
            expiry_time = System.currentTimeMillis() + (Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString())*50);
        } catch (Exception e) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field tick duration: "+sCommandToExec.getSettingValue("timeinticks").toString());
            return;
        }


        AttributeInstance attrInstance = null;

        // the entity arg in the method arguments refer to the target of the command and the player arg represents the caster.
        // if the target is a Player, cast the entity as a player.
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            attrInstance = livingEntity.getAttribute((attrType));
        }

        // make a randomized key to allow spamming of ADD_TEMPORARY_ATTRIBUTE
        NamespacedKey attr_key = new NamespacedKey(SCore.plugin, String.valueOf(UUID.randomUUID()));
        AttributeModifier tempModifier = new AttributeModifier(attr_key, Double.parseDouble(sCommandToExec.getSettingValue("amount").toString()), operation);

        attrInstance.addModifier(tempModifier);

        // add to records
        TemporaryAttributeQuery.insertToRecords(
                Database.getInstance().connect(),
                attr_key.toString(),
                attrTypeID,
                amount,
                String.valueOf(entity.getUniqueId()),
                expiry_time);

        AttributeInstance finalAttrInstance = attrInstance;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isDead()) {
                    finalAttrInstance.removeModifier(tempModifier);
                }
            }
        }.runTaskLater(SCore.plugin, Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString()));
    }

}
