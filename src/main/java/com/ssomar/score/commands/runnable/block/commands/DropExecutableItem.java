package com.ssomar.score.commands.runnable.block.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.executableitems.api.ExecutableItemsAPI;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;

/* DROPEXECUTABLEITEM {id} [quantity} */
public class DropExecutableItem extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
			try {
				if(SCore.hasExecutableItems && ExecutableItemsAPI.isValidID(args.get(0))) {
					int amount = Integer.valueOf(args.get(1));
					if(amount>0) { 
						Item item = ExecutableItemsAPI.getExecutableItemConfig(ExecutableItemsAPI.getExecutableItem(args.get(0)));
						block.getWorld().dropItem(block.getLocation(), item.formItem(amount, p, item.getUse()));
					}
				}
			}catch(Exception e) {}	
		}

		@Override
		public String verify(List<String> args) {
			String error = "";

			String dropei= "DROPEXECUTABLEITEM {id} [quantity}";
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

}
