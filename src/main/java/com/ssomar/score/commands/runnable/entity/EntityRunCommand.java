package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommand;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EntityRunCommand extends RunCommand {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UUID launcherUUID;

    private UUID entityUUID;

    private boolean silenceOutput;

    public EntityRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
        super(brutCommand, delay, aInfo);
    }

    public EntityRunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
        super(brutCommand, runTime, aInfo);
    }

    @Override
    public void pickupInfo() {
        ActionInfo aInfo = this.getaInfo();

        launcherUUID = aInfo.getLauncherUUID();

        entityUUID = aInfo.getEntityUUID();

        silenceOutput = aInfo.isSilenceOutput();
    }

    @Override
    public void runGetManager() {
        this.runCommand(EntityCommandManager.getInstance());
    }

    @Override
    public void runCommand(SCommand command, List<String> args) {
        EntitySCommand pCommand = (EntitySCommand) command;

        this.pickupInfo();

        Player launcher = null;
        if (launcherUUID != null) launcher = Bukkit.getPlayer(launcherUUID);

        Entity receiver = null;
        if (SCore.is1v11Less()) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getUniqueId().equals(entityUUID)) {
                        receiver = entity;
                        break;
                    }
                }
            }
        } else receiver = Bukkit.getEntity(entityUUID);

        //SsomarDev.testMsg("entity uuid: " + entityUUID, true);
        //SsomarDev.testMsg("entity: " + receiver.getType(), true);
        if (receiver != null) {
            pCommand.run(launcher, receiver, args, getaInfo());
        }
        //else SsomarDev.testMsg("EntityRunCommand: receiver is null for the command: " + this.getBrutCommand(), true);
    }


    @Override
    public void insideDelayedCommand() {
        runCommand(EntityCommandManager.getInstance());
        CommandsHandler.getInstance().removeDelayedCommand(getUuid(), entityUUID);
    }

    @Override
    public void executeRunnable(BukkitRunnable runnable) {
        Entity receiver = null;
        if (SCore.is1v11Less()) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getUniqueId().equals(entityUUID)) {
                        receiver = entity;
                        break;
                    }
                }
            }
        } else receiver = Bukkit.getEntity(entityUUID);
        if (receiver != null) SCore.schedulerHook.runEntityTaskAsap(runnable, null, receiver);
        //else SsomarDev.testMsg("EntityRunCommand2: receiver is null for the command: " + this.getBrutCommand(), true);
    }
}
