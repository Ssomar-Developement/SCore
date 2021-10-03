package com.ssomar.score.projectiles.features.Particles;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.menu.activator.requiredei.RequiredEIGUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUI;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ParticlesGUI extends SimpleGUI {

    static int index;
    public List<ParticlesFeature.CustomParticle> particles;
    String projID;

    //Page 1
    public ParticlesGUI(Player p, List<ParticlesFeature.CustomParticle> particles, String projID) {
        super("&8&lEditor - Particles - Page 1", 5*9);
        setIndex(1);
        this.particles = particles;
        this.projID = projID;
        loadParticles(p, particles, projID);
    }

    // other pages
    public ParticlesGUI(int index, Player p, List<ParticlesFeature.CustomParticle> particles, String projID) {
        super("&8&lEditor - Particles - Page "+index, 5*9);
        setIndex(index);
        this.particles = particles;
        this.projID = projID;
        loadParticles(p, particles, projID);
    }

    public void loadParticles(Player p, List<ParticlesFeature.CustomParticle> particles, String projID) {
        int i = 0;
        int total = 0;
        for(ParticlesFeature.CustomParticle particle : particles) {
            if((index-1)*27 <= total && total < index*27) {
                ItemStack itemS = new ItemStack(Material.BOOK);
                List<String> desc = new ArrayList<>();
                desc.add("");
                desc.add("&4(shift + left click to delete)");
                desc.add("&7(click to edit)");
                desc.add("&7• Particle type: &e" + particle.getParticlesType());

                String[]descArray = new String[desc.size()];
                for(int j = 0; j < desc.size(); j++) {
                    if(desc.get(j).length() > 40) {
                        descArray[j] = desc.get(j).substring(0, 39)+"...";
                    }
                    else {
                        descArray[j] = desc.get(j);
                    }
                }
                createItem(itemS, 	1 , i, 	"&2&l✦ Particle ID: &a"+projID, true, false, descArray);
                i++;
            }
            total++;

        }

        //other button
        if(total>27 && index*27<total) createItem(PURPLE, 	1 , 44, 	"&5&l▶ &dNext page ", 	false, false);

        if(index>1) createItem(PURPLE, 	1 , 37, 	"&dPrevious page &5&l◀", 	false, false);

        createItem(RED, 	1 , 36, 	"&4&l▶ &cBack to activator config", 	false, false);

        createItem(GREEN, 	1 , 40, 	"&2&l✚ &aNew Particle", 	false, false);

        createItem(GREEN, 	1 , 44, 	"&2&l✦ Projectile ID: &a"+projID, 	false, false);

        //Last Edit
        if(p != null && RequiredEIGUIManager.getInstance().getCache().containsKey(p)) {
            createItem(BLUE, 							1 , 39, "&3&l✦ &bReturn to your last edit", 		false, false, 	"", "&7&oClick here to continue" , "&7&oyour last edit" );
        }
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        ParticlesGUI.index = index;
    }

    public List<ParticlesFeature.CustomParticle> getParticles() {
        return particles;
    }

    public void setParticles(List<ParticlesFeature.CustomParticle> particles) {
        this.particles = particles;
    }

    public String getProjID() {
        return projID;
    }

    public void setProjID(String projID) {
        this.projID = projID;
    }


}
