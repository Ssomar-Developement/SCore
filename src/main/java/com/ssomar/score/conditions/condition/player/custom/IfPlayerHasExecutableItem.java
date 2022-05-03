package com.ssomar.score.conditions.condition.player.custom;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Optional;

@Getter
@Setter
public class IfPlayerHasExecutableItem {

    private String id;
    private String executableItemID;
    private int slot;
    private Optional<String> usageCalcul;
    private boolean isValid;

    public IfPlayerHasExecutableItem(String id, ConfigurationSection section) {
        isValid = true;
        executableItemID = section.getString("executableItemID");
        if (executableItemID == null) {
            isValid = false;
        }

        slot = section.getInt("slot", -2);
        if (slot < -1) {
            isValid = false;
        }

        usageCalcul = Optional.ofNullable(section.getString("usageCalcul"));
        this.id = id;
    }

    public IfPlayerHasExecutableItem(String id) {
        this.id = id;
        executableItemID = "NOT DEFINE";
        isValid = false;
        slot = 0;
        usageCalcul = Optional.ofNullable(null);
    }


    public boolean verify(@NotNull Player p) {
        PlayerInventory pInv = p.getInventory();
        ItemStack item;
        if (slot != -1) item = pInv.getItem(slot);
        else item = pInv.getItem(pInv.getHeldItemSlot());

        ExecutableItemObject ei = new ExecutableItemObject(item);
        if (!ei.isValid() || !((SObject) (ei.getConfig())).getId().equals(executableItemID)) return false;

        if (usageCalcul.isPresent()) {
            if (!StringCalculation.calculation(usageCalcul.get(), ei.getUsage())) return false;
        }

        return true;
    }

    public boolean isValid() {
        return isValid;
    }

    public static void saveCdt(SPlugin sPlugin, SObject sObject, SActivator sActivator, IfPlayerHasExecutableItem iPHEI, String detail) {

        if (!new File(sObject.getPath()).exists()) {
            sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign() + " Error can't find the file in the folder ! (" + sObject.getId() + ".yml)");
            return;
        }
        File file = new File(sObject.getPath());
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection activatorConfig = config.getConfigurationSection("activators." + sActivator.getID());
        activatorConfig.set("conditions." + detail + ".ifPlayerHasExecutableItem." + iPHEI.getId() + ".executableItemID", "X");
        ConfigurationSection section = activatorConfig.getConfigurationSection("conditions." + detail + ".ifPlayerHasExecutableItem." + iPHEI.getId());

        section.set("executableItemID", iPHEI.executableItemID);
        section.set("slot", iPHEI.slot);
        if (iPHEI.usageCalcul.isPresent()) section.set("usageCalcul", iPHEI.usageCalcul.get());
        else section.set("usageCalcul", null);

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

    /*
     *  @param sPlugin The plugin of the conditions
     *  @param sObject The object
     *  @param sActivator The activator that contains the conditions
     *  @param id the block around cdt id
     */
    public static void deleteIPHEICdt(SPlugin sPlugin, SObject sObject, SActivator sActivator, String id, String detail) {

        if (!new File(sObject.getPath()).exists()) {
            sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign() + " Error can't find the file the folder ! (" + sObject.getId() + ".yml)");
            return;
        }
        File file = new File(sObject.getPath());
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection activatorConfig = config.getConfigurationSection("activators." + sActivator.getID());
        activatorConfig.set("conditions." + detail + ".ifPlayerHasExecutableItem." + id, null);

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
