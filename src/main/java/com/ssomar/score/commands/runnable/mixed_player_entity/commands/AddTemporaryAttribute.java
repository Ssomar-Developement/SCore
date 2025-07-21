package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
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
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * When used, it gives the target player/entity a given attribute for a set period of time.
 * The attribute provided by this custom command does not set the value, but gives a modifier.
 * <br>
 * Checks needed before publishing updates: <br>
 * - Living entities must work properly with it.<br>
 * - If the living entity is a player and the player logged off, it should be stored in a variable then use PlayerJoinEvent to try to attempt removal once more.<br>
 *  - It should not face issues if multiple similar attributes are inserted.<br>
 * - If a non-living entity (arrows for example) was attempted to be given temporary attributes via this custom command, nothing should happen.
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
    public void run(Player p, Entity entityTarget, SCommandToExec sCommandToExec) {

        // this custom command should only work for LivingEntity entities
        if (!(entityTarget instanceof LivingEntity)) return;

        // arg0: attribute
        // arg1: amount
        // arg2: operation
        Operation operation;
        // arg3: timeinticks

        // invalid attribute checker
        Attribute attribute = AttributeUtils.getAttribute((String) sCommandToExec.getSettingValue("attribute"));

        if (attribute == null) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field attribute: "+sCommandToExec.getSettingValue("attribute"));
            return;
        }
        // invalid double checker for temp attribute amount
        try {
            Double.parseDouble(sCommandToExec.getSettingValue("amount").toString());
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
        // invalid long vlaue checker for temp attribute tick duration
        try {
            Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString());
        } catch (Exception e) {
            SCore.plugin.getLogger().info("[ADD_TEMPORARY_ATTRIBUTE] Invalid Attribute argument was provided for field tick duration: "+sCommandToExec.getSettingValue("timeinticks").toString());
            return;
        }


        AttributeInstance attrInstance = ((LivingEntity) entityTarget).getAttribute((attribute));

        // make a randomized key to allow spamming of ADD_TEMPORARY_ATTRIBUTE
        NamespacedKey key = new NamespacedKey(SCore.plugin, "mod_" + UUID.randomUUID());
        AttributeModifier tempModifier = new AttributeModifier(key, Double.parseDouble(sCommandToExec.getSettingValue("amount").toString()), operation);

        if (entityTarget instanceof Player) {
            if (!tempModifiers.containsKey(entityTarget.getUniqueId()))
                tempModifiers.put(entityTarget.getUniqueId(), new HashMap<>());

            final HashMap<Attribute, HashMap<NamespacedKey, Long>> entityTempAttr = tempModifiers.get(entityTarget.getUniqueId());

            if (!entityTempAttr.containsKey(attribute))
                tempModifiers.get(entityTarget.getUniqueId()).put(attribute, new HashMap<>());

            tempModifiers.get(entityTarget.getUniqueId()).get(attribute).put(key, System.currentTimeMillis() + Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString()) * 50);
        }

        attrInstance.addModifier(tempModifier);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (tempModifiers.get(entityTarget.getUniqueId()).get(attribute).containsKey(key)) {
                    if (entityTarget instanceof Player) {
                        if (((Player) entityTarget).isOnline()) {
                            attemptToRemoveModifier((Player) entityTarget);
                        }
                    } else {
                        // ingame entities are less problematic
                        ((LivingEntity) entityTarget).getAttribute(attribute).removeModifier(((LivingEntity) entityTarget).getAttribute(attribute).getModifier(key));
                    }
                }
            }
        }.runTaskLater(SCore.plugin, Long.parseLong(sCommandToExec.getSettingValue("timeinticks").toString()));
    }

    /**
     * Stores ongoing running bukkit tasks to prevent duplicates
     */
    private static final Set<NamespacedKey> scheduledKeyRemovals = new HashSet<>();
    /**<pre>
     * UUID - Stores the player's uuid
     *      Attribute - Stores the Attribute enum to organize temp attribute types
     *          Namespacedkey - a pointer/reference to the temp attribute (mainly for being able to grab its pointer again despite the relogs)
     *          Long - time till expiration in milliseconds</pre>
     */
    public static HashMap<UUID, HashMap<Attribute, HashMap<NamespacedKey, Long>>> tempModifiers = new HashMap<>();

    /**
     * Attempts to remove expired temporary attributes
     * @param player - the player who has the temporary attributes
     * @param ignoreTimeGap - mainly for the BukkitRunnable to rely on their timers. Reasons is that there has been issues where the System.currentTimeMillis() does not properly match up with
     *                      BukkitRunnable delay (some tests have observed 35 tick delays)
     */
    public static void attemptToRemoveModifier(Player player) {
        /**
         * L0 = HashMap<UUID, L1>
         * L1 = HashMap<Attribute, HashMap<Namespacedkey, Long>>
         * Had to write this because my brain is overloading just thinking about this nested hashmaps
         */
        Iterator<Map.Entry<Attribute, HashMap<NamespacedKey, Long>>> attrIterator = tempModifiers.get(player.getUniqueId()).entrySet().iterator();
        while (attrIterator.hasNext()) {
            Map.Entry<Attribute, HashMap<NamespacedKey, Long>> tempModifiers_attr_hashmap = attrIterator.next();
            AttributeInstance attributeInstance = player.getAttribute(tempModifiers_attr_hashmap.getKey());
            if (attributeInstance == null) {
                return;
            }
            for (AttributeModifier attributeModifier : attributeInstance.getModifiers()) {
                NamespacedKey namespacedkey = attributeModifier.getKey();
                if (tempModifiers_attr_hashmap.getValue().containsKey(namespacedkey) && !scheduledKeyRemovals.contains(namespacedkey)) {
                    scheduledKeyRemovals.add(namespacedkey);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // attempt to remove modifier
                            if (player.isOnline()) {
                                attributeInstance.removeModifier(attributeModifier.getKey());
                                tempModifiers_attr_hashmap.getValue().remove(attributeModifier.getKey());

                                cleanRecords(player, tempModifiers_attr_hashmap);
                            }
                            scheduledKeyRemovals.remove(namespacedkey);
                        }
                    }.runTaskLater(SCore.plugin,Math.max(0, (tempModifiers_attr_hashmap.getValue().get(namespacedkey) - System.currentTimeMillis()) / 50));
                }



            }
            cleanRecords(player, tempModifiers_attr_hashmap);
        }
    }

    /**
     * Attempts to clean hashmap records if the hash value isn't in use anymore.
     * @param player
     * @param tempModifiers_attr_hashmap
     */
    private static void cleanRecords(Player player, Map.Entry<Attribute, HashMap<NamespacedKey, Long>> tempModifiers_attr_hashmap) {
        if (!tempModifiers.containsKey(player.getUniqueId())) return;
        // remove empty classes
        if (tempModifiers.get(player.getUniqueId()).get(tempModifiers_attr_hashmap.getKey()).isEmpty()) {
            tempModifiers.get(player.getUniqueId()).remove(tempModifiers_attr_hashmap.getKey());
        }
        // remove the value when it's not needed anymore.
        if (tempModifiers.get(player.getUniqueId()).isEmpty()) {
            tempModifiers.remove(player.getUniqueId());
        }
    }

}
