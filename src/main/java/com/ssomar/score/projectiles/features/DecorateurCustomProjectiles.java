package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DecorateurCustomProjectiles extends CustomProjectile {

    protected CustomProjectile cProj;

    public abstract boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError);

    public abstract void transformTheProjectile(Entity e, Player launcher);

    public abstract SimpleGUI loadConfigGUI(SProjectiles sProj);

    public SimpleGUI getMainGUI() {
        return cProj.getMainGUI();
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        return false;
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message) {
        if (cProj.messageForConfig(gui, player, message)) return true;
        return false;
    }

    public boolean isRequestChat() {
        return cProj.isRequestChat() || requestChat;
    }

    @Override
    public void setRequestChat(boolean request) {
        cProj.setRequestChat(request);
        this.requestChat = request;
    }
}
