package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Bossbar extends PlayerCommand {

    private static Bossbar instance;
    Map<Player, List<ScheduledTask>> tasks;
    Map<Player, List<org.bukkit.boss.BossBar>> bossbars;

    public Bossbar() {
        instance = this;
        tasks = new HashMap<>();
        bossbars = new HashMap<>();
        CommandSetting time = new CommandSetting("time", 0, Integer.class, 200);
        CommandSetting color = new CommandSetting("color", 1, BarColor.class, BarColor.BLUE);
        CommandSetting text = new CommandSetting("text", 2, String.class, "Hello_world");
        CommandSetting count = new CommandSetting("count", 3, Integer.class, 0);
        CommandSetting countTicks = new CommandSetting("countTicks", 4, Boolean.class, false);
        CommandSetting countOrder = new CommandSetting("countOrder", 5, String.class, "descending");

        text.setAcceptUnderScoreForLongText(true);
        List<CommandSetting> settings = getSettings();
        settings.add(time);
        settings.add(color);
        settings.add(text);
        settings.add(count);
        settings.add(countTicks);
        settings.add(countOrder);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        BarColor color = (BarColor) sCommandToExec.getSettingValue("color");
        String text = (String) sCommandToExec.getSettingValue("text");
        Integer time = (Integer) sCommandToExec.getSettingValue("time");
        Integer count = (Integer) sCommandToExec.getSettingValue("count");
        Boolean countTicks = (Boolean) sCommandToExec.getSettingValue("countTicks");
        String countOrder = ((String) sCommandToExec.getSettingValue("countOrder")).toLowerCase();
        boolean isAscending = countOrder.equalsIgnoreCase("ascending");

        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(text);
        message.append(" ");
        for (String s : args) {
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));

        if(!message.toString().isEmpty()) {
            BossBar bossBar = Bukkit.createBossBar(StringConverter.coloredString(message.toString()), color, BarStyle.SOLID);
            bossBar.addPlayer(receiver);
            bossbars.computeIfAbsent(receiver, k -> new ArrayList<>()).add(bossBar);

            final String finalMessage = message.toString();

            if (count > 0) {
                AtomicReference<ScheduledTask> task = new AtomicReference<>();
                Runnable runnable = new Runnable() {
                    int counter = isAscending ? 0 : count;

                    @Override
                    public void run() {
                        if (isAscending) {
                            if (counter >= count) {
                                bossBar.removeAll();
                                task.get().cancel();
                                tasks.computeIfAbsent(receiver, k -> new ArrayList<>()).remove(task.get());
                                bossbars.computeIfAbsent(receiver, k -> new ArrayList<>()).remove(bossBar);
                                return;
                            }
                            counter++;
                        } else {
                            if (counter <= 0) {
                                bossBar.removeAll();
                                task.get().cancel();
                                tasks.computeIfAbsent(receiver, k -> new ArrayList<>()).remove(task.get());
                                bossbars.computeIfAbsent(receiver, k -> new ArrayList<>()).remove(bossBar);
                                return;
                            }
                            counter--;
                        }

                        String countText = String.valueOf(counter);
                        if (!countTicks) {
                            countText += "s";
                        }

                        bossBar.setTitle(StringConverter.coloredString(finalMessage + " " + countText));
                    }
                };
                task.set(SCore.schedulerHook.runRepeatingTask(runnable, 1, countTicks ? 1 : 20));
                tasks.computeIfAbsent(receiver, k -> new ArrayList<>()).add(task.get());
            } else {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        bossBar.removeAll();
                    }
                };
                SCore.schedulerHook.runTask(runnable, time);
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BOSSBAR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BOSSBAR time:200 color:BLUE text:Hello_world count:0 countTicks:false countOrder:descending";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    public static Bossbar getInstance() {
        if (instance == null) {
            instance = new Bossbar();
        }
        return instance;
    }

    public void clearTasks(Player p) {
        if (tasks.containsKey(p)) {
            for (ScheduledTask task : tasks.get(p)) {
                task.cancel();
            }
            tasks.remove(p);
        }
        if (bossbars.containsKey(p)) {
            for (org.bukkit.boss.BossBar bossBar : bossbars.get(p)) {
                bossBar.removeAll();
            }
            bossbars.remove(p);
        }
    }
}