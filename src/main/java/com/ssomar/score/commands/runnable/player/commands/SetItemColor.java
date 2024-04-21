package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETITEMCOLOR {slot} {color in number} */
public class SetItemColor extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        ItemStack item;
        ItemMeta itemmeta;

        try {
            int slot = Integer.parseInt(args.get(0));
            if(slot == -1) item = receiver.getInventory().getItemInMainHand();
            else item = receiver.getInventory().getItem(slot);
            itemmeta = item.getItemMeta();

        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }

        String colorStr = args.get(1);
        colorStr = StringPlaceholder.replacePlaceholderOfPAPI(colorStr, null);
        Optional<Integer> colorOpt = NTools.getInteger(colorStr);

        if (!colorOpt.isPresent())  return;

        int color = colorOpt.get();

        Material material = item.getType();
        if (material.equals(Material.LEATHER_BOOTS) ||
                material.equals(Material.LEATHER_CHESTPLATE) ||
                material.equals(Material.LEATHER_LEGGINGS) ||
                material.equals(Material.LEATHER_HELMET)
                || (!SCore.is1v13Less() && material.equals(Material.LEATHER_HORSE_ARMOR))) {

            LeatherArmorMeta aMeta = (LeatherArmorMeta) itemmeta;
            aMeta.setColor(Color.fromRGB(color));
        }
        else if(itemmeta instanceof FireworkEffectMeta){
            FireworkEffectMeta fMeta = (FireworkEffectMeta) itemmeta;
            FireworkEffect aa = FireworkEffect.builder().withColor(Color.fromRGB(color)).build();
            fMeta.setEffect(aa);
        }

        item.setItemMeta(itemmeta);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETITEMCOLOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETITEMCOLOR {slot} {color in number}";
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
