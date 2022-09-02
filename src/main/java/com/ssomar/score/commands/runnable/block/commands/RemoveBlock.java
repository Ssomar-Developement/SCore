package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* REMOVEBLOCK */
public class RemoveBlock extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        UUID uuid = null;
        if (p != null) {
            uuid = p.getUniqueId();
        }
        SafeBreak.breakBlockWithEvent(block, uuid, aInfo.getSlot(), false, false, true && !aInfo.isNoPlayerTriggeredTheAction());
    }

    public void validBreak(Block block) {
        Location bLoc = block.getLocation();
        bLoc.add(0.5, 0.5, 0.5);

        if (SCore.hasExecutableBlocks) {
            Optional<ExecutableBlockPlacedInterface> eBPOpt = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(bLoc);
            if (eBPOpt.isPresent()) {
                ExecutableBlockPlaced eBP = (ExecutableBlockPlaced) eBPOpt.get();
                ExecutableBlockPlacedManager.getInstance().removeExecutableBlockPlaced(eBP);
            }
        }

        block.setType(Material.AIR);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REMOVEBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REMOVEBLOCK";
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
