package com.ssomar.score.features.custom.cancelevents;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
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

@Getter
@Setter
public class CancelEventFeatures extends FeatureWithHisOwnEditor<CancelEventFeatures, CancelEventFeatures, CancelEventFeaturesEditor, CancelEventFeaturesEditorManager> {

    private BooleanFeature cancelEventIfNoperm;
    private BooleanFeature cancelEventIfNotOwner;

    public CancelEventFeatures(FeatureParentInterface parent) {
        super(parent, "cancelEvents", "CancelEvent features", new String[]{"&7&oThe cancel events features"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.cancelEventIfNoperm = new BooleanFeature(this, "cancelEventIfNoPerm", false, "Cancel event if no perm", new String[]{"&7&oCancel event if no perm"}, Material.LEVER, false, false);
        this.cancelEventIfNotOwner = new BooleanFeature(this, "cancelEventIfNotOwner", false, "Cancel event if not owner", new String[]{"&7&oCancel event if not owner"}, Material.LEVER, false, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        cancelEventIfNoperm.load(plugin, config, isPremiumLoading);
        cancelEventIfNotOwner.load(plugin, config, isPremiumLoading);

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        cancelEventIfNoperm.save(config);
        cancelEventIfNotOwner.save(config);
    }

    @Override
    public CancelEventFeatures getValue() {
        return this;
    }

    @Override
    public CancelEventFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = GUI.CLICK_HERE_TO_CHANGE;

        if (cancelEventIfNoperm.getValue()) {
            finalDescription[finalDescription.length - 2] = "&7CancelEvent No perm: &a&l✔";
        } else {
            finalDescription[finalDescription.length - 2] = "&7CancelEvent No perm: &c&l✘";
        }

        if (cancelEventIfNotOwner.getValue()) {
            finalDescription[finalDescription.length - 1] = "&7CancelEvent Not owner: &a&l✔";
        } else {
            finalDescription[finalDescription.length - 1] = "&7CancelEvent Not owner: &c&l✘";
        }

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public CancelEventFeatures clone(FeatureParentInterface newParent) {
        CancelEventFeatures dropFeatures = new CancelEventFeatures(newParent);
        dropFeatures.setCancelEventIfNoperm(cancelEventIfNoperm.clone(dropFeatures));
        dropFeatures.setCancelEventIfNotOwner(cancelEventIfNotOwner.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(cancelEventIfNoperm, cancelEventIfNotOwner));
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
            if (feature instanceof CancelEventFeatures) {
                CancelEventFeatures dropFeatures = (CancelEventFeatures) feature;
                dropFeatures.setCancelEventIfNoperm(cancelEventIfNoperm);
                dropFeatures.setCancelEventIfNotOwner(cancelEventIfNotOwner);
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
        CancelEventFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
