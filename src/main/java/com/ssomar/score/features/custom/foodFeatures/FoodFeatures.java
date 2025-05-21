package com.ssomar.score.features.custom.foodFeatures;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.*;
import com.ssomar.score.features.editor.GenericFeatureParentEditor;
import com.ssomar.score.features.editor.GenericFeatureParentEditorManager;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class FoodFeatures extends FeatureWithHisOwnEditor<FoodFeatures, FoodFeatures, GenericFeatureParentEditor, GenericFeatureParentEditorManager> implements FeatureForItemNewPaperComponents {


    private IntegerFeature nutrition;
    private IntegerFeature saturation;
    private BooleanFeature isMeat;
    private BooleanFeature canAlwaysEat;
    private IntegerFeature eatSeconds;

    public FoodFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.foodFeatures);
        reset();
    }

    @Override
    public void reset() {
        nutrition = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.nutrition);
        saturation = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.saturation);
        isMeat = new BooleanFeature(this, false, FeatureSettingsSCore.isMeat);
        canAlwaysEat = new BooleanFeature(this, false, FeatureSettingsSCore.canAlwaysEat);
        eatSeconds = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.eatSeconds);
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
            if (!SCore.is1v21v2Plus()) errors.addAll(eatSeconds.load(plugin, section, isPremiumLoading));

        }

        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        for (FeatureInterface feature : getFeatures()) {
            feature.save(section);
        }
        if (isSavingOnlyIfDiffDefault() && section.getKeys(false).isEmpty()) {
            config.set(getName(), null);
            return;
        }

        if (GeneralConfig.getInstance().isEnableCommentsInConfig())
            config.setComments(this.getName(), StringConverter.decoloredString(Arrays.asList(getFeatureSettings().getEditorDescriptionBrut())));

    }

    @Override
    public FoodFeatures getValue() {
        return this;
    }

    @Override
    public FoodFeatures initItemParentEditor(GUI gui, int slot) {
        int len = 5;
        if (!SCore.is1v21v2Plus()) len = 6;
        String[] finalDescription = new String[getEditorDescription().length + len];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - len] = GUI.CLICK_HERE_TO_CHANGE;
        len--;

        if (isMeat.getValue())
            finalDescription[finalDescription.length - len] = "&7IsMeat: &a&l✔";
        else
            finalDescription[finalDescription.length - len] = "&7IsMeat: &c&l✘";
        len--;
        finalDescription[finalDescription.length - len] = "&7Nutrition: &e" + nutrition.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Saturation: &e" + saturation.getValue().get();
        len--;
        finalDescription[finalDescription.length - len] = "&7Can Always Eat: &e" + canAlwaysEat.getValue();
        len--;
        if (!SCore.is1v21v2Plus())
            finalDescription[finalDescription.length - len] = "&7Eat Seconds: &e" + eatSeconds.getValue().get();


        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v5Plus();
    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return true;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {
        /* Added in 1.20.5 */
        if (isAvailable()) {
            if (getIsMeat().getValue()) {
                ItemMeta meta = args.getMeta();
                FoodComponent food = meta.getFood();
                int nutrition = getNutrition().getValue().get();
                if (nutrition < 0) nutrition = 0;
                food.setNutrition(nutrition);
                int saturation = getSaturation().getValue().get();
                if (saturation < 0) saturation = 0;
                food.setSaturation(saturation);
                food.setCanAlwaysEat(getCanAlwaysEat().getValue());
                if (!SCore.is1v21v2Plus()) {
                    int eatSeconds = getEatSeconds().getValue().get();
                    if (eatSeconds <= 0) eatSeconds = 1;
                    Class<?> clazz = food.getClass();
                    try {
                        Method method = clazz.getMethod("setEatSeconds", int.class);
                        method.invoke(food, eatSeconds);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}
                }
                meta.setFood(food);
            }
        }
    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {
        /* Added in 1.20.5 */
        if (isAvailable()) {
            ItemMeta meta = args.getMeta();
            if (meta.hasFood()) {
                FoodComponent food = meta.getFood();
                getNutrition().setValue(Optional.of(food.getNutrition()));
                getSaturation().setValue(Optional.of(new Float(food.getSaturation()).intValue()));
                getCanAlwaysEat().setValue(food.canAlwaysEat());
                if (!SCore.is1v21v2Plus()) {
                    Class<?> clazz = food.getClass();
                    try {
                        Method method = clazz.getMethod("getEatSeconds");
                        int eatSeconds = (int) method.invoke(food);
                        getEatSeconds().setValue(Optional.of(eatSeconds));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}
                }
                getIsMeat().setValue(true);
            }
        }
    }

    @Override
    public void applyOnItem(@NotNull FeatureForItemArgs args) {
        new FoodFeaturesPaper().applyOnItem(args, isMeat.getValue());
    }

    @Override
    public void loadFromItem(@NotNull FeatureForItemArgs args) {

    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.FOOD;
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
        features.add(isMeat);
        features.add(nutrition);
        features.add(saturation);
        features.add(canAlwaysEat);
        if (!SCore.is1v21v2Plus()) features.add(eatSeconds);
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
        GenericFeatureParentEditorManager.getInstance().startEditing(player, this);
    }

}
