package com.ssomar.score.projectiles.features;

import com.ssomar.executableitems.items.SEnchantment;
import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.Couple;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentsFeature extends DecorateurCustomProjectiles {

    private Map<SEnchantment,Integer> enchants;

    public EnchantmentsFeature(CustomProjectile cProj){
        super.cProj = cProj;
        enchants = new HashMap<>();
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig, boolean showError) {
        if(projConfig.contains("enchantments")) {
            Couple<HashMap<SEnchantment, Integer>, Boolean> couple = this.getEnchantments(projConfig.getConfigurationSection("enchantments"), showError);
            if(!couple.getElem2()) return cProj.loadConfiguration(projConfig, showError) && false;
            else enchants = couple.getElem1();
        }
        return cProj.loadConfiguration(projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {

        // #TODO save the enchantments
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if(!SCore.is1v12() && e instanceof Trident) {
            Trident t= (Trident)e;

            try {
                ItemStack item = t.getItem();
                ItemMeta meta = item.getItemMeta();
                for (SEnchantment enchantment : this.enchants.keySet()) {
                    meta.addEnchant(enchantment.getEnchantment(), ((Integer)this.enchants.get(enchantment)).intValue(), true);
                }
                item.setItemMeta(meta);
                t.setItem(item);
            }
            catch(NoSuchMethodError exception) {}
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @SuppressWarnings("deprecation")
    public Couple<HashMap<SEnchantment, Integer>, Boolean> getEnchantments(ConfigurationSection enchtsSection, boolean showError) {
        /*
         * enchantments: 1: enchantment: DURABILITY level: 2
         */
        HashMap<SEnchantment, Integer> result = new HashMap<>();
        for (String id : enchtsSection.getKeys(false)) {
            ConfigurationSection enchtSection = enchtsSection.getConfigurationSection(id);
            Enchantment enchantment;
            try {
                if (!SCore.is1v12())
                    enchantment = Enchantment
                            .getByKey(NamespacedKey.minecraft(enchtSection.getString("enchantment").toLowerCase()));
                else
                    enchantment = Enchantment.getByName(enchtSection.getString("enchantment"));
            } catch (Exception error) {
                if(showError) SCore.plugin.getServer().getLogger().severe("[SCore] ERROR for projectile: "+"ADD ID HERE"+", Enchantment with id: " + id
                            + " has invalid enchantment: " + enchtSection.getString("enchantment") + " !");
                    // #TODO add id here
                return new Couple(new HashMap<>(), false);
            }

            int level = enchtSection.getInt("level", 1);
            if (enchantment == null)
                return new Couple(new HashMap<>(), false); // pour la 1.12 sinon bug
            SEnchantment sEnchantment = new SEnchantment(enchantment, id);
            result.put(sEnchantment, level);
        }
        return new Couple(result, true);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.ENCHANTED_BOOK, 1, gui.TITLE_COLOR+"Enchantments", false, false, gui.CLICK_HERE_TO_CHANGE);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change = StringConverter.decoloredString(gui.TITLE_COLOR+"Pickup");

        if(itemName.equals(change)) {
           //this.changeEnchantment(gui);
        }
        return false;
    }

    @Override
    public void extractInfosGUI(GUI gui) {

    }

/*
    public void changeEnchantment(GUI gui) {
        boolean next = true;
        ItemStack item = gui.getByName(gui.TITLE_COLOR+"Enchantments");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        Enchantment enchant = Enchantment.ARROW_DAMAGE;
        for (String str : lore) {
            str = StringConverter.decoloredString(str);
            if (str.contains("➤")) {
                if(next) enchant =  this.getNext(Enchantment.getByName(str.split("➤ ")[1]));
                // else status =  AbstractArrow.PickupStatus.valueOf(str.split("➤ ")[1]).getPrev();
                break;
            }
        }
        this.updateEnchantment(gui, enchant);
    }

    public Enchantment getNext(Enchantment enchant){
        Enchantment next = null;
        boolean getNext = false;
        for(Enchantment e : Enchantment.values()){
            if(getNext){
                next = e;
                break;
            }
            if(e.equals(enchant)){
                getNext = true;
            }
        }
        if(next == null) next = Enchantment.values()[0];
        return next;
    }

    public void updateEnchantment(GUI gui, Enchantment enchant) {
        ItemStack item = gui.getByName(gui.TITLE_COLOR+"Enchantments");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (Enchantment e : Enchantment.values()) {
            if (enchant.equals(e)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + e.getName()));
                find = true;
            }
            else if(find){
                if(lore.size() == 17 || lore.size() == Enchantment.values().length+2) break;
                else
                    lore.add(StringConverter.coloredString("&6✦ &e" + e.getName()));
            }
        }
        for (Enchantment e : Enchantment.values()) {
            if (lore.size() == 17 || lore.size() == AbstractArrow.PickupStatus.values().length+2) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + e.getName()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        for(HumanEntity e : gui.getInv().getViewers()) {
            if(e instanceof Player) {
                Player p = (Player)e;
                p.updateInventory();
            }
        }
    }

    public Enchantment getEnchantment(GUI gui) {
        ItemStack item = gui.getByName(gui.TITLE_COLOR+"Enchantments");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Enchantment.getByName(str.split("➤ ")[1]);
            }
        }
        return null;
    } */
}
