package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.EntityBuilder;
import com.ssomar.score.utils.backward_compatibility.AttributeUtils;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityOnCursor extends PlayerCommand {

    public SpawnEntityOnCursor() {
        CommandSetting entity = new CommandSetting("entity", 0, EntityBuilder.class, "ZOMBIE");
        CommandSetting amount = new CommandSetting("amount", 1, Integer.class, 1);
        CommandSetting maxRange = new CommandSetting("maxRange", 2, Integer.class, 200);
        CommandSetting customName = new CommandSetting("customName", -1, String.class, null);
        customName.setAcceptUnderScoreForLongText(true);
        CommandSetting health = new CommandSetting("health", -1, Double.class, -1.0);
        CommandSetting baby = new CommandSetting("baby", -1, Boolean.class, false);
        CommandSetting silent = new CommandSetting("silent", -1, Boolean.class, false);
        CommandSetting glowing = new CommandSetting("glowing", -1, Boolean.class, false);
        CommandSetting noGravity = new CommandSetting("noGravity", -1, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(entity);
        settings.add(amount);
        settings.add(maxRange);
        settings.add(customName);
        settings.add(health);
        settings.add(baby);
        settings.add(silent);
        settings.add(glowing);
        settings.add(noGravity);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int range = (int) sCommandToExec.getSettingValue("maxRange");
        int amount = (int) sCommandToExec.getSettingValue("amount");

        EntityBuilder entityBuilder = (EntityBuilder) sCommandToExec.getSettingValue("entity");

        String customName = (String) sCommandToExec.getSettingValue("customName");
        double health = (double) sCommandToExec.getSettingValue("health");
        boolean baby = (boolean) sCommandToExec.getSettingValue("baby");
        boolean silent = (boolean) sCommandToExec.getSettingValue("silent");
        boolean glowing = (boolean) sCommandToExec.getSettingValue("glowing");
        boolean noGravity = (boolean) sCommandToExec.getSettingValue("noGravity");

        Block block = receiver.getTargetBlock(null, range);

        if (!block.getType().equals(Material.AIR)) {

            Location loc = block.getLocation();
            loc.add(0, 1, 0);

            // Can be null
            EntityType toSpawnEntityType = entityBuilder.getEntityType();
            EntityType lightning = SCore.is1v20v5Plus() ? EntityType.LIGHTNING_BOLT : EntityType.valueOf("LIGHTNING");

            for (int i = 0; i < amount; i++) {

                if (toSpawnEntityType != null && toSpawnEntityType.equals(lightning)) {
                    receiver.getWorld().strikeLightning(loc);
                    continue;
                }

                Entity e = entityBuilder.buildEntity(loc);
                if (toSpawnEntityType != null && toSpawnEntityType.equals(EntityType.FIREBALL))
                    e.setVelocity(new Vector(0, 0, 0));

                applyEntityModifications(e, customName, health, baby, silent, glowing, noGravity);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void applyEntityModifications(Entity e, String customName, double health, boolean baby, boolean silent, boolean glowing, boolean noGravity) {
        if (e == null || e.isDead()) return;

        if (customName != null) {
            e.setCustomNameVisible(true);
            e.setCustomName(StringConverter.coloredString(customName));
        }

        if (health > 0 && e instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) e;
            Attribute maxHealthAttr = SCore.is1v21v2Plus() ? Attribute.MAX_HEALTH : AttributeUtils.getAttribute("GENERIC_MAX_HEALTH");
            if (maxHealthAttr != null) {
                AttributeInstance attrInstance = le.getAttribute(maxHealthAttr);
                if (attrInstance != null) {
                    attrInstance.setBaseValue(health);
                    le.setHealth(health);
                }
            }
        }

        if (baby && e instanceof Ageable) {
            ((Ageable) e).setBaby();
        }

        if (silent) {
            e.setSilent(true);
        }

        if (glowing) {
            e.setGlowing(true);
        }

        if (noGravity) {
            e.setGravity(false);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SPAWN_ENTITY_ON_CURSOR");
        names.add("SPAWNENTITYONCURSOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SPAWN_ENTITY_ON_CURSOR entity:ZOMBIE amount:1 maxRange:200 customName:&cBoss health:100 baby:false silent:false glowing:false noGravity:false";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

}
