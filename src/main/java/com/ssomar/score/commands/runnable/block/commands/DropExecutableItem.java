package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssomar.executableitems.executableitems.ExecutableItem;
import com.ssomar.executableitems.executableitems.ExecutableItemsManager;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.jetbrains.annotations.NotNull;

/* DROPEXECUTABLEITEM {id} [quantity} */
public class DropExecutableItem extends BlockCommand{

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
			try {
				if(SCore.hasExecutableItems && ExecutableItemsAPI.getExecutableItemsManager().isValidID(args.get(0))) {
					int amount = Integer.parseInt(args.get(1));
					if(amount > 0) {
						Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(args.get(0));
						if(eiOpt.isPresent()) {
							ExecutableItemInterface ei = eiOpt.get();
							block.getWorld().dropItem(block.getLocation(), ei.buildItem(amount, Optional.ofNullable(p)));
						}
					}
				}
				else{
					SCore.plugin.getLogger().severe("Error when trying to execute the custom command DROPEXECUTABLEITEM but no EI found with the ID: "+args.get(0));
				}
			}catch(Exception ignored) {}
		}

		@Override
		public String verify(List<String> args) {
			String error = "";

			String dropei = "DROPEXECUTABLEITEM {id} {quantity}";
			if(args.size() < 2) error = notEnoughArgs+dropei;
			else if(args.size() != 2) error = tooManyArgs+dropei;

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
			return null;
		}

		@Override
		public ChatColor getExtraColor() {
			return null;
		}

}
