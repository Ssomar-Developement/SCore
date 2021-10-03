package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.CustomColor;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.List;

import static org.bukkit.Color.AQUA;

/* For Arrow and potion color */
public class ColorFeature extends DecorateurCustomProjectiles {

    boolean activeColor;
    Color color;
    boolean hasColor;

    public ColorFeature(CustomProjectile cProj){
        super.cProj = cProj;
        activeColor = true;
        color = Color.fromRGB(1,2,3);
        hasColor = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig, boolean showError) {
        activeColor = projConfig.getBoolean("activeColor", true);
        if(activeColor && projConfig.contains("color")) {
            String colorStr = projConfig.getString("color", "NO_COLOR");
            try {
                color = CustomColor.valueOf(colorStr);
                hasColor = !color.equals(Color.fromRGB(1, 2, 3));
            } catch (Exception e) {
                activeColor = false;
                if (showError) SCore.plugin.getLogger()
                        .severe("[SCore] Error invalid color (" + colorStr + ") for the projectile: " + "ADD THE ID HERE"
                                + " (https://helpch.at/docs/1.12.2/org/bukkit/Color.html)");
                // #TODO add id here
                return cProj.loadConfiguration(projConfig, showError) && false;
            }
        }
        return cProj.loadConfiguration(projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("activeColor", activeColor);
        config.set("color", CustomColor.getName(color));
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Arrow && activeColor && color != null && hasColor) {
            ((Arrow) e).setColor(color);
        }
        else if (e instanceof ThrownPotion) {
            ThrownPotion lp = (ThrownPotion) e;

            try {
                ItemStack item = lp.getItem();
                PotionMeta pMeta = (PotionMeta) item.getItemMeta();
                if (this.activeColor)
                    pMeta.setColor(this.color);
                item.setItemMeta(pMeta);
                lp.setItem(item);
            } catch (NoSuchMethodError exception) {}
        }
        cProj.transformTheProjectile(e, launcher);
    }

    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.RED_DYE, 1, GUI.TITLE_COLOR +"Color", false, false, "",GUI.CLICK_HERE_TO_CHANGE);
        updateColor(gui, color);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change = StringConverter.decoloredString(GUI.TITLE_COLOR +"Color");

        if(itemName.equals(change)) {
            this.changeColor(gui);
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        color = this.getColor(gui);
        hasColor = !color.equals(Color.fromRGB(1, 2 ,3));
    }

    public void changeColor(GUI gui) {
        boolean next = true;
        ItemStack item = gui.getByName(GUI.TITLE_COLOR +"Color");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        Color color = AQUA;
        for (String str : lore) {
            str = StringConverter.decoloredString(str);
            if (str.contains("➤")) {
                if(next) color =  CustomColor.getNext(CustomColor.valueOf(str.split("➤ ")[1]));
                // else status =  AbstractArrow.PickupStatus.valueOf(str.split("➤ ")[1]).getPrev();
                break;
            }
            else this.updateColor(gui, Color.AQUA);
        }
        this.updateColor(gui, color);
    }


    public void updateColor(GUI gui, Color color) {
        ItemStack item = gui.getByName(GUI.TITLE_COLOR +"Color");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (Color col : CustomColor.values()) {
            if (color.equals(col)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + CustomColor.getName(col)));
                find = true;
            }
            else if(find){
                if(lore.size() == 17 || lore.size() == CustomColor.values().length+2) break;
                else
                    lore.add(StringConverter.coloredString("&6✦ &e" + CustomColor.getName(col)));
            }
        }
        for (Color col : CustomColor.values()) {
            if (lore.size() == 17 || lore.size() == CustomColor.values().length+2) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + CustomColor.getName(col)));
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

    public Color getColor(GUI gui) {
        ItemStack item = gui.getByName(GUI.TITLE_COLOR +"Color");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return CustomColor.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }
}
