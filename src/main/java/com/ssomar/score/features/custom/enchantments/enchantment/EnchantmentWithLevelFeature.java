package com.ssomar.score.features.custom.enchantments.enchantment;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.EnchantmentFeature;
import com.ssomar.score.features.types.IntegerFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class EnchantmentWithLevelFeature extends FeatureWithHisOwnEditor<EnchantmentWithLevelFeature, EnchantmentWithLevelFeature, EnchantmentWithLevelFeatureEditor, EnchantmentWithLevelFeatureEditorManager> {

    private EnchantmentFeature enchantment;
    private IntegerFeature level;
    private String id;

    public EnchantmentWithLevelFeature(FeatureParentInterface parent, String id) {
        super(parent, FeatureSettingsSCore.enchantmentWithLevel);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.enchantment = new EnchantmentFeature(this, Optional.of(getDefaultEnchantment()), FeatureSettingsSCore.enchantment);
        this.level = new IntegerFeature(this, Optional.of(1), FeatureSettingsSCore.level);
    }

    public static Enchantment getDefaultEnchantment() {
        return SCore.is1v20v5Plus() ? Enchantment.UNBREAKING : Enchantment.getByName("DURABILITY");
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        if (config.isConfigurationSection(id)) {
            ConfigurationSection enchantmentConfig = config.getConfigurationSection(id);
            errors.addAll(enchantment.load(plugin, enchantmentConfig, isPremiumLoading));
            errors.addAll(level.load(plugin, enchantmentConfig, isPremiumLoading));
        } else {
            errors.add("&cERROR, Couldn't load the Enchantment with level value because there is not section with the good ID: " + id + " &7&o" + getParent().getParentInfo());
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
        ConfigurationSection enchantmentConfig = config.createSection(id);
        enchantment.save(enchantmentConfig);
        level.save(enchantmentConfig);
    }

    @Override
    public EnchantmentWithLevelFeature getValue() {
        return this;
    }

    @Override
    public EnchantmentWithLevelFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = "&7Enchantment: &e" + enchantment.getEnchantmentName(enchantment.getValue().get());
        finalDescription[finalDescription.length - 3] = "&7Level: &e" + level.getValue().get();
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = GUI.SHIFT_CLICK_TO_REMOVE;

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName() + " - " + "(" + id + ")", false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public EnchantmentWithLevelFeature clone(FeatureParentInterface newParent) {
        EnchantmentWithLevelFeature eF = new EnchantmentWithLevelFeature(newParent, id);
        eF.setEnchantment(enchantment.clone(eF));
        eF.setLevel(level.clone(eF));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(enchantment, level));
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection parentSection = getParent().getConfigurationSection();
        if (parentSection.isConfigurationSection(getId())) {
            return parentSection.getConfigurationSection(getId());
        } else return parentSection.createSection(getId());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof EnchantmentWithLevelFeature) {
                EnchantmentWithLevelFeature eF = (EnchantmentWithLevelFeature) feature;
                if (eF.getId().equals(id)) {
                    eF.setEnchantment(enchantment);
                    eF.setLevel(level);
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
        EnchantmentWithLevelFeatureEditorManager.getInstance().startEditing(player, this);
    }

}
