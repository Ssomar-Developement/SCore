package com.ssomar.score.features.custom.enchantments.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.projectiles.features.SProjectileFeatureInterface;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EnchantmentsGroupFeature extends FeatureWithHisOwnEditor<EnchantmentsGroupFeature, EnchantmentsGroupFeature, EnchantmentsGroupFeatureEditor, EnchantmentsGroupFeatureEditorManager> implements FeaturesGroup<EnchantmentWithLevelFeature>, SProjectileFeatureInterface {

    private Map<String, EnchantmentWithLevelFeature> enchantments;
    private boolean notSaveIfNoValue;

    public EnchantmentsGroupFeature(FeatureParentInterface parent, boolean notSaveIfNoValue) {
        super(parent, FeatureSettingsSCore.enchantments);
        this.notSaveIfNoValue = notSaveIfNoValue;
        reset();
    }

    @Override
    public void reset() {
        this.enchantments = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String enchantmentID : enchantmentsSection.getKeys(false)) {
                EnchantmentWithLevelFeature enchantment = new EnchantmentWithLevelFeature(this, enchantmentID);
                List<String> subErrors = enchantment.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                enchantments.put(enchantmentID, enchantment);
            }
        }
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        if (notSaveIfNoValue && enchantments.size() == 0) return;
        ConfigurationSection enchantmentsSection = config.createSection(this.getName());
        for (String enchantmentID : enchantments.keySet()) {
            enchantments.get(enchantmentID).save(enchantmentsSection);
        }

    }

    @Override
    public EnchantmentsGroupFeature getValue() {
        return this;
    }

    @Override
    public EnchantmentsGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oEnchantment(s) added: &e" + enchantments.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public EnchantmentWithLevelFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (EnchantmentWithLevelFeature x : enchantments.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public EnchantmentsGroupFeature clone(FeatureParentInterface newParent) {
        EnchantmentsGroupFeature eF = new EnchantmentsGroupFeature(newParent, isNotSaveIfNoValue());
        HashMap<String, EnchantmentWithLevelFeature> newEnchantments = new HashMap<>();
        for (String x : enchantments.keySet()) {
            newEnchantments.put(x, enchantments.get(x).clone(eF));
        }
        eF.setEnchantments(newEnchantments);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(enchantments.values());
    }

    @Override
    public String getParentInfo() {
        if (getParent() == this) {
            return "";
        } else return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        if (section.isConfigurationSection(this.getName())) {
            return section.getConfigurationSection(this.getName());
        } else return section.createSection(this.getName());
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof EnchantmentsGroupFeature) {
                EnchantmentsGroupFeature eF = (EnchantmentsGroupFeature) feature;
                eF.setEnchantments(getEnchantments());
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
        EnchantmentsGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "enchantment";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!enchantments.containsKey(id)) {
                EnchantmentWithLevelFeature eF = new EnchantmentWithLevelFeature(this, id);
                enchantments.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, EnchantmentWithLevelFeature feature) {
        enchantments.remove(feature.getId());
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (e instanceof Trident) {
            Trident t = (Trident) e;

            try {
                ItemStack item = t.getItem();
                ItemMeta meta = item.getItemMeta();
                for (EnchantmentWithLevelFeature eF : enchantments.values()) {
                    meta.addEnchant(eF.getEnchantment().getValue().get(), eF.getLevel().getValue().get(), true);
                }
                item.setItemMeta(meta);
                t.setItem(item);
            } catch (NoSuchMethodError ignored) {
            }
        }
    }
}
