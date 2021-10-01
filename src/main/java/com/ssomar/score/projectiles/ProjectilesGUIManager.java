package com.ssomar.score.projectiles;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIGUIManager;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUI;
import com.ssomar.score.menu.activator.requiredei.RequiredEIsGUIManager;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ProjectilesGUIManager extends GUIManager<ProjectilesGUI> {

    private static ProjectilesGUIManager instance;

    public void startEditing(Player p) {
        cache.put(p, new ProjectilesGUI(p));
        cache.get(p).openGUISync(p);
    }

    public void clicked(Player p, ItemStack item, String guiTitle) {
        if(item != null && item.hasItemMeta()) {

            String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
            //String plName = sPlugin.getNameDesign();
            String currentPage = StringConverter.decoloredString(guiTitle);

            if(name.contains("Next page")) {
                new ProjectilesGUI(Integer.parseInt(currentPage.split("Page ")[1])+1, p).openGUISync(p);
            }
            else if(name.contains("Previous page")) {
                p.closeInventory();
                new ProjectilesGUI(Integer.parseInt(currentPage.split("Page ")[1])-1, p).openGUISync(p);
            }
            else if(name.contains("Return")) {
                ProjectilesGUIManager.getInstance().getCache().get(p).openGUISync(p);
            }
            else if(name.contains("New Custom projectile")) {
                p.closeInventory();
                p.chat("/score projectiles-create");
            }
            else {
                if(name.isEmpty()) return;
                try {
                    SProjectiles proj;
                   if((proj = ProjectilesManager.getInstance().getProjectileWithID(name.split("ID: ")[1])) != null){
                       proj.openConfigGUI(p);
                   }
                   else {
                       p.closeInventory();
                       p.sendMessage(StringConverter.coloredString("&4&lSCore ERROR (projectiles), &cSend this message to the developer ! (&6Can't find the projectile with the ID: &e"+name.split("ID: ")[1]+"&c)"));
                   }
                }
                catch(Exception e) {
                    p.sendMessage(StringConverter.coloredString("&4&lSCore ERROR (projectiles), &cSend this message to the developer ! &6+ the message that appears in your console"));
                    e.printStackTrace();
                }
            }

            cache.remove(p);
        }
    }

    public void shiftLeftClicked(Player p, ItemStack item, String guiTitle) {
        String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
        //String plName = sPlugin.getNameDesign();
        String currentPage = StringConverter.decoloredString(guiTitle);

        if(name.contains("Next page")) {
            new ProjectilesGUI(Integer.parseInt(currentPage.split("Page ")[1])+1, p).openGUISync(p);
        }
        else if(name.contains("Previous page")) {
            p.closeInventory();
            new ProjectilesGUI(Integer.parseInt(currentPage.split("Page ")[1])-1, p).openGUISync(p);
        }
        else if(name.contains("Return")) {
            RequiredEIGUIManager.getInstance().getCache().get(p).openGUISync(p);
        }
        else if(name.contains("New Custom projectile")) {
            p.closeInventory();
            p.chat("/score projectiles-create");
        }
        else if(!name.isEmpty()) {
            p.closeInventory();
            String id = name.split("ID: ")[1];
            p.sendMessage(StringConverter.coloredString("&4[SCore] &cHey you want delete the projectile: &6"+id));
            TextComponent delete = new TextComponent( StringConverter.coloredString("&4&l[&c&lCLICK HERE TO DELETE&4&l]"));
            delete.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/score projectiles-delete "+id+" confirm"));
            delete.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&4Click here to delete this projectile")).create() ) );
            p.spigot().sendMessage(delete);
            p.updateInventory();
        }
    }

    public static ProjectilesGUIManager getInstance() {
        if(instance == null) instance = new ProjectilesGUIManager();
        return instance;
    }

    @Override
    public void saveTheConfiguration(Player p) {
        // TODO Auto-generated method stub

    }
}
