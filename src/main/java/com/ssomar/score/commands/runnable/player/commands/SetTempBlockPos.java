package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.block.commands.settempblock.SetTempBlockManager;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class SetTempBlockPos extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        try {
            double x = Double.valueOf(args.get(0));
            double y = Double.valueOf(args.get(1));
            double z = Double.valueOf(args.get(2));

            Location loc = receiver.getLocation();
            Location blockLoc = new Location(loc.getWorld(), x, y, z);
            Block block = blockLoc.getBlock();
            BlockData data = block.getBlockData().clone();

            String mat = args.get(3).toUpperCase();

            int time = 60;
            if(args.size() >= 5) time = Integer.parseInt(args.get(4));

            boolean bypassProtection = false;
            if (args.size() >= 6) bypassProtection = Boolean.parseBoolean(args.get(5));

            ListDetailedMaterialFeature listDetailedMaterialFeature = new ListDetailedMaterialFeature(true);
            if(args.size() >= 7) {
                List<String> list = new ArrayList<>();
                String listStr = args.get(6);
                if(listStr.contains(",")) list = Arrays.asList(listStr.split(","));
                else list.add(listStr);
                listDetailedMaterialFeature.load(SCore.plugin, list, true);
            }

            UUID uuid = receiver.getUniqueId();
            boolean placed = false;
            SsomarDev.testMsg("DEBUG place 0", true);

            //levelled for lights OK
            if(data instanceof Bisected || data instanceof Orientable || data instanceof Rotatable || data instanceof Slab || data instanceof Directional || verifDependentBlock(block)) return;

            if(!listDetailedMaterialFeature.verifBlock(block)){
                //SsomarDev.testMsg("DEBUG INVALID BLOCK >> "+data.getMaterial(), true);
                return;
            }

            if (Material.matchMaterial(mat) != null) {
                SsomarDev.testMsg("DEBUG PLACE 1", true);
                SafePlace.placeBlockWithEvent(block, Material.matchMaterial(mat), Optional.empty(), uuid, false, !bypassProtection);
                placed = true;
            } else {
                World w = loc.getWorld();
                List<Entity> entities = w.getEntities();

                if (entities.size() > 0) {
                    if (!bypassProtection && uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
                    RunConsoleCommand.runConsoleCommand("execute at " + entities.get(0).getUniqueId() + " run setblock " + x + " " + y + " " + z + " " + args.get(3).toLowerCase() + " replace", aInfo.isSilenceOutput());
                    placed = true;
                }
            }

            if(placed){
                SetTempBlockManager.getInstance().runInitTempBlock(block.getLocation(), data, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final BlockFace[] BLOCK_FACES = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};


    public boolean verifDependentBlock(Block block) {
        for(BlockFace blockFace: BLOCK_FACES){
            Block relative = block.getRelative(blockFace);
            Material mat = relative.getType();
            if(mat == Material.TORCH || mat == Material.LADDER) return true; //Found torch, return true.
        }
        return false; //No torch found, return false
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        /* Delete verification to not interfer with the vanilla setblock command */
//		String setblock = "SETBLOCK {material}";
//		if(args.size()<1) error = notEnoughArgs+setblock;
//		else if(args.size()>1)error = tooManyArgs+setblock;

        if(args.size() >= 4) {
            List<String> list = new ArrayList<>();
            String listStr = args.get(3);
            if(listStr.contains(",")) list = Arrays.asList(listStr.split(","));
            else list.add(listStr);

            ListDetailedMaterialFeature listDetailedMaterialFeature = new ListDetailedMaterialFeature(true);
            List<String> errors = listDetailedMaterialFeature.load(SCore.plugin, list, true);
            if(errors.size() > 0) {
                error = errors.get(0);
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETTEMPBLOCKPOS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETTEMPBLOCKPOS {x} {y} {z} {material} {time} [bypassProtection true or false] [blocks list]";
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

