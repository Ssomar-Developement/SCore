package com.ssomar.score.commands.runnable.block;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.commands.runnable.RunCommand;
import com.ssomar.score.commands.runnable.SCommand;

public class BlockRunCommand extends RunCommand{

	private UUID launcherUUID;

	private Block block;

	private Material oldBlockMaterial;

	private ActionInfo aInfo;

	private boolean silenceOutput;

	public BlockRunCommand(String brutCommand, int delay, ActionInfo aInfo) {
		super(brutCommand, delay, aInfo);
	}
	@Override
	public void pickupInfo() {
		ActionInfo aInfo = this.getaInfo();

		launcherUUID = aInfo.getLauncherUUID();

		block = aInfo.getBlock();

		oldBlockMaterial = aInfo.getOldBlockMaterial();

		silenceOutput = aInfo.isSilenceOutput();
	}

	@Override
	public void run() {

		if(this.getDelay() == 0) {
			this.runCommand(BlockCommandManager.getInstance());
		}
		else {
			this.runDelayedCommand();
		}
	}

	@Override
	public void runCommand(SCommand command, List<String> args) {
		BlockSCommand pCommand = (BlockSCommand) command;

		Player launcher = Bukkit.getPlayer(launcherUUID);


		pCommand.run(launcher, block, oldBlockMaterial, args, aInfo);
	}


	@Override
	public void insideDelayedCommand() {
		runCommand(BlockCommandManager.getInstance());
		CommandsHandler.getInstance().removeDelayedCommand(getUuid(), null);
	}
	public UUID getLauncherUUID() {
		return launcherUUID;
	}
	public void setLauncherUUID(UUID launcherUUID) {
		this.launcherUUID = launcherUUID;
	}
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block) {
		this.block = block;
	}
	public Material getOldBlockMaterial() {
		return oldBlockMaterial;
	}
	public void setOldBlockMaterial(Material oldBlockMaterial) {
		this.oldBlockMaterial = oldBlockMaterial;
	}
	public boolean isSilenceOutput() {
		return silenceOutput;
	}
	public void setSilenceOutput(boolean silenceOutput) {
		this.silenceOutput = silenceOutput;
	}
}
