package com.ssomar.score.features.editor;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.GUI;
import org.bukkit.entity.Player;

import java.util.*;

public class SaveSessionPathManager {

    private final Map<Player, List<GUI>> playerSessionPath;

    private static SaveSessionPathManager instance;

    public SaveSessionPathManager() {
        this.playerSessionPath = new HashMap<>();
    }

    public void addPlayerSessionPath(Player p, GUI path) {
        if(this.playerSessionPath.containsKey(p)) {
            this.playerSessionPath.get(p).add(path);
        }
        else {
            this.playerSessionPath.put(p, new ArrayList<>(Collections.singletonList(path)));
        }
        // If the player has more than 12 paths, keep only the last 12
        if(this.playerSessionPath.get(p).size() > 12) {
            List<GUI> paths = this.playerSessionPath.get(p);
            List<GUI> newPaths = new ArrayList<>();
            for(int i = paths.size()-12; i < paths.size(); i++) {
                newPaths.add(paths.get(i));
            }
            this.playerSessionPath.put(p, newPaths);
        }
        SsomarDev.testMsg("ADD GUI > "+path.getInventory().getHolder(), true);
    }

    public GUI getLastPlayerSessionPath(Player p) {
        if(this.playerSessionPath.containsKey(p)) {
            List<GUI> paths = this.playerSessionPath.get(p);
            if(paths.size() > 0) {
                return paths.get(paths.size()-1);
            }
        }
        return null;
    }

    public GUI getLastBeforePlayerSessionPath(Player p) {
        if(this.playerSessionPath.containsKey(p)) {
            List<GUI> paths = this.playerSessionPath.get(p);
            if(paths.size() > 1) {
                SsomarDev.testMsg("GET GUI > "+paths.get(paths.size()-2).getInventory().getHolder(), true);
                return paths.get(paths.size()-2);
            }
        }
        return null;
    }

    public void removeLastPlayerSessionPath(Player p) {
        if(this.playerSessionPath.containsKey(p)) {
            List<GUI> paths = this.playerSessionPath.get(p);
            if(paths.size() > 1) {
                SsomarDev.testMsg("REMOVE GUI > "+paths.get(paths.size()-1).getInventory().getHolder(), true);
                paths.remove(paths.size()-1);
            }
        }
    }

    public static SaveSessionPathManager getInstance() {
        if(instance == null) instance = new SaveSessionPathManager();
        return instance;
    }
}
