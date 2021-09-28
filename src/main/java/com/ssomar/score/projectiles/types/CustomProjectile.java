package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomProjectile {

    public boolean requestChat;

    /* true if its loaded correctly */
    public boolean loadConfiguration(FileConfiguration config){
        return true;
    }

    public void transformTheProjectile(Entity e, Player launcher){}

    /* false = no message */
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        return false;
    }

    /* return the formed GUI */
    public SimpleGUI loadConfigGUI(SProjectiles proj){
        SimpleGUI configGui = new SimpleGUI("Editor: Custom Projectiles", 5 * 9);
        configGui.createItem(proj.getMaterial(), 1, 40, GUI.TITLE_COLOR + "&e>>&l &aProjectile type:", false, false, "", "&7actually: ");
        configGui.updateActually(GUI.TITLE_COLOR + "&e>>&l &aProjectile type:", proj.getIdentifierType());

        configGui.createItem(Material.ANVIL, 1, 42, GUI.TITLE_COLOR + "&e>>&l &aProjectile ID:", false, false, "", "&7actually: ");
        configGui.updateActually(GUI.TITLE_COLOR + "&e>>&l &aProjectile ID:", proj.getId());

        configGui.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 44, GUI.TITLE_COLOR + "&aSave the config", false, false, "", "&7&oClick here to save !");
        return configGui;
    }

    /* true = stop */
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title){
        /*String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeProjectile = StringConverter.decoloredString(GUI.TITLE_COLOR+"&e>>&l &aProjectile type:");

        if(itemName.equals(changeProjectile)){
            this.changeType(player);
            return true;
        }
        SsomarDev.testMsg("TITLE NOT EQUALS");
        SsomarDev.testMsg(itemName);
        SsomarDev.testMsg(changeProjectile);*/
        return false;
    }

    public boolean isRequestChat() {
        return false;
    }


}
