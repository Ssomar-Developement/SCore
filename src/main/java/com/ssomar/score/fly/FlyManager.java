package com.ssomar.score.fly;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyManager {

    private static FlyManager instance;

    private final List<String> playerWithFly = new ArrayList<>();

    public static FlyManager getInstance() {
        if (instance == null) instance = new FlyManager();
        return instance;
    }

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
}
