package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.executableblocks.executableblocks.ExecutableBlock;
import com.ssomar.executableblocks.executableblocks.ExecutableBlocksManager;
import com.ssomar.executableblocks.executableblocks.internal.InternalData;
import com.ssomar.executableblocks.utils.OverrideEBP;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.MultiverseAPI;
import com.ssomar.score.utils.CreationType;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SetExecutableBlock extends PlayerCommand {

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 6) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac2 = checkExecutableBlockID(args.get(0), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        for (int i = 1; i < 4; i++) {
            ArgumentChecker ac = checkDouble(args.get(i), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        ArgumentChecker ac3 = checkWorld(args.get(4), isFinalVerification, getTemplate());
        if (!ac3.isValid()) return Optional.of(ac3.getError());

        ArgumentChecker ac4 = checkBoolean(args.get(5), isFinalVerification, getTemplate());
        if (!ac4.isValid()) return Optional.of(ac4.getError());

        if (args.size() > 6) {
            ArgumentChecker ac5 = checkBoolean(args.get(6), isFinalVerification, getTemplate());
            if (!ac5.isValid()) return Optional.of(ac5.getError());
        }

        if (args.size() > 7) {
            ArgumentChecker ac5 = checkUUID(args.get(7), isFinalVerification, getTemplate());
            if (!ac5.isValid()) return Optional.of(ac5.getError());
        }

        return Optional.empty();
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

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        Optional<ExecutableBlock> oOpt = ExecutableBlocksManager.getInstance().getLoadedObjectWithID(args.get(0));
        if (!oOpt.isPresent()) {
            ExecutableBlocks.plugin.getLogger().severe("There is no ExecutableBlock associate with the ID: " + args.get(0) + " for the command SETEXECUTABLEBLOCK (object: " + aInfo.getName() + ")");
            return;
        }

        double x = Double.parseDouble(args.get(1));
        double y = Double.parseDouble(args.get(2));
        double z = Double.parseDouble(args.get(3));

        World world = null;
        String worldStr = args.get(4);
        if (worldStr.isEmpty()) return;
        else {
            if (SCore.hasMultiverse) {
                world = MultiverseAPI.getWorld(worldStr);
            } else {
                if ((world = Bukkit.getWorld(worldStr)) == null){
                    SsomarDev.testMsg("The world "+worldStr+" doesn't exist", true);
                    return;
                }
            }
        }

        boolean replace = Boolean.valueOf(args.get(5));
        boolean bypassProtection = false;
        if (args.size() > 6) {
            bypassProtection = Boolean.valueOf(args.get(6));
        }
        UUID ownerUUID = null;
        Optional<Player> owner = Optional.empty();
        if (args.size() > 7) {
            ownerUUID = UUID.fromString(args.get(7));
            if(ownerUUID != null) owner = Optional.ofNullable(Bukkit.getPlayer(ownerUUID));
        }

        Location loc = new Location(world, x, y, z);

        if (!replace && !loc.getBlock().isEmpty()){
            SsomarDev.testMsg("The block is not empty and replace = false", true);
            return;
        }
        SsomarDev.testMsg("Command 2 ", true);

        OverrideEBP overrideEBP = OverrideEBP.KEEP_EXISTING_EBP;
        if (replace) overrideEBP = OverrideEBP.REMOVE_EXISTING_EBP;

        UUID uuid = null;
        if (p != null) uuid = p.getUniqueId();

        if (uuid != null && !bypassProtection && !SafePlace.verifSafePlace(uuid, loc.getBlock())) return;

        ExecutableBlock eB = oOpt.get();

        if(eB.getCreationType().getValue().get() != CreationType.DISPLAY_CREATION) loc = loc.getBlock().getLocation();
        eB.place(loc, true, overrideEBP, null, null, new InternalData().setOwnerUUID(ownerUUID));
    }

}
