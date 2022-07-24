package com.ssomar.score.menu.score;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class InteractionClickedGUIManager<T> {

    public HashMap<Player, T> cache;
    public SPlugin sPlugin;
    public GUI gui;
    /* Item clicked name */
    public String name;
    public Player player;

    public String title;

    public void resetGUI() {
        gui = (GUI) cache.get(player);

        if (gui instanceof GUIAbstract) {
            ((GUIAbstract) gui).reloadGUI();
        }
    }
}
