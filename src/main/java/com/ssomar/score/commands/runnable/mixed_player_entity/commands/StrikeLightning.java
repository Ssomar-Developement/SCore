package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StrikeLightning extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        receiver.getWorld().strikeLightningEffect(receiver.getLocation());

        if (receiver instanceof Creeper) {
            ((Creeper) receiver).setPowered(true);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STRIKELIGHTNING");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STRIKELIGHTNING";
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
