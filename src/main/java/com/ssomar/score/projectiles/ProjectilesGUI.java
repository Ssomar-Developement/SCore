package com.ssomar.score.projectiles;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ProjectilesGUI extends GUI {

    static int index;

    //Page 1
    public ProjectilesGUI(Player p) {
        super("&8&l Editor - Custom projectiles - Page 1", 5 * 9);
        setIndex(1);
        loadProjectiles();
    }

    // other pages
    public ProjectilesGUI(int index, Player p) {
        super("&8&l Editor - Custom projectiles - Page " + index, 5 * 9);
        setIndex(index);
        loadProjectiles();
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        ProjectilesGUI.index = index;
    }

    public void loadProjectiles() {
        List<SProjectiles> projs = ProjectilesManager.getInstance().getProjectiles();
        int i = 0;
        int total = 0;
        for (SProjectiles proj : projs) {
            if ((index - 1) * 27 <= total && total < index * 27) {
                ItemStack itemS = new ItemStack(proj.getMaterial());
                List<String> desc = new ArrayList<>();
                desc.add("");
                desc.add("&4(shift + left click to delete)");
                desc.add("&7(click to edit)");
                desc.add("&7• PROJ ID: &e" + proj.getId());
                desc.add("&7• TYPE: &e" + proj.getIdentifierType());


                String[] descArray = new String[desc.size()];
                for (int j = 0; j < desc.size(); j++) {
                    if (desc.get(j).length() > 40) {
                        descArray[j] = desc.get(j).substring(0, 39) + "...";
                    } else {
                        descArray[j] = desc.get(j);
                    }
                }
                createItem(itemS, 1, i, "&2&l✦ ID: &a" + proj.getId(), true, false, descArray);
                i++;
            }
            total++;

        }

        //other button
        if (total > 27 && index * 27 < total) createItem(PURPLE, 1, 44, "&5&l▶ &dNext page ", false, false);

        if (index > 1) createItem(PURPLE, 1, 37, "&dPrevious page &5&l◀", false, false);

        createItem(Material.BOOK, 1, 38, GUI.TITLE_COLOR + "&aOpen the wiki", false, false, "", "&7&oClick here to open the wiki !");

        createItem(GREEN, 1, 40, "&2&l✚ &aNew Custom projectile", false, false);

    }

    public void reloadGUI() {
        this.loadProjectiles();
    }
}