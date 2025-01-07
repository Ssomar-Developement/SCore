package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/* BURN {timeinsecs} */
public class DamageResistance extends MixedCommand {

    private static final Boolean DEBUG = false;
    private static DamageResistance instance;
    @Getter
    private final Map<UUID, List<Double>> activeResistances;

    public DamageResistance() {
        activeResistances = new HashMap<>();
    }

    public static DamageResistance getInstance() {
        if (instance == null) instance = new DamageResistance();
        return instance;
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        double reduction = Double.valueOf(args.get(0));
        int time = Double.valueOf(args.get(1)).intValue();

        UUID uuid = receiver.getUniqueId();

        //SsomarDev.testMsg("ADD receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
        if (activeResistances.containsKey(uuid)) {
            activeResistances.get(uuid).add(reduction);
        } else activeResistances.put(uuid, new ArrayList<>(Collections.singletonList(reduction)));

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                //SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
                if (activeResistances.containsKey(uuid)) {
                    if (activeResistances.get(uuid).size() > 1) {
                        activeResistances.get(uuid).remove(reduction);
                    } else activeResistances.remove(uuid);
                }
            }
        };
        SCore.schedulerHook.runTask(runnable3, time);
    }

    public double getNewDamage(UUID uuid, double damage) {
        if (DamageResistance.getInstance().getActiveResistances().containsKey(uuid)) {
            if (DEBUG) SsomarDev.testMsg("DamageResistanceEvent base: " + damage, DEBUG);
            double resistance = 0;
            for (double d : DamageResistance.getInstance().getActiveResistances().get(uuid)) {
                resistance += d;
            }

            double averagePercent = resistance / 100;
            damage = damage + (damage * averagePercent);
            if (DEBUG) SsomarDev.testMsg("DamageResistanceEvent modified " + damage, DEBUG);
        }
        return damage;
    }

    public Optional<String> onRequestPlaceholder(@NotNull OfflinePlayer player, String params) {
        if (params.startsWith("cmd-damage-resistance")) {
            return Optional.of(String.valueOf(getNewDamage(player.getUniqueId(), 1)));
        }
        return Optional.empty();
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
