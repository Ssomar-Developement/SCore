package com.ssomar.score.commands.runnable.player;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommand;

public class PlayerRunCommand extends RunCommand{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID launcherUUID;

	private UUID receiverUUID;

	private ActionInfo aInfo;

	private boolean silenceOutput;

	public PlayerRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
		super(brutCommand, delay, aInfo);
	}
	
	public PlayerRunCommand(String brutCommand, long runTime, ActionInfo aInfo) {
		super(brutCommand, runTime, aInfo);
	}
	
	@Override
	public void pickupInfo() {
		ActionInfo aInfo = this.getaInfo();

		launcherUUID = aInfo.getLauncherUUID();

		receiverUUID = aInfo.getReceiverUUID();

		silenceOutput = aInfo.isSilenceOutput();
	}

	@Override
	public void run() {

		if(this.getDelay() == 0) {
			this.runCommand(PlayerCommandManager.getInstance());
		}
		else {
			this.runDelayedCommand();
		}
	}

	@Override
	public void runCommand(SCommand command, List<String> args) {
		PlayerSCommand pCommand = (PlayerSCommand) command;

		Player launcher = Bukkit.getPlayer(launcherUUID);
		Player receiver = Bukkit.getPlayer(receiverUUID);

		pCommand.run(launcher, receiver, args, aInfo);
	}
	
	
	@Override
	public void insideDelayedCommand() {
		Player receiver = Bukkit.getPlayer(receiverUUID);

		if(receiver != null && receiver.isOnline()) {
			runCommand(PlayerCommandManager.getInstance());
			CommandsHandler.getInstance().removeDelayedCommand(getUuid(), receiverUUID);
		}
		//else {
			//ADD THE COMMAND IN THE DB
		//}
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
