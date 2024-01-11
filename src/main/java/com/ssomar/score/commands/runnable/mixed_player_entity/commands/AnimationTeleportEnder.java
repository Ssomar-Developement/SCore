package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimationTeleportEnder extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        receiver.playEffect(EntityEffect.TELEPORT_ENDER);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("~ANIMATION_TELEPORT_ENDER");
        names.add("TELEPORT_ENDER_ANIMATION");
        return names;
    }

    @Override
    public String getTemplate() {
        return "TELEPORT_ENDER_ANIMATION";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.GOLD;
    }


}
