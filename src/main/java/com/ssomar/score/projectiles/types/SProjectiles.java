package com.ssomar.score.projectiles.types;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.SimpleGUI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class SProjectiles extends CustomProjectile{

    CustomProjectile projectile;
    String id;
    FileConfiguration config;
    SimpleGUI configGUI;

    public SProjectiles(String id, FileConfiguration config){
        this.id = id;
        this.config = config;
        this.projectile = this;
        this.projectile = setup(this.projectile);
        projectile.loadConfiguration(config);
        configGUI = projectile.loadConfigGUI(this);
    }

    public abstract CustomProjectile setup(CustomProjectile proj);

    public CustomProjectile getProjectile(){
        return this.projectile;
    }

    public void openConfigGUI(Player p){
        configGUI.openGUISync(p);
    }

    public boolean isRequestChat(){
        return projectile.isRequestChat();
    }

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
                return Material.FIRE_CHARGE;
            case "SPLASH_POTION":
                return Material.SPLASH_POTION;
            case "SHULKER_BULLET":
                return Material.SHULKER_SHELL;
            case "SNOWBALL":
                return Material.SNOWBALL;
            case "TRIDENT":
                return Material.TRIDENT;
            case "WITHER_SKULL":
                return Material.WITHER_SKELETON_SKULL;
            default:
                SsomarDev.testMsg("Error get material proj: "+ this.getIdentifierType());
                return Material.SPONGE;
        }
    }

    /*public void changeType(Player player){
        CustomProjectile proj = null;
        switch(this.getIdentifierType()){
            case "ARROW":
                projConfig.set("type", "EGG");
                proj = new CustomEgg(id, projConfig);
                break;
            case "EGG":
                projConfig.set("type", "ENDER_PEARL");
                proj = new CustomEnderpearl(id, projConfig);
                break;
            case "ENDER_PEARL":
                projConfig.set("type", "FIREBALL");
                proj = new CustomFireball(id, projConfig);
                break;
            case "FIREBALL":
                projConfig.set("type", "SPLASH_POTION");
                proj = new CustomLingering(id, projConfig);
                break;
            case "SPLASH_POTION":
                projConfig.set("type", "SHULKER_BULLET");
                proj = new CustomShulkerBullet(id, projConfig);
                break;
            case "SHULKER_BULLET":
                projConfig.set("type", "SNOWBALL");
                proj = new CustomSnowball(id, projConfig);
                break;
            case "SNOWBALL":
                projConfig.set("type", "TRIDENT");
                proj = new CustomTrident(id, projConfig);
                break;
            case "TRIDENT":
                projConfig.set("type", "WITHER_SKULL");
                proj = new CustomWitherSkull(id, projConfig);
                break;
            case "WITHER_SKULL":
                projConfig.set("type", "ARROW");
                proj = new CustomArrow(id, projConfig);
                break;
        }
        proj.loadConfiguration();
        proj = proj.getLoaded();
        ProjectilesManager.getInstance().replaceProjectileWithID(this.getId(), proj);
        proj.openConfigGUIFor(player);
    }*/

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
}
