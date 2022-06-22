package com.ssomar.scoretestrecode.features.custom.bannersettings;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.editor.NewGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.FeatureWithHisOwnEditor;
import com.ssomar.scoretestrecode.features.custom.patterns.group.PatternsGroupFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class BannerSettingsFeature extends FeatureWithHisOwnEditor<BannerSettingsFeature, BannerSettingsFeature, BannerSettingsFeatureEditor, BannerSettingsFeatureEditorManager> {

    private PatternsGroupFeature patterns;

    public BannerSettingsFeature(FeatureParentInterface parent) {
        super(parent, "bannerSettings", "Banner Settings", new String[]{"&7&oBanner settings"}, Material.CREEPER_BANNER_PATTERN, false);
        reset();
    }

    @Override
    public void reset() {
        this.patterns = new PatternsGroupFeature(this);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if(config.isConfigurationSection(getName())) {
            ConfigurationSection potionSettings = config.getConfigurationSection(getName());
            errors.addAll(this.patterns.load(plugin, potionSettings, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection potionSettings = config.createSection(getName());
        patterns.save(potionSettings);
    }

    @Override
    public BannerSettingsFeature getValue() {
        return this;
    }

    @Override
    public BannerSettingsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Pattern(s) : &e" + patterns.getPatterns().size();

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public BannerSettingsFeature clone() {
        BannerSettingsFeature dropFeatures = new BannerSettingsFeature(getParent());
        dropFeatures.setPatterns(this.patterns.clone());
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(patterns));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return getParent().getConfigurationSection();
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof BannerSettingsFeature) {
                BannerSettingsFeature hiders = (BannerSettingsFeature) feature;
                hiders.setPatterns(this.patterns);
                break;
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        BannerSettingsFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
