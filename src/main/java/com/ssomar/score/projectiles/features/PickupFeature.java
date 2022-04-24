package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PickupFeature extends DecorateurCustomProjectiles {

    AbstractArrow.PickupStatus pickupStatus;
    boolean isDisable;

    public PickupFeature(CustomProjectile cProj){
        super.cProj = cProj;

        isDisable = SCore.is1v13Less();
        if(!isDisable) {
            pickupStatus = AbstractArrow.PickupStatus.ALLOWED;
        }
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {

        if (!isDisable && projConfig.contains("pickupStatus")) {
            String pickStatus = projConfig.getString("pickupStatus", "null");
            try {
                pickupStatus = AbstractArrow.PickupStatus.valueOf(pickStatus.toUpperCase());
            } catch (Exception e) {
                pickupStatus = AbstractArrow.PickupStatus.ALLOWED;
                if(showError) SCore.plugin.getLogger()
                        .severe("[SCore] Error invalid pickupStatus for the projectile: " + "ADD ID HERE"
                                + " (ALLOWED, CREATIVE_ONLY, DISALLOWED) DEFAULT> ALLOWED");
                return cProj.loadConfiguration(filePath, projConfig, showError) && false;
                // #TODO add id here
            }
        }
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        if(!isDisable) {
            config.set("pickupStatus", pickupStatus.name());
        }
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!isDisable && e instanceof AbstractArrow)
            ((AbstractArrow)e).setPickupStatus(pickupStatus);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        Material lead;
        if(SCore.is1v12Less()) lead = Material.valueOf("LEASH");
        else lead = Material.LEAD;
        if(isDisable) gui.addItem(lead, 1, GUI.TITLE_COLOR +"Pickup", false, false, "", "&cINVALID VERSION (1.14+)");
        else {
            gui.addItem(lead, 1, GUI.TITLE_COLOR +"Pickup", false, false, "", GUI.CLICK_HERE_TO_CHANGE);
            this.updatePickup(gui, pickupStatus);
        }
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        if(!isDisable) {
            String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
            String change = StringConverter.decoloredString(GUI.TITLE_COLOR + "Pickup");

            if (itemName.equals(change)) {
                this.changePickup(gui);
            }
        }
        return false;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if(!isDisable)
        pickupStatus = this.getPickup(gui);
    }

    public void changePickup(GUI gui) {
        boolean next = true;
        ItemStack item = gui.getByName(GUI.TITLE_COLOR +"Pickup");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        AbstractArrow.PickupStatus status = AbstractArrow.PickupStatus.ALLOWED;
        for (String str : lore) {
            str = StringConverter.decoloredString(str);
            if (str.contains("➤")) {
                if(next) status =  this.getNext(AbstractArrow.PickupStatus.valueOf(str.split("➤ ")[1]));
               // else status =  AbstractArrow.PickupStatus.valueOf(str.split("➤ ")[1]).getPrev();
                break;
            }
        }
        this.updatePickup(gui, status);
    }

    public AbstractArrow.PickupStatus getNext(AbstractArrow.PickupStatus status){
        AbstractArrow.PickupStatus next = null;
        boolean getNext = false;
        for(AbstractArrow.PickupStatus sta : AbstractArrow.PickupStatus.values()){
            if(getNext){
                next = sta;
                break;
            }
            if(sta.equals(status)){
                getNext = true;
            }
        }
        if(next == null) next = AbstractArrow.PickupStatus.values()[0];
        return next;
    }

    public void updatePickup(GUI gui, AbstractArrow.PickupStatus status) {
        ItemStack item = gui.getByName(GUI.TITLE_COLOR +"Pickup");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, 2);
        boolean find = false;
        for (AbstractArrow.PickupStatus sta : AbstractArrow.PickupStatus.values()) {
            if (status.equals(sta)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + sta));
                find = true;
            }
            else if(find){
                if(lore.size() == 17 || lore.size() == AbstractArrow.PickupStatus.values().length+2) break;
                else
                    lore.add(StringConverter.coloredString("&6✦ &e" + sta));
            }
        }
        for (AbstractArrow.PickupStatus sta : AbstractArrow.PickupStatus.values()) {
            if (lore.size() == 17 || lore.size() == AbstractArrow.PickupStatus.values().length+2) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + sta));
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

    public AbstractArrow.PickupStatus getPickup(GUI gui) {
        ItemStack item = gui.getByName(GUI.TITLE_COLOR +"Pickup");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return AbstractArrow.PickupStatus.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

}
