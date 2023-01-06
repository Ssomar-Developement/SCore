package com.ssomar.score.commands.runnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.FilterManager;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Optional;


public class RunConsoleCommand {

    public static void runConsoleCommand(String command, boolean silenceOutput) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        FilterManager fM = FilterManager.getInstance();

        if (silenceOutput){
            fM.setLogFilterPrior();
            //fM.showDebug();
            fM.incCurrentlyInRun();
        }

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                try {
                    String newCommand = addWorldCompatibilityForExecute(command);
                    Bukkit.dispatchCommand(console, StringConverter.coloredString(newCommand));
                } catch (Exception e) {
                    e.printStackTrace();
                    SCore.plugin.getLogger().severe(SCore.NAME_2 + " ERROR WHEN THE CONSOLE COMMAND IS RUN !");
                }

            }
        };
        runnable.runTaskLater(SCore.plugin, 1);


        BukkitRunnable runnable3 = new BukkitRunnable() {

            @Override
            public void run() {
                if (silenceOutput) fM.decrCurrentlyInRun();

            }
        };
        runnable3.runTaskLater(SCore.plugin, 2);
    }

    public static String addWorldCompatibilityForExecute(String command) {
        if (command.contains("in <<")) {
            String[] spliter = command.split("in <<");
            if (spliter.length > 1) {
                String[] spliter2 = spliter[1].split(">>");
                String worldName = spliter2[0];
                Optional<World> worldOptional = AllWorldManager.getWorld(worldName);
                World world = null;
                if(worldOptional.isPresent()) world = worldOptional.get();
                if (world != null) {
                    List<Entity> entities = world.getEntities();
                    if(entities.size() > 0) {
                        Entity entity = entities.get(0);
                        command = command.replace("in <<" + worldName + ">>", "at " + entity.getUniqueId());
                    }
                }
            }
        }
        return command;
    }
}
