package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SetBlockPos extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        try {
            double x = Double.valueOf(args.get(0));
            double y = Double.valueOf(args.get(1));
            double z = Double.valueOf(args.get(2));

            Location loc = receiver.getLocation();
            Location blockLoc = new Location(loc.getWorld(), x, y, z);
            Block block = blockLoc.getBlock();

            String mat = args.get(3).toUpperCase();

            boolean bypassProtection = false;
            if (args.size() >= 5) bypassProtection = Boolean.parseBoolean(args.get(4));

            UUID uuid = receiver.getUniqueId();

            if (Material.matchMaterial(mat) != null) {
                SafePlace.placeBlockWithEvent(block, Material.matchMaterial(mat), Optional.empty(), uuid, false, !bypassProtection);
            } else {
                World w = loc.getWorld();
                List<Entity> entities = w.getEntities();

                if (entities.size() > 0) {
                    if (!bypassProtection && uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
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
        /* Delete verification to not interfer with the vanilla setblock command */
//		String setblock = "SETBLOCK {material}";
//		if(args.size()<1) error = notEnoughArgs+setblock;
//		else if(args.size()>1)error = tooManyArgs+setblock;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETBLOCKPOS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETBLOCKPOS {x} {y} {z} {material} [bypassProtection true or false]";
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

