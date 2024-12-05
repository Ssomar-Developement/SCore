package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlocksPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.ItemsAdderAPI;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChangeBlockType extends BlockCommand {

    private static final Boolean DEBUG = true;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        String mat = args.get(0);
        if (!mat.contains("ITEMSADDER:")) mat = mat.toUpperCase();
        UUID uuid = null;
        if (p != null) uuid = p.getUniqueId();
        if (uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;

        if (SCore.hasExecutableBlocks) {
            Optional<ExecutableBlockPlaced> eBPO = ExecutableBlocksPlacedManager.getInstance().getExecutableBlockPlaced(block);
            if (eBPO.isPresent()) {
                ExecutableBlockPlaced eBP = (ExecutableBlockPlaced) eBPO.get();
                if (Material.matchMaterial(mat) != null) {
                    //SsomarDev.testMsg("DEBUG SAFE PLACE 1 >> "+mat, DEBUG);
                    eBP.updateBlockTypeWithMaterial(Material.matchMaterial(mat));
                } else if (mat.contains("ITEMSADDER:") && SCore.hasItemsAdder) {
                    String id = mat.replace("ITEMSADDER:", "").trim();
                    eBP.updateBlockTypeWithItemsAdder(id);
                }
            }
        } else {

            if (Material.matchMaterial(mat) != null) {
                block.setType(Material.valueOf(mat));
            } else if (mat.contains("ITEMSADDER:") && SCore.hasItemsAdder) {
                //Check if itemsadder id exists
                String id = mat.replace("ITEMSADDER:", "").trim();
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
