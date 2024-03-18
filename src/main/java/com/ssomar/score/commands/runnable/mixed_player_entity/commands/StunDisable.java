package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.commands.runnable.player.events.StunEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StunDisable extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {

        if(!(receiver instanceof LivingEntity)) return;
        LivingEntity livingReceiver = (LivingEntity) receiver;

        if(receiver instanceof Player){
            livingReceiver.setGliding(false);
        }
        else {
            livingReceiver.setAI(true);
        }

        StunEvent.stunPlayers.remove(receiver.getUniqueId());
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STUN_DISABLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STUN_DISABLE";
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