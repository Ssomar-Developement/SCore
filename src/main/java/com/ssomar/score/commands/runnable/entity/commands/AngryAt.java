package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* ANGRYAT {ENTITYUUID} */
public class AngryAt extends EntityCommand {

    public AngryAt() {
        CommandSetting entityUUID = new CommandSetting("entityUUID", 0, UUID.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(entityUUID);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {

        UUID uuid = (UUID) sCommandToExec.getSettingValue("entityUUID");

        if(entity instanceof Mob && !entity.isDead()){
            Mob mob = (Mob) entity;
            try {
                mob.setTarget((LivingEntity) Bukkit.getEntity(uuid));
            } catch (Exception e) {
                mob.setTarget(null);
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ANGRY_AT");
        names.add("ANGRYAT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ANGRY_AT entityUUID:THE_UUID";
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
