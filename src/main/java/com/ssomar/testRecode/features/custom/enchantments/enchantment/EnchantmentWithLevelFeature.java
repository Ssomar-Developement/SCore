package com.ssomar.testRecode.features.custom.enchantments.enchantment;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.types.EnchantmentFeature;
import com.ssomar.testRecode.features.types.IntegerFeature;
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
import java.util.Optional;

@Getter @Setter
public class EnchantmentWithLevelFeature extends FeatureWithHisOwnEditor<EnchantmentWithLevelFeature, EnchantmentWithLevelFeature, EnchantmentWithLevelFeatureEditor, EnchantmentWithLevelFeatureEditorManager> {

    private EnchantmentFeature enchantment;
    private IntegerFeature level;
    private String id;

    public EnchantmentWithLevelFeature(FeatureParentInterface parent, String id) {
        super(parent, "Enchantment with level", "Enchantment with level", new String[]{"&7&oAn enchantment with level"}, Material.ENCHANTED_BOOK, false);
        this.id = id;
        reset();
    }

    @Override
    public void reset() {
        this.enchantment = new EnchantmentFeature(this, "Enchantment", Optional.empty(), "Enchantment", new String[]{"&7&oThe enchantment"}, Material.ENCHANTED_BOOK, false);
        this.level = new IntegerFeature(this, "Level", Optional.of(1), "Level", new String[]{"&7&oThe level of the enchantment"}, Material.BEACON, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        enchantment.load(plugin, config, isPremiumLoading);
        level.load(plugin, config, isPremiumLoading);
        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        enchantment.save(config);
        level.save(config);
    }

    @Override
    public EnchantmentWithLevelFeature getValue() {
        return this;
    }

    @Override
    public EnchantmentWithLevelFeature initItemParentEditor(GUI gui, int slot) {
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
    public EnchantmentWithLevelFeature clone() {
        EnchantmentWithLevelFeature eF = new EnchantmentWithLevelFeature(getParent(), id);
        eF.setEnchantment(enchantment.clone());
        eF.setLevel(level.clone());
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
        return getParent().getConfigurationSection();
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for(FeatureInterface feature : getParent().getFeatures()) {
            if(feature instanceof EnchantmentWithLevelFeature) {
                EnchantmentWithLevelFeature eF = (EnchantmentWithLevelFeature) feature;
                if(eF.getId().equals(id)) {
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
