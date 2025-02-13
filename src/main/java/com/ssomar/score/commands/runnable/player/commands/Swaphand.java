package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class Swaphand extends PlayerCommand {

    public Swaphand() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();

        /* Delay fix a double activation of the item, not so easy to esplain, it fixes this issue: https://discord.com/channels/701066025516531753/1014297458735595680/1014299784229683302*/
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                PlayerInventory inv = receiver.getInventory();
                ItemStack mainhand = inv.getItemInMainHand();
                ItemStack offhandItem = inv.getItemInOffHand();

                PlayerSwapHandItemsEvent bbE = new PlayerSwapHandItemsEvent(receiver, mainhand, offhandItem);
                Bukkit.getPluginManager().callEvent(bbE);

                if(!bbE.isCancelled()) {
                    inv.setItemInOffHand(mainhand);
                    inv.setItemInMainHand(offhandItem);
                }
            }
        };
        SCore.schedulerHook.runEntityTask(runnable3, null, receiver, 1);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SWAP_HAND");
        names.add("SWAPHAND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SWAP_HAND";
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
