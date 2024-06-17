package com.ssomar.score.features.custom.displayConditions;

import com.ssomar.executableitems.executableitems.ExecutableItemObject;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.custom.conditions.item.parent.ItemConditionsFeature;
import com.ssomar.score.features.custom.conditions.placeholders.group.PlaceholderConditionGroupFeature;
import com.ssomar.score.features.custom.conditions.player.parent.PlayerConditionsFeature;
import com.ssomar.score.features.custom.conditions.world.parent.WorldConditionsFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DisplayConditionsFeatures extends FeatureWithHisOwnEditor<DisplayConditionsFeatures, DisplayConditionsFeatures, DisplayConditionsFeaturesEditor, DisplayConditionsFeaturesEditorManager> {


    private BooleanFeature enableFeature;
    private PlayerConditionsFeature playerConditions;
    private WorldConditionsFeature worldConditions;
    private ItemConditionsFeature itemConditions;
    private PlaceholderConditionGroupFeature placeholderConditions;

    public DisplayConditionsFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.displayConditions);
        reset();
    }

    @Override
    public void reset() {
        this.enableFeature = new BooleanFeature(this, false, FeatureSettingsSCore.enableFeature, false);

        this.playerConditions = new PlayerConditionsFeature(this, FeatureSettingsSCore.playerConditions);

        this.worldConditions = new WorldConditionsFeature(this, FeatureSettingsSCore.worldConditions);

        this.itemConditions = new ItemConditionsFeature(this, FeatureSettingsSCore.itemConditions);

        this.placeholderConditions = new PlaceholderConditionGroupFeature(this);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (isPremiumLoading && config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(playerConditions.load(plugin, section, isPremiumLoading));
            errors.addAll(worldConditions.load(plugin, section, isPremiumLoading));
            errors.addAll(itemConditions.load(plugin, section, isPremiumLoading));
            errors.addAll(placeholderConditions.load(plugin, section, isPremiumLoading));
            errors.addAll(enableFeature.load(plugin, section, isPremiumLoading));
        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        playerConditions.save(section);
        worldConditions.save(section);
        itemConditions.save(section);
        placeholderConditions.save(section);
        enableFeature.save(section);
    }

    @Override
    public DisplayConditionsFeatures getValue() {
        return this;
    }

    @Override
    public DisplayConditionsFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = GUI.CLICK_HERE_TO_CHANGE;

        if (enableFeature.getValue())
            finalDescription[finalDescription.length - 5] = "&7Enabled: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7Enabled: &c&l✘";

        finalDescription[finalDescription.length - 4] = "&7Player Conditions: &e" + playerConditions.getPlayerConditionEnabledCount();
        finalDescription[finalDescription.length - 3] = "&7World Conditions: &e" + worldConditions.getWorldConditionEnabledCount();
        finalDescription[finalDescription.length - 2] = "&7Item Conditions: &e" + itemConditions.getItemConditionEnabledCount();
        finalDescription[finalDescription.length - 1] = "&7Placeholder Conditions: &e" + placeholderConditions.getPlaceholdersConditions().size();


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    public boolean verify(Player player, ExecutableItemObject eiO, List<String> errors){
        boolean result = true;
        if(!enableFeature.getValue()) return true;

        StringPlaceholder sp = new StringPlaceholder();
        sp.setPlayerPlcHldr(player.getUniqueId());
        sp.setVariables(eiO.getVariables());

        result = playerConditions.verifConditions(player, errors, sp) && result;
        result = worldConditions.verifConditions(player.getWorld(), errors, sp) && result;
        result = itemConditions.verifConditions(eiO.getItem(), errors, sp) && result;
        result = placeholderConditions.verifConditions(player, errors) && result;
        return result;
    }

    @Override
    public DisplayConditionsFeatures clone(FeatureParentInterface newParent) {
        DisplayConditionsFeatures dropFeatures = new DisplayConditionsFeatures(newParent);
        dropFeatures.playerConditions = playerConditions.clone(dropFeatures);
        dropFeatures.worldConditions = worldConditions.clone(dropFeatures);
        dropFeatures.itemConditions = itemConditions.clone(dropFeatures);
        dropFeatures.placeholderConditions = placeholderConditions.clone(dropFeatures);
        dropFeatures.enableFeature = enableFeature.clone(dropFeatures);
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(playerConditions);
        features.add(worldConditions);
        features.add(itemConditions);
        features.add(placeholderConditions);
        features.add(enableFeature);
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
            if (feature instanceof DisplayConditionsFeatures) {
                DisplayConditionsFeatures hiders = (DisplayConditionsFeatures) feature;
                hiders.setPlayerConditions(playerConditions);
                hiders.setWorldConditions(worldConditions);
                hiders.setItemConditions(itemConditions);
                hiders.setPlaceholderConditions(placeholderConditions);
                hiders.setEnableFeature(enableFeature);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        DisplayConditionsFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
