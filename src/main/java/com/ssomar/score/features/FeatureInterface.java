package com.ssomar.score.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface FeatureInterface<FINAL_VALUE_CLASS, FEATURE_CLASS> {

    /**
     * @return The potential errors during that appear during the loading
     */
    List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading);

    void save(ConfigurationSection config);

    void writeInFile(ConfigurationSection config);

    FeatureSettingsInterface getFeatureSettings();

    String getName();

    String getEditorName();

    FINAL_VALUE_CLASS getValue();

    FeatureInterface initItemParentEditor(GUI gui, int slot);

    void updateItemParentEditor(GUI gui);

    void initAndUpdateItemParentEditor(GUI gui, int slot);

    boolean isTheFeatureClickedParentEditor(String featureClicked);

    void reset();

    boolean isRequirePremium();

    FeatureInterface clone(FeatureParentInterface newParent);
}
