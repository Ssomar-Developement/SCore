
package com.ssomar.score.commands.runnable.block;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.CommandsManager;
import com.ssomar.score.commands.runnable.RunConsoleCommand;

public class BlockCommandsExecutor extends CommandsExecutor{

	/* Block who are affected by the commands */
	private Block block;
	
	/* Save the current type of the block */
	private Material blockType;


	public BlockCommandsExecutor(List<String> commands, Player player, Block block, ActionInfo actionInfo) {
		super(commands, player, actionInfo);
		this.block=block;
		this.blockType=block.getType();
	}

	public BlockCommandsExecutor(List<String> commands, Player player, boolean silenceOutput, Block block, Material oldMaterial, ActionInfo actionInfo) {
		super(commands, player, silenceOutput, actionInfo);
		this.block=block;
		this.blockType= oldMaterial;
	}

	public String replaceLocation(String command) {	
		Location loc = block.getLocation();		
		return replaceLocation(command, loc.getX(), loc.getY(), loc.getZ(), loc.getWorld());	
	}


	public void runBlockCommands(boolean silenceOutput) {

		for(Integer d : this.getFinalCommands().keySet()) {
			for(String commandz : this.getFinalCommands().get(d)) {

				UUID uuid = UUID.randomUUID();
				
				//SsomarDev.testMsg("block type3:"+ block.getType());

				int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask( SCore.getPlugin() , new Runnable(){

					
					public void run(){	
						
						String command = replacePlaceholder(replaceLocation(commandz));	

						if(command.contains("ei-giveslot")) {
							try {
								String playeName = command.split("ei-giveslot ")[1].split(" ")[0];
								Player pgive = Bukkit.getPlayer(playeName);
								CommandsManager.getInstance().addStopPickup(pgive, 20);
							}catch(Exception e) {}
						}

						BlockCommand bC = BlockCommand.getBlockCommand(command);

						List<String> args = BlockCommand.getBCArgs(command);

						if(bC!=null) {
							BlockCommand.getReferences().get(bC).run(getPlayer(), block, blockType, args, getActionInfo(), silenceOutput);	
						}
						else {
							if(command.charAt(0)=='/') {
								command=  command.substring(1, command.length());
							}
							RunConsoleCommand.runConsoleCommand(command, silenceOutput);
						}
						CommandsManager.getInstance().removeDelayedCommand(getPlayer(), uuid);
					}
				}, d);
				CommandsManager.getInstance().addDelayedCommand(getPlayer(), uuid, id);
			}
		}

	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}
