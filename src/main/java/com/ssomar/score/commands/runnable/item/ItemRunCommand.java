package com.ssomar.score.commands.runnable.item;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.utils.DynamicMeta;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ItemRunCommand extends RunCommand {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private UUID launcherUUID;
    @Getter @Setter
    private ItemStack itemStack;
    @Getter @Setter
    private boolean silenceOutput;


    public ItemRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
        super(brutCommand, delay, aInfo);
    }

    public ItemRunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
        super(brutCommand, runTime, aInfo);
    }

    @Override
    public void pickupInfo() {
        ActionInfo aInfo = this.getaInfo();

        launcherUUID = aInfo.getLauncherUUID();

        itemStack = aInfo.getItemStack();

        silenceOutput = aInfo.isSilenceOutput();
    }

    @Override
    public void runGetManager() {
        this.runCommand(ItemCommandManager.getInstance());
    }

    @Override
    public void runCommand(SCommandToExec sCommandToExec) {
        //SsomarDev.testMsg("PRE RUN COMMAND BEFORE CHECK LAUNCHER", true);
        @Nullable Player launcher = null;
        if(launcherUUID != null) launcher = Bukkit.getPlayer(launcherUUID);

        //SsomarDev.testMsg("PRE RUN COMMAND STEP: "+this.getaInfo().getStep(), true);
        sCommandToExec.setActionInfo(getaInfo());

        if(sCommandToExec.getSCommand() instanceof ItemMetaSCommand) {
            ItemMetaSCommand pCommand = (ItemMetaSCommand) sCommandToExec.getSCommand();
            DynamicMeta dMeta = new DynamicMeta(itemStack.getItemMeta());
            pCommand.run(launcher, dMeta, sCommandToExec);
            itemStack.setItemMeta(dMeta.getMeta());
        } else if(sCommandToExec.getSCommand() instanceof ItemSCommand) {
            ItemSCommand pCommand = (ItemSCommand) sCommandToExec.getSCommand();
            pCommand.run(launcher, itemStack, sCommandToExec);
        }
        SsomarDev.testMsg("RUN COMMAND: >>>" + sCommandToExec.getSCommand(), true);
    }


    @Override
    public void insideDelayedCommand() {
        runCommand(ItemCommandManager.getInstance());
    }

    @Override
    public void executeRunnable(Runnable runnable) {
        runnable.run();
    }
}
