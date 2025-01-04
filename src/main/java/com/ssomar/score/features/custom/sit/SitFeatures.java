package com.ssomar.score.features.custom.sit;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
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

@Getter
@Setter
public class SitFeatures extends FeatureWithHisOwnEditor<SitFeatures, SitFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> {

    private BooleanFeature playerCanSit;

    public SitFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.sitFeatures);
        reset();
    }

    @Override
    public void reset() {
        this.playerCanSit = new BooleanFeature(this,  false, FeatureSettingsSCore.playerCanSit, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            error.addAll(this.playerCanSit.load(plugin, config.getConfigurationSection(getName()), isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        this.playerCanSit.save(section);
    }

    public SitFeatures getValue() {
        return this;
    }

    @Override
    public SitFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (isRequirePremium() && !isPremium()) finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        if (playerCanSit.getValue())
            finalDescription[finalDescription.length - 1] = "&7Player can sit: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Player can sit: &c&l✘";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public SitFeatures clone(FeatureParentInterface newParent) {
        SitFeatures dropFeatures = new SitFeatures(newParent);
        dropFeatures.setPlayerCanSit(this.playerCanSit.clone(dropFeatures));
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.playerCanSit);
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
            if (feature instanceof SitFeatures) {
                SitFeatures dropFeatures = (SitFeatures) feature;
                dropFeatures.setPlayerCanSit(this.playerCanSit);
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
