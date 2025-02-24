package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SetBlockPos extends BlockCommand {

    @Override
    public void run(@Nullable Player p, @NotNull Block block, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        ActionInfo aInfo = sCommandToExec.getActionInfo();
        try {
            double x = Double.valueOf(args.get(0));
            double y = Double.valueOf(args.get(1));
            double z = Double.valueOf(args.get(2));

            String mat = args.get(3).toUpperCase();

            boolean bypassProtection = false;
            if (args.size() >= 5) bypassProtection = Boolean.parseBoolean(args.get(4));

            boolean replace = false;
            if (args.size() >= 6) replace = Boolean.parseBoolean(args.get(5));

            UUID uuid = null;
            if (p != null) uuid = p.getUniqueId();

            block = block.getWorld().getBlockAt(new Location(block.getWorld(), x, y, z));

            if(!block.isEmpty() && !replace){
                SsomarDev.testMsg("SETBLOCKPOS The block is not empty and replace is false", true);
                return;
            }
            SsomarDev.testMsg("SETBLOCKPOS The block is empty or replace is true >> mat : "+mat, true);

            if (Material.matchMaterial(mat) != null) {
                SsomarDev.testMsg("SETBLOCKPOS The block will be replaced", true);
                SafePlace.placeBlockWithEvent(block.getWorld().getBlockAt(new Location(block.getWorld(), x, y, z)), Material.matchMaterial(mat), Optional.empty(), uuid, false, !bypassProtection);
            } else {
                World w = block.getWorld();
                List<Entity> entities = w.getEntities();

                if (entities.size() > 0) {
                    if (!bypassProtection && uuid != null && !SafePlace.verifSafePlace(uuid, block)){
                        SsomarDev.testMsg("SETBLOCKPOS The block is not safe", true);
                        return;
                    }
                    RunConsoleCommand.runConsoleCommand("execute at " + entities.get(0).getUniqueId() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + args.get(3) + " replace", aInfo.isSilenceOutput());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        /* Delete verification to not interfer with the vanilla setblock command
	    String setblock = "SETBLOCK {material}";
		if(args.isEmpty()) error = notEnoughArgs+setblock;
		else if(args.size()>1)error = tooManyArgs+setblock; */

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETBLOCKPOS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETBLOCKPOS {x} {y} {z} {material} [bypassProtection true or false] [replace true or false]";
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

