package com.ssomar.score.projectiles.features;

import com.ssomar.executableitems.items.SEnchantment;
import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.utils.Couple;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentsFeature extends DecorateurCustomProjectiles {

    private Map<SEnchantment,Integer> enchants;

    public EnchantmentsFeature(CustomProjectile cProj){
        super(cProj.getId(), cProj.getProjConfig());
        super.cProj = cProj;
        enchants = new HashMap<>();
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        if(projConfig.contains("enchantments")) {
            Couple<HashMap<SEnchantment, Integer>, Boolean> couple = this.getEnchantments(projConfig.getConfigurationSection("enchantments"));
            if(!couple.getElem2()) return cProj.loadConfiguration() && false;
            else enchants = couple.getElem1();
        }
        return cProj.loadConfiguration() && true;
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
    public Couple<HashMap<SEnchantment, Integer>, Boolean> getEnchantments(ConfigurationSection enchtsSection) {
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
                    SCore.plugin.getServer().getLogger().severe("[ExecutableItems] ERROR for projectile: "+this.getId()+", Enchantment with id: " + id
                            + " has invalid enchantment: " + enchtSection.getString("enchantment") + " !");
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
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.ENCHANTED_BOOK, 1, gui.TITLE_COLOR+"Enchantments", false, false, gui.CLICK_HERE_TO_CHANGE);
        return gui;
    }
}
