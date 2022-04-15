package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.menu.particles.SParticlesGUIManager;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.sparticles.SParticle;
import com.ssomar.score.sparticles.SParticles;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class ParticlesFeature extends DecorateurCustomProjectiles {

    private SParticles particles;
    private boolean askParticles;

    public ParticlesFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        this.particles = null;
        this.askParticles = false;
    }


    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        this.particles = new SParticles(SCore.plugin, filePath, "", projConfig, showError);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        cProj.saveConfiguration(config);
    }


    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (particles != null) {
            for (SParticle particle : particles.getParticles()) {
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (e.isDead()) cancel();

                        if (Particle.REDSTONE.equals(particle.getParticlesType())) {
                            Particle.DustOptions dO = new Particle.DustOptions(Color.RED, 1);
                            if (particle.getRedstoneColor() != null)
                                dO = new Particle.DustOptions(particle.getRedstoneColor(), 1);
                            e.getWorld().spawnParticle(particle.getParticlesType(), e.getLocation(), particle.getParticlesAmount(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesSpeed(), dO);
                        } else if (Particle.BLOCK_CRACK.equals(particle.getParticlesType())
                                || Particle.BLOCK_DUST.equals(particle.getParticlesType())
                                || Particle.BLOCK_MARKER.equals(particle.getParticlesType())) {
                            BlockData typeData = Material.STONE.createBlockData();
                            if (particle.getBlockType() != null)
                                typeData = particle.getBlockType().createBlockData();
                            e.getWorld().spawnParticle(particle.getParticlesType(), e.getLocation(), particle.getParticlesAmount(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesSpeed(), typeData);
                        } else {
                            e.getWorld().spawnParticle(particle.getParticlesType(), e.getLocation(), particle.getParticlesAmount(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesOffSet(), particle.getParticlesSpeed());
                        }
                    }
                };
                runnable.runTaskTimerAsynchronously(SCore.plugin, 0L, particle.getParticlesDelay());
            }
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.BLAZE_POWDER, 1, GUI.TITLE_COLOR + "Particles", false, false, GUI.CLICK_HERE_TO_CHANGE);
        return gui;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String openParticles = StringConverter.decoloredString((GUI.TITLE_COLOR + "Particles"));

        if (itemName.equals(openParticles)) {
            SParticlesGUIManager.getInstance().startEditing(player, SCore.plugin, getParticles(), gui);
            return true;
        } else return false;

    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message) {
        if (cProj.messageForConfig(gui, player, message)) return true;
        if (askParticles) {
            /*int newKnock;
            try{
                newKnock = Integer.valueOf(StringConverter.decoloredString(message));
            }catch(NumberFormatException e){
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting knockbackStrength ("+message+")"));
                return true;
            }
            if(newKnock == -1)  gui.updateActually(GUI.TITLE_COLOR +"Knockback Strength", "&cVANILLA KNOCKBACK");
            else gui.updateInt(GUI.TITLE_COLOR +"Knockback Strength", newKnock);
            gui.openGUISync(player);
            askKnockbackStrength = false;
            requestChat = false;*/
            return true;
        }
        return false;
    }
}
