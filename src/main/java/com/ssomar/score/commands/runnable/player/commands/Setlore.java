package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETLORE {slot} {line} {text} */
public class Setlore extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        ItemStack item;
        ItemMeta itemmeta;

        try {
            int slot = NTools.getInteger(args.get(0)).get();
            SsomarDev.testMsg("SLOT >> "+slot, true);

            if (slot == -1) slot = receiver.getInventory().getHeldItemSlot();
            item = receiver.getInventory().getItem(slot);
            if (item == null || item.getType() == Material.AIR) return;

            // Not compatible with EI + usageModification

            //boolean isTheEIExecuted = false;
            /* if(SCore.hasExecutableItems && aInfo.getExecutableItem() != null && item.isSimilar(aInfo.getExecutableItem().getItem())){
                SsomarDev.testMsg("Setlore isTheEIExecuted", true);
                item = aInfo.getExecutableItem().getItem();
                isTheEIExecuted = true;
            }*/
            itemmeta = item.getItemMeta();

            StringBuilder build = new StringBuilder();
            for (int i = 2; i < args.size(); i++) {
                build.append(args.get(i) + " ");
            }

            List<String> list = itemmeta.getLore();

            if (list == null) return;
            if (list.size() < Integer.valueOf(args.get(1))) return;

            Integer index = Integer.valueOf(args.get(1));
            if(index > 0) index += -1;
            list.set(index, StringConverter.coloredString(build.toString()));

            itemmeta.setLore(list);
            item.setItemMeta(itemmeta);

            receiver.getInventory().setItem(slot, item);

        } catch (NullPointerException e) {
            return;
        }

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETLORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETLORE {slot} {line} {text dont need brackets}";
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
