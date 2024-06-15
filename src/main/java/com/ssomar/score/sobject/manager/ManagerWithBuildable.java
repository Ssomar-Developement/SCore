package com.ssomar.score.sobject.manager;

import com.ssomar.score.sobject.SObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ManagerWithBuildable<T extends SObject> {

    Optional<SObject> getObject(ItemStack item);


    // %ei_checkamount% count all
    // %ei_checkamount_slot:0,2,3,4%
    // %ei_checkamount_id:item1,item2_slot:0,2,3,4%
    static Optional<String> checkObjectAmountInInvPlaceholder(ManagerWithBuildable instance, PlayerInventory inv, String placeholder){
        if(placeholder.startsWith("checkamount")){
            List<Integer> checkSlots = new ArrayList<>();
            List<String> searchObject = new ArrayList<>();
            if(placeholder.contains("_slot:")){
                String[] split = placeholder.split("_slot:");
                if(split.length > 1){
                    String[] slotsStr = split[1].split("_id:")[0].split(",");
                    for(String s : slotsStr){
                        try{
                            checkSlots.add(Integer.parseInt(s));
                        }catch (NumberFormatException e){}
                    }
                }
            }
            if(placeholder.contains("_id:")){
                String[] split = placeholder.split("_id:");
                if(split.length > 1){
                    String[] objects = split[1].split("_slot:")[0].split(",");
                    Collections.addAll(searchObject, objects);
                }
            }

            int count = checkObjectAmountInInv(instance, inv, checkSlots, searchObject);
            return Optional.of(String.valueOf(count));
        }
        return Optional.empty();
    }

    static int checkObjectAmountInInv(ManagerWithBuildable instance, PlayerInventory inv, List<Integer> checkSlots, List<String> searchObject){

        int count = 0;
        if(checkSlots.isEmpty()){
            for(int i = 0; i < inv.getSize(); i++){
                ItemStack item = inv.getItem(i);
                if(item != null){
                    Optional<SObject> object = instance.getObject(item);
                    if(object.isPresent()){
                        if(searchObject.isEmpty()) count += item.getAmount();
                        else if(searchObject.contains(object.get().getId())) count += item.getAmount();
                    }
                }
            }
        }
        else{
            for(int i : checkSlots){
                if(i == -1) i = inv.getHeldItemSlot();
                ItemStack item = inv.getItem(i);
                if(item != null){
                    Optional<SObject> object = instance.getObject(item);
                    if(object.isPresent()){
                        if(searchObject.isEmpty()) count += item.getAmount();
                        else if(searchObject.contains(object.get().getId())) count += item.getAmount();
                    }
                }
            }
        }
        return count;
    }
}
