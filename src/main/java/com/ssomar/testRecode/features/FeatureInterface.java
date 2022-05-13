package com.ssomar.testRecode.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.menu.NewGUIManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public interface FeatureInterface<T, Y> {

    /** @return The potential errors during that appear during the loading */
    List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading);

    void save(ConfigurationSection config);

    T getValue();

    void initEditorItem(GUI gui, int slot);

    boolean isTheFeatureClicked(String featureClicked);

    void clickEditor(NewGUIManager manager, Player player);

    void extractInfoFromEditor(NewGUIManager manager, Player player);

    void reset();
}
