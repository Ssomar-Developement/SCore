package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.NTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* DROPITEM {material} [quantity} */
public class DropItem extends BlockCommand {

    @Override
    public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        try {
            Optional<Double> quantityOpt = NTools.getDouble(args.get(1));
            if (quantityOpt.isPresent()) {
                int quantity = quantityOpt.get().intValue();
                if (quantity > 0) {
                    block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.valueOf(args.get(0).toUpperCase()), quantity));
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String dropitem = "DROPITEM {material} [quantity}";
        if (args.size() < 2) error = notEnoughArgs + dropitem;
        else if (args.size() == 2) {
            if (!args.get(0).contains("%") && Material.matchMaterial(args.get(0).toUpperCase()) == null)
                error = invalidMaterial + args.get(0) + " for command: " + dropitem;
            else {
                if (!args.get(1).contains("%")) {
                    try {
                        Integer.valueOf(args.get(1));
                    } catch (NumberFormatException e) {
                        error = invalidQuantity + args.get(1) + " for command: " + dropitem;
                    }
                }
            }
        } else error = tooManyArgs + dropitem;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DROPITEM");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DROPITEM {material} {quantity}";
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
