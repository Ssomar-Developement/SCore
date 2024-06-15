package com.ssomar.score.features.custom.patterns.group;

import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.patterns.subgroup.PatternFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class PatternsGroupFeature extends FeatureWithHisOwnEditor<PatternsGroupFeature, PatternsGroupFeature, PatternsGroupFeatureEditor, PatternsGroupFeatureEditorManager> implements FeaturesGroup<PatternFeature> {

    private Map<String, PatternFeature> patterns;

    public PatternsGroupFeature(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.patterns);
        reset();
    }

    @Override
    public void reset() {
        this.patterns = new HashMap<>();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> error = new ArrayList<>();
        if (config.isConfigurationSection(this.getName())) {
            ConfigurationSection enchantmentsSection = config.getConfigurationSection(this.getName());
            for (String attributeID : enchantmentsSection.getKeys(false)) {
                PatternFeature attribute = new PatternFeature(this, attributeID);
                List<String> subErrors = attribute.load(plugin, enchantmentsSection, isPremiumLoading);
                if (subErrors.size() > 0) {
                    error.addAll(subErrors);
                    continue;
                }
                patterns.put(attributeID, attribute);
            }
        }
        return error;
    }

    public void load(SPlugin plugin, ItemStack item, boolean isPremiumLoading) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof BannerMeta) {
                BannerMeta bmeta = (BannerMeta) meta;
                List<Pattern> patterns = bmeta.getPatterns();
                if (patterns.size() > 0) {
                    int i = 0;
                    for (Pattern pattern : patterns) {
                        PatternFeature patternFeature = new PatternFeature(this, "pattern" + i);
                        patternFeature.load(plugin, pattern, isPremiumLoading);
                        this.patterns.put("pattern" + i, patternFeature);
                        i++;
                    }
                }
            }
            if (meta instanceof BlockStateMeta) {
                BlockStateMeta bmeta = (BlockStateMeta) meta;
                Banner banner = (Banner) bmeta.getBlockState();
                List<Pattern> patterns = banner.getPatterns();
                if (patterns.size() > 0) {
                    int i = 0;
                    for (Pattern pattern : patterns) {
                        PatternFeature patternFeature = new PatternFeature(this, "pattern" + i);
                        patternFeature.load(plugin, pattern, isPremiumLoading);
                        this.patterns.put("pattern" + i, patternFeature);
                        i++;
                    }
                }
            }
        }

    }

    public List<Pattern> getMCPatterns() {
        List<Pattern> patterns = new ArrayList<>();
        SortedMap<String, PatternFeature> sortedAttributes = new TreeMap<>(this.patterns);
        for (PatternFeature pattern : sortedAttributes.values()) {
            patterns.add(pattern.getPattern());
        }
        return patterns;
    }

    @Override
    public void save(ConfigurationSection config) {
        config.set(this.getName(), null);
        ConfigurationSection attributesSection = config.createSection(this.getName());
        SortedMap<String, PatternFeature> sortedAttributes = new TreeMap<>(this.patterns);
        for (String enchantmentID : sortedAttributes.keySet()) {
            patterns.get(enchantmentID).save(attributesSection);
        }
    }

    @Override
    public PatternsGroupFeature getValue() {
        return this;
    }

    @Override
    public PatternsGroupFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7&oPattern(s) added: &e" + patterns.size();

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public PatternFeature getTheChildFeatureClickedParentEditor(String featureClicked) {
        for (PatternFeature x : patterns.values()) {
            if (x.isTheFeatureClickedParentEditor(featureClicked)) return x;
        }
        return null;
    }

    @Override
    public PatternsGroupFeature clone(FeatureParentInterface newParent) {
        PatternsGroupFeature eF = new PatternsGroupFeature(newParent);
        HashMap<String, PatternFeature> newPatterns = new HashMap<>();
        for (String x : patterns.keySet()) {
            newPatterns.put(x, patterns.get(x).clone(eF));
        }
        eF.setPatterns(newPatterns);
        return eF;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return new ArrayList<>(patterns.values());
    }

    @Override
    public String getParentInfo() {
        return getParent().getParentInfo();
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
            if (feature instanceof PatternsGroupFeature) {
                PatternsGroupFeature eF = (PatternsGroupFeature) feature;
                eF.setPatterns(patterns);
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
        PatternsGroupFeatureEditorManager.getInstance().startEditing(player, this);
    }

    @Override
    public void createNewFeature(@NotNull Player editor) {
        String baseId = "pattern";
        for (int i = 0; i < 1000; i++) {
            String id = baseId + i;
            if (!patterns.containsKey(id)) {
                PatternFeature eF = new PatternFeature(this, id);
                patterns.put(id, eF);
                eF.openEditor(editor);
                break;
            }
        }
    }

    @Override
    public void deleteFeature(@NotNull Player editor, PatternFeature feature) {
        patterns.remove(feature.getId());
    }

}
