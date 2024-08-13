package com.ssomar.score.commands.runnable.console;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;

public class ConsoleRunCommand extends RunCommand {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean silenceOutput;

    public ConsoleRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
        super(brutCommand, delay, aInfo);
    }

    public ConsoleRunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
        super(brutCommand, runTime, aInfo);
    }

    @Override
    public void pickupInfo() {
        ActionInfo aInfo = this.getaInfo();

        silenceOutput = aInfo.isSilenceOutput();
    }

    @Override
    public void runGetManager() {
        this.runCommand(ConsoleCommandManager.getInstance());
    }

    @Override
    public void runCommand(SCommandToExec sCommandToExec) {
        // NOTHING HERE
    }


    @Override
    public void insideDelayedCommand() {
        runCommand(ConsoleCommandManager.getInstance());
    }

    @Override
    public void executeRunnable(Runnable runnable) {
        SCore.schedulerHook.runTask(runnable, 0);
    }
    public boolean isSilenceOutput() {
        return silenceOutput;
    }

    public void setSilenceOutput(boolean silenceOutput) {
        this.silenceOutput = silenceOutput;
    }
}
