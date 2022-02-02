package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetMaterialCooldown extends PlayerCommand {


    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int cooldown = 10;
        Material mat;
        if(args.size() > 1){
            try{
                mat = Material.valueOf(args.get(0).toUpperCase());
            }catch (Exception e){
                return;
            }
            try{
                cooldown = Integer.parseInt(args.get(1));
            }catch (Exception ignored){}
            receiver.setCooldown(mat, 20*cooldown);
        }
    }

    @Override
    public String verify(List<String> args) {
        String error = "";

        return error;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETMATERIALCOOLDOWN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETMATERIALCOOLDOWN {Material} {cooldown in secs}";
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
