package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static com.ssomar.score.commands.runnable.player.commands.Around.aroundExecution;

/* AROUND {distance} {true or false} {Your commands here} */
public class Around extends EntityCommand {

	@Override
	public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
		aroundExecution(receiver, args, aInfo);
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String around = "AROUND {distance} {Your commands here}";
		if(args.size() < 2) error = notEnoughArgs+around;
		else if(args.size() > 2) {
			try {
				Double.valueOf(args.get(0));

				if(Boolean.valueOf(args.get(1)) == null) error = invalidBoolean+args.get(1)+" for command: "+around;

			}catch(NumberFormatException e){
				error = invalidDistance+args.get(0)+" for command: "+around;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("AROUND");
		return names;
	}

	@Override
	public String getTemplate() {
		return "AROUND {distance} {Your commands here}";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.LIGHT_PURPLE;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.DARK_PURPLE;
	}

}
