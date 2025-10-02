package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.ToolsListMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class PlantInSquare extends BlockCommand {

    public PlantInSquare() {
        CommandSetting radius = new CommandSetting("radius", 0, Integer.class, 1);
        CommandSetting takeFromInv = new CommandSetting("takeFromInv", 1, Boolean.class, false);
        CommandSetting acceptEI = new CommandSetting("acceptEI", 2, Boolean.class, false);
        CommandSetting cropType = new CommandSetting("cropType", 3, String.class, "WHEAT");
        CommandSetting isCube = new CommandSetting("isCube", 4, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(radius);
        settings.add(takeFromInv);
        settings.add(acceptEI);
        settings.add(cropType);
        settings.add(isCube);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, @NotNull Block block, SCommandToExec sCommandToExec) {
        // #1
        // args
        int radius = Integer.parseInt(sCommandToExec.getSettingValue("radius").toString());
        boolean takeFromInventory = Boolean.parseBoolean(sCommandToExec.getSettingValue("takeFromInv").toString());
        boolean acceptEI = Boolean.parseBoolean(sCommandToExec.getSettingValue("acceptEI").toString());
        List<String> specifiedCropTypes = Arrays.asList(sCommandToExec.getSettingValue("cropType").toString().toUpperCase().split(","));
        boolean isCube = Boolean.parseBoolean(sCommandToExec.getSettingValue("isCube").toString());

        // #2
        List<Material> validCropsToPlace;
        // get the clicked block to identify what crops to plant
        Material determineMode = block.getType();
        if(determineMode == Material.FARMLAND){
            validCropsToPlace = ToolsListMaterial.getInstance().getPlantWithGrowthOnlyFarmland();
        }
        else if(determineMode == Material.SOUL_SAND){
            validCropsToPlace = ToolsListMaterial.getInstance().getPlantWithGrowthOnlySoulSand();
        }
        else if (determineMode == Material.JUNGLE_LOG || (!SCore.is1v13Less() && determineMode == Material.JUNGLE_WOOD)) {
            validCropsToPlace = ToolsListMaterial.getInstance().getPlantWithGrowthOnlyJungleWood();
        }
        else {
            SsomarDev.testMsg("[#s0001] CLICKED BLOCK IS UNSUPPORTED", true);
            return;
        }

        // #3
        // compute the estimate resources needed to plant
        int resourcesNeeded = (radius*2+1)* (radius*2+1);
        if(radius == 0) resourcesNeeded = 1;

        // #4
        // start listing down crops to place
        List<Material> cropsToPlace = new ArrayList<>();
        for (Material cropMaterial : validCropsToPlace) {
            // first condition is for making the 4th arg optional
            if(specifiedCropTypes.isEmpty() || specifiedCropTypes.contains(cropMaterial.toString())) {
                cropsToPlace.add(ToolsListMaterial.getInstance().getRealMaterialOfBlock(cropMaterial));
            }
        }


        // #5
        // It's registered as <item slot, item stack details>
        Map<Integer, ItemStack> resources = new HashMap<>();

        // #6
        if(takeFromInventory) {
            int slot = 0;
            //SsomarDev.testMsg("resourcesNeeded: "+resourcesNeeded ,true);
            for (ItemStack item : p.getInventory().getContents()) {
                //SsomarDev.testMsg("item: "+item ,true);
                if (item != null && cropsToPlace.contains(item.getType()) && (acceptEI || !new ExecutableItemObject(item).isValid())) {
                    //SsomarDev.testMsg("item valis: "+item ,true);
                    resources.put(slot, item);
                    resourcesNeeded = resourcesNeeded - item.getAmount();
                }
                if (resourcesNeeded <= 0) break;
                slot++;
            }
        }
        // #7
        else{
            // random resources
            int slot = 0;
            while(resourcesNeeded > 0){
                // get random index of validMaterial
                int randomIndex = new Random().nextInt(cropsToPlace.size());
                Material randomMaterial = cropsToPlace.get(randomIndex);
                ItemStack item = new ItemStack(randomMaterial);
                // get random amount of item
                int randomAmount = new Random().nextInt(64);
                item.setAmount(randomAmount);
                resources.put(slot, item);
                resourcesNeeded -= randomAmount;
                slot++;
            }
        }

        // #8
        //print resources to ssomar
        if(!resources.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ItemStack item : resources.values()) {
                sb.append(item.getAmount()).append(" ").append(item.getType().toString()).append(", ");
            }
            SsomarDev.testMsg(ChatColor.GREEN + "[#s0002] Resources: " + sb.toString(), true);
        }

        // #9
        if(radius == 0){
            ItemStack item = getValidItem(resources);
            if (item == null) return;
            plant(determineMode, block, item);
        }

        // #10
        for (int x = -radius; x < radius + 1; x++) {
            for (int z = -radius; z < radius + 1; z++) {
                if (isCube) {
                    for (int y = -radius; y < radius + 1; y++) {
                        ItemStack item = getValidItem(resources);
                        if (item == null) break;
                        Block farm = block.getWorld().getBlockAt(block.getX() + x, block.getY() + y, block.getZ() + z);
                        plant(determineMode, farm, item);
                    }
                } else {
                    ItemStack item = getValidItem(resources);
                    if (item == null) break;
                    Block farm = block.getWorld().getBlockAt(block.getX() + x, block.getY(), block.getZ() + z);
                    plant(determineMode, farm, item);
                }
            }
        }

        if(takeFromInventory) {
            Inventory inv = p.getInventory();
            for (int s : resources.keySet()) {
                inv.setItem(s, resources.get(s));
            }
        }

    }

    /**
     *
     * @param mode The clicked block
     * @param farm The surrounding blocks including the clicked block where the crops would be placed above
     * @param item The crop material
     */
    private void plant(Material mode, Block farm, ItemStack item){
        // the else logic wants to place crops above the farm block but
        // if we want to plant cocoa, we'd want to do it differently.
        if (farm.getType() != mode) {
            // check if the clicked block is a jungle log/wood
            if (!farm.isEmpty()) return;
            if ((mode == Material.JUNGLE_LOG || mode == Material.JUNGLE_WOOD) && farm.getType() == Material.AIR) {
                plantCocoa(farm, item);
            }
        } else {
            if (item.getType() == Material.COCOA_BEANS) return;
            Block toPlant = farm.getWorld().getBlockAt(farm.getX(), farm.getY() + 1, farm.getZ());
            if (!toPlant.isEmpty()) {
                return;
            }
            toPlant.setType(ToolsListMaterial.getInstance().getBlockMaterialOfItem(item.getType()));
            item.setAmount(item.getAmount() - 1);
        }
    }

    private ItemStack getValidItem(Map<Integer, ItemStack> disposal){
        for(ItemStack item : disposal.values()){
            if(item.getAmount() > 0) return item;
        }
        return null;
    }

    private void plantCocoa(Block farm, ItemStack item) {
        // check the surroundings of the target block because cocoa grows on the walls of jungle logs/woods
        Object[][] offsetChecks = new Object[][] {
                { 1, 0, BlockFace.EAST },
                { -1, 0, BlockFace.WEST },
                { 0, 1, BlockFace.SOUTH },
                { 0, -1, BlockFace.NORTH }
        };

        // iterate through the offsetchecks to inspect each corner then set the direction of the cocoa accordingly.
        for (Object[] offsetVal : offsetChecks) {
            Material neigborBlock = farm.getWorld().getBlockAt(farm.getX() + (int)offsetVal[0], farm.getY(), farm.getZ() + (int)offsetVal[1]).getType();
            if (neigborBlock.equals(Material.JUNGLE_WOOD) || neigborBlock.equals(Material.JUNGLE_LOG)) {
                farm.setType(Material.COCOA);
                BlockData toPlantData = farm.getBlockData();
                Directional toPlantDirectional = (Directional) toPlantData;
                toPlantDirectional.setFacing((BlockFace) offsetVal[2]);
                farm.setBlockData(toPlantData);
                item.setAmount(item.getAmount() - 1);
                break;
            }
        }


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
        return "PLANT_IN_SQUARE radius:2 takeFromInv:false acceptEI:false cropType:WHEAT isCube:false";
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
