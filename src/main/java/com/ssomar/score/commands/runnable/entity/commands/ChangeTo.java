package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.EntityBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ChangeTo extends EntityCommand {

    public ChangeTo() {
        CommandSetting value = new CommandSetting("entity", 0, EntityBuilder.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(value);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        EntityBuilder entityBuilder = (EntityBuilder) sCommandToExec.getSettingValue("entity");
        ActionInfo aInfo = sCommandToExec.getActionInfo();
        /* EXCEPTION */
        Location loc = entity.getLocation();
        Vector velocity = entity.getVelocity();
        entity.remove();
        Entity newEntity = entityBuilder.buildEntity(loc);
        newEntity.setVelocity(velocity);
        aInfo.setEntityUUID(newEntity.getUniqueId());
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CHANGE_TO");
        names.add("CHANGETO");
        names.add("CHANGETOMYTHICMOB");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CHANGE_TO entity:PIG";
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
