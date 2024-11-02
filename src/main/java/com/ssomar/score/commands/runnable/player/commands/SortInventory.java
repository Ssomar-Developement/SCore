package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
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

public class SortInventory extends PlayerCommand {

    public SortInventory() {
        CommandSetting includeHotBar = new CommandSetting("includeHotBar", 0, Boolean.class, false);
        CommandSetting sortType = new CommandSetting("sortType", 1, String.class, "TYPE");
        List<CommandSetting> settings = getSettings();
        settings.add(includeHotBar);
        settings.add(sortType);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Boolean includeHotBar = (Boolean) sCommandToExec.getSettingValue("includeHotBar");
        String sortType = (String) sCommandToExec.getSettingValue("sortType");
        boolean sortByType = sortType.equalsIgnoreCase("type");

        if (receiver.isDead()) return;
        receiver.closeInventory();

        Inventory inventory = receiver.getInventory();
        ItemStack[] inventoryContents = inventory.getStorageContents();
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack air = new ItemStack(Material.AIR);

        //guardar todos los items en una carpeta
        for(int i = 0 ; i <= 35 ; i++){
            if(i <= 8){
                if(includeHotBar){
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
        if(includeHotBar){
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

        if(includeHotBar){
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
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SORT_INVENTORY");
        names.add("SORTINVENTORY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SORT_INVENTORY includeHotBar:false sortType:TYPE or sortType:AMOUNT";
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
