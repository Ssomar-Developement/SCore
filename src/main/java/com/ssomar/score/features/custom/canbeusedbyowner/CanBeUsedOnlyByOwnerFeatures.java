package com.ssomar.score.features.custom.canbeusedbyowner;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class CanBeUsedOnlyByOwnerFeatures extends FeatureWithHisOwnEditor<CanBeUsedOnlyByOwnerFeatures, CanBeUsedOnlyByOwnerFeatures, CanBeUsedOnlyByOwnerFeaturesEditor, CanBeUsedOnlyByOwnerFeaturesEditorManager> {

    private BooleanFeature canBeUsedOnlyByTheOwner;
    private BooleanFeature cancelEventIfNotOwner;
    private ListUncoloredStringFeature blackListedActivators;

    public CanBeUsedOnlyByOwnerFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.canBeUsedOnlyByTheOwner);
        reset();
    }

    @Override
    public void reset() {
        this.canBeUsedOnlyByTheOwner = new BooleanFeature(this, false, FeatureSettingsSCore.canBeUsedOnlyByTheOwner, false);
        this.cancelEventIfNotOwner = new BooleanFeature(this, false, FeatureSettingsSCore.cancelEventIfNotOwner, false);
        this.blackListedActivators = new ListUncoloredStringFeature(this, new ArrayList<>(), FeatureSettingsSCore.onlyOwnerBlackListedActivators, false, Optional.empty());
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        errors.addAll(canBeUsedOnlyByTheOwner.load(plugin, config, isPremiumLoading));
        errors.addAll(cancelEventIfNotOwner.load(plugin, config, isPremiumLoading));
        errors.addAll(blackListedActivators.load(plugin, config, isPremiumLoading));

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        canBeUsedOnlyByTheOwner.save(config);
        cancelEventIfNotOwner.save(config);
        blackListedActivators.save(config);
    }

    @Override
    public CanBeUsedOnlyByOwnerFeatures getValue() {
        return this;
    }

    @Override
    public CanBeUsedOnlyByOwnerFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        if (canBeUsedOnlyByTheOwner.getValue())
            finalDescription[finalDescription.length - 3] = "&7Can be used only by the owner: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Can be used only by the owner: &c&l✘";

        if (cancelEventIfNotOwner.getValue()) {
            finalDescription[finalDescription.length - 2] = "&7CancelEvent Not owner: &a&l✔";
        } else {
            finalDescription[finalDescription.length - 2] = "&7CancelEvent Not owner: &c&l✘";
        }
        finalDescription[finalDescription.length - 1] = "&7BlackListed Activators: &e"+blackListedActivators.getValue().toString();

        gui.createItem(GUI.GRINDSTONE, 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public CanBeUsedOnlyByOwnerFeatures clone(FeatureParentInterface newParent) {
        CanBeUsedOnlyByOwnerFeatures dropFeatures = new CanBeUsedOnlyByOwnerFeatures(newParent);
        dropFeatures.setCanBeUsedOnlyByTheOwner(canBeUsedOnlyByTheOwner.clone(dropFeatures));
        dropFeatures.setCancelEventIfNotOwner(cancelEventIfNotOwner.clone(dropFeatures));
        dropFeatures.setBlackListedActivators(blackListedActivators.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(canBeUsedOnlyByTheOwner);
        features.add(cancelEventIfNotOwner);
        features.add(blackListedActivators);
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
            if (feature instanceof CanBeUsedOnlyByOwnerFeatures) {
                CanBeUsedOnlyByOwnerFeatures hiders = (CanBeUsedOnlyByOwnerFeatures) feature;
                hiders.setCanBeUsedOnlyByTheOwner(canBeUsedOnlyByTheOwner);
                hiders.setCancelEventIfNotOwner(cancelEventIfNotOwner);
                hiders.setBlackListedActivators(blackListedActivators);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        CanBeUsedOnlyByOwnerFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
