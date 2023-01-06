package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.config.ExecutableBlockInterface;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DropExecutableBlock extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        if (SCore.hasExecutableBlocks && ExecutableBlocksAPI.getExecutableBlocksManager().isValidID(args.get(0))) {
            int amount = Double.valueOf(args.get(1)).intValue();
            if (amount > 0) {
                Optional<ExecutableBlockInterface> eiOpt = ExecutableBlocksAPI.getExecutableBlocksManager().getExecutableBlock(args.get(0));
                if (eiOpt.isPresent()) {
                    ExecutableBlockInterface ei = eiOpt.get();
                    block.getWorld().dropItem(block.getLocation(), ei.buildItem(amount, Optional.ofNullable(p)));
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return com.ssomar.score.commands.runnable.entity.commands.DropExecutableBlock.staticVerif(args, isFinalVerification, getTemplate());
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DROPEXECUTABLEBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DROPEXECUTABLEBLOCK {id} {quantity}";
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
