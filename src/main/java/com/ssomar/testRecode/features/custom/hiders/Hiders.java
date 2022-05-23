package com.ssomar.testRecode.features.custom.hiders;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureWithHisOwnEditor;
import com.ssomar.testRecode.features.custom.drop.DropFeatures;
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
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class Hiders extends FeatureWithHisOwnEditor<Hiders, Hiders, HidersEditor, HidersEditorManager> {

    private BooleanFeature hideEnchantments;
    private BooleanFeature hideUnbreakable;
    private BooleanFeature hideAttributes;
    private BooleanFeature hidePotionEffects;
    private BooleanFeature hideUsage;

    public Hiders(FeatureParentInterface parent) {
        super(parent, "Hiders", "Hiders", new String[]{"&7&oHiders to hide:", "Attributes, Enchants, ..."}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.hideEnchantments = new BooleanFeature(getParent(), "hideEnchantments", false, "Hide echantments", new String[]{"&7&oHide enchantments"}, Material.LEVER, false);
        this.hideUnbreakable = new BooleanFeature(getParent(), "hideUnbreakable", false, "Hide unbreakable", new String[]{"&7&oHide unbreakable"}, Material.LEVER, false);
        this.hideAttributes = new BooleanFeature(getParent(), "hideAttributes", false, "Hide attributes", new String[]{"&7&oHide attributes"}, Material.LEVER, false);
        this.hidePotionEffects = new BooleanFeature(getParent(), "hidePotionEffects", false, "Hide potion effects / banner tags", new String[]{"&7&oHide Potion effects", "and banner tags"}, Material.LEVER, false);
        this.hideUsage = new BooleanFeature(getParent(), "hideUsage", false, "Hide usage", new String[]{"&7&oHide usage"}, Material.LEVER, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        hideEnchantments.load(plugin, config, isPremiumLoading);
        hideUnbreakable.load(plugin, config, isPremiumLoading);
        hideAttributes.load(plugin, config, isPremiumLoading);
        hidePotionEffects.load(plugin, config, isPremiumLoading);
        hideUsage.load(plugin, config, isPremiumLoading);

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        hideEnchantments.save(config);
        hideUnbreakable.save(config);
        hideAttributes.save(config);
        hidePotionEffects.save(config);
        hideUsage.save(config);
    }

    @Override
    public Hiders getValue() {
        return this;
    }

    @Override
    public Hiders initItemParentEditor(GUI gui, int slot) {
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
    public Hiders clone() {
        Hiders dropFeatures = new Hiders(getParent());
        dropFeatures.hideEnchantments = hideEnchantments.clone();
        dropFeatures.hideUnbreakable = hideUnbreakable.clone();
        dropFeatures.hideAttributes = hideAttributes.clone();
        dropFeatures.hidePotionEffects = hidePotionEffects.clone();
        dropFeatures.hideUsage = hideUsage.clone();
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(Arrays.asList(hideEnchantments, hideUnbreakable, hideAttributes, hidePotionEffects, hideUsage));
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
            if(feature instanceof Hiders) {
                Hiders hiders = (Hiders) feature;
                hiders.setHideEnchantments(hideEnchantments);
                hiders.setHideUnbreakable(hideUnbreakable);
                hiders.setHideAttributes(hideAttributes);
                hiders.setHidePotionEffects(hidePotionEffects);
                hiders.setHideUsage(hideUsage);
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
        HidersEditorManager.getInstance().startEditing(player, this);
    }

}
