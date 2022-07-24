package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetBlock extends BlockCommand {

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        try {
            String mat = args.get(0).toUpperCase();
            UUID uuid = null;
            if (p != null) uuid = p.getUniqueId();
            if (Material.matchMaterial(mat) != null) {
                SafePlace.placeBlockWithEvent(block, Material.matchMaterial(mat), Optional.empty(), uuid, false, true);
            } else {
                World w = block.getWorld();
                List<Entity> entities = w.getEntities();

                if (entities.size() > 0) {
                    if (uuid != null && SafePlace.verifSafePlace(uuid, block)) return;
                    RunConsoleCommand.runConsoleCommand("execute at " + entities.get(0).getUniqueId() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + args.get(0).toLowerCase() + " replace", aInfo.isSilenceOutput());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        /* Delete verification to not interfer with the vanilla setblock command */
//		String setblock = "SETBLOCK {material}";
//		if(args.size()<1) error = notEnoughArgs+setblock;
//		else if(args.size()>1)error = tooManyArgs+setblock;

        return  error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETBLOCK {material}";
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
