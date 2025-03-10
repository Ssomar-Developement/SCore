package com.ssomar.score.features.custom.storage;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ColoredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class StorageFeatures extends FeatureWithHisOwnEditor<StorageFeatures, StorageFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> {

    private BooleanFeature enable;
    private ColoredStringFeature title;

    public StorageFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.storageFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.enable = new BooleanFeature(this,  false, FeatureSettingsSCore.enable);
        this.title = new ColoredStringFeature(this, Optional.empty(), FeatureSettingsSCore.title);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            error.addAll(this.enable.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
            error.addAll(this.title.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.enable.save(section);
        this.title.save(section);
    }

    public StorageFeatures getValue() {
        return this;
    }

    @Override
    public StorageFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 3] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;
        if (enable.getValue())
            finalDescription[finalDescription.length - 2] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Disabled: &c&l✘";
        finalDescription[finalDescription.length - 1] = "&7Title: &e" + title.getValue().orElse("No title");

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public StorageFeatures clone(FeatureParentInterface newParent) {
        StorageFeatures dropFeatures = new StorageFeatures(newParent);
        dropFeatures.setEnable(this.enable.clone(dropFeatures));
        dropFeatures.setTitle(this.title.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.enable);
        features.add(this.title);
        return features;
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
        for (FeatureInterface feature : (List<FeatureInterface>) getParent().getFeatures()) {
            if (feature instanceof StorageFeatures) {
                StorageFeatures dropFeatures = (StorageFeatures) feature;
                dropFeatures.setEnable(this.enable);
                dropFeatures.setTitle(this.title);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

    public String getSimpleLocString(Location loc){
        return loc.getWorld().getName() + "-" + loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ();
    }
}
