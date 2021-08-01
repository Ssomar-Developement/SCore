//package com.ssomar.score.commands.runnable.player.old;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.bukkit.ChatColor;
//
//import com.ssomar.score.commands.rework.SCommand;
//import com.ssomar.score.commands.runnable.player.commands.ActionbarCommand;
//import com.ssomar.score.commands.runnable.player.commands.Around;
//import com.ssomar.score.commands.runnable.player.commands.BackDash;
//import com.ssomar.score.commands.runnable.player.commands.Burn;
//import com.ssomar.score.commands.runnable.player.commands.CustomDash1;
//import com.ssomar.score.commands.runnable.player.commands.Damage;
//import com.ssomar.score.commands.runnable.player.commands.FlyOff;
//import com.ssomar.score.commands.runnable.player.commands.FlyOn;
//import com.ssomar.score.commands.runnable.player.commands.FrontDash;
//import com.ssomar.score.commands.runnable.player.commands.Head;
//import com.ssomar.score.commands.runnable.player.commands.Jump;
//import com.ssomar.score.commands.runnable.player.commands.Launch;
//import com.ssomar.score.commands.runnable.player.commands.LaunchEntity;
//import com.ssomar.score.commands.runnable.player.commands.MobAround;
//import com.ssomar.score.commands.runnable.player.commands.ParticleCommand;
//import com.ssomar.score.commands.runnable.player.commands.ProjectileCustomDash1;
//import com.ssomar.score.commands.runnable.player.commands.RegainFood;
//import com.ssomar.score.commands.runnable.player.commands.RegainHealth;
//import com.ssomar.score.commands.runnable.player.commands.RemoveBurn;
//import com.ssomar.score.commands.runnable.player.commands.ReplaceBlock;
//import com.ssomar.score.commands.runnable.player.commands.SendBlankMessage;
//import com.ssomar.score.commands.runnable.player.commands.SendCenteredMessage;
//import com.ssomar.score.commands.runnable.player.commands.SendMessage;
//import com.ssomar.score.commands.runnable.player.commands.SetBlock;
//import com.ssomar.score.commands.runnable.player.commands.SetExecutableBlock;
//import com.ssomar.score.commands.runnable.player.commands.SetHealth;
//import com.ssomar.score.commands.runnable.player.commands.SpawnEntityOnCursor;
//import com.ssomar.score.commands.runnable.player.commands.StrikeLightning;
//import com.ssomar.score.commands.runnable.player.commands.Sudo;
//import com.ssomar.score.commands.runnable.player.commands.SudoOp;
//import com.ssomar.score.commands.runnable.player.commands.TeleportOnCursor;
//import com.ssomar.score.commands.runnable.player.commands.WorldTeleport;
//import com.ssomar.score.splugin.SPlugin;
//import com.ssomar.score.utils.StringConverter;
//
//public class PlayerCommandManager {
//	
//	private static PlayerCommandManager instance;
//	
//	private List<PlayerCommand> commands;
//	
//	public PlayerCommandManager() {
//		List<PlayerCommand> commands = new ArrayList<>();
//		commands.add(new ActionbarCommand());
//		commands.add(new Around());
//		commands.add(new MobAround());
//		commands.add(new SendBlankMessage());
//		commands.add(new SendMessage());
//		commands.add(new SendCenteredMessage());
//		/* SUDOOP MUST BE BEFORE SUDO */
//		commands.add(new SudoOp());
//		commands.add(new Sudo());
//		commands.add(new FlyOn());
//		commands.add(new FlyOff());
//		commands.add(new SetBlock());
//		commands.add(new ReplaceBlock());
//		commands.add(new ParticleCommand());
//		commands.add(new CustomDash1());
//		commands.add(new ProjectileCustomDash1());
//		commands.add(new FrontDash());
//		commands.add(new BackDash());
//		commands.add(new TeleportOnCursor());
//		commands.add(new WorldTeleport());
//		commands.add(new SpawnEntityOnCursor());
//		commands.add(new Damage());
//		commands.add(new LaunchEntity());
//		commands.add(new Launch());
//		commands.add(new Burn());
//		commands.add(new Jump());
//		commands.add(new RemoveBurn());
//		commands.add(new SetHealth());
//		commands.add(new SetExecutableBlock());
//		commands.add(new StrikeLightning());
//		commands.add(new RegainHealth());
//		commands.add(new RegainFood());
//		commands.add(new Head());
//		
//		this.commands = commands;
//	}
//	
//
//	public String verifArgs(PlayerCommand pC, List<String> args) {
//
//		/* ""> No error */
//		String error="";
//
//		error = pC.verify(args);
//		
//		return error;
//	}
//
//	public boolean isValidPlayerCommads(String entry) {
//		for(PlayerCommand playerCommands : this.commands) {
//			for(String name: playerCommands.getNames()) {
//				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//
//	public PlayerCommand getPlayerCommand(String entry) {
//		for(PlayerCommand playerCommands : this.commands) {
//			for(String name: playerCommands.getNames()) {
//				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
//					return playerCommands;
//				}
//			}
//		}
//		return null;
//	}
//
//	public List<String> getPCArgs(String entry) {
//		List<String> args = new ArrayList<>();
//		boolean first = true;
//		boolean second = false;
//		if(entry.toUpperCase().startsWith("FLY ON")
//				|| entry.toUpperCase().startsWith("FLY OFF")
//				|| entry.toUpperCase().startsWith("REGAIN HEALTH")
//				|| entry.toUpperCase().startsWith("REGAIN FOOD")) second = true;
//		for(String s : entry.split(" ")) {
//			if(first) {
//				first = false;
//				continue;
//			}
//			if(second) {
//				second = false;
//				continue;
//			}
//			args.add(s);
//		}
//		return args;
//	}
//	
//	public List<String> getCommands(SPlugin sPlugin, List<String> commands, List<String> errorList, String id) {
//
//		List<String> result = new ArrayList<>();
//
//		for (int i = 0; i < commands.size(); i++) {
//
//			String command = StringConverter.coloredString(commands.get(i));
//
//			if (this.isValidPlayerCommads(commands.get(i))) {
//				PlayerCommand bc = this.getPlayerCommand(command);
//				List<String> args = this.getPCArgs(command);
//
//				String error = "";
//				if (!(error = this.verifArgs(bc, args)).isEmpty()) {
//					errorList.add(sPlugin.getNameDesign()+" " + error + " for item: " + id);
//					continue;
//				}
//			}
//			result.add(command);
//		}
//		return result;
//	}
//
//	public static PlayerCommandManager getInstance() {
//		if(instance == null) instance = new PlayerCommandManager();
//		return instance;
//	}
//
//
//	public List<PlayerCommand> getCommands() {
//		return commands;
//	}
//	
//	public Map<String, String> getCommandsDisplay() {
//		Map<String, String> result = new HashMap<>();
//		for(SCommand c : this.commands) {
//
//			ChatColor extra = c.getExtraColor();
//			if(extra == null) extra = ChatColor.DARK_PURPLE;
//
//			ChatColor color = c.getColor();
//			if(color == null) color = ChatColor.LIGHT_PURPLE;
//
//			result.put(extra+"["+color+"&l"+c.getNames().get(0)+extra+"]", c.getTemplate());
//		}
//		return result;
//	}
//
//
//	public void setCommands(List<PlayerCommand> commands) {
//		this.commands = commands;
//	}	
//
//}
