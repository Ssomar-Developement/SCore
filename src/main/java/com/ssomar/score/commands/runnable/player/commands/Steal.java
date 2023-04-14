package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* STEAL [slot] {remove item default true} */
public class Steal extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        if(receiver.isDead() | p.isDead()) return;

       boolean remove = true;
       if(args.size() == 2){
           if (args.get(1).equalsIgnoreCase("false")) remove = false;
       }

       Integer slot = NTools.getInteger(args.get(0)).get();

       ItemStack itemtosteal;
       try {
           System.out.println(args.get(0));
           System.out.println(args.get(0).equalsIgnoreCase("-1"));
           if (args.get(0).equalsIgnoreCase("-1")) itemtosteal = receiver.getInventory().getItemInMainHand();
           else itemtosteal = receiver.getInventory().getItem(slot);

           if(itemtosteal.getType() == Material.AIR) return;

           if(remove){
               if (args.get(0).equalsIgnoreCase("-1")) receiver.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
               else receiver.getInventory().setItem(slot, new ItemStack(Material.AIR));
           }

           if(p.getInventory().firstEmpty() == -1) Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(),itemtosteal);
           else p.getInventory().addItem(itemtosteal);
       }catch(NullPointerException | IllegalArgumentException e){return;}



    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if(args.size() >= 2){
            ArgumentChecker ac2 = checkBoolean(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("STEAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "STEAL [slot] {remove item default true}";
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
