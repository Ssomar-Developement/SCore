package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.executableitems.executableitems.manager.ExecutableItemsManager;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.strings.StringSetting;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/* CONTENT_ADD ITEM AMOUNT*/
public class ContentAdd extends BlockCommand {

    private static final boolean DEBUG = true;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        args = new ArrayList<>(args);
        Map<String, Object> settings = StringSetting.extractSettingsAndRebuildCorrectly(args, 0, Arrays.asList("EI:", "ei:"));

        /*for (String s : settings.keySet()) {
            System.out.println(s + " : " + settings.get(s));
        }*/

        Optional<Double> intOptional = NTools.getDouble(args.get(1));
        int amount = intOptional.orElse(1.0).intValue();

        Optional<Integer> slotOptional = Optional.empty();
        if(args.size() >= 3) slotOptional = NTools.getInteger(args.get(2));
        int slot = slotOptional.orElse(-1);

        if (args.size() >= 1) {
            ItemStack item;
            if(!(args.get(0).contains("EI:") || args.get(0).contains("ei:"))) {
                item = new ItemStack(Material.valueOf(args.get(0)), amount);
            }
            else{
                String id = args.get(0).split(":")[1];

                Optional<ExecutableItemInterface> ei = ExecutableItemsManager.getInstance().getExecutableItem(id);
                if(ei.isPresent()) {
                    item = ei.get().buildItem(amount, Optional.ofNullable(p), settings);
                }
                else return;
            }
            if (block.getState() instanceof Container) {
                Container container = (Container) block.getState();
                Inventory inv = container.getInventory();

                if(slot == -1) inv.addItem(item);
                else {
                    ItemStack item2 = inv.getItem(slot);
                    if(item2 == null) inv.setItem(slot, item);
                    else {
                        if(item2.isSimilar(item)) {
                            item2.setAmount(item2.getAmount() + item.getAmount());
                            inv.setItem(slot, item2);
                        }
                        // else The item in the slot "+slot+" is not similar to the item you want to add"
                        else inv.addItem(item);
                    }
                }
            }
        }

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        args = new ArrayList<>(args);

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        if(!(args.get(0).contains("EI:") || args.get(0).contains("ei:"))) {
            ArgumentChecker ac = checkMaterial(args.get(0), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }
        else StringSetting.extractSettingsAndRebuildCorrectly(args, 0, Arrays.asList("EI:", "ei:"));

        if (args.size() >= 2) {
            ArgumentChecker ac2 = checkDouble(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        if (args.size() >= 3) {
            ArgumentChecker ac3 = checkInteger(args.get(2), isFinalVerification, getTemplate());
            if (!ac3.isValid()) return Optional.of(ac3.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("CONTENT_ADD");
        return names;
    }

    @Override
    public String getTemplate() {
        return "CONTENT_ADD {Item} [Amount] [slot]";
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
