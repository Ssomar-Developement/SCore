package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PlayerRunCommand extends RunCommand {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UUID launcherUUID;

    private UUID receiverUUID;

    private boolean silenceOutput;

    @Getter
    private boolean runOffline;

    @Getter
    private boolean clearIfDisconnect;

    public PlayerRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
        super(brutCommand, delay, aInfo);
        this.initRunOffline(brutCommand);
        this.initClearIfDisconnect(brutCommand);
    }

    public PlayerRunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
        super(brutCommand, runTime, aInfo);
        this.initRunOffline(brutCommand);
        this.initClearIfDisconnect(brutCommand);
    }

    public void initRunOffline(String brutCommand) {
        if (brutCommand.contains("[<OFFLINE>]")) {
            runOffline = true;
            this.setBrutCommand(brutCommand.replaceAll("\\[<OFFLINE>]", "").trim());
        } else runOffline = false;
    }

    public void initClearIfDisconnect(String brutCommand) {
        if (brutCommand.contains("[<CLEAR_IF_DISCONNECT>]")) {
            clearIfDisconnect = true;
            this.setBrutCommand(brutCommand.replaceAll("\\[<CLEAR_IF_DISCONNECT>]", "").trim());
        } else clearIfDisconnect = false;
    }

    @Override
    public void pickupInfo() {
        ActionInfo aInfo = this.getaInfo();

        launcherUUID = aInfo.getLauncherUUID();

        receiverUUID = aInfo.getReceiverUUID();

        silenceOutput = aInfo.isSilenceOutput();
    }

    @Override
    public void runGetManager() {
        this.runCommand(PlayerCommandManager.getInstance());
    }

    @Override
    public void runCommand(SCommand command, List<String> args) {
        PlayerSCommand pCommand = (PlayerSCommand) command;

        //SsomarDev.testMsg("PRE RUN COMMAND BEFORE CHECK LAUNCHER", true);
        @Nullable Player launcher = null;
        if(launcherUUID != null) launcher = Bukkit.getPlayer(launcherUUID);

        Player receiver = Bukkit.getPlayer(receiverUUID);

        //SsomarDev.testMsg("PRE RUN COMMAND STEP: "+this.getaInfo().getStep(), true);
        pCommand.run(launcher, receiver, args, getaInfo());
    }


    @Override
    public void insideDelayedCommand() {
        Player receiver = Bukkit.getPlayer(receiverUUID);

        if ((receiver != null && receiver.isOnline()) || runOffline) {
            runCommand(PlayerCommandManager.getInstance());
        }
        //else {
        //ADD THE COMMAND IN THE DB
        /* No need >> onPlayerQuitEvent its auto delete and save in the DB */
        //}
        CommandsHandler.getInstance().removeDelayedCommand(getUuid(), receiverUUID);
    }

    @Override
    public void executeRunnable(BukkitRunnable runnable) {
        /* Exceptional case for server loop */
        /* if(receiverUUID == null) {
            SCore.schedulerHook.runTask(runnable, 0);
            return;
        } */
        Player receiver = Bukkit.getPlayer(receiverUUID);
        SCore.schedulerHook.runEntityTaskAsap(runnable, null, receiver);
    }

    public UUID getLauncherUUID() {
        return launcherUUID;
    }

    public void setLauncherUUID(UUID launcherUUID) {
        this.launcherUUID = launcherUUID;
    }

    public UUID getReceiverUUID() {
        return receiverUUID;
    }

    public void setReceiverUUID(UUID receiverUUID) {
        this.receiverUUID = receiverUUID;
    }

    public boolean isSilenceOutput() {
        return silenceOutput;
    }

    public void setSilenceOutput(boolean silenceOutput) {
        this.silenceOutput = silenceOutput;
    }
}
