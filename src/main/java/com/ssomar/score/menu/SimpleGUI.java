package com.ssomar.score.menu;

import org.bukkit.inventory.Inventory;

public class SimpleGUI extends GUI {

    public SimpleGUI(String name, int size) {
        super(name, size);
    }

    public SimpleGUI(Inventory inv) {
        super(inv);
    }
}
