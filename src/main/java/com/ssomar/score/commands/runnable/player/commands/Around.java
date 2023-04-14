package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.ComparatorFeature;
import com.ssomar.score.features.types.PlaceholderConditionTypeFeature;
import com.ssomar.score.utils.emums.Comparator;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.emums.PlaceholdersCdtType;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Around extends PlayerCommand {

    private final static Boolean DEBUG = false;

    public static void aroundExecution(Entity receiver, List<String> args, ActionInfo aInfo, boolean displayMsgIfNoTargetHit) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                double distance = Double.valueOf(args.get(0));
                int cpt = 0;

                for (Entity e : receiver.getNearbyEntities(distance, distance, distance)) {
                    if (e instanceof Player) {
                        Player target = (Player) e;
                        if (target.hasMetadata("NPC") || target.equals(receiver)) continue;

                        ActionInfo aInfo2 = aInfo.clone();
                        aInfo2.setReceiverUUID(target.getUniqueId());

                        StringPlaceholder sp = new StringPlaceholder();
                        sp.setAroundTargetPlayerPlcHldr(target.getUniqueId());

                        /* regroup the last args that correspond to the commands */
                        StringBuilder prepareCommands = new StringBuilder();
                        for (String s : args.subList(2, args.size())) {
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
                                //SsomarDev.testMsg("receive : s = " + s, true);
                                s = sp.replacePlaceholder(s);
                                s = s.replaceAll("%::", "%");
                                s = s.replaceAll("::%", "%");
                                //SsomarDev.testMsg("receive 2 : s = " + s, true);
                                List<PlaceholderConditionFeature> conditions = extractConditions(s);
                                s = getFirstCommandWithoutConditions(s);
                                //SsomarDev.testMsg("s: " + s+" conditions size: "+conditions.size(), true);
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
                        cpt++;
                    }
                }
                if (cpt == 0 && displayMsgIfNoTargetHit && receiver instanceof Player)
                    sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_PLAYER_HIT));

            }
        };
        runnable.runTask(SCore.plugin);
    }

    public static List<PlaceholderConditionFeature> extractConditions(String s) {
        List<PlaceholderConditionFeature> conditions = new ArrayList<>();
        if (s.contains("CONDITIONS(")) {
            String[] tab = s.split("CONDITIONS\\(");
            int indexOfClose = tab[1].indexOf(")");
            if (indexOfClose != -1) {
                String conditionsStr = tab[1].substring(0, indexOfClose);
                String[] tab3 = conditionsStr.split("&");
                for (String condition : tab3) {
                    for (Comparator comparator : Comparator.values()) {
                        if (condition.contains(comparator.getSymbol())) {
                            String[] conditionSplit = condition.split(comparator.getSymbol());
                            String placeholder = conditionSplit[0];
                            String value = conditionSplit[1];
                            PlaceholderConditionFeature conditionFeature = PlaceholderConditionFeature.buildNull();
                            if ((comparator.equals(Comparator.EQUALS) || comparator.equals(Comparator.DIFFERENT)) && !NTools.isNumber(value)) {
                                conditionFeature.setType(PlaceholderConditionTypeFeature.buildNull(PlaceholdersCdtType.PLAYER_STRING));
                            } else
                                conditionFeature.setType(PlaceholderConditionTypeFeature.buildNull(PlaceholdersCdtType.PLAYER_NUMBER));

                            conditionFeature.setPart1(ColoredStringFeature.buildNull(placeholder));
                            conditionFeature.setPart2(ColoredStringFeature.buildNull(value));
                            conditionFeature.setComparator(ComparatorFeature.buildNull(comparator));
                            conditions.add(conditionFeature);

                            break;
                        }
                    }
                }
            }
        }
        return conditions;
    }

    public static String getFirstCommandWithoutConditions(String s) {
        if (s.contains("CONDITIONS(")) {
            String[] tab = s.split("CONDITIONS\\(");
            int indexOfClose = tab[1].indexOf(")");
            if (indexOfClose != -1) {
                String without = tab[1].substring(indexOfClose + 1);
                return without;
            }
        }
        return s;
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
