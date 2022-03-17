package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.features.DecorateurCustomProjectiles;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PassengerFeature extends DecorateurCustomProjectiles {

    public PassengerFeature(CustomProjectile cProj){
        super.cProj = cProj;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {

       /* ArmorStand armorStand = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().add(0, 5, 0), EntityType.ARMOR_STAND);
        armorStand.setGlowing(true);
        armorStand.setSmall(true);
        armorStand.setBasePlate(false);
        armorStand.setInvisible(true);
        armorStand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));

        e.addPassenger(armorStand);*/

        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        else return false;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
    }

}
