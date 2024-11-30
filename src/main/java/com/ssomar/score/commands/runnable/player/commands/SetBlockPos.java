package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
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


    public SetBlockPos() {
        CommandSetting x = new CommandSetting("x", 0, Double.class, 0.0);
        CommandSetting y = new CommandSetting("y", 1, Double.class, 0.0);
        CommandSetting z = new CommandSetting("z", 2, Double.class, 0.0);
        CommandSetting material = new CommandSetting("material", 3, Material.class, Material.STONE);
        CommandSetting bypassProtection = new CommandSetting("bypassProtection", 4, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(x);
        settings.add(y);
        settings.add(z);
        settings.add(material);
        settings.add(bypassProtection);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {

        double x = (double) sCommandToExec.getSettingValue("x");
        double y = (double) sCommandToExec.getSettingValue("y");
        double z = (double) sCommandToExec.getSettingValue("z");
        Material material = (Material) sCommandToExec.getSettingValue("material");
        boolean bypassProtection = (boolean) sCommandToExec.getSettingValue("bypassProtection");

        Location loc = receiver.getLocation();
        Location blockLoc = new Location(loc.getWorld(), x, y, z);
        Block block = blockLoc.getBlock();
        UUID uuid = receiver.getUniqueId();

        if (material != null) {
            SafePlace.placeBlockWithEvent(block, material, Optional.empty(), uuid, false, !bypassProtection);
        } else {
            World w = loc.getWorld();
            List<Entity> entities = w.getEntities();

            if (entities.size() > 0) {
                if (!bypassProtection && uuid != null && !SafePlace.verifSafePlace(uuid, block)) return;
                RunConsoleCommand.runConsoleCommand("execute at " + entities.get(0).getUniqueId() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + material.toString().toLowerCase() + " replace", sCommandToExec.getActionInfo().isSilenceOutput());
            }
        }

    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_BLOCK_POS");
        names.add("SETBLOCKPOS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_BLOCK_POS x:0 y:0 z:0 material:STONE bypassProtection:false";
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

