package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Around extends PlayerCommand{

    private final static Boolean DEBUG = false;

    public Around() {
        CommandSetting distance = new CommandSetting("distance", 0, Double.class, 3d);
        CommandSetting displayMsgIfNoPlayer = new CommandSetting("displayMsgIfNoPlayer", 1, Boolean.class, true, true);
        CommandSetting throughBlocks = new CommandSetting("throughBlocks", -1, Boolean.class, true);
        CommandSetting safeDistance = new CommandSetting("safeDistance", -1, Double.class, 0d);
        CommandSetting x = new CommandSetting("x", -1, Double.class, 0d);
        CommandSetting y = new CommandSetting("y", -1, Double.class, 0d);
        CommandSetting z = new CommandSetting("z", -1, Double.class, 0d);
        CommandSetting world = new CommandSetting("world", -1, String.class, "");   
        CommandSetting targetMobs = new CommandSetting("targetMobs", -1, Boolean.class, false, false);    
        CommandSetting targetSelf = new CommandSetting("targetSelf", -1, Boolean.class, true, true);      
        CommandSetting targetNPC = new CommandSetting("targetNPC", -1, Boolean.class, false, false);        
        List<CommandSetting> settings = getSettings();
        settings.add(distance);
        settings.add(displayMsgIfNoPlayer);
        settings.add(throughBlocks);
        settings.add(safeDistance);
        settings.add(x);
        settings.add(y);
        settings.add(z);
        settings.add(world);     
        settings.add(targetMobs);
        settings.add(targetSelf);
        settings.add(targetNPC);   
        setNewSettingsMode(true);
        setCanExecuteCommands(true);
    }

    public static void aroundExecution(Entity receiver, SCommandToExec sCommandToExec, boolean displayMsgIfNoTargetHit) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                double distance = (double) sCommandToExec.getSettingValue("distance");
                boolean throughBlocks = (boolean) sCommandToExec.getSettingValue("throughBlocks");
                double safeDistance = (double) sCommandToExec.getSettingValue("safeDistance");

                double x = (double) sCommandToExec.getSettingValue("x");
                double y = (double) sCommandToExec.getSettingValue("y");
                double z = (double) sCommandToExec.getSettingValue("z");
                String world = (String) sCommandToExec.getSettingValue("world");
                boolean targetMobs = (boolean) sCommandToExec.getSettingValue("targetMobs");
                boolean self = (boolean) sCommandToExec.getSettingValue("targetSelf");
                boolean targetNPC = (boolean) sCommandToExec.getSettingValue("targetNPC");

                List<LivingEntity> targets = new ArrayList<>();

                List<Player> playerTargets = new ArrayList<>();

                for (Entity e : receiver.getNearbyEntities(distance, distance, distance)) {
                    if (e instanceof LivingEntity) {
                        LivingEntity target = (Player) e;

                        Location originalLoc = receiver.getLocation();
                        Location receiverLoc;
                        if (x != -1 && y != -1 && z != -1) {
                            // Use explicit coordinates
                            World targetWorld = originalLoc.getWorld();
                            if (world != null && !world.isEmpty()) {
                                World w = Bukkit.getWorld(world);
                                if (w != null) targetWorld = w;
                            }
                            receiverLoc = new Location(targetWorld, x, y, z);
                        } else {
                            // Fall back to original
                            receiverLoc = originalLoc;
                        }

                        if(safeDistance > 0) {
                            Location targetLoc = target.getLocation();
                            if(receiverLoc.distance(targetLoc) <= safeDistance) continue;
                        }

                        if(!throughBlocks){
                            if(receiver instanceof LivingEntity) receiverLoc = ((LivingEntity) receiver).getEyeLocation();

                            // Check see feet and yers
                            List<Location> toCheck = new ArrayList<>();
                            toCheck.add(target.getLocation());
                            toCheck.add(target.getEyeLocation());
                            // middle between locatiuon and eyelocation
                            toCheck.add(target.getLocation().add(0, 1, 0));
                            boolean valid = false;
                            for(Location loc : toCheck){
                                double distanceBetween = receiverLoc.distance(loc);
                                Vector direction = loc.toVector().subtract(receiverLoc.toVector()).normalize();
                                RayTraceResult rayTraceResult = receiver.getWorld().rayTraceBlocks(receiverLoc, direction, distanceBetween, FluidCollisionMode.NEVER, true);
                                if(rayTraceResult == null) {
                                    valid = true;
                                    break;
                                }
                            }
                            if(!valid) continue;
                        }

                        // don't target NPCs: 
                        if (!targetNPC && target.hasMetadata("NPC")) continue;

                        // don't target self:
                        if(!self && target.equals(receiver)) continue;

                        // Split targets so both get targeted if we choose to do so
                        if ((e instanceof Player) == false && targetMobs) targets.add(target);
                        if (e instanceof Player) playerTargets.add((Player)target);
                    }
                }
                // Check if players are hit
                boolean hit = CommmandThatRunsCommand.runPlayerCommands(playerTargets, sCommandToExec.getOtherArgs(),sCommandToExec.getActionInfo());
                boolean phit = CommmandThatRunsCommand.runEntityCommands(targets, sCommandToExec.getOtherArgs(), sCommandToExec.getActionInfo());
                // If no players hit, check if entity is hit
                if(!hit){
                    hit = phit;
                }

                if (!hit && displayMsgIfNoTargetHit && receiver instanceof Player)
                    sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_PLAYER_HIT));
            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
    }





    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        aroundExecution(receiver, sCommandToExec, (boolean) sCommandToExec.getSettingValue("displayMsgIfNoPlayer"));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AROUND distance:3.0 displayMsgIfNoPlayer:true throughBlocks:true safeDistance:0.0 {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }
}
