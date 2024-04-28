package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VeinBreaker extends BlockCommand {

    /**
     * THIS COMMAND MUST BE DELAYED OF AT LEAST 1 TICK
     * WHY ?
     * TO LET ALL ACTIVATORS OF THE ITEMS BE RUNNED CORRECLTY FIRST
     * BECAUSE THIS COMMAND CAN GENERATE ANOTHER EVENT SO IT CAN CREATE THIS SITUATION
     * X ( activator where this command is )
     * Y ( the new event generates by this command)
     * Y
     * Y
     * X
     * X
     * <p>
     * This situation is bad for the variables edition, example with the same situation and with a increment of the variable by one
     * X 1
     * Y 2
     * Y 3
     * Y 4
     * X 2
     * X 3
     * <p>
     * The delay help us to fix that and let us have:
     * X 1
     * X 2
     * X 3
     * Y 4
     * Y 5
     * Y 6
     **/

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {

                if (aInfo.isEventFromCustomBreakCommand()) return;

                DetailedBlocks whiteList;
                if ((whiteList = aInfo.getDetailedBlocks()) != null) {
                    /* I have set playerOpt on empty, otherwise if it will spam the error message if too many blocks are broken with a not valid type */
                    if (!whiteList.isValid(block, Optional.empty(), null, new StringPlaceholder(), oldMaterial, Optional.empty())) {
                        p.sendMessage(ChatColor.RED + "This block is not allowed to be broken ! : "+block.getType().name());
                        return;
                    }
                }

                int veinSize = 120;
                try {
                    veinSize = Integer.parseInt(args.get(0));
                } catch (Exception ignored) {}

                boolean triggerevent = true;
                if(args.size() >= 3) {
                    if (args.get(2).equalsIgnoreCase("false")) triggerevent = false;
                }

                List<Block> vein;
                UUID pUUID = null;
                if (p != null) pUUID = p.getUniqueId();
                SafeBreak.breakBlockWithEvent(block, pUUID, aInfo.getSlot(), true, triggerevent, true);

                vein = getVein(block, oldMaterial, veinSize);

                for (Block b : vein) {
                    SafeBreak.breakBlockWithEvent(b, pUUID, aInfo.getSlot(), true, triggerevent, true);
                }
            }
        };
        SCore.schedulerHook.runTask(runnable3, 1L);
    }

    public List<Block> getVein(Block block, Material oldMaterial, int veinSize) {
        List<Block> result = new ArrayList<>();
        this.fillVein(result, block, oldMaterial, veinSize);
        return result;
    }

    public void fillVein(List<Block> vein, Block block, Material oldMaterial, int veinSize) {

        Location loc = block.getLocation();
        int radius = 1;
        for (int y = -radius; y < radius + 1; y++) {
            for (int x = -radius; x < radius + 1; x++) {
                for (int z = -radius; z < radius + 1; z++) {

                    if (y == 0 && z == 0 && x == 0) continue;

                    if (vein.size() > veinSize) return;

                    Location newLoc = loc.clone();
                    newLoc.add(x, y, z);
                    Block newBlock;

                    if ((newBlock = newLoc.getBlock()).getType().equals(oldMaterial)) {
                        if (!vein.contains(newBlock)) {
                            vein.add(newBlock);
                            this.fillVein(vein, newBlock, oldMaterial, veinSize);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("VEIN_BREAKER");
        return names;
    }

    @Override
    public String getTemplate() {
        return "VEIN_BREAKER [Max_vein_size] [trigger BREAK event, default true]";
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
