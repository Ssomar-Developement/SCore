package com.ssomar.score.usedapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.griefdefender.api.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.ssomar.score.SCore;

public class MyCoreProtectAPI {


	public static boolean isNaturalBlock(Block block) {
		if(SCore.hasCoreProtect) {
			Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
			



			//				SCore.plugin.getLogger().info("DEBUG for the user LUCKYWARRIOR pls send me the following message");
							/* for(String[] tab : list) {
								SCore.plugin.getLogger().info("==========");
								for(String s: tab) {
									SCore.plugin.getLogger().info(s);
								}
								SCore.plugin.getLogger().info("==========");
						}*/

		}
		
		return false;
	}

	public void addPickup(Location location, ItemStack itemStack, Player player) {
		if(SCore.hasCoreProtect) {
			Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");




			if (itemStack == null)
				return;

		}

	}


}
