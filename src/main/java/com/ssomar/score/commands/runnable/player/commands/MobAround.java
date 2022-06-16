package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.sobject.sactivator.features.detailedentities.DetailedEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.entity.EntityRunCommandsBuilder;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.jetbrains.annotations.Nullable;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(receiver.getLocation(), receiver, false, args, aInfo);
    }

    public static void mobAroundExecution(Location location, @Nullable Entity receiver, boolean forceMute, List<String> args, ActionInfo aInfo) {
        List<DetailedEntity> whiteList = new ArrayList<>();
        List<DetailedEntity> blackList = new ArrayList<>();

        List<String> verifyArgs = new ArrayList<>();
        boolean concatNext = false;
        String toConcat = "";
        for (String s : args) {
            if(concatNext){
                if(!s.contains("\"")){
                    verifyArgs.add(toConcat);
                    verifyArgs.add(s);
                    toConcat = "";
                    concatNext = false;
                }
                else toConcat = toConcat +" "+ s.replaceAll("\"", "");
            }
            else {
                int count = 0;
                for (char c : s.toCharArray()) {
                    if (c == '"') {
                        count++;
                    }
                }
                if (count % 2 == 0) {
                    verifyArgs.add(s);
                } else {
                    concatNext = true;
                    toConcat = toConcat + s.replaceAll("\"", "");
                }
            }
        }
        if(!toConcat.isEmpty()) verifyArgs.add(toConcat);
        args.clear();
        args.addAll(verifyArgs);
        /* for(String s : args) {
            SsomarDev.testMsg(">>>> " + s);
        }*/

        int argToRemove = -1;
        int cpt = 0;
        for (String s : args) {
            String[] split;
            try {
                if (s.contains("BLACKLIST(")) {
                    argToRemove = cpt;
                    split = s.split("BLACKLIST\\(");
                    String blackListString = split[1].split("\\)")[0];
                    split = blackListString.split(",");
                    for (String s1 : split) {
                        try {
                            //SsomarDev.testMsg(">>>>> "+s1);
                            blackList.add(new DetailedEntity(s1));
                        } catch (Exception e) {
                        }
                    }
                } else if (s.contains("WHITELIST(")) {
                    argToRemove = cpt;
                    split = s.split("WHITELIST\\(");
                    String whiteListString = split[1].split("\\)")[0];
                    split = whiteListString.split(",");
                    for (String s1 : split) {
                        try {
                            whiteList.add(new DetailedEntity(s1));
                        } catch (IllegalArgumentException e) {
                        }
                    }
                }
            } catch (Exception e) {
            }
            cpt++;
        }
        if (argToRemove != -1) args.remove(argToRemove);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    double distance = Double.parseDouble(args.get(0));
                    int cpt = 0;

                    int startForCommand = 1;
                    boolean mute = false;
                    if (!forceMute) {
                        if (args.get(1).equalsIgnoreCase("true")) {
                            startForCommand = 2;
                            mute = true;
                        } else if (args.get(1).equalsIgnoreCase("false")) {
                            startForCommand = 2;
                        }
                    }

                    for (Entity e : location.getWorld().getNearbyEntities(location, distance, distance, distance)) {
                        if (e instanceof LivingEntity && !(e instanceof Player)) {

                            if (e.hasMetadata("NPC") || e.equals(receiver)) continue;

                            if (whiteList.size() > 0) {
                                boolean notValid = true;
                                for (DetailedEntity de : whiteList) {
                                    if (de.isValidEntity(e)) {
                                        notValid = false;
                                        break;
                                    }
                                }
                                if (notValid) continue;
                            }

                            if (blackList.size() > 0) {
                                boolean notValid = false;
                                for (DetailedEntity de : blackList) {
                                    if (de.isValidEntity(e)) {
                                        notValid = true;
                                        break;
                                    }
                                }
                                if (notValid) continue;
                            }

                            StringPlaceholder sp = new StringPlaceholder();
                            sp.setAroundTargetEntityPlcHldr(e.getUniqueId());

                            ActionInfo aInfo2 = aInfo.clone();
                            aInfo2.setEntityUUID(e.getUniqueId());

                            /* regroup the last args that correspond to the commands */
                            StringBuilder prepareCommands = new StringBuilder();
                            for (String s : args.subList(startForCommand, args.size())) {
                                prepareCommands.append(s);
                                prepareCommands.append(" ");
                            }
                            prepareCommands.deleteCharAt(prepareCommands.length() - 1);

                            String buildCommands = prepareCommands.toString();
                            String[] tab;
                            if (buildCommands.contains("<+>")) tab = buildCommands.split("\\<\\+\\>");
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

                                s = sp.replacePlaceholder(s);
                                commands.add(s);
                            }
                            EntityRunCommandsBuilder builder = new EntityRunCommandsBuilder(commands, aInfo2);
                            CommandsExecutor.runCommands(builder);

                            cpt++;
                        }
                    }
                    if (cpt == 0 && !mute && receiver != null && receiver instanceof Player)
                        sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_ENTITY_HIT));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.runTask(SCore.plugin);
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        String around = "MOB_AROUND {distance} {muteMsgIfNoEntity true or false} {Your commands here}";
        if (args.size() < 2) error = notEnoughArgs + around;
        else if (args.size() > 2) {
            try {
                Double.valueOf(args.get(0));

            } catch (NumberFormatException e) {
                error = invalidDistance + args.get(0) + " for command: " + around;
            }
        }

        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_AROUND {distance} [muteMsgIfNoEntity true or false] {Your commands here}";
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
