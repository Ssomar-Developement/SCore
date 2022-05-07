package com.ssomar.score.utils.safebreak;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.events.BlockBreakEventExtension;
import com.ssomar.score.usedapi.GriefPreventionAPI;
import com.ssomar.score.usedapi.IridiumSkyblockTool;
import com.ssomar.score.usedapi.LandsIntegrationAPI;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class SafeBreak {

    private static final boolean DEBUG = false;

    public static void breakBlockWithEvent(final Block block, @Nullable final UUID playerUUID, int slot, final boolean drop, boolean generateBreakEvent, boolean verifSafeBreak) {

        SsomarDev.testMsg("DEBUG SAFE BREAK 1", DEBUG);
        if(playerUUID == null){
            if(breakEB(block, drop)) return;
            block.breakNaturally();
            return;
        }
       SsomarDev.testMsg("DEBUG SAFE BREAK 1.5", DEBUG);

        if(verifSafeBreak && !verifSafeBreak(playerUUID, block)) return;

        Player player = Bukkit.getServer().getPlayer(playerUUID);
        SsomarDev.testMsg("DEBUG SAFE BREAK 2", DEBUG);
        if (player != null) {
            SsomarDev.testMsg("DEBUG SAFE BREAK 3", DEBUG);
            boolean canceled = false;
            if(generateBreakEvent) {
                SsomarDev.testMsg("DEBUG SAFE BREAK 4");
                BlockBreakEvent bbE = new BlockBreakEventExtension(block, player, true);
                bbE.setCancelled(false);
                /* */
                Bukkit.getPluginManager().callEvent(bbE);
                canceled = bbE.isCancelled();
            }

            if (!canceled) {
                if(breakEB(block, drop)) return;

                if (drop){
                    if(slot != 40) block.breakNaturally(player.getInventory().getItemInMainHand());
                    else block.breakNaturally(player.getInventory().getItemInOffHand());
                }
                else block.setType(Material.AIR);
            }
        }
        else {
            if(breakEB(block, drop)) return;

            //SsomarDev.testMsg("DEBUG SAFE BREAK 6");
            if (drop) block.breakNaturally();
            else block.setType(Material.AIR);
        }

    }

    public static boolean breakEB(Block block, boolean drop){
        SsomarDev.testMsg("DEBUG SAFE BREAK 10", DEBUG);
        if(SCore.hasExecutableBlocks){
            SsomarDev.testMsg("DEBUG SAFE BREAK has EB", DEBUG);
            Optional<ExecutableBlockPlacedInterface> eBPOpt = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(block);
            if(eBPOpt.isPresent()){
                SsomarDev.testMsg("DEBUG SAFE BREAK has EB 2", DEBUG);
                eBPOpt.get().breakBlock(null, drop);
                return true;
            }
        }
        return false;
    }

    public static boolean verifSafeBreak(@NotNull final UUID playerUUID, @NotNull Block block){

        //SsomarDev.testMsg("DEBUG SAFE BREAK CDT 1");

        if(SCore.hasGriefPrevention) if(!GriefPreventionAPI.playerCanBreakClaimBlock(playerUUID, block.getLocation())) return false;

       // SsomarDev.testMsg("DEBUG SAFE BREAK CDT 2");

        if(SCore.hasIridiumSkyblock) if(!IridiumSkyblockTool.playerCanBreakIslandBlock(playerUUID, block.getLocation())) return false;

        //SsomarDev.testMsg("DEBUG SAFE BREAK CDT 3");

        if(SCore.hasLands) if(!new LandsIntegrationAPI(SCore.plugin).playerCanBreakClaimBlock(playerUUID, block.getLocation())) return false;

        //SsomarDev.testMsg("DEBUG SAFE BREAK CDT 4");

        if(SCore.hasWorldGuard) if(!WorldGuardAPI.playerCanBreakInRegion(playerUUID, block.getLocation())) return false;

        return true;
    }

}
