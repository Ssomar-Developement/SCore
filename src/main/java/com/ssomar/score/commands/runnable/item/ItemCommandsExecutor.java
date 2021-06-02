package com.ssomar.score.commands.runnable.item;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.CommandsManager;

@SuppressWarnings("unused")
public class ItemCommandsExecutor extends CommandsExecutor{
	
	private ItemStack item;
	
	private UUID itemUUID;


	public ItemCommandsExecutor(List<String> commands, Player player, ItemStack item, ActionInfo actionInfo) {
		super(commands, player, actionInfo);
		this.itemUUID = UUID.randomUUID();
		this.item=item;
	}

	public ItemCommandsExecutor(List<String> commands, Player player, boolean silenceOutput, ItemStack item, ActionInfo actionInfo) {
		super(commands, player, silenceOutput, actionInfo);
		this.itemUUID = UUID.randomUUID();
		this.item=item;
	}

	public String replaceLocation(String command) {	
		Location loc = getPlayer().getLocation();		
		return replaceLocation(command, loc.getX(), loc.getY(), loc.getZ(), loc.getWorld());	
	}

	public void runItemCommands() {
		
		/*
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("testt ccc");
		item.setItemMeta(meta);*/

		for(Integer d : this.getFinalCommands().keySet()) {
			for(String commandz : this.getFinalCommands().get(d)) {

				UUID uuid = UUID.randomUUID();

				BukkitTask task;
				BukkitRunnable runnable =new BukkitRunnable(){

					public void run(){		

						String command = replacePlaceholder(replaceLocation(commandz));	

						//SsomarDev.testMsg("plyCMD> "+command+ " player> "+receiver.getName()+ " online> "+receiver.isOnline());

						/*
						if(itemOwner.isOnline()) { */
							/* to make sure that the player variable is correct, deco/reco can disturb the variable */
							/*receiver = Bukkit.getPlayer(receiver.getUniqueId());

							if(command.contains("ei-giveslot")) {
								try {
									String playeName= command.split("ei-giveslot ")[1].split(" ")[0];
									Player pgive = Bukkit.getPlayer(playeName);
									CommandsManager.getInstance().addStopPickup(pgive);
								}catch(Exception e) {}
							}


							PlayerCommand pC = PlayerCommand.getPlayerCommand(command);

							List<String> args = PlayerCommand.getPCArgs(command);

							if(pC!=null) {

								//AROUND {distance} {true or false} {Your commands here}
								if(pC==PlayerCommand.AROUND) {
									//SsomarDev.testMsg("plyCMD> passe around");
									try {
										double distance= Double.valueOf(args.get(0));
										int cpt=0;
										for (Entity e: receiver.getNearbyEntities(distance, distance, distance)) {
											if(e instanceof Player) {
												Player target= (Player) e;
												if(!target.hasMetadata("NPC")) {
													if(target!=receiver) {
														String prepareCommands="";
														for(String s: args.subList(2, args.size())) {
															prepareCommands= prepareCommands+s+" ";
														}
														prepareCommands = prepareCommands.substring(0, prepareCommands.length()-1);

														String [] tab;

														if(prepareCommands.contains("+++")) tab = prepareCommands.split("\\+\\+\\+");
														else {
															tab = new String[1];
															tab[0] = prepareCommands;
														}
														for(String s : tab) {
															while(s.startsWith(" ")) {
																s = s.substring(1, s.length());
															}
															while(s.endsWith(" ")) {
																s = s.substring(0, s.length()-1);
															}
															if(s.startsWith("/")) s = s.substring(1, s.length());
															new PlayerCommandsExecutor(Arrays.asList(s.replaceAll("%target%", target.getName())), getPlayer(), isSilenceOutput(), target, getActionInfo()).runPlayerCommands();		
														}				
														cpt++;
													}
												}
											}
										}
										if(cpt==0) if(Boolean.valueOf(args.get(1))) getSm().sendMessage(receiver, MessageMain.getInstance().getNoHit());

									}catch(Exception e) {
										e.printStackTrace();
									}
								}

															}
							else {
								if(command.charAt(0)=='/') {
									command=  command.substring(1, command.length());
								}
								runConsoleCommand(command);
							}
							CommandsManager.getInstance().removeDelayedCommand(receiver, uuid);
						}
						else CommandsManager.getInstance().addDisconnectedPlayerCommand(receiver, command);
						*/
					}
				};
				task = runnable.runTaskLater(SCore.getPlugin(), d);
				CommandsManager.getInstance().addDelayedCommand(getPlayer(), uuid, task.getTaskId());
			}
		}

	}
}
