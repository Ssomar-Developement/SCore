package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DropSpecificEI extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        if(receiver.isDead()) return;

        List<String> args = sCommandToExec.getOtherArgs();
        String id = args.get(0);

        Location loc = receiver.getLocation();
        ExecutableItemsManagerInterface manager = ExecutableItemsAPI.getExecutableItemsManager();
        Inventory inventory = receiver.getInventory();
        for(ItemStack item : inventory){
            if(receiver.isDead()) return;
            if(manager.getExecutableItem(item).isPresent()){
                if(manager.getExecutableItem(item).get().getId().equalsIgnoreCase(id)){
                    ItemStack copy = item.clone();
                    item.setAmount(0);
                    loc.getWorld().dropItem(loc, copy);
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DROPSPECIFICEI");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DROPSPECIFICEI {id}";
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
