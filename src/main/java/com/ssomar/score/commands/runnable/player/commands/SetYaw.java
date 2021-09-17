package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetYaw extends PlayerCommand {


    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        float yaw = Float.valueOf(args.get(0));

        Location location = receiver.getLocation();
        location.setYaw(yaw);
        receiver.teleport(location);
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        String setYaw = "SETYAW {yaw_number}";
        if(args.size()>1) error = tooManyArgs+setYaw;
        else if(args.size() == 0) error = notEnoughArgs+setYaw;
        else {
            try {
                Float.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidTime + args.get(0) + " for command: " + setYaw;
            }
        }

        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETYAW");
        return names;
    }

    @Override
    public String getTemplate() {
        // TODO Auto-generated method stub
        return "SETYAW {yaw_number}";
    }

    @Override
    public ChatColor getColor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        // TODO Auto-generated method stub
        return null;
    }
}
