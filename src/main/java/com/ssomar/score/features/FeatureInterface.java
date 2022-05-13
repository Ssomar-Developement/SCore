package com.ssomar.score.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public interface FeatureInterface<T> {

    /** @return The potential errors during that appear during the loading */
    List<String> load(SPlugin plugin, FeatureParentInterface parent, ConfigurationSection config);

    void save(SPlugin plugin, ConfigurationSection config);

    T getValue();

    void initEditorItem(GUI gui, int slot);

    boolean isTheFeatureClicked(String featureClicked);

    void clickEditor(GUIManager manager, Player player);
}
