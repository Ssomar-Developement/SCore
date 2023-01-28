package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.executableblocks.executableblocks.manager.ExecutableBlocksManager;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlocksPlacedManagerInterface;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.ItemsAdderAPI;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ChangeBlockType extends BlockCommand {

    private static final Boolean DEBUG = false;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {

        String mat = args.get(0).toUpperCase();
        UUID uuid = null;
        if (p != null) uuid = p.getUniqueId();
        if(uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
        if (Material.matchMaterial(mat) != null) {
           block.setType(Material.valueOf(mat));
        } else if(mat.contains("ITEMSADDER:") && SCore.hasItemsAdder){
            //Check if itemsadder id exists
            String id = mat.replace("ITEMSADDER:", "").trim();

            // Check if the block is an EB because it requires a specific replacement
            boolean EBreplacement = false;
            if(SCore.hasExecutableBlocks){
                Optional<ExecutableBlockPlacedInterface> eBPO = ExecutableBlockPlacedManager.getInstance().getExecutableBlockPlaced(block);
                if(eBPO.isPresent()){
                    ExecutableBlockPlaced eBP = (ExecutableBlockPlaced) eBPO.get();
                    // ebp to eb object
                    // eb object get config clone
                    // update the itemsadderfeature
                    // force update the config of the eb object
                    // place
                    EBreplacement = true;
                }
            }
            if(!EBreplacement){
                ItemsAdderAPI.placeItemAdder(block.getLocation(), id);
            }

        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkMaterial(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CHANGE_BLOCK_TYPE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CHANGE_BLOCK_TYPE {material}";
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
