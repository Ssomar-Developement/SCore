package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/* FORMAT_ENCHANTMENTS {slot}*/
public class FormatEnchantments extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        try {
            Bukkit.getScheduler().runTaskLater(com.ssomar.score.SCore.plugin, new Runnable() {
                @Override
                public void run() {
                    ItemStack item;
                    ItemMeta itemmeta;
                    List<String> LoreCURRENT;

                    item = receiver.getInventory().getItem(Integer.valueOf(args.get(0)));
                    if (item == null) return;

                    itemmeta = item.getItemMeta();

                    LoreCURRENT = itemmeta.getLore();
                    if (LoreCURRENT == null) {
                        LoreCURRENT = new ArrayList<>();
                    }
                    List<String> LoreOfEnchantmentsArtifical = new ArrayList<>();

                    Iterator enchantments = itemmeta.getEnchants().keySet().iterator();

                    while (enchantments.hasNext()) {
                        Enchantment enchant = (Enchantment) enchantments.next();

                        String line = getNameOfEnchantment(enchant.getKey().toString());
                        line = line + " " + getCODIGOROMANO(item.getEnchantmentLevel(enchant));

                        LoreOfEnchantmentsArtifical.add(line);
                    }

                    SsomarDev.testMsg(String.valueOf(LoreOfEnchantmentsArtifical), true);

                    for (int i = 0; i < LoreCURRENT.size(); i++) {
                        for (String enchantmentlore : LoreOfEnchantmentsArtifical) {
                            if (LoreCURRENT.get(i).contains(enchantmentlore)) {
                                LoreCURRENT.remove(i);
                            }
                        }
                    }

                    SsomarDev.testMsg(String.valueOf(LoreCURRENT), true);

                    for (String enchantmentlore : LoreCURRENT) {
                        LoreOfEnchantmentsArtifical.add(enchantmentlore);
                    }

                    SsomarDev.testMsg(String.valueOf(LoreOfEnchantmentsArtifical), true);

                    itemmeta.setLore(LoreOfEnchantmentsArtifical);
                    itemmeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
                    item.setItemMeta(itemmeta);
                }
            }, 1L);

        } catch (NullPointerException e) {
            return;
        }
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORMAT_ENCHANTMENTS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORMAT_ENCHANTMENTS {slot}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    public static String getNameOfEnchantment(String name) {
        return ChatColor.GRAY + WordUtils.capitalize(name.replace("minecraft:", "").replace("_", " "));
    }

    public static String getCODIGOROMANO(int numerito) {

        HashMap<Integer, String> codigos_romanos = new HashMap<>();

        codigos_romanos.put(1000, "M");
        codigos_romanos.put(900, "CM");
        codigos_romanos.put(500, "D");
        codigos_romanos.put(400, "CD");
        codigos_romanos.put(100, "C");
        codigos_romanos.put(90, "XC");
        codigos_romanos.put(50, "L");
        codigos_romanos.put(40, "XL");
        codigos_romanos.put(10, "X");
        codigos_romanos.put(9, "IX");
        codigos_romanos.put(5, "V");
        codigos_romanos.put(4, "IV");
        codigos_romanos.put(1, "I");

        ArrayList<Integer> numeros_claves = new ArrayList<>();
        numeros_claves.add(1000);
        numeros_claves.add(900);
        numeros_claves.add(500);
        numeros_claves.add(400);
        numeros_claves.add(100);
        numeros_claves.add(90);
        numeros_claves.add(50);
        numeros_claves.add(40);
        numeros_claves.add(10);
        numeros_claves.add(9);
        numeros_claves.add(5);
        numeros_claves.add(4);
        numeros_claves.add(1);

        StringBuilder builder = new StringBuilder();

        //for(int i = 0 ; i < 20 ; i++)
        while (numerito != 0) {
            for (Integer codigos_romanos_array : numeros_claves) {
                while (numerito / codigos_romanos_array >= 1) {
                    builder.append(codigos_romanos.get(codigos_romanos_array));
                    numerito += -codigos_romanos_array;
                }
            }
        }

        String codigoROMANOIDE = builder.toString();

        return codigoROMANOIDE;
    }
}
