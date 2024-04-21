package com.ssomar.score.usedapi;

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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class WorldGuardAPI {

    public static boolean playerCanBreakInRegion(@NotNull UUID pUUID, @NotNull org.bukkit.Location location) {

        if (SCore.is1v12Less()) return true;

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapOfflinePlayer(Bukkit.getServer().getOfflinePlayer(pUUID));
        Location loc = new Location(BukkitAdapter.adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        StateFlag[] conditions = new StateFlag[1];
        conditions[0] = Flags.BLOCK_BREAK;
        //Bukkit.broadcastMessage(query.testBuild(loc, localPlayer)+"");
        return query.testBuild(loc, localPlayer, conditions);
    }

    public static boolean playerCanPlaceInRegion(@NotNull UUID pUUID, @NotNull org.bukkit.Location location) {

        if (SCore.is1v12Less()) return true;

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapOfflinePlayer(Bukkit.getServer().getOfflinePlayer(pUUID));
        Location loc = new Location(BukkitAdapter.adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        StateFlag[] conditions = new StateFlag[1];
        conditions[0] = Flags.BLOCK_PLACE;
        //Bukkit.broadcastMessage(query.testBuild(loc, localPlayer)+"");
        return query.testBuild(loc, localPlayer, conditions);
    }

    public static boolean isInPvpZone(Player p, org.bukkit.Location location) {

        if (SCore.is1v12Less()) return true;

        /* Antoher method that doesnt count the default value, and the default value allows pvp , so it's bad to use it */

        //LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
        Location loc = new Location(BukkitAdapter.adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
//		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
//		RegionQuery query = container.createQuery();
//
//		StateFlag [] conditions = new StateFlag[1];
//		conditions[0] = Flags.PVP;
//		//Bukkit.broadcastMessage(query.testBuild(loc, localPlayer)+"");
//
//		boolean isPVP = query.testState(loc, localPlayer, conditions);


        boolean isPVP = true;

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(loc);

        int allowMaxPriority = -2147483648;

        for (ProtectedRegion region : set) {
            if (region != null) {
                StateFlag.State state = region.getFlag(Flags.PVP);
                if (state != null && state.equals(StateFlag.State.ALLOW)) {
                    //SsomarDev.testMsg("arene: "+region.getId()+ " >> "+region.getPriority());
                    if (allowMaxPriority < region.getPriority()) allowMaxPriority = region.getPriority();
                }
            }
        }

        for (ProtectedRegion region : set) {
            if (region != null) {
                StateFlag.State state = region.getFlag(Flags.PVP);
                if (state != null && state.equals(StateFlag.State.DENY) && region.getPriority() > allowMaxPriority) {
                    //SsomarDev.testMsg("arene: "+region.getId()+ " >> "+region.getPriority()+" || "+allowMaxPriority);
                    isPVP = false;
                    break;
                }
            }
        }

        //SsomarDev.testMsg("isinPvp ? : "+ isPVP);

        return isPVP;
    }

    public boolean canBuild(@NotNull Player p, org.bukkit.Location location) {

        if (SCore.is1v12Less()) return true;

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
        Location loc = new Location(BukkitAdapter.adapt(location.getWorld()), location.getX(), location.getY(), location.getZ());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        StateFlag[] conditions = new StateFlag[1];
        conditions[0] = Flags.BLOCK_BREAK;
        //Bukkit.broadcastMessage(query.testBuild(loc, localPlayer)+"");
        return query.testBuild(loc, localPlayer, conditions);

    }

    public boolean isInRegion(Player p, String name) {

        Location loc = BukkitAdapter.adapt(p.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(p.getWorld()));

        if (regions == null) return false;

        ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

        for (ProtectedRegion region : set) {
            if (region.getId().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInRegionWithKeepInventory(Player p) {

        if(!Dependency.WORLD_GUARD_EXTRA_FLAGS.isEnabled()) return false;

        Location loc = BukkitAdapter.adapt(p.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(p.getWorld()));

        if (regions == null) return false;

        ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

        for (ProtectedRegion region : set) {
            boolean keepInv = region.getFlag(net.goldtreeservers.worldguardextraflags.flags.Flags.KEEP_INVENTORY);
            if (keepInv) return true;
        }
        return false;
    }

    public boolean isInRegion(Player p, List<String> names) {

        Location loc = BukkitAdapter.adapt(p.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(p.getWorld()));

        if (regions == null) return false;

        ApplicableRegionSet set = regions.getApplicableRegions(loc.toVector().toBlockPoint());

        for (String name : names) {
            for (ProtectedRegion region : set) {
                if (region.getId().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
