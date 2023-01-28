package com.ssomar.score.commands.runnable;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class RunCommand implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /* The command enter by the user */
    private String brutCommand;

    /* Placehodlers manager */
    private StringPlaceholder sp;

    /* In Ticks */
    private int delay;

    /* The time where the command must be executed */
    private long runTime;

    /* Extra infos is stored in */
    private ActionInfo aInfo;

    private UUID uuid;

    private BukkitTask task;

    public RunCommand(String brutCommand, int delay, ActionInfo aInfo) {
        this.brutCommand = brutCommand;
        this.aInfo = aInfo;
        this.sp = aInfo.getSp();
        this.delay = delay;
        this.runTime = -1;
        this.uuid = UUID.randomUUID();
        this.task = null;
        this.pickupInfo();
    }

    public RunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
        this.brutCommand = brutCommand;
        this.aInfo = aInfo;
        this.sp = aInfo.getSp();
        this.uuid = UUID.randomUUID();
        this.task = null;
        this.pickupInfo();

        long current = System.currentTimeMillis();

        long diff = runTime - current;

        if (diff == 0) delay = 0;
        else delay = (int) (diff / 50);

        this.runTime = -1;
    }

    public void run() {
        if (delay == 0) {
            runTime = 0;
            this.runGetManager();
        } else if(runTime != -1) {
            delay = (int) ((runTime - System.currentTimeMillis()) / 50L);
            if(delay < 0) delay = 0;
            this.runDelayedCommand();
        } else {
            runTime = System.currentTimeMillis() + delay * 50L;
            this.runDelayedCommand();
        }
    }

    public abstract void runGetManager();


    public void runCommand(CommandManager manager) {
        //SsomarDev.testMsg("Command run command: "+this.getBrutCommand());
        String finalCommand = this.getBrutCommand();
        finalCommand = this.getSp().replacePlaceholder(finalCommand);

        if (getBrutCommand().contains("ei giveslot")) {
            try {
                String playeName = finalCommand.split("ei giveslot ")[1].split(" ")[0];
                Player pgive = Bukkit.getServer().getPlayer(playeName);
                CommandsHandler.getInstance().addStopPickup(pgive, 20);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }


        Optional<SCommand> commandOpt = manager.getCommand(finalCommand);
        if (commandOpt.isPresent()) {
            SCommand command = commandOpt.get();
            //SsomarDev.testMsg("Command: valid: "+finalCommand, true);
            List<String> args = manager.getArgs(command, finalCommand);

            Optional<String> error = command.verify(args, true);
            if (!error.isPresent()) {
                this.runCommand(command, args);
            } else aInfo.getDebugers().sendDebug(error.get());
        } else {
            if (finalCommand.trim().isEmpty()) return;

            if (finalCommand.charAt(0) == '/') finalCommand = finalCommand.substring(1);
            // accept the "color": HEX COLOR in title
            if (finalCommand.contains("\"color\"") && finalCommand.contains("title"))
                finalCommand = StringConverter.deconvertColor(finalCommand);
            RunConsoleCommand.runConsoleCommand(finalCommand, aInfo.isSilenceOutput());
        }
    }

    public void runDelayedCommand() {
        BukkitRunnable runnable = new BukkitRunnable() {

            public void run() {
                insideDelayedCommand();
            }
        };
        task = runnable.runTaskLater(SCore.plugin, this.getDelay());
        CommandsHandler.getInstance().addDelayedCommand(this);
    }

    public abstract void insideDelayedCommand();

    public abstract void runCommand(SCommand command, List<String> args);

    public abstract void pickupInfo();


    public String getBrutCommand() {
        return brutCommand;
    }

    public void setBrutCommand(String brutCommand) {
        this.brutCommand = brutCommand;
    }

    public StringPlaceholder getSp() {
        return sp;
    }

    public void setSp(StringPlaceholder sp) {
        this.sp = sp;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public ActionInfo getaInfo() {
        return aInfo;
    }

    public void setaInfo(ActionInfo aInfo) {
        this.aInfo = aInfo;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

}
