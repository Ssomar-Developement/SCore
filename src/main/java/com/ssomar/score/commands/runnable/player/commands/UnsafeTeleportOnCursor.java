package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/* TELEPORTONCURSOR {range}:Integer {acceptAir}:boolean */
public class UnsafeTeleportOnCursor extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int amount = 200;
        if (args.size() >= 1) {
            try {
                amount = Integer.parseInt(args.get(0));
                if (amount <= 0) {
                    amount = 200;
                }
            } catch (Exception ignored) {}
        }
        try {

            Location eyeLoc = receiver.getEyeLocation();
            Vector eyeVec = eyeLoc.getDirection();
            Vector checkVec = eyeVec.clone().multiply(2);
            Location checkLoc = eyeLoc.clone().add(checkVec);

            int cpt = 2;
            /* Invalid if its a cheat movement */
            Block lastAirBlock = null;
            while (cpt < amount && isAirBlock((checkLoc = eyeLoc.clone().add(eyeVec.clone().multiply(cpt))).getBlock())) {
                if (!isAirBlock(checkLoc.clone().getBlock())) {
                    break;
                }
                else lastAirBlock = checkLoc.clone().getBlock();
                cpt++;
            }

            if(lastAirBlock != null && TeleportOnCursorManager.getInstance().canTp(receiver.getUniqueId())){
                Location toTeleport = lastAirBlock.getLocation();
                toTeleport.setPitch(receiver.getLocation().getPitch());
                toTeleport.setYaw(receiver.getLocation().getYaw());
                receiver.teleport(toTeleport);
            }

        } catch (Exception ignored) {}
    }

    public boolean isAirBlock(Block block) {
        Material mat = block.getType();
        return (SCore.is1v16Plus() && mat.isAir()) || (!SCore.is1v16Plus() && mat.equals(Material.AIR));
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        String tpOnCursor = "UNSAFE_TELEPORTONCURSOR {maxRange}";
        if (args.size() > 1) {
            error = tooManyArgs + tpOnCursor;
            return error;
        } else {
            if (args.size() >= 1) {
                try {
                    Integer.valueOf(args.get(0));
                } catch (NumberFormatException e) {
                    error = invalidRange + args.get(0) + " for command: " + tpOnCursor;
                }
            }
        }
        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("UNSAFE_TELEPORTONCURSOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "UNSAFE_TELEPORTONCURSOR {maxRange}";
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
