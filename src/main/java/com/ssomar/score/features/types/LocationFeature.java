package com.ssomar.score.features.types;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class LocationFeature extends FeatureAbstract<Optional<Location>, LocationFeature> {

    private Optional<Location> value;
    private Optional<Location> defaultValue;

    public LocationFeature(FeatureParentInterface parent, Optional<Location> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        Location location = config.getLocation(this.getName(), null);
        if (location == null) {
            value = defaultValue;
        } else {
            value = Optional.of(location);
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Location> value = getValue();
        value.ifPresent(location -> config.set(this.getName(), location));
    }

    @Override
    public Optional<Location> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public LocationFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public LocationFeature clone(FeatureParentInterface newParent) {
        LocationFeature clone = new LocationFeature(newParent, getDefaultValue(), getFeatureSettings());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

}
