package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForceDrop extends PlayerCommand {

    public ForceDrop() {
        CommandSetting ei_id = new CommandSetting("ei_id", 0, String.class, "MyExecutableItem");
        List<CommandSetting> settings = getSettings();
        settings.add(ei_id);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        if(receiver.isDead()) return;
        String id = (String) sCommandToExec.getSettingValue("ei_id");

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
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORCE_DROP");
        names.add("DROPSPECIFICEI");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORCE_DROP ei_id:MyExecutableItem";
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
