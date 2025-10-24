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

        // to prevent this custom command from working if used by nonliving entities such as arrows
        if (!(entity instanceof LivingEntity)) return;

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

        // At this point, the checks are done. All provided arguments are now valid!
        LivingEntity livingEntity = (LivingEntity) entity;
        AttributeInstance attrInstance = livingEntity.getAttribute((attrType));

        // make a randomized key to allow spamming of ADD_TEMPORARY_ATTRIBUTE
        NamespacedKey attr_key = new NamespacedKey(SCore.plugin, String.valueOf(UUID.randomUUID()));
        AttributeModifier tempModifier = new AttributeModifier(attr_key, Double.parseDouble(sCommandToExec.getSettingValue("amount").toString()), operation);

        assert attrInstance != null;

        attrInstance.addModifier(tempModifier);

        // a record must be made asap to ensure when crashes or restarts occur, expired attribute modifiers can be removed upon the player's relog
        if (entity instanceof Player) TemporaryAttributeQuery.insertToRecords(
                Database.getInstance().connect(),
                String.valueOf(attr_key),
                attrTypeID,
                amount,
                String.valueOf(entity.getUniqueId()),
                expiry_time);



        String finalAttrTypeID1 = attrTypeID;
        Runnable runlater = new Runnable() {
            @Override
            public void run() {
                if (!entity.isDead() || (entity instanceof Player && ((Player) entity).isOnline())) {
                    AttributeUtils.removeSpecificAttribute((LivingEntity) entity, finalAttrTypeID1, attr_key.toString());
                    if (entity instanceof Player) TemporaryAttributeQuery.removeFromRecords(Database.getInstance().connect(), attr_key.toString());
                }
                // AddTemporaryAttributeManager's method will be called upon player relog if it expires while the player is offline
            }
        };


        if (!(entity instanceof Player)) SCore.schedulerHook.runEntityTask(runlater, null, entity, Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString()));
        else SCore.schedulerHook.runTask(runlater, Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString()));
    }

}
