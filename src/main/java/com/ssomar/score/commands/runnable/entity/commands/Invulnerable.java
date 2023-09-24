package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*  */
public class Invulnerable extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {

        if (entity.isDead()) return;
        boolean invulnerable;

        if (args.size() > 0) {
            invulnerable = Boolean.parseBoolean(args.get(0));
        } else {
            invulnerable = !entity.isInvulnerable();
        }

        p.sendMessage(Boolean.toString(invulnerable));
        entity.setInvulnerable(invulnerable);

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if(args.size() > 0) {
            ArgumentChecker ac1 = checkBoolean(args.get(0), isFinalVerification, getTemplate());
            if (!ac1.isValid()) return Optional.of(ac1.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("INVULNERABILITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "INVULNERABILITY {true false}";
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
