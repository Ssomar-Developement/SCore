package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomProjectile {

    public boolean requestChat;

    /* true if its loaded correctly */
    public boolean loadConfiguration(FileConfiguration config, boolean showError){
        return true;
    }

    public abstract void saveConfiguration(FileConfiguration config);

    public void transformTheProjectile(Entity e, Player launcher){}

    public abstract SimpleGUI getMainGUI();

    /* false = no message */
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        return false;
    }

    /* return the formed GUI */
    public abstract SimpleGUI loadConfigGUI(SProjectiles proj);

    /* true = stop */
    public abstract boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title);

    public abstract void extractInfosGUI(GUI gui);

    public boolean isRequestChat() {
        return false;
    }

    public void setRequestChat(boolean request) {
        this.requestChat = request;
    };



}
