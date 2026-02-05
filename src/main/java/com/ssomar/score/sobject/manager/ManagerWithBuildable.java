package com.ssomar.score.sobject.manager;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.SCore;
import com.ssomar.score.features.custom.variables.real.VariableReal;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.utils.strings.StringArgExtractor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

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
                        }catch (NumberFormatException ignored){}
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

    /**
     * "var:" is required to work.<br/>
     * You won't see any usages in this project because it's used by ExecutableItems's and ExecutableBlocks's codebase<br/>
     * <br/>
     * Custom placeholder that returns the value of an internal variable within.<br/>
     * If the user provides an array of slot values, it will assume the datatype<br/>
     * value of the target variables are integer/double types and add them up in a<br/>
     * "double" variable.<br/>
     * <br/>
     * If the user passes multiple values in "var:", it will also add them up<br/>
     * in a "double" variable.<br/>
     * @param inv
     * @param placeholder
     * @return Value of either extracted String value of sum of all mentioned target slots
     */
    /*
    Sample/Reference Text: executableitems_checkvar_slot:36,37,38,39_var:bonus
     */
    static Optional<String> getObjectVariableValueInInvPlaceholder(ManagerWithBuildable instance, PlayerInventory inv, String placeholder) {
        if(placeholder.startsWith("checkvar")){
            ArrayList<String> searchVariableKey = new ArrayList<>(Arrays.asList(StringArgExtractor.extractArgValue(placeholder, "var")));
            ArrayList<String> searchObject = new ArrayList<>(Arrays.asList(StringArgExtractor.extractArgValue(placeholder, "id")));
            ArrayList<Integer> checkSlots = new ArrayList<>();
            for (String val : StringArgExtractor.extractArgValue(placeholder, "slot")) {
                if (val.equals("-1")) {
                    checkSlots.add(inv.getHeldItemSlot());
                }
                else checkSlots.add(Integer.valueOf(val));
            }

            // Immediately quit if there's no var key provided.
            if (searchVariableKey.isEmpty()) return Optional.empty(); // to immediately end the method if no "var" is provided.

            double totalNumValue = 0; // To be converted to string later. Will not be used if checkSlots's length is exactly 1

            for (int slotToEval = 0; slotToEval<41; slotToEval++) {
                if (checkSlots.isEmpty() || checkSlots.contains(slotToEval)) {
                    ItemStack itemStack = inv.getItem(slotToEval);

                    if (itemStack == null) continue;

                    ExecutableItemObject ei = new ExecutableItemObject(itemStack);
                    if (ei.isValid()) {
                        if (!(searchObject.isEmpty() || searchObject.contains(ei.getConfig().getId()))) continue;
                        ei.loadExecutableItemInfos();
                        for (VariableReal vR : ei.getInternalData().getVariableRealsList()) {
                            if (searchVariableKey.contains(vR.getConfig().getVariableName().getValue().get())) {
                                if (checkSlots.size() == 1) {
                                    if (vR.getValue() instanceof Double) {
                                        return vR.getValue().toString().describeConstable();
                                    }
                                    else {
                                        return Optional.of((String) vR.getValue());
                                    }
                                } else {
                                    if (vR.getValue() instanceof Double) {
                                        totalNumValue += (Double) vR.getValue();
                                    } else {
                                        return Optional.of((String) vR.getValue());
                                    }

                                }
                            }
                        }
                    }

                }
            }

            return Double.toString(totalNumValue).describeConstable();
        }
        return Optional.empty();
    }

}
