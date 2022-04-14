package com.ssomar.score.projectiles.types;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.ProjectilesGUIManager;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;

public abstract class SProjectiles extends CustomProjectile{

    private static final Boolean DEBUG = false;
    CustomProjectile projectile;
    String id;
    File file;
    FileConfiguration config;
    SimpleGUI configGUI;

    public SProjectiles(String id, File file){
        this.id = id;
        this.file = file;
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        this.projectile = this;
        this.projectile = setup(this.projectile);
        projectile.loadConfiguration(file.getPath(), config, true);
        configGUI = projectile.loadConfigGUI(this);
    }

    public SProjectiles(String id, File file, boolean showError){
        this.id = id;
        this.file = file;
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        this.projectile = this;
        this.projectile = setup(this.projectile);
        projectile.loadConfiguration(file.getPath(), config, showError);
        configGUI = projectile.loadConfigGUI(this);
    }

    public void reload(){
        projectile.loadConfiguration(file.getPath(), config, true);
        configGUI = projectile.loadConfigGUI(this);
    }

    public abstract CustomProjectile setup(CustomProjectile proj);

    public void saveConfiguration(FileConfiguration config){
        if (!new File(file.getPath()).exists()) {
            SCore.plugin.getLogger().severe(SCore.plugin.getNameDesign() + " Error can't find the file  (" + file.getPath() + ")");
            return;
        }
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

            try {
                if(DEBUG) SsomarDev.testMsg("Saving file " + file.getPath());
                writer.write(config.saveToString());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles proj){
        SimpleGUI configGui = new SimpleGUI("Editor: Custom Projectiles", 5 * 9);
        configGui.createItem(proj.getMaterial(), 1, 40, GUI.TITLE_COLOR + "&e>>&l &aProjectile type:", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        configGui.updateActually(GUI.TITLE_COLOR + "&e>>&l &aProjectile type:", proj.getIdentifierType());

        configGui.createItem(Material.ANVIL, 1, 42, GUI.TITLE_COLOR + "&e>>&l &aProjectile ID:", false, false, "", "&7actually: ");
        configGui.updateActually(GUI.TITLE_COLOR + "&e>>&l &aProjectile ID:", proj.getId());

        configGui.createItem(GUI.ORANGE, 1, 36, GUI.TITLE_COLOR + "&cBack to projectiles", false, false, "", "&7&oBack to projectiles !");

        configGui.createItem(GUI.GREEN, 1, 44, GUI.TITLE_COLOR + "&aSave the config", false, false, "", "&7&oClick here to save !");
        return configGui;
    }

    public boolean sendInteractionConfigGUI(GUI gui, Player player, ItemStack itemS, String title){
        return projectile.interactionConfigGUI(gui, player, itemS, title);
    }

    public void executeTransformTheProjectile(Entity e, Player launcher){
        projectile.transformTheProjectile(e, launcher);
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title){
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change = StringConverter.decoloredString(GUI.TITLE_COLOR + "&e>>&l &aProjectile type:");
        String save = StringConverter.decoloredString(GUI.TITLE_COLOR + "&aSave the config");
        String back = StringConverter.decoloredString(GUI.TITLE_COLOR + "&cBack to projectiles");

        if(itemName.equals(change)) {
            this.changeType(player);
        }
        else if(itemName.equals(save)){
            file = new File(file.getPath());
            config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
            projectile.extractInfosGUI(gui);
            projectile.saveConfiguration(config);
            this.resetRequestChat();
            ProjectilesGUIManager.getInstance().startEditing(player);
        }
        else if(itemName.equals(back)){
            this.resetRequestChat();
            ProjectilesGUIManager.getInstance().startEditing(player);
        }
        else return false;
        return true;
    }

    public void extractInfosGUI(GUI gui){}

    public boolean sendMessageForConfig(SimpleGUI gui, Player player, String message){
        return projectile.messageForConfig(gui, player, message);
    }

    public CustomProjectile getProjectile(){
        return this.projectile;
    }

    public void openConfigGUI(Player p){
        configGUI.openGUISync(p);
    }

    public boolean hasRequestChat(){
        return projectile.isRequestChat();
    }

    public void resetRequestChat() { projectile.setRequestChat(false); }

    public String getIdentifierType(){
        return config.getString("type", "NULZ");
    }

    public Material getMaterial(){
        switch(this.getIdentifierType()) {
            case "ARROW":
                return Material.ARROW;
            case "EGG":
                return Material.EGG;
            case "ENDER_PEARL":
                return Material.ENDER_PEARL;
            case "FIREBALL":
                Material fireball;
                if(SCore.is1v12()) fireball = Material.valueOf("FIREBALL");
                else fireball = Material.FIRE_CHARGE;
                return fireball;
            case "SPLASH_POTION":
                return Material.SPLASH_POTION;
            case "LINGERING_POTION":
                return Material.LINGERING_POTION;
            case "SHULKER_BULLET":
                return Material.SHULKER_SHELL;
            case "SNOWBALL":
                Material snowball;
                if(SCore.is1v12()) snowball = Material.valueOf("SNOW_BALL");
                else snowball = Material.SNOWBALL;
                return snowball;
            case "TRIDENT":
                Material trident;
                if(SCore.is1v12()) trident = Material.STICK;
                else trident = Material.TRIDENT;
                return trident;
            case "WITHER_SKULL":
                Material skull;
                if(SCore.is1v12()) {
                    skull = Material.valueOf("NETHER_STAR");
                }
                else skull = Material.WITHER_SKELETON_SKULL;
                return skull;
            default:
                //SsomarDev.testMsg("Error get material proj: "+ this.getIdentifierType());
                return Material.SPONGE;
        }
    }

    public void changeType(Player player){
        SProjectiles proj = null;
        switch(this.getIdentifierType()){
            case "ARROW":
                config.set("type", "EGG");
                this.saveConfigInFile(config, file);
                proj = new CustomEgg(id, file);
                break;
            case "EGG":
                config.set("type", "ENDER_PEARL");
                this.saveConfigInFile(config, file);
                proj = new CustomEnderpearl(id, file);
                break;
            case "ENDER_PEARL":
                config.set("type", "FIREBALL");
                this.saveConfigInFile(config, file);
                proj = new CustomFireball(id, file);
                break;
            case "FIREBALL":
                config.set("type", "SPLASH_POTION");
                this.saveConfigInFile(config, file);
                proj = new CustomSplashPotion(id, file);
                break;
            case "SPLASH_POTION":
                config.set("type", "LINGERING_POTION");
                this.saveConfigInFile(config, file);
                proj = new CustomLingering(id, file);
                break;
            case "LINGERING_POTION":
                config.set("type", "SHULKER_BULLET");
                this.saveConfigInFile(config, file);
                proj = new CustomShulkerBullet(id, file);
                break;
            case "SHULKER_BULLET":
                config.set("type", "SNOWBALL");
                this.saveConfigInFile(config, file);
                proj = new CustomSnowball(id, file);
                break;
            case "SNOWBALL":
                config.set("type", "TRIDENT");
                this.saveConfigInFile(config, file);
                proj = new CustomTrident(id, file);
                break;
            case "TRIDENT":
                config.set("type", "WITHER_SKULL");
                this.saveConfigInFile(config, file);
                proj = new CustomWitherSkull(id, file);
                break;
            case "WITHER_SKULL":
                config.set("type", "ARROW");
                this.saveConfigInFile(config, file);
                proj = new CustomArrow(id, file);
                break;
        }

        ProjectilesManager.getInstance().replaceProjectileWithID(this.getId(), proj);
        proj.openConfigGUI(player);
    }

    public void saveConfigInFile(FileConfiguration config, File file){
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

            try {
                writer.write(config.saveToString());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProjectile(CustomProjectile projectile) {
        this.projectile = projectile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public SimpleGUI getConfigGUI() {
        return configGUI;
    }

    public void setConfigGUI(SimpleGUI configGUI) {
        this.configGUI = configGUI;
    }

    public SimpleGUI getMainGUI(){
        return configGUI;
    }
}
