package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/* BURN {timeinsecs} */
public class DamageResistance extends EntityCommand {

    private static final Boolean DEBUG = false;

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        double reduction = Double.valueOf(args.get(0));
        int time = Double.valueOf(args.get(1)).intValue();

        //SsomarDev.testMsg("ADD receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
        if (com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().containsKey(receiver.getUniqueId())) {
            com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().get(receiver.getUniqueId()).add(reduction);
        } else com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().put(receiver.getUniqueId(), new ArrayList<>(Collections.singletonList(reduction)));

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().containsKey(receiver.getUniqueId())) {
                    if (com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().get(receiver.getUniqueId()).size() > 1) {
                        com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().get(receiver.getUniqueId()).remove(reduction);
                    } else com.ssomar.score.commands.runnable.player.commands.DamageResistance.getInstance().getActiveResistances().remove(receiver.getUniqueId());
                }
            }
        };
        runnable3.runTaskLater(SCore.plugin, time);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DAMAGE_RESISTANCE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DAMAGE_RESISTANCE {modification in percentage example 100} {timeinticks}";
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
