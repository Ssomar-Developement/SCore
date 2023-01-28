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
        Set<Material> set = new TreeSet<>();
        set.add(Material.WATER);
        set.add(Material.LAVA);
        set.add(Material.AIR);

        Block block = receiver.getTargetBlock(set, 5);

        //SsomarDev.testMsg("block: "+block.getType().toString(), true);

        if (block.getType() != Material.AIR) {

            block = block.getRelative(BlockFace.valueOf(args.get(0)));

            //SsomarDev.testMsg("block: "+block.getType().toString(), true);

            UUID uuid = null;
            if (p != null) uuid = p.getUniqueId();

            if (Material.matchMaterial(args.get(1).toUpperCase()) != null) {
                SafePlace.placeBlockWithEvent(block, Material.matchMaterial(args.get(1).toUpperCase()), Optional.empty(), uuid, false, true);
                //SsomarDev.testMsg("block: "+block.getType().toString(), true);
            } else {
                if (uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
                RunConsoleCommand.runConsoleCommand("execute at " + receiver.getName() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + args.get(0), aInfo.isSilenceOutput());
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        /* different because the arguiement of the blockdace is added automatically in the code and not by the user */
        if(isFinalVerification){
            if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

            ArgumentChecker ac = checkMaterial(args.get(1), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }
        else {
            if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

            ArgumentChecker ac = checkMaterial(args.get(0), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return Optional.empty();
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
