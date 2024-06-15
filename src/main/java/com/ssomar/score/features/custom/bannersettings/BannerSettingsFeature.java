package com.ssomar.score.features.custom.bannersettings;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.patterns.group.PatternsGroupFeature;
import com.ssomar.score.features.types.ColorIntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Banner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BannerSettingsFeature extends FeatureWithHisOwnEditor<BannerSettingsFeature, BannerSettingsFeature, BannerSettingsFeatureEditor, BannerSettingsFeatureEditorManager> {

    private ColorIntegerFeature color;
    private PatternsGroupFeature patterns;

    public BannerSettingsFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.bannerSettings);
        reset();
    }

    @Override
    public void reset() {
        this.color = new ColorIntegerFeature(this, Optional.empty(), FeatureSettingsSCore.color);
        this.patterns = new PatternsGroupFeature(this);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection potionSettings = config.getConfigurationSection(getName());
            errors.addAll(color.load(plugin, potionSettings, isPremiumLoading));
            errors.addAll(this.patterns.load(plugin, potionSettings, isPremiumLoading));
        }

        return errors;
    }

    public void load(SPlugin plugin, ItemStack item, boolean isPremiumLoading) {
        if (item != null && item.hasItemMeta()) {
            patterns.load(plugin, item, isPremiumLoading);
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof BlockStateMeta) {
                BlockStateMeta bmeta = (BlockStateMeta) meta;
                Banner banner = (Banner) bmeta.getBlockState();
                color.setValue(Optional.of(banner.getBaseColor().getColor().asRGB()));
            }
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection potionSettings = config.createSection(getName());
        color.save(potionSettings);
        patterns.save(potionSettings);
    }

    @Override
    public BannerSettingsFeature getValue() {
        return this;
    }

    @Override
    public BannerSettingsFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        if (color.getValue().isPresent())
            finalDescription[finalDescription.length - 2] = "&7Color : &e" + color.getValue().get();
        else finalDescription[finalDescription.length - 2] = "&7Color : &cNO VALUE";
        finalDescription[finalDescription.length - 1] = "&7Pattern(s) : &e" + patterns.getMCPatterns().size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public BannerSettingsFeature clone(FeatureParentInterface newParent) {
        BannerSettingsFeature dropFeatures = new BannerSettingsFeature(newParent);
        dropFeatures.color = color.clone(dropFeatures);
        dropFeatures.setPatterns(this.patterns.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(color, patterns));
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
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof BannerSettingsFeature) {
                BannerSettingsFeature hiders = (BannerSettingsFeature) feature;
                hiders.setColor(color);
                hiders.setPatterns(patterns);
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
