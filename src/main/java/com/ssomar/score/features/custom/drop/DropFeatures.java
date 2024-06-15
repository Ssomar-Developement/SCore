package com.ssomar.score.features.custom.drop;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.ChatColorFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class DropFeatures extends FeatureWithHisOwnEditor<DropFeatures, DropFeatures, DropFeaturesEditor, DropFeaturesEditorManager> {

    private BooleanFeature glowDrop;
    private ChatColorFeature dropColor;
    private BooleanFeature displayNameDrop;

    public DropFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.dropOptions);
        reset();
    }

    @Override
    public void reset() {
        this.glowDrop = new BooleanFeature(getParent(), false, FeatureSettingsSCore.glowDrop, false);
        this.dropColor = new ChatColorFeature(getParent(), Optional.of(ChatColor.WHITE), FeatureSettingsSCore.glowDropColor);
        this.displayNameDrop = new BooleanFeature(getParent(), false, FeatureSettingsSCore.displayNameDrop, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            glowDrop.load(plugin, section, isPremiumLoading);
            if (glowDrop.getValue() && SCore.is1v11Less()) {
                error.add(plugin.getNameDesign() + " " + getParent().getParentInfo() + " glowDrop is not supported in 1.11, 1.10, 1.9, 1.8 !");
                glowDrop.setValue(false);
            }
            if (!SCore.is1v11Less()) dropColor.load(plugin, section, isPremiumLoading);
            displayNameDrop.load(plugin, section, isPremiumLoading);
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        glowDrop.save(section);
        dropColor.save(section);
        displayNameDrop.save(section);
    }

    @Override
    public DropFeatures getValue() {
        return this;
    }

    @Override
    public DropFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        if (SCore.is1v12Less())
            finalDescription[finalDescription.length - 3] = "&7Glow drop: &c&lNot for 1.11 or lower";
        else if (glowDrop.getValue())
            finalDescription[finalDescription.length - 3] = "&7Glow drop: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Glow drop: &c&l✘";
        if (SCore.is1v12Less())
            finalDescription[finalDescription.length - 2] = "&7Glow drop color: &c&lNot for 1.11 or lower";
        else if (dropColor.getValue().isPresent()) {
            finalDescription[finalDescription.length - 2] = "&7Glow drop color: &e" + dropColor.getValue().get().name();
        } else {
            finalDescription[finalDescription.length - 2] = "&7Glow drop color: &c&l✘";
        }

        if (displayNameDrop.getValue())
            finalDescription[finalDescription.length - 1] = "&7Display custom name: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Display custom name: &c&l✘";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public DropFeatures clone(FeatureParentInterface newParent) {
        DropFeatures dropFeatures = new DropFeatures(newParent);
        dropFeatures.setGlowDrop(glowDrop.clone(dropFeatures));
        dropFeatures.setDropColor(dropColor.clone(dropFeatures));
        dropFeatures.setDisplayNameDrop(displayNameDrop.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        if (!SCore.is1v11Less()) {
            features.add(glowDrop);
            features.add(dropColor);
        }
        features.add(displayNameDrop);
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
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof DropFeatures) {
                DropFeatures dropFeatures = (DropFeatures) feature;
                dropFeatures.setGlowDrop(glowDrop);
                dropFeatures.setDropColor(dropColor);
                dropFeatures.setDisplayNameDrop(displayNameDrop);
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
        DropFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
