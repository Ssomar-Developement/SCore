package com.ssomar.score.commands.runnable.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssomar.score.commands.runnable.player.commands.ActionbarCommand;
import com.ssomar.score.commands.runnable.player.commands.Around;
import com.ssomar.score.commands.runnable.player.commands.BackDash;
import com.ssomar.score.commands.runnable.player.commands.Burn;
import com.ssomar.score.commands.runnable.player.commands.CustomDash1;
import com.ssomar.score.commands.runnable.player.commands.Damage;
import com.ssomar.score.commands.runnable.player.commands.FlyOff;
import com.ssomar.score.commands.runnable.player.commands.FlyOn;
import com.ssomar.score.commands.runnable.player.commands.FrontDash;
import com.ssomar.score.commands.runnable.player.commands.Jump;
import com.ssomar.score.commands.runnable.player.commands.Launch;
import com.ssomar.score.commands.runnable.player.commands.LaunchEntity;
import com.ssomar.score.commands.runnable.player.commands.MobAround;
import com.ssomar.score.commands.runnable.player.commands.ParticleCommand;
import com.ssomar.score.commands.runnable.player.commands.RegainFood;
import com.ssomar.score.commands.runnable.player.commands.RegainHealth;
import com.ssomar.score.commands.runnable.player.commands.RemoveBurn;
import com.ssomar.score.commands.runnable.player.commands.ReplaceBlock;
import com.ssomar.score.commands.runnable.player.commands.SendBlankMessage;
import com.ssomar.score.commands.runnable.player.commands.SendMessage;
import com.ssomar.score.commands.runnable.player.commands.SetBlock;
import com.ssomar.score.commands.runnable.player.commands.SetHealth;
import com.ssomar.score.commands.runnable.player.commands.SpawnEntityOnCursor;
import com.ssomar.score.commands.runnable.player.commands.StrikeLightning;
import com.ssomar.score.commands.runnable.player.commands.Sudo;
import com.ssomar.score.commands.runnable.player.commands.SudoOp;
import com.ssomar.score.commands.runnable.player.commands.TeleportOnCursor;
import com.ssomar.score.commands.runnable.player.commands.WorldTeleport;
import com.ssomar.score.utils.StringConverter;

public enum PlayerCommand {

	SUDOOP ("SUDOOP"),
	SUDO ("SUDO"),
	AROUND ("AROUND"),
	MOB_AROUND ("MOB_AROUND"),
	SENDMESSAGE ("SENDMESSAGE"),
	SENDBLANKMESSAGE ("SENDBLANKMESSAGE"),
	FLY_ON ("FLY ON"),
	FLY_OFF ("FLY OFF"),
	REGAIN_HEALTH ("REGAIN HEALTH"),
	REGAIN_FOOD ("REGAIN FOOD"),
	SETBLOCK ("SETBLOCK"),
	REPLACEBLOCK ("REPLACEBLOCK"),
	PARTICLE ("PARTICLE"),
	ACTIONBAR ("ACTIONBAR"),
	CUSTOMDASH1 ("CUSTOMDASH1"),
	FRONTDASH ("FRONTDASH"),
	BACKDASH ("BACKDASH"),
	TELEPORTONCURSOR ("TELEPORTONCURSOR"),
	WORLDTELEPORT ("WORLDTELEPORT"),
	SPAWNENTITYONCURSOR ("SPAWNENTITYONCURSOR"),
	DAMAGE ("DAMAGE"),
	LAUNCHENTITY ("LAUNCHENTITY"),
	LAUNCH ("LAUNCH"),
	BURN ("BURN"),
	JUMP ("JUMP"),
	REMOVEBURN ("REMOVEBURN"),
	SETHEALTH ("SETHEALTH"),
	STRIKELIGHTNING ("STRIKELIGHTNING");

	private String[] names;

	PlayerCommand(String... names) {
		this.names=names;
	}
	
	public static Map<PlayerCommand, PlayerCommandTemplate> getReferences(){
		Map<PlayerCommand, PlayerCommandTemplate> references = new HashMap<>();
		references.put(SUDOOP, new SudoOp());
		references.put(SUDO, new Sudo());
		references.put(AROUND, new Around());
		references.put(MOB_AROUND, new MobAround());
		references.put(SENDMESSAGE, new SendMessage());
		references.put(SENDBLANKMESSAGE, new SendBlankMessage());
		references.put(FLY_ON, new FlyOn());
		references.put(FLY_OFF, new FlyOff());
		references.put(SETBLOCK, new SetBlock());
		references.put(REPLACEBLOCK, new ReplaceBlock());
		references.put(PARTICLE, new ParticleCommand());
		references.put(ACTIONBAR, new ActionbarCommand());
		references.put(CUSTOMDASH1, new CustomDash1());
		references.put(FRONTDASH, new FrontDash());
		references.put(BACKDASH, new BackDash());
		references.put(TELEPORTONCURSOR, new TeleportOnCursor());
		references.put(WORLDTELEPORT, new WorldTeleport());
		references.put(SPAWNENTITYONCURSOR, new SpawnEntityOnCursor());
		references.put(DAMAGE, new Damage());
		references.put(LAUNCHENTITY, new LaunchEntity());
		references.put(LAUNCH, new Launch());
		references.put(BURN, new Burn());
		references.put(JUMP, new Jump());
		references.put(REMOVEBURN, new RemoveBurn());
		references.put(SETHEALTH, new SetHealth());
		references.put(STRIKELIGHTNING, new StrikeLightning());
		references.put(REGAIN_HEALTH, new RegainHealth());
		references.put(REGAIN_FOOD, new RegainFood());
		
		return references;
	}

	public static String verifArgs(PlayerCommand pC, List<String> args) {

		/* ""> No error */
		String error="";

		error = getReferences().get(pC).verify(args);
		
		return error;
	}

	public static boolean isValidPlayerCommads(String entry) {
		for(PlayerCommand playerCommands : values()) {
			for(String name: playerCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}


	public static PlayerCommand getPlayerCommand(String entry) {
		for(PlayerCommand playerCommands : values()) {
			for(String name: playerCommands.getNames()) {
				if(entry.toUpperCase().startsWith(name.toUpperCase())) {
					return playerCommands;
				}
			}
		}
		return null;
	}

	public static List<String> getPCArgs(String entry) {
		List<String> args = new ArrayList<>();
		boolean first= true;
		boolean second= false;
		if(entry.toUpperCase().startsWith("ACTIONBAR ON")
				|| entry.toUpperCase().startsWith("FLY ON")
				|| entry.toUpperCase().startsWith("FLY OFF")
				|| entry.toUpperCase().startsWith("REGAIN HEALTH")
				|| entry.toUpperCase().startsWith("REGAIN FOOD")) second=true;
		for(String s : entry.split(" ")) {
			if(first) {
				first=false;
				continue;
			}
			if(second) {
				second=false;
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
	
	public static List<String> getCommands(List<String> commands, List<String> errorList, String id) {

		List<String> result = new ArrayList<>();

		for (int i = 0; i < commands.size(); i++) {

			String command = StringConverter.coloredString(commands.get(i));

			if (PlayerCommand.isValidPlayerCommads(commands.get(i))) {
				PlayerCommand bc = PlayerCommand.getPlayerCommand(command);
				List<String> args = PlayerCommand.getPCArgs(command);

				String error = "";
				if (!(error = PlayerCommand.verifArgs(bc, args)).isEmpty()) {
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
