package com.ssomar.score.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface FeatureInterface<T, Y extends FeatureInterface> {

    /**
     * @return The potential errors during that appear during the loading
     */
    List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading);

    void save(ConfigurationSection config);

    void writeInFile(ConfigurationSection config);

    String getName();

    String getEditorName();

    T getValue();

    Y initItemParentEditor(GUI gui, int slot);

    void updateItemParentEditor(GUI gui);

    boolean isTheFeatureClickedParentEditor(String featureClicked);

    void reset();

    boolean isRequirePremium();

    Y clone(FeatureParentInterface newParent);
}
