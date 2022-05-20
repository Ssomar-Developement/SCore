package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/* BURN {timeinsecs} */
public class DisableGlideActivation extends PlayerCommand{

	private static DisableGlideActivation instance;
	@Getter
	private Map<UUID, Integer> activeDisabled;

	public DisableGlideActivation() {
		activeDisabled = new HashMap<>();
	}

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			int time = Integer.valueOf(args.get(0));

			if(activeDisabled.containsKey(receiver.getUniqueId())){
				activeDisabled.put(receiver.getUniqueId(), activeDisabled.get(receiver.getUniqueId())+1);
			}
			else activeDisabled.put(receiver.getUniqueId(), 1);

			BukkitRunnable runnable3 = new BukkitRunnable() {
				@Override
				public void run() {
					//SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
					if(activeDisabled.containsKey(receiver.getUniqueId())){
						if(activeDisabled.get(receiver.getUniqueId()) > 1){
							activeDisabled.put(receiver.getUniqueId(), activeDisabled.get(receiver.getUniqueId())-1);
						}
						else activeDisabled.remove(receiver.getUniqueId());
					}
				}
			};
			runnable3.runTaskLater(SCore.plugin, time*20);

		}catch(Exception ignored) {}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String disableFA = "DISABLE_GLIDE_ACTIVATION {timeinsecs}";
		if(args.size() > 1) error = tooManyArgs+disableFA;
		else if(args.size() < 1) error = notEnoughArgs+disableFA;
		else if(!args.get(0).contains("%")){
			try {
				Integer.valueOf(args.get(0));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(0) + " for command: " + disableFA;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DISABLE_GLIDE_ACTIVATION");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DISABLE_GLIDE_ACTIVATION {timeinsecs}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

	public static DisableGlideActivation getInstance() {
		if(instance == null) instance = new DisableGlideActivation();
		return instance;
	}
}
