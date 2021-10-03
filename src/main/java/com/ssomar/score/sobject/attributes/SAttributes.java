package com.ssomar.score.sobject.attributes;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.executableitems.configs.api.PlaceholderAPI;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SAttributes extends HashMap<SAttribute, AttributeModifier> {

    public void loadAttributes(SPlugin sPlugin, ConfigurationSection attsSection, List<String> errorList, boolean isDefaultItem) {
        /*
         * attributes: 1: attribute: GENERIC_MAX_HEALTH uuid: random name:
         * generic.maxHealth operation: ADD_NUMBER amount: 10.0 slot: HAND
         */
        this.clear();
        int cptAtt = 0;
        for (String id : attsSection.getKeys(false)) {

            if ((PlaceholderAPI.isLotOfWork() && !isDefaultItem) && cptAtt >= 2) {
                errorList.add(sPlugin.getNameDesign()+" " + id+ " REQUIRE PREMIUM: to add more than two attributes you need the premium version");
                break;
            }
            ConfigurationSection attSection = attsSection.getConfigurationSection(id);
            Attribute att;
            if (attSection.contains("attribute")) {
                try {
                    att = Attribute.valueOf(attSection.getString("attribute"));
                } catch (Exception e) {
                    errorList.add(sPlugin.getNameDesign()+" Attribute with id: " + id+ " contain an invalid Attribute: " + attSection.getString("attribute") + " !");
                    continue;
                }
            } else {
                errorList.add(sPlugin.getNameDesign()+" Attribute with id: " + id+ " doesnt contain a definition of Attribute read the wiki !");
                continue;
            }
            SAttribute sAtt = new SAttribute(att, id);
            UUID uuid;
            if (attSection.contains("uuid")) {
                try {
                    uuid = UUID.fromString(attSection.getString("uuid"));
                } catch (Exception e) {
                    uuid = UUID.randomUUID();
                }
            } else
                uuid = UUID.randomUUID();
            String name = "";
            if (attSection.contains("name")) {
                name = attSection.getString("name");
            }
            AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_NUMBER;
            if (attSection.contains("operation")) {
                try {
                    operation = AttributeModifier.Operation.valueOf(attSection.getString("operation"));
                } catch (Exception e) {}
            }
            double amount = 0;
            if (attSection.contains("amount")) {
                try {
                    amount = attSection.getDouble("amount");
                } catch (Exception e) {
                    errorList.add(sPlugin.getNameDesign()+" Attribute with id: " + id+ " contain an invalid amount: " + attSection.getString("amount") + " !");
                    continue;
                }
            }
            EquipmentSlot slot = null;
            if (attSection.contains("slot")) {
                try {
                    slot = EquipmentSlot.valueOf(attSection.getString("slot"));
                } catch (Exception e) {
                    errorList.add(sPlugin.getNameDesign()+" Attribute with id: " + id+ " contain an invalid slot: " + attSection.getString("slot") + " !");
                    continue;
                }
            }
            AttributeModifier attM;
            if (slot == null)
                attM = new AttributeModifier(uuid, name, amount, operation, EquipmentSlot.HAND);
            else
                attM = new AttributeModifier(uuid, name, amount, operation, slot);

            cptAtt++;
            this.put(sAtt, attM);
        }
    }

    public void saveAttributes(SPlugin sPlugin, SObject sObject) {

        if(!new File(sObject.getPath()).exists()) {
            SCore.plugin.getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file of the item in the folder items ! ("+sObject.getID()+".yml)");
            return;
        }
        File file = new File(sObject.getPath());
        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        config.set("attributes", null);

        for(SAttribute sAtt : this.keySet()) {
            config.set("attributes." + sAtt.getId() + ".attribute", sAtt.getAttribute().toString());
            config.set("attributes." + sAtt.getId() + ".name", this.get(sAtt).getName());
            config.set("attributes." + sAtt.getId() + ".uuid", this.get(sAtt).getUniqueId().toString());
            config.set("attributes." + sAtt.getId() + ".amount", this.get(sAtt).getAmount());
            config.set("attributes." + sAtt.getId() + ".operation", this.get(sAtt).getOperation().toString());
            if (this.get(sAtt).getSlot() != null) config.set("attributes." + sAtt.getId() + ".slot", this.get(sAtt).getSlot().toString());
            else config.set("attributes." + sAtt.getId() + ".slot", null);
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
