//package com.ssomar.score.commands.runnable.player.old;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.scheduler.BukkitTask;
//
//import com.ssomar.score.SCore;
//import com.ssomar.score.commands.runnable.ActionInfo;
//import com.ssomar.score.commands.runnable.CommandsExecutor;
//import com.ssomar.score.commands.runnable.CommandsHandler;
//import com.ssomar.score.commands.runnable.RunConsoleCommand;
//import com.ssomar.score.utils.StringConverter;
//
//public class PlayerCommandsExecutor extends CommandsExecutor{
//
//	/* Entity who are affected by the commands */
//	private Player receiver;
//
//
//	public PlayerCommandsExecutor(List<String> commands, Player player, Player receiver, ActionInfo actionInfo) {
//		super(commands, player, actionInfo);
//		this.receiver=receiver;
//	}
//
//	public PlayerCommandsExecutor(List<String> commands, Player player, boolean silenceOutput, Player receiver, ActionInfo actionInfo) {
//		super(commands, player, silenceOutput, actionInfo);
//		this.receiver=receiver;
//	}
//
//	public String replaceLocation(String command) {	
//		Location loc = receiver.getLocation();		
//		return replaceLocation(command, loc.getX(), loc.getY(), loc.getZ(), loc.getWorld());	
//	}
//
//	public void runPlayerCommands(boolean silenceOutput) {
//
//		for(Integer d : this.getFinalCommands().keySet()) {
//			for(String commandz : this.getFinalCommands().get(d)) {
//
//				UUID uuid = UUID.randomUUID();
//
//				BukkitTask task;
//				BukkitRunnable runnable = new BukkitRunnable(){
//
//					public void run(){		
//
//						String command = replacePlaceholder(replaceLocation(commandz));	
//
//						//SsomarDev.testMsg("plyCMD> "+command+ " player> "+receiver.getName()+ " online> "+receiver.isOnline());
//
//						if(receiver.isOnline()) {
//							/* to make sure that the player variable is correct, deco/reco can disturb the variable */
//							receiver = Bukkit.getPlayer(receiver.getUniqueId());
//
//							if(command.contains("ei giveslot")) {
//								try {
//									String playeName= command.split("ei giveslot ")[1].split(" ")[0];
//									Player pgive = Bukkit.getPlayer(playeName);
//									CommandsHandler.getInstance().addStopPickup(pgive, 20);
//								}catch(Exception e) {}
//							}
//
//
//							PlayerCommand pC = PlayerCommandManager.getInstance().getPlayerCommand(command);
//
//							List<String> args = PlayerCommandManager.getInstance().getPCArgs(command);
//
//							if(pC!=null) {
//								pC.run(getPlayer(), receiver, args, getActionInfo(), silenceOutput);
//							}
//							else {
//								if(command.charAt(0)=='/') command=  command.substring(1, command.length());
//								// accept the "color": HEX COLOR in title
//								if(command.contains("\"color\"") && command.contains("title")) command = StringConverter.deconvertColor(command);
//								RunConsoleCommand.runConsoleCommand(command, silenceOutput);
//							}
//							CommandsHandler.getInstance().removeDelayedCommand(receiver, uuid);
//						}
//						else CommandsHandler.getInstance().addDisconnectedPlayerCommand(receiver, command);
//					}
//				};
//				task = runnable.runTaskLater(SCore.getPlugin(), d);
//				CommandsHandler.getInstance().addDelayedCommand(receiver, uuid, task.getTaskId());
//			}
//		}
//	}
//}
