package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableblocks.api.ExecutableBlocksAPI;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlocksPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Explode extends BlockCommand {

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {

        if (SCore.hasWorldGuard && p != null) {
            if (new WorldGuardAPI().canBuild(p, new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()))) {
                this.validBreak(block);
            }
        } else {
            this.validBreak(block);
        }
    }

    public void validBreak(Block block) {
        Location bLoc = block.getLocation();
        bLoc.add(0.5, 0.5, 0.5);

        if (SCore.hasExecutableBlocks) {

            Optional<ExecutableBlockPlaced> eBPOpt = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(bLoc);

            if (eBPOpt.isPresent()) {
                ExecutableBlockPlaced eBP = eBPOpt.get();
                ExecutableBlocksPlacedManager.getInstance().removeExecutableBlockPlaced(eBP);
            }
        }

        block.breakNaturally();
        EntityType type = SCore.is1v20v5Plus() ? EntityType.TNT : EntityType.valueOf("PRIMED_TNT");
        block.getWorld().spawnEntity(block.getLocation(), type);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("EXPLODE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "EXPLODE";
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
