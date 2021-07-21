package com.ssomar.score.commands.runnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.StringPlaceholder;

import me.clip.placeholderapi.PlaceholderAPI;

public abstract class CommandsExecutor{

	/* Commands to run */
	private List<String> commands;

	/* Player who initiate the commands */
	private Player player;

	private ActionInfo actionInfo;

	/* delay in tick - commands */
	private HashMap<Integer, List<String>> finalCommands = new HashMap<>();

	public static SendMessage sm = new SendMessage();


	public CommandsExecutor(List<String> commands, Player player, ActionInfo actionInfo) {
		init(commands, player, false, actionInfo);
	}

	public CommandsExecutor(List<String> commands, Player player, boolean silenceOutput, ActionInfo actionInfo) {
		init(commands, player, silenceOutput, actionInfo);
	}

	public void init(List<String> commands, Player player, boolean silenceOutput, ActionInfo actionInfo) {
		this.commands = commands;
		this.player = player;
		this.actionInfo = actionInfo;
		this.replaceLoop();
		this.initFinalCommands();
	}

	public String replacePlaceholder(String command) {
		if (SCore.hasPlaceholderAPI) return PlaceholderAPI.setPlaceholders(player, command);
		else return command;
	}


	public List<String> selectRandomCommands(List<String> commands, Integer amount){
		List<String> commandsList = new ArrayList<>();
		commandsList.addAll(commands);

		List<String> result = new ArrayList<>();

		for(int i = 0; i < amount; i++) {
			if(commandsList.size() == 0) return result;
			int rdn = (int)(Math.random()*commandsList.size());
			result.add(commandsList.get(rdn));
			commandsList.remove(rdn);
		}		
		return result;
	}

	public void replaceLoop() {

		List<String> result = new ArrayList<>();
		boolean isInLoop = false;
		int loopAmount = 0;
		List<String> commandsInLoop = new ArrayList<>();

		for (int i = 0; i < commands.size(); i++) {

			String command = StringConverter.coloredString(commands.get(i));

			if(command.contains("LOOP START: ")) {
				try {
					loopAmount = Integer.valueOf(command.split("LOOP START: ")[1]);
					isInLoop = true;
					continue;
				}catch(Exception e) {
					loopAmount=0;
					isInLoop= false;
					continue;
				}
			}
			else if(command.contains("LOOP END")) {
				for(int k = 0; k < loopAmount; k++) {
					for(String str: commandsInLoop) {
						result.add(str);
					}
				}
				loopAmount = 0;
				isInLoop = false;
				commandsInLoop.clear();
				continue;
			}

			if(isInLoop) commandsInLoop.add(command);
			else result.add(command);
		}
		commands = result;
	}


	public void inserFinalCommands(Integer delay, String command) {
		if(finalCommands.containsKey(delay)) {
			finalCommands.get(delay).add(command);
		}
		else {
			List<String> result = new ArrayList<>();
			result.add(command);
			finalCommands.put(delay, result);
		}
	}

	public void inserFinalCommands(Integer delay, List<String> commands) {
		if(finalCommands.containsKey(delay)) {
			finalCommands.get(delay).addAll(commands);
		}
		else finalCommands.put(delay, commands);
	}


	public List<String> replaceNothing(String command){
		List<String> result = new ArrayList<>();

		if(command.contains("nothing*")) {
			try {
				int m=0;
				if(command.contains("//")) m = Integer.valueOf(command.split("nothing\\*")[1].split("//")[0].trim());
				else m = Integer.valueOf(command.split("nothing\\*")[1]);

				for(int k = 0; k < m; k++) {
					if(command.contains("//")) result.add("SENDMESSAGE "+command.split("//")[1]);
					else result.add("");
				}

			}catch(Exception err) {
				return Arrays.asList(command);
			}
		}
		else if(command.contains("NOTHING*")) {
			try {
				int m = 0;
				if(command.contains("//")) m=Integer.valueOf(command.split("NOTHING\\*")[1].split("//")[0].trim());
				else m = Integer.valueOf(command.split("NOTHING\\*")[1]);

				for(int k = 0; k < m; k++) {
					if(command.contains("//")) result.add("SENDMESSAGE "+command.split("//")[1]);
					else result.add("");
				}
			}catch(Exception err) {
				return Arrays.asList(command);
			}
		}
		else return Arrays.asList(command);
		return result;
	}


