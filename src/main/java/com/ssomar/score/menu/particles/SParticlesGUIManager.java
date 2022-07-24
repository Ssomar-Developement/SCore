package com.ssomar.score.menu.particles;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sparticles.SParticles;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SParticlesGUIManager extends GUIManager<SParticlesGUI> {

    private static SParticlesGUIManager instance;

    public static SParticlesGUIManager getInstance() {
        if (instance == null) instance = new SParticlesGUIManager();
        return instance;
    }

    public void startEditing(Player p, SPlugin sPlugin, SParticles sParticles, GUI guiFrom) {
        cache.put(p, new SParticlesGUI(p, sPlugin, sParticles, guiFrom));
        cache.get(p).openGUISync(p);
    }

    public void clicked(Player p, ItemStack item, String guiTitle) {
        if (item != null && item.hasItemMeta()) {
            SPlugin sPlugin = cache.get(p).getsPlugin();
            String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
            //String plName = sPlugin.getNameDesign();
            String currentPage = StringConverter.decoloredString(guiTitle);

            if (name.contains("Next page")) {
                new SParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1]) + 1, p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getGuiFrom()).openGUISync(p);
            } else if (name.contains("Previous page")) {
                p.closeInventory();
                new SParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1]) - 1, p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getGuiFrom()).openGUISync(p);
            } else if (name.contains("Back")) {
                cache.get(p).getGuiFrom().openGUISync(p);
            } else if (name.contains("New Particle")) {
                SParticleGUIManager.getInstance().startEditing(p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getGuiFrom());
            } else {
                try {
                    SParticleGUIManager.getInstance().startEditing(p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getSParticles().getParticle(name.split("ID: ")[1]).get(), cache.get(p).getGuiFrom());
                } catch (Exception ignored) {
                }
            }

            cache.remove(p);
        }
    }

    public void shiftLeftClicked(Player p, ItemStack item, String guiTitle) {
        SPlugin sPlugin = cache.get(p).getsPlugin();
        String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
        //String plName = sPlugin.getNameDesign();
        String currentPage = StringConverter.decoloredString(guiTitle);

        if (name.contains("Next page")) {
            new SParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1]) + 1, p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getGuiFrom()).openGUISync(p);
        } else if (name.contains("Previous page")) {
            p.closeInventory();
            new SParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1]) - 1, p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getGuiFrom()).openGUISync(p);
        } else if (name.contains("Back")) {
            cache.get(p).getGuiFrom().openGUISync(p);
        } else if (name.contains("New Particle")) {
            SParticleGUIManager.getInstance().startEditing(p, sPlugin, cache.get(p).getSParticles(), cache.get(p).getGuiFrom());
        } else if (!name.isEmpty()) {
            try {
                SParticles sParticles = cache.get(p).getSParticles();
                sParticles.removeParticle(name.split("ID: ")[1]);
                sParticles.save();

                cache.put(p, new SParticlesGUI(Integer.parseInt(currentPage.split("Page ")[1]), p, sPlugin, sParticles, cache.get(p).getGuiFrom()));
                cache.get(p).openGUISync(p);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void saveTheConfiguration(Player p) {

    }
}
