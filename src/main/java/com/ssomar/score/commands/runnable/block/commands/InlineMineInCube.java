package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.features.custom.detailedblocks.DetailedBlocks;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.bukkit.block.BlockFace.*;

/* MINEINCUBE {radius} {ActiveDrop true or false} */
public class InlineMineInCube extends BlockCommand {

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

        //SsomarDev.testMsg("InlineMineInCube is running !", true);
        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                /* Cancel a Loop of blockBreakEvent that MineInCbe can create */
                if (aInfo.isEventFromCustomBreakCommand()) return;

                try {
                    int radius = Integer.parseInt(args.get(0));

                    int depth = 1;
                    if (args.size() >= 2) depth = Integer.parseInt(args.get(1));
                    depth = depth - 1;

                    boolean drop = true;
                    if (args.size() >= 3) drop = Boolean.parseBoolean(args.get(2));

                    boolean createBBEvent = true;
                    if (args.size() >= 4) createBBEvent = Boolean.parseBoolean(args.get(3));

                    BlockFace directionWritten = null;
                    if(args.size() >= 5) {
                        switch (args.get(4).toLowerCase()) {
                            case "north":
                            case "n":
                            case "-z":
                                directionWritten = BlockFace.NORTH;
                                break;
                            case "south":
                            case "s":
                            case "+z":
                                directionWritten = BlockFace.SOUTH;
                                break;
                            case "east":
                            case "e":
                            case "+x":
                                directionWritten = BlockFace.EAST;
                                break;
                            case "west":
                            case "w":
                            case "-x":
                                directionWritten = BlockFace.WEST;
                                break;
                            case "up":
                                directionWritten = BlockFace.UP;
                                break;
                            case "down":
                                directionWritten = BlockFace.DOWN;
                                break;
                        }
                    }
                   // SsomarDev.testMsg("directionWritten: " + directionWritten, true);

                    List<Material> blackList = new ArrayList<>();
                    blackList.add(Material.BEDROCK);
                    blackList.add(Material.AIR);

                    int initX = -radius;
                    int initZ = -radius;
                    int initY = -radius;
                    int maxX = radius;
                    int maxZ = radius;
                    int maxY = radius;

                    BoundingBox bb = new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX()+1, block.getY()+1, block.getZ()+1);
                    RayTraceResult rayTraceResult = bb.rayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection(), 10);
                    //SsomarDev.testMsg("rayTraceResult: " + rayTraceResult.getHitBlockFace(), true);

                    BlockFace face = null;
                    if(directionWritten == null){
                        if(rayTraceResult != null &&  rayTraceResult.getHitBlockFace() != null)  face = rayTraceResult.getHitBlockFace().getOppositeFace();
                        else face = p.getFacing().getOppositeFace();
                    }else{
                        face = directionWritten;
                    }
                    //SsomarDev.testMsg("face: " + face, true);

                    int multiplier = 1;

                    if (face == NORTH) {
                        initZ = 0;
                        maxZ = depth;
                        multiplier = -1;
                    } else if (face == SOUTH) {
                        initZ = 0;
                        maxZ = depth;
                        multiplier = 1;
                    } else if (face == WEST) {
                        initX = 0;
                        maxX = depth;
                        multiplier = -1;
                    } else if (face == EAST) {
                        initX = 0;
                        maxX = depth;
                        multiplier = 1;
                    } else if (face == UP) {
                        initY = 0;
                        maxY = depth;
                        multiplier = 1;
                    } else if (face == DOWN) {
                        initY = 0;
                        maxY = depth;
                        multiplier = -1;
                    }

                    //SsomarDev.testMsg("initX: " + initX + " initY: " + initY + " initZ: " + initZ, true);

                    if (radius < 10) {
                        for (int y = initY; y < maxY + 1; y++) {
                            for (int x = initX; x < maxX + 1; x++) {
                                for (int z = initZ; z < maxZ + 1; z++) {

                                   // SsomarDev.testMsg("x: " + x + " y: " + y + " z: " + z, true);

                                    Block toBreak = block.getWorld().getBlockAt(block.getX() + (x*multiplier) , block.getY() + (y*multiplier),  block.getZ() + (z*multiplier) );

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
        SCore.schedulerHook.runTask(runnable3, 1);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("INLINE_MINEINCUBE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "INLINE_MINEINCUBE {cube_radius} {depth} {ActiveDrop true or false} {create blockBreakEvent true or false} {optional direction}";
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
