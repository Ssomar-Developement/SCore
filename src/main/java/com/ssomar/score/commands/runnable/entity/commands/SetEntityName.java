package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetEntityName extends EntityCommand {

    public SetEntityName() {
        CommandSetting message = new CommandSetting("name", 0, String.class, "&6Hello world");
        message.setAcceptUnderScoreForLongText(true);
        List<CommandSetting> settings = getSettings();
        settings.add(message);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        String baseName = (String) sCommandToExec.getSettingValue("name");
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder name = new StringBuilder(baseName);
        name.append(" ");
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            name.append(s).append(" ");
        }
        name = new StringBuilder(name.substring(0, name.length() - 1));

        if (!entity.isDead()) {
            if (StringConverter.decoloredString(name.toString()).trim().isEmpty()) {
                entity.setCustomNameVisible(false);
                entity.setCustomName(null);
                return;
            }

            entity.setCustomNameVisible(true);
            entity.setCustomName(StringConverter.coloredString(name.toString()));
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ENTITY_NAME");
        names.add("SETNAME");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ENTITY_NAME name:&6Final &cBoss";
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
