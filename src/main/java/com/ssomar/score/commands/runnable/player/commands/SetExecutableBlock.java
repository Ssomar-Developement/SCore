package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.executableblocks.executableblocks.ExecutableBlock;
import com.ssomar.executableblocks.executableblocks.ExecutableBlocksManager;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.sobject.InternalData;
import com.ssomar.score.usedapi.MultiverseAPI;
import com.ssomar.score.utils.CreationType;
import com.ssomar.score.utils.place.OverrideMode;
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

    public SetExecutableBlock() {
        CommandSetting id = new CommandSetting("id", 0, String.class, "id");
        CommandSetting x = new CommandSetting("x", 1, Double.class, 0.0);
        CommandSetting y = new CommandSetting("y", 2, Double.class, 0.0);
        CommandSetting z = new CommandSetting("z", 3, Double.class, 0.0);
        CommandSetting world = new CommandSetting("world", 4, String.class, "world");
        CommandSetting replace = new CommandSetting("replace", 5, Boolean.class, false);
        CommandSetting bypassProtection = new CommandSetting("bypassProtection", 6, Boolean.class, false);
        CommandSetting ownerUUID = new CommandSetting("ownerUUID", 7, UUID.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(id);
        settings.add(x);
        settings.add(y);
        settings.add(z);
        settings.add(world);
        settings.add(replace);
        settings.add(bypassProtection);
        settings.add(ownerUUID);
        setNewSettingsMode(true);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_EXECUTABLE_BLOCK");
        names.add("SETEXECUTABLEBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_EXECUTABLE_BLOCK id:MyEb x:0.0 y:0.0 z:0.0 world:world replace:false bypassProtection:false ownerUUID:%player_uuid%";
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
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ActionInfo aInfo = sCommandToExec.getActionInfo();

        String id = (String) sCommandToExec.getSettingValue("id");
        double x = (double) sCommandToExec.getSettingValue("x");
        double y = (double) sCommandToExec.getSettingValue("y");
        double z = (double) sCommandToExec.getSettingValue("z");
        String worldStr = (String) sCommandToExec.getSettingValue("world");
        boolean replace = (boolean) sCommandToExec.getSettingValue("replace");
        boolean bypassProtection = (boolean) sCommandToExec.getSettingValue("bypassProtection");
        UUID ownerUUID = (UUID) sCommandToExec.getSettingValue("ownerUUID");

        Optional<ExecutableBlock> oOpt = ExecutableBlocksManager.getInstance().getLoadedObjectWithID(id);
        if (!oOpt.isPresent()) {
            ExecutableBlocks.plugin.getLogger().severe("There is no ExecutableBlock associate with the ID: " + id + " for the command SET_EXECUTABLE_BLOCK (object: " + aInfo.getName() + ")");
            return;
        }

        World world = null;
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

        Optional<Player> owner = Optional.empty();
        if (ownerUUID != null) {
            owner = Optional.ofNullable(Bukkit.getPlayer(ownerUUID));
        }

        Location loc = new Location(world, x, y, z);

        if (!replace && !loc.getBlock().isEmpty()){
            SsomarDev.testMsg("The block is not empty and replace = false", true);
            return;
        }
        SsomarDev.testMsg("Command 2 ", true);

        OverrideMode overrideMode = OverrideMode.KEEP_EXISTING;
        if (replace) overrideMode = OverrideMode.REMOVE_EXISTING;

        UUID uuid = null;
        if (p != null) uuid = p.getUniqueId();

        if (uuid != null && !bypassProtection && !SafePlace.verifSafePlace(uuid, loc.getBlock())) return;

        ExecutableBlock eB = oOpt.get();

        if(eB.getCreationType().getValue().get() != CreationType.DISPLAY_CREATION) loc = loc.getBlock().getLocation();
        eB.place(loc, true, overrideMode, owner.orElse(null), null, new InternalData().setOwnerUUID(ownerUUID));
    }

}
