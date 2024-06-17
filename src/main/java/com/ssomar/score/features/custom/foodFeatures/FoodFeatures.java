package com.ssomar.score.features.custom.foodFeatures;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
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
public class FoodFeatures extends FeatureWithHisOwnEditor<FoodFeatures, FoodFeatures, FoodFeaturesEditor, FoodFeaturesEditorManager> {


    private IntegerFeature nutrition;
    private IntegerFeature saturation;
    private BooleanFeature isMeat;
    private BooleanFeature canAlwaysEat;
    private IntegerFeature eatSeconds;

    public FoodFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.food);
        reset();
    }

    @Override
    public void reset() {
        nutrition = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.nutrition);
        saturation = new IntegerFeature(this,  Optional.of(1), FeatureSettingsSCore.saturation);
        isMeat = new BooleanFeature(this, false, FeatureSettingsSCore.isMeat, false);
        canAlwaysEat = new BooleanFeature(this,  false, FeatureSettingsSCore.canAlwaysEat, false);
        eatSeconds = new IntegerFeature(this,  Optional.of(1), FeatureSettingsSCore.eatSeconds);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(getName())) {
            ConfigurationSection section = config.getConfigurationSection(getName());
            errors.addAll(nutrition.load(plugin, section, isPremiumLoading));
            errors.addAll(saturation.load(plugin, section, isPremiumLoading));
            errors.addAll(isMeat.load(plugin, section, isPremiumLoading));
            errors.addAll(canAlwaysEat.load(plugin, section, isPremiumLoading));
            errors.addAll(eatSeconds.load(plugin, section, isPremiumLoading));

        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        nutrition.save(section);
        saturation.save(section);
        isMeat.save(section);
        canAlwaysEat.save(section);
        eatSeconds.save(section);
    }

    @Override
    public FoodFeatures getValue() {
        return this;
    }

    @Override
    public FoodFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 6];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 6] = GUI.CLICK_HERE_TO_CHANGE;

        if (isMeat.getValue())
            finalDescription[finalDescription.length - 5] = "&7IsMeat: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7IsMeat: &c&l✘";

        finalDescription[finalDescription.length - 4] = "&7Nutrition: &e" + nutrition.getValue().get();
        finalDescription[finalDescription.length - 3] = "&7Saturation: &e" + saturation.getValue().get();
        finalDescription[finalDescription.length - 2] = "&7Can Always Eat: &e" + canAlwaysEat.getValue();
        finalDescription[finalDescription.length - 1] = "&7Eat Seconds: &e" + eatSeconds.getValue().get();




        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public FoodFeatures clone(FeatureParentInterface newParent) {
        FoodFeatures dropFeatures = new FoodFeatures(newParent);
        dropFeatures.nutrition = nutrition.clone(dropFeatures);
        dropFeatures.saturation = saturation.clone(dropFeatures);
        dropFeatures.isMeat = isMeat.clone(dropFeatures);
        dropFeatures.canAlwaysEat = canAlwaysEat.clone(dropFeatures);
        dropFeatures.eatSeconds = eatSeconds.clone(dropFeatures);

        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(nutrition);
        features.add(saturation);
        features.add(isMeat);
        features.add(canAlwaysEat);
        features.add(eatSeconds);
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
            if (feature instanceof FoodFeatures) {
                FoodFeatures hiders = (FoodFeatures) feature;
                hiders.setNutrition(nutrition);
                hiders.setSaturation(saturation);
                hiders.setIsMeat(isMeat);
                hiders.setCanAlwaysEat(canAlwaysEat);
                hiders.setEatSeconds(eatSeconds);
            }
        }
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        FoodFeaturesEditorManager.getInstance().startEditing(player, this);
    }

}
