package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
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
public class TeleportOnCursor extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int amount = 200;
        boolean acceptAir = false;
        if (args.size() >= 1) {
            try {
                amount = Integer.parseInt(args.get(0));
                if (amount <= 0) {
                    amount = 200;
                }
            } catch (Exception ignored) {
            }
        }
        if (args.size() >= 2) {
            try {
                acceptAir = Boolean.parseBoolean(args.get(1));
            } catch (Exception ignored) {
            }
        }
        try {

            Location eyeLoc = receiver.getEyeLocation();
            Vector eyeVec = eyeLoc.getDirection();
            Vector checkVec = eyeVec.clone().multiply(2);
            Location checkLoc = eyeLoc.clone().add(checkVec);

            int cpt = 2;
            /* Invalid if its a cheat movement */
            boolean invalid = false;
            while (cpt < amount && isAirBlock((checkLoc = eyeLoc.clone().add(eyeVec.clone().multiply(cpt))).getBlock())) {
                if (!isAirBlock(checkLoc.clone().add(0, 1, 0).getBlock())) {
                    invalid = true;
                    break;
                }
                cpt++;
            }

            if (invalid) return;

            Material mat = checkLoc.getBlock().getType();
            boolean validTp = (acceptAir && isAirBlock(checkLoc.getBlock()) || (!isAirBlock(checkLoc.getBlock()) && mat.isBlock() && !mat.equals(Material.LAVA)));

            if (validTp && isAirBlock(checkLoc.clone().add(0, 1, 0).getBlock()) && isAirBlock(checkLoc.clone().add(0, 2, 0).getBlock())) {
                if(TeleportOnCursorManager.getInstance().canTp(receiver.getUniqueId())) receiver.teleport(checkLoc.add(0, 1, 0));

            }


			/* Block block = receiver.getTargetBlock(null, amount);

			if(acceptAir || block.getType() != Material.AIR) {
				Location locP = receiver.getLocation();

				Location loc = block.getLocation();
				loc.add(0, 1, 0);	
*/
            /* isPassable not available in 1.12 and 1.13 */
				/*if(!SCore.is1v12() && !SCore.is1v13() && !locP.getWorld().getBlockAt(loc).isPassable()) return;

				Location newLoc = new Location(block.getWorld(), loc.getX()+0.5, loc.getY(), loc.getZ()+0.5);
				newLoc.setPitch(locP.getPitch());
				newLoc.setYaw(locP.getYaw());

				receiver.teleport(newLoc);
*/
            /* NO FALL DAMAGE PART, IF THE PLAYER IS TELEPORTED IN THE AIR */
				/*NoFallDamageManager.getInstance().addNoFallDamage(receiver);
			}*/
        } catch (Exception ignored) {
        }
    }

    public boolean isAirBlock(Block block) {
        Material mat = block.getType();
        return (SCore.is1v16Plus() && mat.isAir()) || (!SCore.is1v16Plus() && mat.equals(Material.AIR));
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        String tpOnCursor = "TELEPORTONCURSOR {maxRange} {acceptAir true or false}";
        if (args.size() > 2) {
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
            if (args.size() >= 2) {
                try {
                    Boolean.valueOf(args.get(1));
                } catch (NumberFormatException e) {
                    error = invalidBoolean + args.get(1) + " for command: " + tpOnCursor;
                }
            }
        }
        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("TELEPORTONCURSOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "TELEPORTONCURSOR {maxRange} {acceptAir true or false}";
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
