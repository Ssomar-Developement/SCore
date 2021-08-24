package com.ssomar.score.usedapi;

import java.util.List;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.ssomar.score.SCore;

public class WorldGuardAPI {

	public boolean canBuild(Player p, org.bukkit.Location location) {

		if(SCore.is1v12()) return true;
		
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
		Location loc = new Location(BukkitAdapter.adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();

		StateFlag [] conditions = new StateFlag[1];
		conditions[0] = Flags.BLOCK_BREAK;
		//Bukkit.broadcastMessage(query.testBuild(loc, localPlayer)+"");
		return query.testBuild(loc, localPlayer, conditions);

	}
	
	public boolean isInPvpZone(Player p, org.bukkit.Location location) {

		if(SCore.is1v12()) return true;
		
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
		Location loc = new Location(BukkitAdapter.adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();

		StateFlag [] conditions = new StateFlag[1];
		conditions[0] = Flags.PVP;
		//Bukkit.broadcastMessage(query.testBuild(loc, localPlayer)+"");
		return query.testState(loc, localPlayer, conditions);
	}

	public boolean isInRegion(Player p, String name) {

		Location loc = BukkitAdapter.adapt(p.getLocation());
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regions = container.get(BukkitAdapter.adapt(p.getWorld()));

		if(regions == null) return false;

		ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

		for (ProtectedRegion region : set) {
			if( region.getId().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}

	public boolean isInRegion(Player p, List<String> names) {

		Location loc = BukkitAdapter.adapt(p.getLocation());
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regions = container.get(BukkitAdapter.adapt(p.getWorld()));

		if(regions==null) return false;

		ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

		for(String name : names) {
			for (ProtectedRegion region : set) {
				if( region.getId().equalsIgnoreCase(name)){
					return true;
				}
			}
		}
		return false;
	}
}
