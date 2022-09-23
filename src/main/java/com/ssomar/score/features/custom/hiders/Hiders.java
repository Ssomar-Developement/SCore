package com.ssomar.score.features.custom.hiders;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Hiders extends FeatureWithHisOwnEditor<Hiders, Hiders, HidersEditor, HidersEditorManager> {

    private BooleanFeature hideEnchantments;
    private BooleanFeature hideUnbreakable;
    private BooleanFeature hideAttributes;
    private BooleanFeature hidePotionEffects;
    private BooleanFeature hideUsage;
    private BooleanFeature hideDye;

    public Hiders(FeatureParentInterface parent) {
        super(parent, "hiders", "Hiders", new String[]{"&7&oHiders to hide:", "&7&oAttributes, Enchants, ..."}, Material.ANVIL, false);
        reset();
    }

    @Override
    public void reset() {
        this.hideEnchantments = new BooleanFeature(getParent(), "hideEnchantments", false, "Hide enchantments", new String[]{"&7&oHide enchantments"}, Material.LEVER, false, false);
        this.hideUnbreakable = new BooleanFeature(getParent(), "hideUnbreakable", false, "Hide unbreakable", new String[]{"&7&oHide unbreakable"}, Material.LEVER, false, false);
        this.hideAttributes = new BooleanFeature(getParent(), "hideAttributes", false, "Hide attributes", new String[]{"&7&oHide attributes"}, Material.LEVER, false, false);
        this.hidePotionEffects = new BooleanFeature(getParent(), "hidePotionEffects", false, "Hide potion effects / banner tags", new String[]{"&7&oHide Potion effects", "&7&oand banner tags"}, Material.LEVER, false, false);
        this.hideUsage = new BooleanFeature(getParent(), "hideUsage", false, "Hide usage", new String[]{"&7&oHide usage"}, Material.LEVER, false, false);
        this.hideDye = new BooleanFeature(getParent(), "hideDye", false, "Hide dye", new String[]{"&7&oHide dye"}, Material.LEVER, false, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection section = config.getConfigurationSection(this.getName());
            hideEnchantments.load(plugin, section, isPremiumLoading);
            hideUnbreakable.load(plugin, section, isPremiumLoading);
            hideAttributes.load(plugin, section, isPremiumLoading);
            hidePotionEffects.load(plugin, section, isPremiumLoading);
            hideUsage.load(plugin, section, isPremiumLoading);
            if(SCore.is1v17Plus()) hideDye.load(plugin, section, isPremiumLoading);
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(getName(), null);
        ConfigurationSection section = config.createSection(getName());
        hideEnchantments.save(section);
        hideUnbreakable.save(section);
        hideAttributes.save(section);
        hidePotionEffects.save(section);
        hideUsage.save(section);
        if(SCore.is1v17Plus()) hideDye.save(section);
    }

    @Override
    public Hiders getValue() {
        return this;
    }

    @Override
    public Hiders initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 7];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 7] = GUI.CLICK_HERE_TO_CHANGE;
        if (hideEnchantments.getValue())
            finalDescription[finalDescription.length - 6] = "&7Hide enchantments: &a&l✔";
        else
            finalDescription[finalDescription.length - 6] = "&7Hide enchantments: &c&l✘";
        if (hideUnbreakable.getValue())
            finalDescription[finalDescription.length - 5] = "&7Hide unbreakable: &a&l✔";
        else
            finalDescription[finalDescription.length - 5] = "&7Hide unbreakable: &c&l✘";
        if (hideAttributes.getValue())
            finalDescription[finalDescription.length - 4] = "&7Hide attributes: &a&l✔";
        else
            finalDescription[finalDescription.length - 4] = "&7Hide attributes: &c&l✘";
        if (hidePotionEffects.getValue())
            finalDescription[finalDescription.length - 3] = "&7Hide effects / banner tags: &a&l✔";
        else
            finalDescription[finalDescription.length - 3] = "&7Hide effects / banner tags: &c&l✘";
        if (hideUsage.getValue())
            finalDescription[finalDescription.length - 2] = "&7Hide usage: &a&l✔";
        else
            finalDescription[finalDescription.length - 2] = "&7Hide usage: &c&l✘";
        if(!SCore.is1v17Plus()) {
            finalDescription[finalDescription.length - 1] = "&7Hide dye: &6&lONLY 1.17 & +";
        }
        else if (hideDye.getValue())
            finalDescription[finalDescription.length - 1] = "&7Hide dye: &a&l✔";
        else
            finalDescription[finalDescription.length - 1] = "&7Hide dye: &c&l✘";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public Hiders clone(FeatureParentInterface newParent) {
        Hiders dropFeatures = new Hiders(newParent);
        dropFeatures.hideEnchantments = hideEnchantments.clone(dropFeatures);
        dropFeatures.hideUnbreakable = hideUnbreakable.clone(dropFeatures);
        dropFeatures.hideAttributes = hideAttributes.clone(dropFeatures);
        dropFeatures.hidePotionEffects = hidePotionEffects.clone(dropFeatures);
        dropFeatures.hideUsage = hideUsage.clone(dropFeatures);
        dropFeatures.hideDye = hideDye.clone(dropFeatures);
        return dropFeatures;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(hideEnchantments);
        features.add(hideUnbreakable);
        features.add(hideAttributes);
        features.add(hidePotionEffects);
        features.add(hideUsage);
        if(SCore.is1v17Plus()) features.add(hideDye);
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
            if (feature instanceof Hiders) {
                Hiders hiders = (Hiders) feature;
                hiders.setHideEnchantments(hideEnchantments);
                hiders.setHideUnbreakable(hideUnbreakable);
                hiders.setHideAttributes(hideAttributes);
                hiders.setHidePotionEffects(hidePotionEffects);
                hiders.setHideUsage(hideUsage);
                if(SCore.is1v17Plus()) hiders.setHideDye(hideDye);
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
