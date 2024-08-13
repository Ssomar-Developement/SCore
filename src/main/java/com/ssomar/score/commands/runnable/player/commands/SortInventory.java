package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* SORTINVENTORY {HOTBAR INCLUDED DEFAULT FALSE} {AMOUNT/TYPE} */
public class SortInventory extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        //
        boolean sortByType = args.get(1).equalsIgnoreCase("type");
        boolean includeHotbar = args.get(0).equalsIgnoreCase("true");

        //Bukkit.broadcastMessage("Sort by type:" + sortByType);
        //Bukkit.broadcastMessage("Include hotbar:" + includeHotbar);

        if (receiver.isDead()) return;
        receiver.closeInventory();

        Inventory inventory = receiver.getInventory();
        ItemStack[] inventoryContents = inventory.getStorageContents();
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack air = new ItemStack(Material.AIR);

        //guardar todos los items en una carpeta
        for(int i = 0 ; i <= 35 ; i++){
            if(i <= 8){
                if(includeHotbar){
                    if(inventoryContents[i] == null){
                        items.add(air);
                        continue;
                    }
                    if(inventoryContents[i].getType() == Material.AIR) {
                        items.add(air);
                        continue;
                    }

                    items.add(inventoryContents[i]);
                }
            }else{
                if(inventoryContents[i] == null){
                    items.add(air);
                    continue;
                }
                if(inventoryContents[i].getType() == Material.AIR) {
                    items.add(air);
                    continue;
                }

                items.add(inventoryContents[i]);
            }
        }

        if (sortByType) {
            items.sort(Comparator.comparing(ItemStack::getType));
        } else {
            items.sort(Comparator.comparing(ItemStack::getAmount).reversed());
        }

        //remover items del inventario
        for(int i = 9 ; i <= 35 ; i++){
            inventory.setItem(i,air);
        }
        if(includeHotbar){
            for(int i = 0 ; i <= 8 ; i++){
                inventory.setItem(i,air);
            }
        }

        int offset1 = 9;
        int offset2 = 0;

        int offsetForAirContainersOn1 = 0;
        Integer offsetForAirContainersOn1COPY = 0;
        //ordenar
        for(int i = 9 ; i <= 35 ; i++){
            //System.out.println("ORDENAR 1 -> "+i);
            // i-9 = primer item ordenado
            if(items.get(i-9).getType() == Material.AIR){
                offsetForAirContainersOn1 += 1;
                continue;
            }
            inventory.setItem(offset1,items.get(i-9));
            offset1 += 1;
        }

        if(includeHotbar){
            if(offsetForAirContainersOn1 > 0){
                offsetForAirContainersOn1COPY = offsetForAirContainersOn1;
                for(int i = 0 ; i <= offsetForAirContainersOn1 ; i++){
                    inventory.setItem(35-offsetForAirContainersOn1,items.get(i+27));
                    //System.out.println(35-offsetForAirContainersOn1);
                    offsetForAirContainersOn1 -= 1;
                }
            }
            // 35 - 9 = 26
            for(int i = offsetForAirContainersOn1COPY ; i <= 8 ; i++){
                //System.out.println("ORDENAR 2 -> "+i);
                if(items.get(i+27).getType() == Material.AIR){
                    continue;
                }
                inventory.setItem(offset2,items.get(i+27));
                offset2+=1;
            }
        }
        receiver.updateInventory();
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SORTINVENTORY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SORTINVENTORY {HOTBAR INCLUDED DEFAULT FALSE} {AMOUNT/TYPE}";
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
