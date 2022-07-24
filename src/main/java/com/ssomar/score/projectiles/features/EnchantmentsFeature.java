package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.scoretestrecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.scoretestrecode.features.custom.enchantments.group.EnchantmentsGroupFeature;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentsFeature extends DecorateurCustomProjectiles {

    boolean askEnchantments;
    private EnchantmentsGroupFeature enchants;

    public EnchantmentsFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        enchants = new EnchantmentsGroupFeature(null, false);
        this.askEnchantments = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        if (projConfig.contains("enchantments")) {
            enchants.load(SCore.plugin, projConfig, true);
        }
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {

        // #TODO test the saving
        enchants.save(config);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Trident) {
            Trident t = (Trident) e;

            try {
                ItemStack item = t.getItem();
                ItemMeta meta = item.getItemMeta();
                for (EnchantmentWithLevelFeature enchant : enchants.getEnchantments().values()) {
                    meta.addEnchant(enchant.getEnchantment().getValue().get(), enchant.getLevel().getValue().get(), true);
                }
                item.setItemMeta(meta);
                t.setItem(item);
            } catch (NoSuchMethodError exception) {
            }
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        // TODO echantmentsGUI
        gui.addItem(Material.ENCHANTED_BOOK, 1, GUI.TITLE_COLOR + "Enchantments", false, false, "&c&oNOT EDITABLE IN GAME FOR THE MOMENT");
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change = StringConverter.decoloredString(GUI.TITLE_COLOR + "Pickup");

        if (itemName.equals(change)) {
            //this.changeEnchantment(gui);
        }

        return false;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
    }
}
