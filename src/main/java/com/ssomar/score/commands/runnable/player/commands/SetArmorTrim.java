package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetArmorTrim extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        int slot = NTools.getInteger(args.get(0)).get();
        ItemStack item;
        if(slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        ItemMeta itemMeta;
        try {
            itemMeta = item.getItemMeta();
        }catch(Exception e){
            return;
        }


        if (itemMeta instanceof ArmorMeta) {
            ArmorMeta armor = (ArmorMeta) itemMeta;

            String arg1 = args.get(1);
            if(arg1.equalsIgnoreCase("remove") || arg1.equalsIgnoreCase("null")){
                armor.setTrim(null);
                item.setItemMeta(itemMeta);
                return;
            }

            TrimPattern trimPattern = getTrimPattern(args.get(1));
            if (trimPattern == null) return;

            TrimMaterial trimMaterial = getTrimMaterial(args.get(2));
            if (trimMaterial == null) return;


            ArmorTrim armorTrim = new ArmorTrim(trimMaterial,trimPattern);

            try {
                armor.setTrim(armorTrim);
                item.setItemMeta(itemMeta);
            }catch(Exception e){
                return;
            }

        }
    }

    private TrimMaterial getTrimMaterial(String str) {
        switch (str) {
            case "gold":
                return TrimMaterial.GOLD;
            case "amethyst":
                return TrimMaterial.AMETHYST;
            case "copper":
                return TrimMaterial.COPPER;
            case "diamond":
                return TrimMaterial.DIAMOND;
            case "iron":
                return TrimMaterial.IRON;
            case "emerald":
                return TrimMaterial.EMERALD;
            case "lapis":
                return TrimMaterial.LAPIS;
            case "netherite":
                return TrimMaterial.NETHERITE;
            case "quartz":
                return TrimMaterial.QUARTZ;
            case "redstone":
                return TrimMaterial.REDSTONE;
            default:
                return null;
        }
    }

    private TrimPattern getTrimPattern(String str) {
        switch (str) {
            case "coast":
                return TrimPattern.COAST;
            case "eye":
                return TrimPattern.EYE;
            case "dune":
                return TrimPattern.DUNE;
            case "rib":
                return TrimPattern.RIB;
            case "host":
                return TrimPattern.HOST;
            case "raiser":
                return TrimPattern.RAISER;
            case "sentry":
                return TrimPattern.SENTRY;
            case "shaper":
                return TrimPattern.SHAPER;
            case "silence":
                return TrimPattern.SILENCE;
            case "snout":
                return TrimPattern.SNOUT;
            case "spire":
                return TrimPattern.SPIRE;
            case "tide":
                return TrimPattern.TIDE;
            case "vex":
                return TrimPattern.VEX;
            case "ward":
                return TrimPattern.WARD;
            case "wayfinder":
                return TrimPattern.WAYFINDER;
            case "wild":
                return TrimPattern.WILD;
            default:
                return null;
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETARMORTRIM");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETARMORTRIM {slot} {PATTERN} {MATERIAL OF PATTERN}";
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
