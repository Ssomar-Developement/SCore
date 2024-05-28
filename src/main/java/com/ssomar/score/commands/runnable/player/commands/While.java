package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.features.custom.conditions.placeholders.placeholder.PlaceholderConditionFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.features.types.ComparatorFeature;
import com.ssomar.score.features.types.PlaceholderConditionTypeFeature;
import com.ssomar.score.utils.emums.Comparator;
import com.ssomar.score.utils.emums.PlaceholdersCdtType;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class While extends PlayerCommand {

    private static final boolean DEBUG = false;

    private static While instance;

    private final Map<UUID, List<BukkitRunnable>> whileTasks;

    public While() {
        whileTasks = new HashMap<>();
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        SsomarDev.testMsg("WHILE STARTED", DEBUG);

        PlaceholderConditionFeature conditionFeature = PlaceholderConditionFeature.buildNull();
        conditionFeature.setType(PlaceholderConditionTypeFeature.buildNull(PlaceholdersCdtType.PLAYER_PLAYER));
        String condition = args.get(0);
        int delay = (int) Double.parseDouble(aInfo.getSp().replacePlaceholder(args.get(1)));

        // "%"+c.getSymbol() because the placeholder can also contains comparator so to be sure the comparator is outside the placeholder we need to be sure there is a % before
        Comparator comparator = null;
        for(Comparator c : Comparator.values()){
            if(condition.contains("%"+c.getSymbol())){
                conditionFeature.setComparator(ComparatorFeature.buildNull(c));
                comparator = c;
                break;
            }
        }
        if(comparator == null) return;
        String[] parts = condition.split("%"+comparator.getSymbol());
        conditionFeature.setPart1(ColoredStringFeature.buildNull(parts[0]+"%"));
        conditionFeature.setPart2(ColoredStringFeature.buildNull(parts[1]));

        StringPlaceholder sp = new StringPlaceholder();
        sp.setPlayerPlcHldr(receiver.getUniqueId());

        String cmdsDef = "";
        for (int i = 2; i < args.size(); i++) {
            String cmd = args.get(i);
            cmdsDef += cmd + " ";
        }
        cmdsDef = cmdsDef.trim();
        SsomarDev.testMsg("WHILE CMD DEF: " + cmdsDef, DEBUG);
        String[] cmdsArray = cmdsDef.split("<\\+>");
        List<String> cmds = new ArrayList<>();
        for (String cmd : cmdsArray) {
            cmds.add(cmd);
            SsomarDev.testMsg("WHILE CMD: " + cmd, DEBUG);
        }

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                sp.reloadAllPlaceholders();
                if(conditionFeature.verify(receiver, null, sp) && receiver.isOnline()) {

                    PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(cmds, aInfo);
                    CommandsExecutor.runCommands(builder);
                }
                else{
                    SsomarDev.testMsg("WHILE STOPPED", DEBUG);
                    this.cancel();
                }
            }
        };
        runnable3.runTaskTimerAsynchronously(SCore.plugin, 0, delay);
        // add the task to the list of tasks
        List<BukkitRunnable> tasks = whileTasks.get(receiver.getUniqueId());
        if(tasks == null) tasks = new ArrayList<>();
        tasks.add(runnable3);
        whileTasks.put(receiver.getUniqueId(), tasks);

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(1), false, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("WHILE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "WHILE {condition_without_spaces} {delay_in_ticks} {command1} <+> {command2} <+> ...";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    public void removeWhile(Player p) {
        List<BukkitRunnable> tasks = whileTasks.get(p.getUniqueId());
        if(tasks != null) {
            for (BukkitRunnable task : tasks) {
                task.cancel();
            }
        }
        whileTasks.remove(p.getUniqueId());
    }

    public static While getInstance(){
        if(instance == null) instance = new While();
        return instance;
    }

}
