package com.ssomar.score.fly;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class FlyManager {

    private static FlyManager instance;

    private final List<String> playerWithFly = new ArrayList<>();

    public void addPlayerWithFly(Player p) {
        String name = p.getName();
        playerWithFly.add(name);
    }

    public void removePlayerWithFly(Player p) {
        String name = p.getName();
        playerWithFly.remove(name);
    }

    public boolean isPlayerWithFly(Player p) {
        String name = p.getName();
        return playerWithFly.contains(name);
    }

    public static FlyManager getInstance() {
        if (instance == null) instance = new FlyManager();
        return instance;
    }
}
