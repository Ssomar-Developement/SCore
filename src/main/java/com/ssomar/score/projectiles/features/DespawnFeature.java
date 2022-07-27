package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DespawnFeature extends DecorateurCustomProjectiles {

    int despawnDelay;
    boolean askDespawnDelay;

    public DespawnFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        despawnDelay = -1;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        despawnDelay = projConfig.getInt("despawnDelay", -1);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("despawnDelay", despawnDelay);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (despawnDelay != -1) {
            BukkitRunnable runnable = new BukkitRunnable() {
                public void run() {
                    if (e != null)
                        e.remove();
                }
            };
            runnable.runTaskLater(SCore.plugin, despawnDelay * 20L);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.DEAD_BUSH, 1, GUI.TITLE_COLOR + "1) Despawn delay", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if (despawnDelay == -1) gui.updateActually(GUI.TITLE_COLOR + "1) Despawn delay", "&cNO DESPAWN");
        else gui.updateInt(GUI.TITLE_COLOR + "1) Despawn delay", despawnDelay);
        return gui;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if (gui.getActually(GUI.TITLE_COLOR + "1) Despawn delay").contains("NO DESPAWN")) despawnDelay = -1;
        else despawnDelay = gui.getInt(GUI.TITLE_COLOR + "1) Despawn delay");
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change = StringConverter.decoloredString(GUI.TITLE_COLOR + "1) Despawn delay");

        if (itemName.equals(change)) {
            requestChat = true;
            askDespawnDelay = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &eDespawn delay&a: &7&o(Number, no despawn: -1)"));
            return true;
        }
        return false;
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message) {
        if (cProj.messageForConfig(gui, player, message)) return true;
        if (askDespawnDelay) {
            int newDespawnDelay;
            try {
                newDespawnDelay = Integer.valueOf(StringConverter.decoloredString(message));
            } catch (NumberFormatException e) {
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting despawn delay (" + message + ") || &7&o(Number, no despawn: -1)"));
                return true;
            }
            if (newDespawnDelay == -1) gui.updateActually(GUI.TITLE_COLOR + "1) Despawn delay", "&cNO DESPAWN");
            else gui.updateInt(GUI.TITLE_COLOR + "1) Despawn delay", newDespawnDelay);
            gui.openGUISync(player);
            askDespawnDelay = false;
            requestChat = false;
            return true;
        }
        return false;
    }

}
