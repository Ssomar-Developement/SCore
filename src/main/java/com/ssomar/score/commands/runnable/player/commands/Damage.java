package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class Damage extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			double amount = Double.valueOf(args.get(0));
			if(amount > 0 && !receiver.isDead()) {


				/* DELETE THE MESSAGE Message.DAMAGE_COMMAND_KILL */

				//				if(receiver.getHealth() <= Integer.valueOf(amount)) {
				//					StringPlaceholder sp = new StringPlaceholder();
				//					sp.setPlayer(p.getName());
				//					sp.setTarget(receiver.getName());
				//					sp.setItem(aInfo.getName());
				//					String message = MessageMain.getInstance().getMessage(SCore.plugin, Message.DAMAGE_COMMAND_KILL);
				//					if(!StringConverter.decoloredString(message).isEmpty())
				//					Bukkit.getServer().broadcastMessage(StringConverter.coloredString(sp.replacePlaceholder(message)));
				//				}

				if(p != null) {
					amount = amount + 0.00007772;
					EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(p, receiver, DamageCause.ENTITY_ATTACK, amount);

					/* */
					Bukkit.getPluginManager().callEvent(event);
				}
				receiver.damage(amount);

			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "DAMAGE {number}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}
}