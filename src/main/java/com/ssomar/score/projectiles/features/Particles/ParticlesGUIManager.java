package com.ssomar.score.projectiles.features.Particles;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIGUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUI;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUIManager;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.CustomColor;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ParticlesGUIManager extends GUIManager<ParticlesGUI> {

    private static ParticlesGUIManager instance;

    public void startEditing(Player p, List<ParticlesFeature.CustomParticle> particles, String projID) {
        cache.put(p, new ParticlesGUI(p, particles, projID));
        cache.get(p).openGUISync(p);
    }

    public void clicked(Player p, ItemStack item, String guiTitle) {
        if(item != null && item.hasItemMeta()) {
            String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
            //String plName = sPlugin.getNameDesign();
            String currentPage = StringConverter.decoloredString(guiTitle);

            if(name.contains("Next page")) {
                new ParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1])+1, p, cache.get(p).getParticles(), cache.get(p).getProjID()).openGUISync(p);
            }
            else if(name.contains("Previous page")) {
                p.closeInventory();
                new ParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1])-1, p, cache.get(p).getParticles(), cache.get(p).getProjID()).openGUISync(p);
            }
            // TODO BACK PARCTILES
            else if(name.contains("Back")) {
                //LinkedPlugins.openActivatorMenu(p, sPlugin, sObject, sActivator);
            }
            // TODO NEW PARCTILE
            else if(name.contains("New Particle")) {
                //RequiredEIGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator);
            }
            else {
                try {
                    // TODO CLICK PROJECTILE
                    //RequiredEIGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator, sActivator.getRequiredEI(name.split("ID: ")[1]));
                }
                catch(Exception ignored) {}
            }

            cache.remove(p);
        }
    }

    public void shiftLeftClicked(Player p, ItemStack item, String guiTitle) {
        String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
        //String plName = sPlugin.getNameDesign();
        String currentPage = StringConverter.decoloredString(guiTitle);

        if(name.contains("Next page")) {
            new ParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1])+1, p, cache.get(p).getParticles(), cache.get(p).getProjID()).openGUISync(p);
        }
        else if(name.contains("Previous page")) {
            p.closeInventory();
            new ParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1])-1, p, cache.get(p).getParticles(), cache.get(p).getProjID()).openGUISync(p);
        }
        else if(name.contains("Back")) {
           // LinkedPlugins.openActivatorMenu(p, sPlugin, sObject, sActivator);
        }
        else if(name.contains("New Particle")) {
            //RequiredEIGUIManager.getInstance().startEditing(p, sPlugin, sObject, sActivator);
        }
        // TODO DELETE PROJECTILE
        else if(!name.isEmpty()) {
           try {
                List<ParticlesFeature.CustomParticle> particles = cache.get(p).getParticles();
                String projID = cache.get(p).getProjID();

                int index = Integer.valueOf(name.split("✦ Particle ID: ")[1]);
                particles.remove(index);

                for(SProjectiles proj : ProjectilesManager.getInstance().getProjectiles()){
                    if(proj.getId().equals(projID)){
                        FileConfiguration config = proj.getConfig();
                        int i = 0;
                        for(ParticlesFeature.CustomParticle part : particles) {
                            config.set("particles." + i + ".particlesType", part.getParticlesType());
                            config.set("particles." + i + ".particlesAmount", part.getParticlesAmount());
                            config.set("particles." + i + ".particlesOffSet", part.getParticlesOffSet());
                            config.set("particles." + i + ".particlesSpeed", part.getParticlesSpeed());
                            config.set("particles." + i + ".particlesDelay", part.getParticlesDelay());
                            if (part.getParticlesType().equals(Particle.REDSTONE)) {
                                config.set("particles." + i + ".redstoneColor", CustomColor.getName(part.getRedstoneColor()));
                            }
                        i++;
                        }
                        proj.saveConfiguration(config);
                        proj.reload();
                        break;
                    }
                }
            }
            catch(Exception ignored) {}
        }
    }

    public static ParticlesGUIManager getInstance() {
        if(instance == null) instance = new ParticlesGUIManager();
        return instance;
    }

    @Override
    public void saveTheConfiguration(Player p) {

    }
}
