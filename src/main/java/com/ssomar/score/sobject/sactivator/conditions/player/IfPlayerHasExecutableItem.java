package com.ssomar.score.sobject.sactivator.conditions.player;

import com.ssomar.executableitems.items.ExecutableItem;
import com.ssomar.score.utils.StringCalculation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class IfPlayerHasExecutableItem {

    private String executableItemID;
    private int slot;
    private String usageCalcul;
    private boolean isValid;

    public IfPlayerHasExecutableItem(String condition){
        if(condition.contains(":")){
            String [] tab = condition.split(":");
            executableItemID = tab[0];
            try {
                slot = Integer.parseInt(tab[1]);
                isValid = true;
            }catch (NumberFormatException e){
                isValid = false;
            }
        }
        else isValid = false;
    }

    public IfPlayerHasExecutableItem(ConfigurationSection section){
       executableItemID = section.getString("executableItemID");
       if(executableItemID == null){
           isValid = false;
           return;
       }

       slot = section.getInt("slot", -2);
        if(slot < -1){
            isValid = false;
            return;
        }

        usageCalcul = section.getString("usageCalcul");
        isValid = true;
    }


    public boolean verify(@NotNull Player p){
        PlayerInventory pInv = p.getInventory();
        ItemStack item;
        if(slot != -1) item = pInv.getItem(slot);
        else item = pInv.getItem(pInv.getHeldItemSlot());

        ExecutableItem ei = new ExecutableItem(item);
        if(!ei.isValid() || !ei.getConfig().getID().equals(executableItemID)) return false;

        Optional<Integer> usageOpt = ei.getUsage();
        if(usageCalcul != null){
            if(usageOpt.isPresent()){
                if(!StringCalculation.calculation(usageCalcul, usageOpt.get())) return false;
            }
            else return false;
        }

        return true;
    }

    public boolean isValid() {
        return isValid;
    }
}
