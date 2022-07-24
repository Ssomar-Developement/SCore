package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.*;

/* SETBLOCK {material} */
public class SetBlock extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        try {
            Set<Material> set = new TreeSet<>();
            set.add(Material.WATER);
            set.add(Material.LAVA);
            set.add(Material.AIR);

            Block block = receiver.getTargetBlock(set, 5);

            if (block.getType() != Material.AIR) {

                block = block.getRelative(BlockFace.valueOf(args.get(0)));

                UUID uuid = null;
                if (p != null) uuid = p.getUniqueId();

                if (Material.matchMaterial(args.get(1).toUpperCase()) != null) {
                    SafePlace.placeBlockWithEvent(block, Material.matchMaterial(args.get(1).toUpperCase()), Optional.empty(), uuid, false, true);
                } else {
                    if (uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
                    RunConsoleCommand.runConsoleCommand("execute at " + receiver.getName() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + args.get(0).toLowerCase(), aInfo.isSilenceOutput());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkMaterial(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
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
