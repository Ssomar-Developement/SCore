package com.ssomar.score.commands.runnable.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssomar.score.commands.runnable.block.commands.Break;
import com.ssomar.score.commands.runnable.block.commands.DropExecutableItem;
import com.ssomar.score.commands.runnable.block.commands.DropItem;
import com.ssomar.score.commands.runnable.block.commands.Explode;
import com.ssomar.score.commands.runnable.block.commands.FarmInCube;
import com.ssomar.score.commands.runnable.block.commands.FertilizeInCube;
import com.ssomar.score.commands.runnable.block.commands.MineInCube;
import com.ssomar.score.commands.runnable.block.commands.ParticleCommand;
import com.ssomar.score.commands.runnable.block.commands.SendMessage;
import com.ssomar.score.commands.runnable.block.commands.SetBlock;
import com.ssomar.score.utils.StringConverter;

public enum BlockCommand {

	SETBLOCK ("SETBLOCK"),
	PARTICLE ("PARTICLE"),
	SENDMESSAGE ("SENDMESSAGE"),
	EXPLODE ("EXPLODE"),
	BREAK ("BREAK"),
	DROPITEM ("DROPITEM"),
	DROPEXECUTABLEITEM ("DROPEXECUTABLEITEM", "DROPEI"),
	MINEINCUBE ("MINEINCUBE"),
	FARMINCUBE ("FARMINCUBE"),
	FERTILIZEINCUBE ("FERTILIZEINCUBE");

	private String[] names;

	BlockCommand(String... names) {
		this.names=names;
	}

	public static Map<BlockCommand, BlockCommandTemplate> getReferences(){
		Map<BlockCommand, BlockCommandTemplate> references = new HashMap<>();

		references.put(SETBLOCK, new SetBlock());
		references.put(PARTICLE, new ParticleCommand());
		references.put(SENDMESSAGE, new SendMessage());
		references.put(EXPLODE, new Explode());
		references.put(BREAK, new Break());
		references.put(DROPITEM, new DropItem());
		references.put(DROPEXECUTABLEITEM, new DropExecutableItem());
		references.put(MINEINCUBE, new MineInCube());
		references.put(FARMINCUBE, new FarmInCube());
		references.put(FERTILIZEINCUBE, new FertilizeInCube());

		return references;
	}

	public static String verifArgs(BlockCommand bC, List<String> args) {
		return BlockCommand.getReferences().get(bC).verify(args);
	}


	public static boolean isValidBlockCommads(String entry) {
		for(BlockCommand blockCommands : values()) {
			for(String name: blockCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}


	public static BlockCommand getBlockCommand(String entry) {
		for(BlockCommand blockCommands : values()) {
			for(String name: blockCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return blockCommands;
				}
			}
		}
		return null;
	}

	public static List<String> getBCArgs(String entry) {
		List<String> args = new ArrayList<>();
		boolean first= true;
		for(String s : entry.split(" ")) {
			if(first) {
				first=false;
				continue;
			}
			args.add(s);
		}
		return args;
	}

	public boolean containsThisName(String entry) {
		for(String name : getNames()) {
			if(name.equalsIgnoreCase(entry)) return true;
		}
		return false;
	}
	
	public static List<String> getBlockCommands(List<String> commands, List<String> errorList, String id) {

		List<String> result = new ArrayList<>();

		for (int i = 0; i < commands.size(); i++) {

			String command = StringConverter.coloredString(commands.get(i));

			/*
			 * if (command.contains("\\{")) command= command.replaceAll("\\{", ""); if
			 * (command.contains("\\}")) command= command.replaceAll("\\}", "");
			 */

			if (BlockCommand.isValidBlockCommads(commands.get(i)) && !commands.get(i).contains("//")) {
				BlockCommand bc = BlockCommand.getBlockCommand(command);
				List<String> args = BlockCommand.getBCArgs(command);

				String error = "";
				if (!(error = BlockCommand.verifArgs(bc, args)).isEmpty()) {
					errorList.add("[ExecutableItems] " + error + " for item: " + id);
					continue;
				}
			}
			result.add(command);
		}
		return result;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
}
