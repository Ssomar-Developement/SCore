package com.ssomar.score.commands.runnable.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;

import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.block.commands.Around;
import com.ssomar.score.commands.runnable.block.commands.Break;
import com.ssomar.score.commands.runnable.block.commands.DropExecutableItem;
import com.ssomar.score.commands.runnable.block.commands.DropItem;
import com.ssomar.score.commands.runnable.block.commands.Explode;
import com.ssomar.score.commands.runnable.block.commands.FarmInCube;
import com.ssomar.score.commands.runnable.block.commands.FertilizeInCube;
import com.ssomar.score.commands.runnable.block.commands.MineInCube;
import com.ssomar.score.commands.runnable.block.commands.MobAround;
import com.ssomar.score.commands.runnable.block.commands.ParticleCommand;
import com.ssomar.score.commands.runnable.block.commands.RemoveBlock;
import com.ssomar.score.commands.runnable.block.commands.SendMessage;
import com.ssomar.score.commands.runnable.block.commands.SetBlock;
import com.ssomar.score.commands.runnable.block.commands.SetExecutableBlock;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class BlockCommandManager implements CommandManager{
	
	private static BlockCommandManager instance;
	
	private List<BlockCommand> commands;

	public BlockCommandManager() {
		List<BlockCommand> references = new ArrayList<>();
		references.add(new SetBlock());
		references.add(new SetExecutableBlock());
		references.add(new ParticleCommand());
		references.add(new SendMessage());
		references.add(new Explode());
		references.add(new Break());
		references.add(new DropItem());
		references.add(new DropExecutableItem());
		references.add(new MineInCube());
		references.add(new FarmInCube());
		references.add(new FertilizeInCube());
		references.add(new RemoveBlock());
		references.add(new Around());
		references.add(new MobAround());
		this.commands = references;
	}

	/*
	 *  return "" if no error else return the error
	 */
	public String verifArgs(BlockCommand bC, List<String> args) {
		return bC.verify(args);
	}


	public boolean isValidBlockCommads(String entry) {
		for(BlockCommand blockCommands : commands) {
			for(String name: blockCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<String> getBlockCommands(SPlugin sPlugin, List<String> commands, List<String> errorList, String id) {

		List<String> result = new ArrayList<>();

		for (int i = 0; i < commands.size(); i++) {

			String command = StringConverter.coloredString(commands.get(i));

			/*
			 * if (command.contains("\\{")) command= command.replaceAll("\\{", ""); if
			 * (command.contains("\\}")) command= command.replaceAll("\\}", "");
			 */

			if (this.isValidBlockCommads(commands.get(i)) && !commands.get(i).contains("//")) {
				BlockCommand bc = (BlockCommand) this.getCommand(command);
				List<String> args = this.getArgs(command);

				String error = "";
				if (!(error = this.verifArgs(bc, args)).isEmpty()) {
					errorList.add(sPlugin.getNameDesign()+" " + error + " for item: " + id);
					continue;
				}
			}
			result.add(command);
		}
		return result;
	}

	public static BlockCommandManager getInstance() {
		if(instance == null) instance = new BlockCommandManager();
		return instance;
	}

	public List<BlockCommand> getCommands() {
		return commands;
	}

	public Map<String, String> getCommandsDisplay() {
		Map<String, String> result = new HashMap<>();
		for(SCommand c : this.commands) {

			ChatColor extra = c.getExtraColor();
			if(extra == null) extra = ChatColor.DARK_PURPLE;

			ChatColor color = c.getColor();
			if(color == null) color = ChatColor.LIGHT_PURPLE;

			result.put(extra+"["+color+"&l"+c.getNames().get(0)+extra+"]", c.getTemplate());
		}
		return result;
	}
	
	
	public void setCommands(List<BlockCommand> commands) {
		this.commands = commands;
	}

	@Override
	public SCommand getCommand(String brutCommand) {
		for(BlockCommand blockCommands : commands) {
			for(String name: blockCommands.getNames()) {
				if(brutCommand.toUpperCase().startsWith(name.toUpperCase())) {
					return blockCommands;
				}
			}
		}
		return null;
	}

	@Override
	public List<String> getArgs(String command) {
		List<String> args = new ArrayList<>();
		boolean first = true;
		for(String s : command.split(" ")) {
			if(first) {
				first = false;
				continue;
			}
			args.add(s);
		}
		return args;
	}
}
