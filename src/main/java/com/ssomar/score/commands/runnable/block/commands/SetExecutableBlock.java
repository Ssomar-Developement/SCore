package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.executableblocks.api.ExecutableBlocksAPI;
import com.ssomar.executableblocks.executableblocks.ExecutableBlock;
import com.ssomar.executableblocks.executableblocks.internal.InternalData;
import com.ssomar.executableblocks.utils.OverrideEBP;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.usedapi.Dependency;
import com.ssomar.score.usedapi.MultiverseAPI;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SetExecutableBlock extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {


        if (SCore.hasExecutableBlocks && Dependency.EXECUTABLE_BLOCKS.isEnabled()) {
          Optional<ExecutableBlock> oOpt = ExecutableBlocksAPI.getExecutableBlocksManager().getExecutableBlock(args.get(0));
            if (!oOpt.isPresent()) {
                ExecutableBlocks.plugin.getLogger().severe("There is no ExecutableBlock associate with the ID: " + args.get(0) + " for the command SETEXECUTABLEBLOCK (object: " + aInfo.getName() + ")");
                return;
            }

            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(args.get(1));
            } catch (Exception e) {
                SCore.plugin.getLogger().info("Tried to run the custom block command: SETEXECUTABLEBLOCK, but the x coordinate is not a number (x: " + args.get(1) + ")");
                return;
            }

            try {
                y = Double.parseDouble(args.get(2));
            } catch (Exception e) {
                SCore.plugin.getLogger().info("Tried to run the custom block command: SETEXECUTABLEBLOCK, but the y coordinate is not a number (y: " + args.get(2) + ")");
                return;
            }

            try {
                z = Double.parseDouble(args.get(3));
            } catch (Exception e) {
                SCore.plugin.getLogger().info("Tried to run the custom block command: SETEXECUTABLEBLOCK, but the z coordinate is not a number (z: " + args.get(3) + ")");
                return;
            }

            World world = null;
            String worldStr = args.get(4);
            if (worldStr.isEmpty()) {
                SCore.plugin.getLogger().info("Tried to run the custom block command: SETEXECUTABLEBLOCK, but the world is not specified (world: " + worldStr + ")");
                return;
            } else {
                if (SCore.hasMultiverse) {
                    world = MultiverseAPI.getWorld(worldStr);
                } else {
                    world = Bukkit.getWorld(worldStr);
                }
            }
            if (world == null) {
                SCore.plugin.getLogger().info("Tried to run the custom block command: SETEXECUTABLEBLOCK, but the world is not found (world: " + worldStr + ")");
                return;
            }

            boolean replace = false;
            try {
                replace = Boolean.parseBoolean(args.get(5));
            } catch (Exception ignored) {
            }

            boolean bypassProtection = false;
            try {
                bypassProtection = Boolean.parseBoolean(args.get(6));
            } catch (Exception ignored) {
            }

            UUID ownerUUID = null;
            try {
                ownerUUID = UUID.fromString(args.get(args.size() - 1));
            } catch (Exception ignored) {
            }

            block = new Location(world, x, y, z).getBlock();
            Location loc = block.getLocation();

            //SsomarDev.testMsg("replace:::: "+(!replace)+" >>>>>> "+(!block.isEmpty()));
            if (!replace && !block.isEmpty()) {
                return;
            }
            OverrideEBP overrideEBP = OverrideEBP.KEEP_EXISTING_EBP;
            if (replace) overrideEBP = OverrideEBP.REMOVE_EXISTING_EBP;

            UUID uuid = null;
            if (p != null) uuid = p.getUniqueId();

            if (uuid != null && !bypassProtection && !SafePlace.verifSafePlace(uuid, block)) return;

            ExecutableBlock eB = oOpt.get();

            eB.place(loc, true, overrideEBP, null, null, new InternalData().setOwnerUUID(ownerUUID));
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String setEB = this.getTemplate();

        if (args.size() > 8) {
            error = tooManyArgs + setEB;
            return error.isEmpty() ? Optional.empty() : Optional.of(error);
        } else if (args.size() < 6) {
            error = notEnoughArgs + setEB;
        } else {

            if (SCore.hasExecutableBlocks && Dependency.EXECUTABLE_BLOCKS.isEnabled()) {
                /* Ne pas verif car ca peut bloquer si on veut poser le block qui est associé à l'activator */
//				if(!ExecutableBlockManager.getInstance().containsBlockWithID(args.get(0))) {
//					error = "There is no ExecutableBlock associate with the ID: "+args.get(0)+" for the command"+setEB;
//					return error;
//				}
            } else {
                error = "You must have ExecutableBlock for the command" + setEB;
                return error.isEmpty() ? Optional.empty() : Optional.of(error);
            }

            if (!args.get(1).contains("%")) {
                try {
                    Double.valueOf(args.get(1));
                } catch (Exception e) {
                    error = invalidCoordinate + args.get(1) + " for command: " + setEB;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                }
            }
            if (!args.get(2).contains("%")) {
                try {
                    Double.valueOf(args.get(2));
                } catch (Exception e) {
                    error = invalidCoordinate + args.get(2) + " for command: " + setEB;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                }
            }
            if (!args.get(3).contains("%")) {
                try {
                    Double.valueOf(args.get(3));
                } catch (Exception e) {
                    error = invalidCoordinate + args.get(3) + " for command: " + setEB;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                }
            }

            String worldStr = args.get(4);
            if (!args.get(4).contains("%")) {
                if (worldStr.isEmpty()) {
                    error = invalidWorld + args.get(4) + " for the command: " + setEB;
                    return error.isEmpty() ? Optional.empty() : Optional.of(error);
                } else {
                    Optional<World> worldOptional = AllWorldManager.getWorld(worldStr);
                    if (!worldOptional.isPresent()) {
                        error = invalidWorld + args.get(4) + " for the command: " + setEB;
                        return error.isEmpty() ? Optional.empty() : Optional.of(error);
                    }
                }
            }

            try {
                Boolean.valueOf(args.get(5));
            } catch (Exception e) {
                error = invalidBoolean + args.get(5) + " for the command: " + setEB;
                return error.isEmpty() ? Optional.empty() : Optional.of(error);
            }
        }

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETEXECUTABLEBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETEXECUTABLEBLOCK {id} {x} {y} {z} {world} {replace true or false} [bypassProtection true or false] [ownerUUID]";
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
