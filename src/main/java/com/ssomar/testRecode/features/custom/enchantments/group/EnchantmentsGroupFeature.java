package com.ssomar.testRecode.features.custom.enchantments.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.FeaturesGroup;
import com.ssomar.testRecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.testRecode.features.custom.enchantments.enchantment.EnchantmentWithLevelFeatureEditor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter @Setter
public class EnchantmentsGroupFeature extends FeatureWithHisOwnEditor<EnchantmentsGroupFeature, EnchantmentsGroupFeature, EnchantmentsGroupFeatureEditor, EnchantmentsGroupFeatureEditorManager> implements FeaturesGroup {

    private Map<String, EnchantmentWithLevelFeature> enchantments;

    public EnchantmentsGroupFeature(FeatureParentInterface parent) {
        super(parent, "Enchantments", "Enchantments", new String[]{"&7&oThe enchantments"}, Material.ENCHANTED_BOOK, false);
        reset();
    }

    @Override
    public void reset() {
        this.enchantments = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if(config.isConfigurationSection("enchantments")) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection("enchantments");
            for(String enchantmentID : enchantmentsSection.getKeys(false)) {
                ConfigurationSection enchantmentSection = enchantmentsSection.getConfigurationSection(enchantmentID);
                EnchantmentWithLevelFeature enchantment = new EnchantmentWithLevelFeature(this, enchantmentID);
                List<String> subErrors = enchantment.load(plugin, enchantmentSection, isPremiumLoading);
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
        config.set("enchantments", null);
        for(String enchantmentID : enchantments.keySet()) {
            config.createSection("enchantments." + enchantmentID);
            enchantments.get(enchantmentID).save(config.getConfigurationSection("enchantments." + enchantmentID));
        }
    }

    @Override
    public EnchantmentsGroupFeature getValue() {
        return this;
    }

    @Override
    public EnchantmentsGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 1];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 1] = gui.CLICK_HERE_TO_CHANGE;

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {

    }

    @Override
    public EnchantmentsGroupFeature clone() {
        EnchantmentsGroupFeature eF = new EnchantmentsGroupFeature(getParent());
        eF.setEnchantments(new HashMap<>(getEnchantments()));
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(enchantments.values());
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
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof EnchantmentsGroupFeature) {
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
        for(int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if(!enchantments.containsKey(id)) {
                EnchantmentWithLevelFeature eF = new EnchantmentWithLevelFeature(this, id);
                enchantments.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }
}
