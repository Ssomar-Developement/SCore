package com.ssomar.score.commands.runnable.block.commands;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Worth.WorthItem;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.usedapi.Dependency;
import com.ssomar.score.usedapi.ShopGUIPlusTool;
import com.ssomar.score.usedapi.VaultAPI;
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

/* STRIKELIGHTNING */
public class SellContent extends BlockCommand {

    private static final boolean DEBUG = true;

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        SsomarDev.testMsg("block type : " + block.getType(), DEBUG);
        SsomarDev.testMsg("SellContent activated > container ? " + (block.getState() instanceof Container) + " player ? " + (p != null), DEBUG);


        double priceBoost = 1;
        if (args.size() >= 1) {
            priceBoost = Double.valueOf(args.get(0));
        }

        if (block.getState() instanceof Container && p != null) {
            Container container = (Container) block.getState();
            Inventory inv = container.getInventory();
            double amount = 0;
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                if (item != null) {
                    if (SCore.hasShopGUIPlus) {
                        double check = ShopGUIPlusTool.sellItem(p, item);
                        SsomarDev.testMsg("item : " + item.getType() + " qty: " + item.getAmount() + "check : " + check, DEBUG);
                        if (check > 0) {
                            amount += check;
                            ShopGUIPlusTool.registerTransaction(item, p, check, priceBoost);
                            item.setAmount(0);
                        }
                    }
                    else if(Dependency.CMI.isEnabled()){
                        WorthItem worth = CMI.getInstance().getWorthManager().getWorth(item);
                        if (worth == null){
                            // Worthless item so we can return null or 0D, whatever is needed in your case
                           continue;
                        }
                        // Sell price defines actual worth of the file
                        Double sellPrice = worth.getSellPrice();
                        amount += sellPrice;
                        item.setAmount(0);
                    }
                }
            }
            if (amount > 0) {
                amount = amount * priceBoost;
                SsomarDev.testMsg("SellContent activated > amount > " + amount, DEBUG);
                VaultAPI v = new VaultAPI();
                v.verifEconomy(p);
                v.addMoney(p, amount);
            }
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() >= 1) {
            ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
            if (!ac.isValid()) return Optional.of(ac.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SELL_CONTENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SELL_CONTENT [price_boost]";
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
