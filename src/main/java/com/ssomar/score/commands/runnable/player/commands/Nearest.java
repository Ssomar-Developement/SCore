package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Nearest extends PlayerCommand {

    public static void aroundExecution(Entity receiver, List<String> args, ActionInfo aInfo, boolean displayMsgIfNoTargetHit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                double distance = Double.valueOf(args.get(0));

                SsomarDev.testMsg("distance: " + distance, true);

                Player target = receiver.getWorld().getPlayers().stream()
                        .filter(p -> !p.equals(receiver))
                        .min(Comparator.comparingDouble((p) -> p.getLocation().distanceSquared(receiver.getLocation())))
                        .orElse(null);

                if (target == null || target.getLocation().distance(receiver.getLocation()) > distance) {
                    return;
                }
                SsomarDev.testMsg("target: " + target.getName(), true);

                if (target.hasMetadata("NPC") || target.equals(receiver)) return;

                ActionInfo aInfo2 = aInfo.clone();
                aInfo2.setReceiverUUID(target.getUniqueId());

                StringPlaceholder sp = new StringPlaceholder();
                sp.setAroundTargetPlayerPlcHldr(target.getUniqueId());

                /* regroup the last args that correspond to the commands */
                StringBuilder prepareCommands = new StringBuilder();
                boolean removeMaxDistance = true;
                for (String s : args) {
                    if (removeMaxDistance) {
                        removeMaxDistance = false;
                        continue;
                    }
                    prepareCommands.append(s);
                    prepareCommands.append(" ");
                }
                prepareCommands.deleteCharAt(prepareCommands.length() - 1);

                String buildCommands = prepareCommands.toString();
                String[] tab;
                if (buildCommands.contains("<+>")) tab = buildCommands.split("<\\+>");
                else {
                    tab = new String[1];
                    tab[0] = buildCommands;
                }
                List<String> commands = new ArrayList<>();
                for (int m = 0; m < tab.length; m++) {
                    String s = tab[m];
                    while (s.startsWith(" ")) {
                        s = s.substring(1);
                    }
                    while (s.endsWith(" ")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    if (s.startsWith("/")) s = s.substring(1);

                    commands.add(s);
                }
                SsomarDev.testMsg("commands: " + commands, true);
                commands = sp.replacePlaceholders(commands);
                PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(commands, aInfo2);
                CommandsExecutor.runCommands(builder);

            }
        };
        runnable.runTask(SCore.plugin);
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        aroundExecution(receiver, args, aInfo, Boolean.valueOf(args.get(1)));
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("NEAREST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "NEAREST {max distance} {Your commands here}";
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
