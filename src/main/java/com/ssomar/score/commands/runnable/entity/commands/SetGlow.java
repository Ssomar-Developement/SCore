package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.features.custom.drop.glowdrop.GlowDropManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETGLOW */
public class SetGlow extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        if (!entity.isDead()) {
            ChatColor color = ChatColor.WHITE;
            if (args.size() >= 1) {
                color = ChatColor.valueOf(args.get(0).toUpperCase());
            }
            GlowDropManager.getInstance().addGlow(entity, color);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() >= 1) {
            ArgumentChecker ac2 = checkChatColor(args.get(0), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETGLOW");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETGLOW [color]";
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
