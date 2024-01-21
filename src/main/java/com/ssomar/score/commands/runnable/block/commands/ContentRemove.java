package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* CONTENT_REMOVE ITEM AMOUNT*/
public class ContentRemove extends BlockCommand {

    private static final boolean DEBUG = true;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {

        Optional<Double> intOptional = NTools.getDouble(args.get(1));
        int amount = intOptional.orElse(1.0).intValue();

        if (args.size() >= 1) {

            //ItemStack item = new ItemStack(Material.getMaterial(args.get(0)),amount);

            if (block.getState() instanceof Container) {

                Container container = (Container) block.getState();
                Inventory inv = container.getInventory();

                //inv.remove(item);
                //I TRIED THE inv.remove() but didn't work, I don't know why, tried many times.

                for (int i = 0; i < amount; i++) {
                    for (ItemStack itemChest : inv.getStorageContents()) {
                        if (itemChest == null) continue;

                        if(!(args.get(0).contains("EI:") || args.get(0).contains("ei:"))) {
                            if (itemChest.getType() == Material.valueOf(args.get(0))) {
                                itemChest.setAmount(itemChest.getAmount() - 1);
                                break;
                            }
                        }
                        else{
                           ExecutableItemObject eio = new ExecutableItemObject(itemChest);
                            if(eio.isValid()){
                                String id = args.get(0).split(":")[1];
                                if(eio.getConfig().getId().equalsIgnoreCase(id)){
                                    itemChest.setAmount(itemChest.getAmount() - 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        if(!(args.get(0).contains("EI:") || args.get(0).contains("ei:"))) {
            ArgumentChecker ac = checkMaterial(args.get(0), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        if (args.size() >= 2) {
            ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CONTENT_REMOVE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CONTENT_REMOVE {Item} [Amount]";
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
