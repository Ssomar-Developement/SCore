package com.ssomar.score.projectiles.types;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.menu.conditions.RequestMessageInfo;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public abstract class CustomProjectile {

    String identifierType;
    SimpleGUI configGui;
    String id;
    FileConfiguration projConfig;

    public CustomProjectile(String id, FileConfiguration projConfig){
        this.id = id;
        this.projConfig = projConfig;
        this.setup();
    }

    public abstract void setup();

    public boolean loadConfiguration(){
        this.identifierType = projConfig.getString("type", "NULZ");
        return true;
    }

    public void transformTheProjectile(Entity e, Player launcher){

    }

    public SimpleGUI getConfigGUI(){
        SsomarDev.testMsg("PASSE CREATION");
        configGui = new SimpleGUI("Editor: Custom Projectiles", 5*9);
        configGui.createItem(this.getMaterial(), 1, 40, GUI.TITLE_COLOR+"&e>>&l &aProjectile type:", false, false,"", "&7actually: ");
        configGui.updateActually(GUI.TITLE_COLOR+"&e>>&l &aProjectile type:", identifierType);

        configGui.createItem(Material.ANVIL, 1, 42, GUI.TITLE_COLOR+"&e>>&l &aProjectile ID:", false, false,"", "&7actually: ");
        configGui.updateActually(GUI.TITLE_COLOR+"&e>>&l &aProjectile ID:", id);

        configGui.createItem(Material.LIME_STAINED_GLASS_PANE, 1, 44, GUI.TITLE_COLOR+"&aSave the config", false, false,"", "&7&oClick here to save !");

        return this.configGui;
    }

    /* true = stop */
    public boolean interactionConfigGUI(Player player, ItemStack itemS, String title){
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeProjectile = StringConverter.decoloredString(GUI.TITLE_COLOR+"&e>>&l &aProjectile type:");

        if(itemName.equals(changeProjectile)){
            SsomarDev.testMsg("TITLE EQUALS");
            player.closeInventory();
            CustomProjectile proj = new CustomSnowball(id, projConfig);
            System.out.println(projConfig.toString());
            proj.loadConfiguration();
            proj.openConfigGUIFor(player);
            return true;
        }
        SsomarDev.testMsg("TITLE NOT EQUALS");
        SsomarDev.testMsg(itemName);
        SsomarDev.testMsg(changeProjectile);
        return false;
    }


    public void openConfigGUIFor(Player p){
        if(configGui != null) configGui.openGUISync(p);
        else (configGui = this.getConfigGUI()).openGUISync(p);
    }

    public void load(){

    }

    public abstract CustomProjectile getLoaded();

    public String getId(){
        return id;
    }

    public FileConfiguration getProjConfig() {
        return projConfig;
    }

    public String getIdentifierType(){
        return identifierType;
    }

    public Material getMaterial(){
        if(identifierType.equals("SNOWBALL")) return Material.SNOWBALL;
        else if(identifierType.equals("ARROW")) return Material.ARROW;
        else{
            SsomarDev.testMsg("Error get material proj: "+ identifierType);
            return Material.SPONGE;
        }
    }

}
