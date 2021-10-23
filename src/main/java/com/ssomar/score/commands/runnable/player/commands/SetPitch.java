package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetPitch extends PlayerCommand {


    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        float pitch = Float.parseFloat(args.get(0));

       Location location = receiver.getLocation();
       location.setPitch(pitch);
       receiver.teleport(location);
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        String setPitch= "SETPITCH {pitch_number}";
        if(args.size()>1) error = tooManyArgs+setPitch;
        else if(args.size() == 0) error = notEnoughArgs+setPitch;
        else if(!args.get(0).contains("%")){
            try {
                Float.valueOf(args.get(0));
            } catch (NumberFormatException e) {
                error = invalidTime + args.get(0) + " for command: " + setPitch;
            }
        }

        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETPITCH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETPITCH {pitch_number}";
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
