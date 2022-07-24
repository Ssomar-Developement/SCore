package com.ssomar.score.menu;

import com.ssomar.score.splugin.SPlugin;

public abstract class GUIAbstract extends GUI {

    private SPlugin sPlugin;

    public GUIAbstract(String name, int size, SPlugin sPlugin) {
        super(name, size);
        this.sPlugin = sPlugin;
    }

    public abstract void reloadGUI();

    public SPlugin getsPlugin() {
        return sPlugin;
    }

    public void setsPlugin(SPlugin sPlugin) {
        this.sPlugin = sPlugin;
    }
}

