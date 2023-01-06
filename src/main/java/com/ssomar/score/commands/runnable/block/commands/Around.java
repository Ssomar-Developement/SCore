package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeature;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.commands.runnable.player.commands.Around.extractConditions;
import static com.ssomar.score.commands.runnable.player.commands.Around.getFirstCommandWithoutConditions;

/* AROUND {distance} {true or false} {Your commands here} */
public class Around extends BlockCommand {

    private final static Boolean DEBUG = false;

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String around = "AROUND {distance} [affectThePlayerThatActivesTheActivator true or false] {Your commands here}";

        if (args.size() < 2) error = notEnoughArgs + around;
        else if (args.size() > 2) {
            try {
                Double.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidDistance + args.get(0) + " for command: " + around;
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AROUND {distance} [affectThePlayerThatActivesTheActivator true or false] {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    double distance = Double.parseDouble(args.get(0));
                    boolean affectThePlayerThatActivesTheActivator = true;
                    if (args.get(1).equalsIgnoreCase("false")) affectThePlayerThatActivesTheActivator = false;

                    for (Entity e : block.getWorld().getNearbyEntities(block.getLocation().add(0.5, 0.5, 0.5), distance, distance, distance)) {
                        if (e instanceof Player) {
                            Player target = (Player) e;
                            if (target.hasMetadata("NPC") || (!affectThePlayerThatActivesTheActivator && (p != null && p.equals(target))))
                                continue;

                            StringPlaceholder sp = new StringPlaceholder();
                            sp.setAroundTargetPlayerPlcHldr(target.getUniqueId());

                            ActionInfo aInfo2 = aInfo.clone();
                            aInfo2.setReceiverUUID(target.getUniqueId());

                            /* regroup the last args that correspond to the commands */
                            StringBuilder prepareCommands = new StringBuilder();
                            for (String s : args.subList(1, args.size())) {
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
                            boolean passToNextPlayer = false;
                            for (int m = 0; m < tab.length; m++) {
                                String s = tab[m];

                                if (m == 0) {
                                    //SsomarDev.testMsg("receive : s = " + s, DEBUG);
                                    s = sp.replacePlaceholder(s);
                                    s = s.replaceAll("%::", "%");
                                    s = s.replaceAll("::%", "%");
                                    List<PlaceholderConditionFeature> conditions = extractConditions(s);
                                    s = getFirstCommandWithoutConditions(s);
                                    //SsomarDev.testMsg("s: " + s, true);
                                    if (!conditions.isEmpty() && SCore.hasPlaceholderAPI) {
                                        for (PlaceholderConditionFeature condition : conditions) {
                                            //SsomarDev.testMsg("condition: " + condition, true);
                                            if (!condition.verify(target, null)) {
                                                //SsomarDev.testMsg("condition not verified", DEBUG);
                                                passToNextPlayer = true;
                                                break;
                                            }
                                        }
                                    }
                                }

                                while (s.startsWith(" ")) {
                                    s = s.substring(1);
                                }
                                while (s.endsWith(" ")) {
                                    s = s.substring(0, s.length() - 1);
                                }
                                if (s.startsWith("/")) s = s.substring(1);

                                commands.add(s);
                            }
                            if(passToNextPlayer) continue;

                            commands = sp.replacePlaceholders(commands);
                            PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(commands, aInfo2);
                            CommandsExecutor.runCommands(builder);

                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.runTask(SCore.plugin);


    }
}
