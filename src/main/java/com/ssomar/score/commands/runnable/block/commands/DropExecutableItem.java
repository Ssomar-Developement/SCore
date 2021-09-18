package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.executableitems.api.ExecutableItemsAPI;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.jetbrains.annotations.NotNull;

/* DROPEXECUTABLEITEM {id} [quantity} */
public class DropExecutableItem extends BlockCommand{

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
			try {
				if(SCore.hasExecutableItems && ExecutableItemsAPI.isValidID(args.get(0))) {
					int amount = Integer.parseInt(args.get(1));
					if(amount>0) { 
						Item item = ExecutableItemsAPI.getExecutableItemConfig(ExecutableItemsAPI.getExecutableItem(args.get(0)));
						block.getWorld().dropItem(block.getLocation(), item.formItem(amount, p, item.getUse()));
					}
				}
			}catch(Exception ignored) {}
		}

		@Override
		public String verify(List<String> args) {
			String error = "";

			String dropei= "DROPEXECUTABLEITEM {id} {quantity}";
			if(args.size()<2) error = notEnoughArgs+dropei;
			else if(args.size()==2) {
				if(!SCore.hasExecutableItems || !ExecutableItemsAPI.isValidID(args.get(0))) error = invalidExecutableItems+args.get(0)+" for command: "+dropei;
				else {
					try {
						Integer.valueOf(args.get(1));
					}catch(NumberFormatException e){
						error = invalidQuantity+args.get(1)+" for command: "+dropei;
					}
				}
			}
			else error= tooManyArgs+dropei;

			return error;
		}

		@Override
		public List<String> getNames() {
			List<String> names = new ArrayList<>();
			names.add("DROPEXECUTABLEITEM");
			return names;
		}

		@Override
		public String getTemplate() {
			return "DROPEXECUTABLEITEM {id} {quantity}";
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
