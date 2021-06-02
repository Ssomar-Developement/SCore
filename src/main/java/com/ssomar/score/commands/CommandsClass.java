//package com.ssomar.score.commands;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.bukkit.ChatColor;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.command.TabExecutor;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//
//import com.ssomar.score.SCore;
//import com.ssomar.score.configs.messages.Message;
//import com.ssomar.score.configs.messages.MessageMain;
//import com.ssomar.score.data.Database;
//import com.ssomar.score.data.ParkourCompletionQuery;
//import com.ssomar.score.data.RatingsQuery;
//import com.ssomar.score.inventories.InventoryReader;
//import com.ssomar.score.inventories.InventoryWriter;
//import com.ssomar.score.menu.categories.CategoriesAdminGUI;
//import com.ssomar.score.menu.categories.CategoriesGUI;
//import com.ssomar.score.menu.parkour.ParkourGUIManager;
//import com.ssomar.score.menu.trades.TradeNtoPGUIManager;
//import com.ssomar.score.menu.trades.TradePtoNGUIManager;
//import com.ssomar.score.parkour.Parkour;
//import com.ssomar.score.parkour.ParkourManager;
//import com.ssomar.score.runs.Run;
//import com.ssomar.score.runs.RunManager;
//import com.ssomar.score.utils.SendMessage;
//import com.ssomar.score.utils.StringConverter;
//
//public class CommandsClass implements CommandExecutor, TabExecutor{
//
//	private SendMessage sm = new SendMessage();
//
//	private SCore main;
//
//	public CommandsClass(SCore main) {
//		this.main = main;
//	}
//
//	@Override
//	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//
//		if(args.length>0) {
//
//			switch(args[0]) {
//			case "reload":
//				this.runCommand(sender, "reload", args);
//				break;
//			case "create":
//				this.runCommand(sender, "create", args);
//				break;
//			case "join":
//				this.runCommand(sender, "join", args);
//				break;
//			case "editor":
//				this.runCommand(sender, "editor", args);
//				break;
//			case "copy-armor":
//				this.runCommand(sender, "copy-armor", args);
//				break;
//			case "delete":
//				this.runCommand(sender, "delete", args);
//				break;
//			case "leave":
//				this.runCommand(sender, "leave", args);
//				break;
//			case "rate":
//				this.runCommand(sender, "rate", args);
//				break;
//			case "items":
//				this.runCommand(sender, "items", args);
//				break;
//			default:
//				sender.sendMessage(StringConverter.coloredString("&4[SParkour] &cInvalid argument /sparkour [ reload | editor | join | leave | create | delete | rate ]"));
//				break;
//			}
//		}
//		else {
//			sender.sendMessage(StringConverter.coloredString("&4[SParkour] &cInvalid argument /sparkour [ reload | editor | join | leave | create | delete | rate ]"));
//		}
//		return true;
//	}
//
//	public void runCommand(CommandSender sender, String command, String[] fullArgs) {
//
//		String [] args;
//		if(fullArgs.length>1) {
//			args = new String[fullArgs.length-1];
//			for(int i = 0; i<fullArgs.length;i++) {
//				if(i==0) continue;
//				else args[i-1]= fullArgs[i];
//			}
//		}
//		else args = new String[0];
//		Player player=null;
//		if((sender instanceof Player)) {
//			player = (Player) sender;
//			if(!(player.hasPermission("sparkour.cmd."+command) || player.hasPermission("sparkour.cmds") || player.hasPermission("sparkour.*"))) {
//				player.sendMessage(StringConverter.coloredString("&4[Sparkour] &cYou don't have the permission to execute this command: "+"&6sparkour.cmd."+command));
//				return;
//			}
//		}
//
//		switch(command) {
//
//		case "reload":
//			main.onReload();
//			sm.sendMessage(sender, ChatColor.GREEN+"SParkour has been reload");	
//			System.out.println(MessageMain.getInstance().getMessage(Message.M_reloadMessage));
//			break;
//		case "join":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				new CategoriesGUI(player).openGUISync(player);
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		case "editor":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				new CategoriesAdminGUI(player).openGUISync(player);
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		case "leave":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				RunManager.getInstance().stopAllRunsOf(player);
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		case "rate":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				if(args.length==2) {
//					String parkourID = args[0];
//					Parkour parkour;
//					if((parkour = ParkourManager.getInstance().getParkourWithID(parkourID)) == null) {
//						sm.sendMessage(player, MessageMain.getInstance().getMessage(Message.M_invalidParkour).replaceAll("%parkour_id%", parkourID));
//						return;
//					}
//
//					if(ParkourCompletionQuery.getTimesCompletedOfFor(Database.getInstance().connect(), player, parkour) == 0) {
//						sm.sendMessage(player, MessageMain.getInstance().getMessage(Message.M_parkourMustBeFinished));
//						return;
//					}
//
//					if(RatingsQuery.hasRateOfBy(Database.getInstance().connect(), parkour, player)) {
//						sm.sendMessage(player, MessageMain.getInstance().getMessage(Message.M_alreadyRated));
//						return;
//					}
//
//					int rate = -1;
//					try {
//						rate = Integer.valueOf(args[1]);
//					}catch(Exception e) {
//						sm.sendMessage(player, MessageMain.getInstance().getMessage(Message.M_invalidRate));
//						return;
//					}
//
//					if(rate<0 || rate > 5) {
//						sm.sendMessage(player, MessageMain.getInstance().getMessage(Message.M_invalidRateValue));
//						return;
//					}
//
//
//					RatingsQuery.insertRate(Database.getInstance().connect(), player, parkour, rate);
//					sm.sendMessage(player, MessageMain.getInstance().getMessage(Message.M_successRate).replaceAll("%parkour_name%", parkour.getName()).replaceAll("%rate%", rate+""));
//				}
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		case "create":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				if(args.length == 1) {
//					if(ParkourGUIManager.getInstance().getCache().containsKey(player)) {
//						ParkourGUIManager.getInstance().getCache().get(player).openGUISync(player);
//					}
//					else {
//						if(ParkourManager.getInstance().containsPakourWithID(args[0])) {
//							sm.sendMessage(sender, "&4&l[SParkour] &cError a parkour already exist with this ID");
//						}
//						else ParkourGUIManager.getInstance().startEditing(player, args[0]);
//					}
//				}
//				else {
//					sm.sendMessage(sender, "&4&l[SParkour] &cYou must enter a parkour id, &6/sparkour create TEST");
//				}
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		case "copy-armor":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				Run run = RunManager.getInstance().getRunOf(player);
//				if(run == null) {
//					Map<Integer, ItemStack> armors = new HashMap<>();
//					int slot = 36;
//					for(ItemStack is : player.getInventory().getArmorContents()) {
//						if(is!=null) {
//							armors.put(slot, is);
//						}
//						slot++;
//					}
//					InventoryWriter.writeArmorsRunInv(player, armors);
//					sm.sendMessage(sender, "&2&l[SParkour] &aArmor copied correctly !");
//				}
//				else {
//					Map<Integer, ItemStack> armors =InventoryReader.getSavedArmorNormalInv(player);
//					ItemStack[] armorsConvert = new ItemStack[4];
//					armorsConvert[0] = armors.get(36);
//					armorsConvert[1] = armors.get(37);
//					armorsConvert[2] = armors.get(38);
//					armorsConvert[3] = armors.get(39);
//					
//					player.getInventory().setArmorContents(armorsConvert);
//					sm.sendMessage(sender, "&2&l[SParkour] &aArmor copied correctly !");
//				}
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		case "items":
//			if((sender instanceof Player)) {
//				player = (Player) sender;
//				Run run = RunManager.getInstance().getRunOf(player);
//				if(run == null) {
//					TradeNtoPGUIManager.getInstance().startEditing(player);
//				}
//				else {
//					TradePtoNGUIManager.getInstance().startEditing(player);
//				}
//			}
//			else {
//				sm.sendMessage(sender, MessageMain.getInstance().getMessage(Message.M_commandMustBeExecutedByPlayer));	
//			}
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
//		if(command.getName().equalsIgnoreCase("sparkour")) {
//			ArrayList<String> arguments = new ArrayList<String>();
//			if (args.length == 1) {
//
//				arguments.add("reload");
//				arguments.add("editor");
//				arguments.add("join");
//				arguments.add("create");
//				arguments.add("delete");
//				arguments.add("rate");
//				arguments.add("leave");
//				arguments.add("copy-armor");
//				arguments.add("items");
//
//				Collections.sort(arguments);
//				return arguments;
//			}
//			if (args.length >= 2) {
//
//				switch(args[0]) {
//				case "giveall":
//					//					for(Item item : ItemManager.getInstance().getLoadedItems()) {
//					//						arguments.add(item.getIdentification());
//					//					}
//					//					Collections.sort(arguments);
//					//					return arguments;
//
//				default:
//					break;
//				}
//			}
//		}
//		return null;
//	}
//
//}
