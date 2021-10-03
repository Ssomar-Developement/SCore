package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;

public class Damage extends PlayerCommand{

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			double amount;
			String damage = args.get(0);
			/* percentage damage */
			if(damage.contains("%")) {
				String [] decomp = damage.split("\\%");
				damage = decomp[0];
				
				double percentage = damage.equals("100") ? 1 : Double.parseDouble("0."+damage);
				amount = receiver.getMaxHealth() * percentage;
				amount = Double.parseDouble((amount+"").substring(0, 3));
			}
			else amount = Double.parseDouble(damage);
			
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
					boolean doDamage = true;
					if(SCore.hasWorldGuard) doDamage = WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation());
					if(doDamage) {
						p.setMetadata("cancelDamageEvent", new FixedMetadataValue(SCore.plugin, 7772));
						receiver.damage(amount, p);
					}
				}
				else {
					boolean doDamage = true;
					if(SCore.hasWorldGuard) doDamage = WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation());
					if(doDamage) receiver.damage(amount);
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
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
		return "DAMAGE {number}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}
}