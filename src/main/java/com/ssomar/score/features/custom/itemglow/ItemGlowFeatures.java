package com.ssomar.score.features.custom.itemglow;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemGlowFeatures extends FeatureWithHisOwnEditor<ItemGlowFeatures, ItemGlowFeatures, ItemGlowFeaturesEditor, ItemGlowFeaturesEditorManager> {


    private BooleanFeature glow;
    private BooleanFeature disableEnchantGlide;

    public ItemGlowFeatures(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.glow);
        reset();
    }

    @Override
    public void reset() {
        this.glow = new BooleanFeature(this, false, FeatureSettingsSCore.glow, false);
        this.disableEnchantGlide = new BooleanFeature(this, false, FeatureSettingsSCore.disableEnchantGlide, false);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        for (FeatureInterface feature : getFeatures()) {
            error.addAll(feature.load(plugin, config, isPremiumLoading));
        }

        return error;
    }

    @Override
    public void save(ConfigurationSection config) {
        for (FeatureInterface feature : getFeatures()) {
            feature.save(config);
        }
    }

    @Override
    public ItemGlowFeatures getValue() {
        return this;
    }

    @Override
    public ItemGlowFeatures initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 3];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 3] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 2] = glow.getValue() ? "&7&oGlow: &a&l✔" : "&7&oEnabled: &c&l✘";
        finalDescription[finalDescription.length - 1] = disableEnchantGlide.getValue() ? "&7&oDisable Enchant Glide: &a&l✔" : "&7&oDisable Enchant Glide: &c&l✘";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
    }

    @Override
    public ItemGlowFeatures clone(FeatureParentInterface newParent) {
        ItemGlowFeatures eF = new ItemGlowFeatures(newParent);
        eF.glow = this.glow.clone(eF);
        if(SCore.is1v20v5Plus()) eF.disableEnchantGlide = this.disableEnchantGlide.clone(eF);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        List<FeatureInterface> features = new ArrayList<>();
        features.add(this.glow);
        if(SCore.is1v20v5Plus()) features.add(this.disableEnchantGlide);
        return features;
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        ConfigurationSection section = getParent().getConfigurationSection();
        return section;
    }

    @Override
    public File getFile() {
        return getParent().getFile();
    }

    @Override
    public void reload() {
        for (FeatureInterface feature : getParent().getFeatures()) {
            if (feature instanceof ItemGlowFeatures) {
                ItemGlowFeatures eF = (ItemGlowFeatures) feature;
                eF.setGlow(glow);
                eF.setDisableEnchantGlide(disableEnchantGlide);
                break;
            }
        }
    }

    public boolean canBeApplied(BlockData blockData) {
        return (SCore.isPaperOrFork() || SCore.is1v17Plus()) && blockData instanceof Furnace;
    }

    @Override
    public void openBackEditor(@NotNull Player player) {
        getParent().openEditor(player);
    }

    @Override
    public void openEditor(@NotNull Player player) {
        ItemGlowFeaturesEditorManager.getInstance().startEditing(player, this);
    }
}
