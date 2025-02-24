package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.RunConsoleCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.safeplace.SafePlace;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.*;

public class SetBlock extends PlayerCommand {

    public SetBlock() {
        CommandSetting blockFace = new CommandSetting("blockface", 0, BlockFace.class, null);
        CommandSetting material = new CommandSetting("material", 1, String.class, "STONE");
        CommandSetting bypassProtection = new CommandSetting("bypassProtection", -1, Boolean.class, true);
        List<CommandSetting> settings = getSettings();
        settings.add(material);
        settings.add(blockFace);
        settings.add(bypassProtection);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {

        String materialStr = (String) sCommandToExec.getSettingValue("material");
        boolean bypassProtection = (boolean) sCommandToExec.getSettingValue("bypassProtection");

        Material material = null;
        try {
            material = Material.valueOf(materialStr.toUpperCase());
        } catch (Exception e) {
        }

        Set<Material> set = new TreeSet<>();
        set.add(Material.WATER);
        set.add(Material.LAVA);
        set.add(Material.AIR);

        Block block = receiver.getTargetBlock(set, 5);

        //SsomarDev.testMsg("block: "+block.getType().toString(), true);

        if (block.getType() != Material.AIR) {

            BlockFace blockFace = (BlockFace) sCommandToExec.getSettingValue("blockface");
            if(blockFace == null){
                //Raytrace to get blockface
                RayTraceResult result = receiver.getLocation().getWorld().rayTraceBlocks(receiver.getEyeLocation(), receiver.getEyeLocation().getDirection(), 5);
                if(result != null) blockFace = result.getHitBlockFace();
            }
            if(blockFace == null) return;
            block = block.getRelative(blockFace);

            UUID uuid = receiver.getUniqueId();
            if (!bypassProtection && !SafePlace.verifSafePlace(uuid, block)) return;

            //SsomarDev.testMsg("block: "+block.getType().toString(), true);

            if (material != null) {
                SafePlace.placeBlockWithEvent(block, material, Optional.empty(), uuid, false, true);
                //SsomarDev.testMsg("block: "+block.getType().toString(), true);
            } else {
                if (!SafePlace.verifSafePlace(uuid, block)) return;
                RunConsoleCommand.runConsoleCommand("execute at " + receiver.getName() + " run setblock " + block.getX() + " " + block.getY() + " " + block.getZ() + " " + materialStr.toLowerCase(), sCommandToExec.getActionInfo().isSilenceOutput());
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_BLOCK");
        names.add("SETBLOCK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_BLOCK material:STONE blockface:UP bypassProtection:true";
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
