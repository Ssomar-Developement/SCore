package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETNAME {name} */
public class SetName extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        if (!entity.isDead()) {
            StringBuilder name = new StringBuilder();
            for (String s : args) {
                name.append(s).append(" ");
            }
            name = new StringBuilder(name.substring(0, name.length() - 1));

            if(StringConverter.decoloredString(name.toString()).trim().isEmpty()) {
                entity.setCustomNameVisible(false);
                entity.setCustomName(null);
                return;
            }

            entity.setCustomNameVisible(true);
            entity.setCustomName(StringConverter.coloredString(name.toString()));
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETNAME");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETNAME {name}";
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
