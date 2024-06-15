package com.ssomar.score.features.custom.patterns.subpattern;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.ObjectFeature;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SubPatternFeature extends FeatureWithHisOwnEditor<SubPatternFeature, SubPatternFeature, SubPatternFeatureEditor, SubPatternFeatureEditorManager> {

    private UncoloredStringFeature string;
    private ObjectFeature object;
    private String id;

    public SubPatternFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.subPattern);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.string = new UncoloredStringFeature(this, Optional.empty(), FeatureSettingsSCore.string, false);
        this.object = new ObjectFeature(this, FeatureSettingsSCore.object);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(this.string.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(this.object.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Sub Pattern with its options because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
        }
        return errors;
    }

    @Override
    public boolean isTheFeatureClickedParentEditor(String featureClicked) {
        return featureClicked.contains(getEditorName()) && featureClicked.contains("(" + id + ")");
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(id, null);
        ConfigurationSection attributeConfig = config.createSection(id);
        this.string.save(attributeConfig);
        this.object.save(attributeConfig);
    }

    @Override
    public SubPatternFeature getValue() {
        return this;
    }

    @Override
    public SubPatternFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = GUI.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public SubPatternFeature clone(FeatureParentInterface newParent) {
        SubPatternFeature eF = new SubPatternFeature(newParent, id);
        eF.setString(string.clone(eF));
        eF.setObject(object.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(string, object));
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
            if (feature instanceof SubPatternFeature) {
                SubPatternFeature aFOF = (SubPatternFeature) feature;
                if (aFOF.getId().equals(id)) {
                    aFOF.setString(string);
                    aFOF.setObject(object);
                    break;
                }
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        SubPatternFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
