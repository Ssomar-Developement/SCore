package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssomar.score.SsomarDev;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

public class DropItem extends EntityCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
        try {
            int amount = Integer.parseInt(args.get(1));
            Location eLoc = entity.getLocation();
            if (amount > 0) {
                eLoc.getWorld().dropItem(eLoc, new ItemStack(Material.valueOf(args.get(0).toUpperCase()), amount));
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DROPITEM");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DROPITEM {material} {quantity}";
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
