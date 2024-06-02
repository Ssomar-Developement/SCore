package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.ToolsListMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/* FARMINCUBE {radius} {ActiveDrop true or false} */
public class PlantInSquare extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        int radius = Integer.parseInt(args.get(0));
        boolean takeFromInventory = true;
        if(args.size() > 1) takeFromInventory = Boolean.parseBoolean(args.get(1));
        boolean accepteEI = false;
        if(args.size() > 2) accepteEI = Boolean.parseBoolean(args.get(2));
        List<String> cropTypes = new ArrayList<>();
        if(args.size() > 3) cropTypes = Arrays.asList(args.get(3).toUpperCase().split(","));

        List<Material> acceptedBlocks;
        Material determineMode = block.getType();
        if(determineMode == Material.FARMLAND){
            acceptedBlocks = ToolsListMaterial.getInstance().getPlantWithGrowthOnlyFarmland();
        }
        else if(determineMode == Material.SOUL_SAND){
            acceptedBlocks = ToolsListMaterial.getInstance().getPlantWithGrowthOnlySoulSand();
        }
        else return;


        int resourcesNeeded = (radius*2+1)* (radius*2+1);
        if(radius == 0) resourcesNeeded = 1;

        List<Material> validMaterial = new ArrayList<>();
        for (Material cropMaterial : acceptedBlocks) {

            if(cropTypes.isEmpty() || cropTypes.contains(cropMaterial.toString())) validMaterial.add(ToolsListMaterial.getInstance().getRealMaterialOfBlock(cropMaterial));
        }

        Map<Integer, ItemStack> resources = new HashMap<>();


        if(takeFromInventory) {
            int slot = 0;
            //SsomarDev.testMsg("resourcesNeeded: "+resourcesNeeded ,true);
            for (ItemStack item : p.getInventory().getContents()) {
                //SsomarDev.testMsg("item: "+item ,true);
                if (item != null && validMaterial.contains(item.getType()) && (accepteEI || !new ExecutableItemObject(item).isValid())) {
                    //SsomarDev.testMsg("item valis: "+item ,true);
                    resources.put(slot, item);
                    resourcesNeeded = resourcesNeeded - item.getAmount();
                }
                if (resourcesNeeded <= 0) break;
                slot++;
            }
        }
        else{
            // random resources
            int slot = 0;
            while(resourcesNeeded > 0){
                // get random index of validMaterial
                int randomIndex = new Random().nextInt(validMaterial.size());
                Material randomMaterial = validMaterial.get(randomIndex);
                ItemStack item = new ItemStack(randomMaterial);
                // get random amount of item
                int randomAmount = new Random().nextInt(64);
                item.setAmount(randomAmount);
                resources.put(slot, item);
                resourcesNeeded = resourcesNeeded - randomAmount;
                slot++;
            }
        }

        //print resources to ssomar
        if(!resources.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ItemStack item : resources.values()) {
                sb.append(item.getAmount()).append(" ").append(item.getType().toString()).append(", ");
            }
            SsomarDev.testMsg(ChatColor.GREEN + "Resources: " + sb.toString(), true);
        }

        if(radius == 0){
            ItemStack item = getValidItem(resources);
            if (item == null) return;
            plant(determineMode, block, item);
        }

        for (int x = -radius; x < radius + 1; x++) {
            for (int z = -radius; z < radius + 1; z++) {
                ItemStack item = getValidItem(resources);
                if (item == null) break;
                Block farm = block.getWorld().getBlockAt(block.getX() + x, block.getY() , block.getZ() + z);
                plant(determineMode, farm, item);
            }
        }

        if(takeFromInventory) {
            Inventory inv = p.getInventory();
            for (int s : resources.keySet()) {
                inv.setItem(s, resources.get(s));
            }
        }

    }

    public void plant(Material mode, Block farm, ItemStack item){
        if(farm.getType() == mode){
            Block toPlant = farm.getWorld().getBlockAt(farm.getX(), farm.getY() + 1, farm.getZ());
            if(toPlant.isEmpty()){
                toPlant.setType(ToolsListMaterial.getInstance().getBlockMaterialOfItem(item.getType()));
                item.setAmount(item.getAmount() - 1);
            }
        }
    }

    public ItemStack getValidItem(Map<Integer, ItemStack> disposal){
        for(ItemStack item : disposal.values()){
            if(item.getAmount() > 0) return item;
        }
        return null;
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PLANT_IN_SQUARE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PLANT_IN_SQUARE {radius} [takeFromInv] [acceptEI] [cropType]";
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
