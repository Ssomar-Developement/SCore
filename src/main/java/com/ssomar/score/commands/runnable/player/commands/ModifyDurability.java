package com.ssomar.score.commands.runnable.player.commands;

import com.iridium.iridiumskyblock.dependencies.ormlite.stmt.query.In;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/* MODIFYDURABILITY {number} {slot} */
public class ModifyDurability extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		int slot = -1;
		int modification = -1;
		if(args.size() >= 1) {
			try {
				modification = Integer.valueOf(args.get(0));
			} catch (NumberFormatException e) {}
		}
		if(args.size() >= 2) {
			try {
				slot = Integer.valueOf(args.get(1));
			} catch (NumberFormatException e) {}
		}
		PlayerInventory pInv = receiver.getInventory();

		if(slot == -1) slot = pInv.getHeldItemSlot();

		ItemStack item = pInv.getItem(slot);
		if(item != null && item.hasItemMeta() && item.getItemMeta() instanceof Damageable){
			Damageable meta = (Damageable) item.getItemMeta();
			meta.setDamage(meta.getDamage()-modification);
			if(meta.getDamage() >= item.getType().getMaxDurability()){
				item.setAmount(item.getAmount()-1);
				return;
			}
			item.setItemMeta(meta);
		}

	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String modifyDurability = "MODIFYDURABILITY {number} {slot}";
		if(args.size() > 2) error = tooManyArgs + modifyDurability;
		else {
			if(args.size() >= 1 && !args.get(0).contains("%")) {
				try {
					Integer.valueOf(args.get(0));
				} catch (NumberFormatException e) {
					error = invalidTime + args.get(0) + " for command: " + modifyDurability;
				}
			}
			if(args.size() >= 2 && !args.get(1).contains("%")) {
				try {
					Integer.valueOf(args.get(1));
				} catch (NumberFormatException e) {
					error = invalidTime + args.get(1) + " for command: " + modifyDurability;
				}
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("MODIFYDURABILITY");
		return names;
	}

	@Override
	public String getTemplate() {
		return "MODIFYDURABILITY {number} {slot}";
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
