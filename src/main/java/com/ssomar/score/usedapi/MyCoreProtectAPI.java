package com.ssomar.score.usedapi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import com.ssomar.score.SCore;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class MyCoreProtectAPI {


	public static boolean isNaturalBlock(Block block) {
		if(SCore.hasCoreProtect) {
			Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
			
			 // Check that CoreProtect is loaded
	        if (!(plugin instanceof CoreProtect)) {
	        	return false;
	        }

			CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
			if (!CoreProtect.isEnabled()) {
				return false;
			}

			// Check that a compatible version of the API is loaded
			if (CoreProtect.APIVersion() < 7) {
				return false;
			}
			
			List<String[]> list = CoreProtect.blockLookup(block, 2592000);

			//				SCore.plugin.getLogger().info("DEBUG for the user LUCKYWARRIOR pls send me the following message");
							for(String[] tab : list) {
								SCore.plugin.getLogger().info("==========");
								for(String s: tab) {
									SCore.plugin.getLogger().info(s);
								}
								SCore.plugin.getLogger().info("==========");
						}
			return list.size() == 0;
		}
		
		return false;
	}

}
