package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Around extends PlayerCommand{

    private final static Boolean DEBUG = false;

    public static void aroundExecution(Entity receiver, List<String> args, ActionInfo aInfo, boolean displayMsgIfNoTargetHit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                double distance = Double.valueOf(args.get(0));

                List<Player> targets = new ArrayList<>();
                for (Entity e : receiver.getNearbyEntities(distance, distance, distance)) {
                    if (e instanceof Player) {
                        Player target = (Player) e;
                        if (target.hasMetadata("NPC") || target.equals(receiver)) continue;
                        targets.add(target);
                    }
                }

                boolean hit = CommmandThatRunsCommand.runPlayerCommands(targets, args.subList(2, args.size()), aInfo);

                if (!hit && displayMsgIfNoTargetHit && receiver instanceof Player)
                    sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_PLAYER_HIT));
            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
    }





    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        aroundExecution(receiver, args, aInfo, Boolean.valueOf(args.get(1)));
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkBoolean(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AROUND {distance} {DisplayMsgIfNoPlayer true or false} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }
}
