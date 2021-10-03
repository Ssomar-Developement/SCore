package com.ssomar.score.sobject.enchantments;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class SEnchantments extends HashMap<SEnchantment, Integer> {

    public void loadEnchantments(SPlugin sPlugin, ConfigurationSection enchtsSection, List<String> errorList) {
        /*
         * enchantments: 1: enchantment: DURABILITY level: 2
         */
        this.clear();
        for (String id : enchtsSection.getKeys(false)) {
            ConfigurationSection enchtSection = enchtsSection.getConfigurationSection(id);
            Enchantment enchantment;
            try {
                if (!SCore.is1v12())
                    enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchtSection.getString("enchantment").toLowerCase()));
                else
                    enchantment = Enchantment.getByName(enchtSection.getString("enchantment"));
            } catch (Exception error) {
                errorList.add(sPlugin.getNameDesign()+" Enchantment with id: " + id+ " has invalid enchantment: " + enchtSection.getString("enchantment") + " !");
                continue;
            }

            int level = enchtSection.getInt("level", 1);
            if (enchantment == null) continue; // pour la 1.12 sinon bug
            SEnchantment sEnchantment = new SEnchantment(enchantment, id);
            this.put(sEnchantment, level);
        };
    }

    public void saveEnchantments(SPlugin sPlugin, SObject sObject) {

        // TODO not general string
        if(!new File(sObject.getPath()).exists()) {
            sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the item in the folder items ! ("+sObject.getID()+".yml)");
            return;
        }
        File file = new File(sObject.getPath());
        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

        config.set("enchantments", null);

        for(SEnchantment sEnch : this.keySet()) {
            if (!SCore.is1v12()) {
                config.set("enchantments." + sEnch.getId() + ".enchantment", sEnch.getEnchantment().getKey().toString().split("minecraft:")[1]);
            } else config.set("enchantments." + sEnch.getId() + ".enchantment", sEnch.getEnchantment().getName());
            config.set("enchantments." + sEnch.getId() + ".level", this.get(sEnch));
        }
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

            try {
                writer.write(config.saveToString());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
