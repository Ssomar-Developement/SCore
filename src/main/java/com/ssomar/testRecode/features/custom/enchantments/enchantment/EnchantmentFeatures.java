package com.ssomar.testRecode.features.custom.enchantments.enchantment;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.types.BooleanFeature;
import com.ssomar.testRecode.features.types.ChatColorFeature;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
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
public class EnchantmentFeatures extends FeatureWithHisOwnEditor<EnchantmentFeatures, EnchantmentFeatures, EnchantmentFeaturesEditor, SEnchantmentEditorManager> {

    private BooleanFeature glowDrop;
    private ChatColorFeature dropColor;
    private BooleanFeature displayNameDrop;

    public EnchantmentFeatures(FeatureParentInterface parent) {
        super(parent, "Drop features", "Drop features", new String[]{"&7&oThe drop features"}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.glowDrop = new BooleanFeature(getParent(), "glowDrop", false, "Glow drop", new String[]{"&7&oGlow drop"}, Material.LEVER, false);
        this.dropColor = new ChatColorFeature(getParent(), "glowDropColor", Optional.of(ChatColor.WHITE), "Glow color", new String[]{"&7&oGlow drop color"}, Material.REDSTONE, true);
        this.displayNameDrop = new BooleanFeature(getParent(), "displayNameDrop", false, "Display custom name", new String[]{"&7&oDisplay custom name above the item"}, Material.LEVER, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        glowDrop.load(plugin, config, isPremiumLoading);
        if(glowDrop.getValue() && SCore.is1v11Less()) {
            error.add(plugin.getNameDesign() + " " + getParent().getParentInfo() + " glowDrop is not supported in 1.11, 1.10, 1.9, 1.8 !");
            glowDrop.setValue(false);
        }
        dropColor.load(plugin, config, isPremiumLoading);
        displayNameDrop.load(plugin, config, isPremiumLoading);

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        glowDrop.save(config);
        dropColor.save(config);
        displayNameDrop.save(config);
    }

    @Override
    public EnchantmentFeatures getValue() {
        return this;
    }

    @Override
    public EnchantmentFeatures initItemParentEditor(GUI gui, int slot) {
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
    public EnchantmentFeatures clone() {
        EnchantmentFeatures dropFeatures = new EnchantmentFeatures(getParent());
        dropFeatures.setGlowDrop(glowDrop.clone());
        dropFeatures.setDropColor(dropColor.clone());
        dropFeatures.setDisplayNameDrop(displayNameDrop.clone());
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(glowDrop, dropColor, displayNameDrop));
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
            if(feature instanceof EnchantmentFeatures) {
                EnchantmentFeatures dropFeatures = (EnchantmentFeatures) feature;
                dropFeatures.setGlowDrop(glowDrop);
                dropFeatures.setDropColor(dropColor);
                dropFeatures.setDisplayNameDrop(displayNameDrop);
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
        SEnchantmentEditorManager.getInstance().startEditing(player, this);
    }

}
