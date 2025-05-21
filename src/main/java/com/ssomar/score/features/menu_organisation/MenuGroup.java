package com.ssomar.score.features.menu_organisation;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class MenuGroup extends FeatureWithHisOwnEditor<MenuGroup, MenuGroup, MenuGroupEditor, MenuGroupEditorManager> implements FeaturesGroup<FeatureInterface> {

    List<FeatureInterface> features;

    private int premiumLimit = 5;

    public MenuGroup(FeatureParentInterface parent, FeatureSettingsSCore settings, FeatureInterface... features) {
        super(parent, settings);
        this.features = new ArrayList<>(Arrays.asList(features));
        // Remove null values
        this.features.removeIf(Objects::isNull);
        reset();
    }

    @Override
    public void reset() {

    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {

    }

    @Override
    public MenuGroup getValue() {
        return this;
    }

    @Override
    public MenuGroup initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void createNewFeature(@NotNull Player editor) {

    }

    @Override
    public void deleteFeature(@NotNull Player editor, FeatureInterface feature) {

    }

    @Override
    public FeatureInterface getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (FeatureInterface x : features) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public MenuGroup clone(FeatureParentInterface newParent) {
        setParent(newParent);
        return this;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return features;
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        } else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        SsomarDev.testMsg("Reloading MenuGroup " + getName(), true);
        MemoryConfiguration section = new MemoryConfiguration();
        for (FeatureInterface feature : features) {
            feature.save(section);
        }

        // Recupération des features update
        List<FeatureInterface> newFeatures = new ArrayList<>();
        for (FeatureInterface feature : features) {
            for (FeatureInterface featureOfParent : (List<FeatureInterface>) getParent().getFeatures()) {
                if (featureOfParent.getName().equalsIgnoreCase(feature.getName())) {
                    featureOfParent.load(SCore.plugin, section, true);
                    newFeatures.add(featureOfParent);
                    break;
                }
            }
        }
        features = newFeatures;
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        // Reload is a workaround to be sure to have the latest values, its important to keep it here
        reload();
        MenuGroupEditorManager.getInstance().startEditing(player, this);
    }

}
