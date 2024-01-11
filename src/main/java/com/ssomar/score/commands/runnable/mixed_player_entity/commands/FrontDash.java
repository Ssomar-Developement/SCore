package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.nofalldamage.NoFallDamageManager;
import com.ssomar.score.utils.Couple;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FrontDash extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {

        if(!(receiver instanceof LivingEntity)) return;
        LivingEntity livingReceiver = (LivingEntity) receiver;

        double amount = Double.parseDouble(args.get(0));
        double customY = 0;
        if (args.size() > 1) customY = Double.parseDouble(args.get(1));

        boolean fallDamage = false;
        if (args.size() >= 3) {
            fallDamage = Boolean.parseBoolean(args.get(2));
        }

        Location pLoc = receiver.getLocation();
        if (SCore.is1v11Less() || !livingReceiver.isGliding()) pLoc.setPitch(0);
        Vector v = pLoc.getDirection();
        v.multiply(amount);
        if (customY != 0) {
            Vector vec = new Vector();
            vec.setY(customY);
            v.add(vec);
        }
        receiver.setVelocity(v);

        UUID uuid = UUID.randomUUID();

        if (!fallDamage) {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    NoFallDamageManager.getInstance().removeNoFallDamage(receiver, uuid);
                }
            };
            ScheduledTask scheduledTask = SCore.schedulerHook.runTask(runnable, 300);
            NoFallDamageManager.getInstance().addNoFallDamage(receiver, new Couple<>(uuid, scheduledTask));
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if (args.size() > 1) {
            ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        if (args.size() >= 3) {
            String value = args.get(2);
            ArgumentChecker ac3 = checkBoolean(value, isFinalVerification, getTemplate());
            if (!ac3.isValid()) return Optional.of(ac.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FRONTDASH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FRONTDASH {number} [custom y] [fallDamage]";
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
