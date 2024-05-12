package com.ssomar.score.commands.runnable;

import com.ssomar.score.SCore;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.strings.StringConverter;
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
            //fM.setLogFilterPrior();
            //fM.showDebug();
            fM.incCurrentlyInRun();
        }

        // By default Bukkit.dispatchCommand() is not sync , so one tick in addition is not a problem, its more to remind me that console commands are not sync
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                try {
                    String newCommand = addWorldCompatibilityForExecute(command);
                    Bukkit.dispatchCommand(console, StringConverter.coloredString(newCommand));
                } catch (Exception e) {
                    e.printStackTrace();
                    SCore.plugin.getLogger().severe(SCore.NAME_COLOR_WITH_BRACKETS + " ERROR WHEN THE CONSOLE COMMAND IS RUN !");
                }

            }
        };
        SCore.schedulerHook.runTask(runnable, 1);


        BukkitRunnable runnable3 = new BukkitRunnable() {

            @Override
            public void run() {
                if (silenceOutput) fM.decrCurrentlyInRun();

            }
        };
        SCore.schedulerHook.runTask(runnable3, 2);
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
                //SsomarDev.testMsg("world: "+world, true);
                if (world != null) {
                    List<Entity> entities = world.getEntities();
                    if(entities.size() > 0) {
                        Entity entity = entities.get(0);
                        //SsomarDev.testMsg("entity: "+entity, true);
                        command = command.replaceAll("in <<" + worldName + ">>", "at " + entity.getUniqueId());
                    }
                    else return "";
                }
            }
        }
        return command;
    }
}
