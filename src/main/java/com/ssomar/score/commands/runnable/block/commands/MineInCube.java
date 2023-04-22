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
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.bukkit.block.BlockFace.*;

/* MINEINCUBE {radius} {ActiveDrop true or false} */
public class MineInCube extends BlockCommand {

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
                /* Cancel a Loop of blockBreakEvent that MineInCbe can create */
                if (aInfo.isEventCallByMineInCube()) return;

                try {
                    int radius = Integer.parseInt(args.get(0));
                    boolean drop = true;
                    if (args.size() >= 2) drop = Boolean.parseBoolean(args.get(1));

                    boolean createBBEvent = true;
                    if (args.size() >= 3) createBBEvent = Boolean.parseBoolean(args.get(2));

                    boolean offset = false;
                    if(args.size() >= 4 ) offset = Boolean.parseBoolean(args.get(3));

                    List<Material> blackList = new ArrayList<>();
                    blackList.add(Material.BEDROCK);
                    blackList.add(Material.AIR);

                    Integer offsetx = 0;
                    Integer offsety = 0;
                    Integer offsetz = 0;

                    if(offset) {
                        Set<Material> trasnparent = new HashSet<>();
                        trasnparent.add(Material.WATER);
                        trasnparent.add(Material.AIR);

                        List<Block> lastBlocks = p.getLastTwoTargetBlocks(trasnparent, 5);
                        BlockFace face = lastBlocks.get(1).getFace(lastBlocks.get(0)).getOppositeFace();

                        if (face == NORTH) {
                            offsetz = (-1 * radius);
                        } else if (face == SOUTH) {
                            offsetz = (1 * radius);
                        } else if (face == WEST) {
                            offsetx = (-1 * radius);
                        } else if (face == EAST) {
                            offsetx = (1 * radius);
                        } else if (face == UP) {
                            offsety = (1 * radius);
                        } else if (face == DOWN) {
                            offsety = (-1 * radius);
                        }
                    }

                    boolean isv18plus = SCore.is1v18Plus();
                    if (radius < 10) {
                        for (int y = -radius; y < radius + 1; y++) {
                            for (int x = -radius; x < radius + 1; x++) {
                                for (int z = -radius; z < radius + 1; z++) {

                                    if(isv18plus) {
                                        if ((block.getY() + y + offsety) < -64) continue;
                                    }else{
                                        if ((block.getY() + y + offsety) < 0) continue;
                                    }

                                    Location toBreakLoc = new Location(block.getWorld(), block.getX() + x, block.getY() + y, block.getZ() + z);
                                    Block toBreak = block.getWorld().getBlockAt(block.getX() + x+offsetx, block.getY() + y+offsety, block.getZ() + z+offsetz);

                                    DetailedBlocks whiteList;
                                    if ((whiteList = aInfo.getDetailedBlocks()) != null) {
                                        /* I have set playerOpt on empty, otherwise if it will spam the error message if too many blocks are broken with a not valid type */
                                        if (!whiteList.isValid(toBreak, Optional.empty(), null, new StringPlaceholder()))
                                            continue;
                                    }

                                    if (!blackList.contains(toBreak.getType())) {

                                        UUID pUUID = null;
                                        if (p != null) pUUID = p.getUniqueId();
                                        SafeBreak.breakBlockWithEvent(toBreak, pUUID, aInfo.getSlot(), drop, createBBEvent, true);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        };
        runnable3.runTaskLater(SCore.plugin, 1);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MINEINCUBE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MINEINCUBE {radius} {ActiveDrop true or false} {create blockBreakEvent true or false} {offset default false}";
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