	public boolean initFinalCommands() {

		Integer delay = 0;
		boolean inRandom = false;
		int nbRandom = 0;
		ArrayList<String> commandsRandom = new ArrayList<String>();


		for(String command: commands) {

			//SsomarDev.testMsg("Command: "+ command);

			//SsomarDev.testMsg("cmdall> "+command);
			if(command.contains("RANDOM RUN:")) {
				//SsomarDev.testMsg("cmdrdn> "+command);
				nbRandom = Integer.valueOf(command.split("RANDOM RUN:")[1].replaceAll(" ",""));
				inRandom = true;
				continue;
			}

			else if(command.contains("RANDOM END")) {

				//				for(String s : commandsRandom) {
				//					SsomarDev.testMsg("randomrun not selected: "+s);
				//				}


				//SsomarDev.testMsg("size> "+commandsRandom.size()+" amount>"+nbRandom);
				this.inserFinalCommands(delay, this.selectRandomCommands(commandsRandom, nbRandom));
				inRandom = false;
				commandsRandom.clear();
				nbRandom = 0;
				continue;
			}

			else if(inRandom) {
				commandsRandom.addAll(this.replaceNothing(command));
				continue;
			}

			else if(command.contains("DELAYTICK ")) {
				delay = delay+(Integer.valueOf(command.replaceAll("DELAYTICK ", "")));
			}
			else if(command.contains("DELAY ")) {
				delay = delay+(Integer.valueOf(command.replaceAll("DELAY ", ""))*20);
			}
			else {
				this.inserFinalCommands(delay, command);
			}
		}
		if(commandsRandom.size() > 0) {
			this.inserFinalCommands(delay, this.selectRandomCommands(commandsRandom, nbRandom));
		}

		/* Decomp message in commands
		 * + delete empty commands
		 */
		this.clearFinalCommands();

		return true;
	}

	public void clearFinalCommands() {
		for(Integer d : finalCommands.keySet()) {
			List<String> gCommands = finalCommands.get(d);
			ArrayList<String> result = new ArrayList<>();
			for(String command : gCommands) {
				if(command.trim().length() == 0) continue;
				result.addAll(this.decompMsgInCommand(command));
			}
			finalCommands.put(d, new ArrayList<String>());
			this.inserFinalCommands(d, result);
		}
	}

	public List<String> decompMsgInCommand(String command){
		List<String> result = new ArrayList<>();

		String [] tab;

		if(command.contains("+++")) tab = command.split("\\+\\+\\+");
		else {
			tab = new String[1];
			tab[0] = command;
		}
		for(String s : tab) {
			while(s.startsWith(" ")) {
				s = s.substring(1, s.length());
			}
			while(s.endsWith(" ")) {
				s = s.substring(0, s.length()-1);
			}
			if(s.startsWith("/")) s = s.substring(1, s.length());
			result.add(s);
		}	

		String s = command;
		if(!result.isEmpty()) {
			s = result.get(result.size()-1);
			result.remove(result.size()-1);
		}

		if(s.contains("//")) {
			String [] spliter = s.split("//");

			String commandF = spliter[0];
			result.add(commandF);
			String message = "";
			if(spliter.length >= 2) {
				if(spliter[1].charAt(0) != ' ') {
					message = "SENDMESSAGE "+spliter[1];
				}
				else message = "SENDMESSAGE"+spliter[1];
				result.add(message);
			}

			return result;
		}
		else {
			result.add(s);
			return result;
		}
	}



	public String replaceLocation(String command, double x, double y, double z, World world) {

		String prepareCommand = command;

		prepareCommand = StringPlaceholder.replaceCalculPlaceholder(prepareCommand, "%x%", x+"", true);
		prepareCommand = StringPlaceholder.replaceCalculPlaceholder(prepareCommand, "%y%", y+"", true);
		prepareCommand = StringPlaceholder.replaceCalculPlaceholder(prepareCommand, "%z%", z+"", true);
		prepareCommand = prepareCommand.replaceAll("%world%", world.getName());
		
		return prepareCommand;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public HashMap<Integer, List<String>> getFinalCommands() {
		return finalCommands;
	}

	public void setFinalCommands(HashMap<Integer, List<String>> finalCommands) {
		this.finalCommands = finalCommands;
	}

	public static SendMessage getSm() {
		return sm;
	}

	public static void setSm(SendMessage sm) {
		CommandsExecutor.sm = sm;
	}

	public ActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(ActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}
}
